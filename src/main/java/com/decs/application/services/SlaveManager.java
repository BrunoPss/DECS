package com.decs.application.services;

import com.shared.JobFile;
import com.shared.SlaveInfo;
import com.shared.SlaveService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SlaveManager {
    // Constants
    private static final int PORT = 46000;

    //Internal Data
    private DatagramSocket socket;
    private DatagramPacket packet;
    private ByteArrayInputStream byteIn;
    private ObjectInputStream objIn;
    private byte[] buffer;
    private volatile ArrayList<SlaveInfo> slaveList;
    private ObjectListDatabase objectListDatabase;

    //Constructor
    public SlaveManager(ObjectListDatabase objectListDatabase) {
        this.slaveList = new ArrayList<>();
        this.objectListDatabase = objectListDatabase;
    }

    //Get Methods


    //Set Methods


    //Methods
    public void startSlaveListener() {
        Thread slaveListener = new Thread(slaveListenerRun);
        slaveListener.start();
    }

    public void initializeSlaves() {
        // Send Slave Files
        try {
            for (SlaveInfo slaveInfo : slaveList) {
                this.locateSlave(slaveInfo);
                ArrayList<JobFile> jobFileMap = buildProblemFileMap();

                slaveInfo.getSlaveService().setupProblemEnvironment(jobFileMap, objectListDatabase.getSelectedProblem().getCode());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("RMI Remote Exception");
        }
    }

    public void startInference() {
        System.out.println("Start Inference");
        try {
            for (SlaveInfo slaveInfo : slaveList) {
                slaveInfo.getSlaveService().startInference(objectListDatabase.getSelectedProblem().getCode());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Inference RMI Remote Exception");
        }
    }

    //Overrides


    //Internal Functions
    private ArrayList<JobFile> buildProblemFileMap() {
        ArrayList<JobFile> jobFiles = new ArrayList<>();
        File[] filesList = objectListDatabase.getSelectedProblem().getRootFile().listFiles();

        FileInputStream fIn;
        try {
            for (File file : filesList) {
                fIn = new FileInputStream(file);
                byte[] fileBytes = new byte[(int) file.length()];
                fIn.read(fileBytes);

                if (file.getName().endsWith(".conf")) {
                    jobFiles.add(new JobFile(file.getName(), "CONF", fileBytes));
                }
                else if (file.getName().endsWith(".params")) {
                    jobFiles.add(new JobFile(file.getName(), "PARAMS", fileBytes));
                }

                fIn.close();
            }
            return jobFiles;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found exception");
        }

        return null;
    }
    private void locateSlave(SlaveInfo slaveInfo) {
        System.out.println("LOCATE SLAVE");
        try {
            Registry registry = LocateRegistry.getRegistry(slaveInfo.getAddress(), slaveInfo.getPort());
            slaveInfo.setSlaveService((SlaveService) registry.lookup(slaveInfo.getId()));
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Remote Exception");
        } catch (NotBoundException e) {
            e.printStackTrace();
            System.out.println("Not Bound Exception");
        }
    }
    private Runnable slaveListenerRun = new Runnable() {
        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(PORT);

                while (true) {
                    buffer = new byte[1050];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    byteIn = new ByteArrayInputStream(buffer);
                    objIn = new ObjectInputStream(byteIn);

                    SlaveInfo slaveInfo = (SlaveInfo) objIn.readObject();

                    slaveList.add(slaveInfo);

                    System.out.println(slaveList.size());
                    System.out.println(this);
                }
            } catch (SocketException e) {
                e.printStackTrace();
                System.out.println("Socket Exception");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Exception");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Class not found exception");
            }
        }
    };
}