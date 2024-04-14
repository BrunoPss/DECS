package com.decs.application.services;

import com.decs.application.data.DistributionType;
import com.decs.application.data.Island;
import com.shared.JobFile;
import com.shared.SlaveInfo;
import com.shared.SlaveService;
import com.vaadin.flow.data.provider.DataProvider;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public int getConnectedSlaves() {
        return slaveList.size();
    }
    public DataProvider<SlaveInfo, Void> getSlaveListDataProvider() { return slaveListDataProvider; }
    public ArrayList<SlaveInfo> fetchSlaveList() { return slaveList; }

    //Set Methods


    //Methods
    public void startSlaveListener() {
        // Slave Listener
        Thread slaveListener = new Thread(slaveListenerRun);
        slaveListener.start();

        // Slave Status Checker
        ScheduledExecutorService slaveStatusChecker = Executors.newSingleThreadScheduledExecutor();
        slaveStatusChecker.scheduleAtFixedRate(slaveStatusCheckerRun, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void initializeSlaves() {
        // Send Slave Files
        try {
            for (SlaveInfo slaveInfo : slaveList) {
                if (slaveInfo.getSlaveService() == null) {
                    this.locateSlave(slaveInfo);
                }
                ArrayList<JobFile> jobFileMap = buildProblemFileMap();

                slaveInfo.getSlaveService().setupProblemEnvironment(jobFileMap, objectListDatabase.getSelectedProblem().getCode(), objectListDatabase.getSelectedProblem().getDistribution().toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("RMI Remote Exception");
        }
    }

    public void startInference() {
        System.out.println("Start Inference");
        try {
            if (objectListDatabase.getSelectedProblem().getDistribution().equals(DistributionType.ISLANDS)) {
                ArrayList<String> islandList = objectListDatabase.getSelectedProblem().getIslandList();
                islandList.remove(objectListDatabase.getSelectedProblem().getParamsFile().getName().replace(".params", ""));
                System.out.println(islandList);
                for (int i = 0; i < slaveList.size() && i < islandList.size(); i++) {
                    slaveList.get(i).getSlaveService().startInference(islandList.get(i));
                }
            }
            else if (objectListDatabase.getSelectedProblem().getDistribution().equals(DistributionType.DIST_EVAL)) {
                for (SlaveInfo slave : slaveList) {
                    slave.getSlaveService().startInference(objectListDatabase.getSelectedProblem().getCode());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Inference RMI Remote Exception");
        }
    }

    public void stopInference() {
        System.out.println("STOP INFERENCE");
        try {
            for (SlaveInfo slaveInfo : slaveList) {
                slaveInfo.getSlaveService().stopInference();
            }
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
            e.printStackTrace();
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
            System.out.println("ADRESS: " + slaveInfo.getAddress());
            System.out.println("PORT: " + slaveInfo.getPort());
            Registry registry = LocateRegistry.getRegistry(slaveInfo.getAddress(), slaveInfo.getPort());
            System.out.println(registry);
            System.out.println("ID: " + slaveInfo.getId());
            slaveInfo.setSlaveService((SlaveService) registry.lookup(slaveInfo.getId()));
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Not Bound Exception");
            e.printStackTrace();
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

                    locateSlave(slaveInfo);

                    slaveList.add(slaveInfo);

                    System.out.println("New Slave!");
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
            } finally {
                socket.close();
            }
        }
    };

    private Runnable slaveStatusCheckerRun = new Runnable() {
        @Override
        public void run() {
            //System.out.println(slaveList.size());
            for (int i=0; i<slaveList.size(); i++) {
                try {
                    slaveList.get(i).getSlaveService().checkStatus();
                } catch (RemoteException e) {
                    System.err.println("Remote Exception");
                    slaveList.remove(i);
                }
            }
        }
    };

    private DataProvider<SlaveInfo, Void> slaveListDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchSlaveList().stream();
                    },
                    query -> getConnectedSlaves()
            );

}