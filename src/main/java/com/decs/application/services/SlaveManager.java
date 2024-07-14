package com.decs.application.services;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.views.notifications.ErrorNotification;
import com.decs.shared.JobFile;
import com.decs.shared.SlaveInfo;
import com.decs.shared.SlaveService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <b>Slave Manager Class</b>
 * <p>
 *     This class handles the connected DECS-Slave instances.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Service
public class SlaveManager {
    // Constants
    /**
     * Network Port for the DECS-Slave registration
     */
    private static final int PORT = 46000;
    private ByteArrayInputStream byteIn;
    private ObjectInputStream objIn;
    private byte[] buffer;
    private volatile ArrayList<SlaveInfo> slaveList;
    private ObjectListDatabase objectListDatabase;

    /**
     * Class Constructor
     * @param objectListDatabase Class containing global relevant objects
     */
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
    /**
     * Initiates the Slave Listener service.
     * This service is responsible for handling the DECS-Slave registration process.
     * It also processes the heartbeat system which checks the liveliness of all connected slaves.
     */
    public void startSlaveListener() {
        try {
            // Slave Listener
            Thread slaveListener = new Thread(slaveListenerRun);
            slaveListener.start();

            // Slave Status Checker
            ScheduledExecutorService slaveStatusChecker = Executors.newSingleThreadScheduledExecutor();
            slaveStatusChecker.scheduleAtFixedRate(slaveStatusCheckerRun, 0, 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.err.println("Exception at startSlaveListener");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error!");
        }
    }

    /**
     * Initializes all connected slaves and sets up their individual environment with the required
     * components. Relevant parameter files are sent to each slave for latter evolutionary execution.
     */
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
            System.out.println("RMI Remote Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at initializeSlaves");
            e.printStackTrace();
            ErrorNotification.show("Error!");
        }
    }

    /**
     * Initiates the inference process of all connected slaves
     */
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
            System.out.println("Inference RMI Remote Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at startInference Slave manager");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error!");
        }
    }

    /**
     * Stops any ongoing inference process on all connected slaves.
     * <p>(Not implemented yet)</p>
     */
    public void stopInference() {
        System.out.println("STOP INFERENCE");
        try {
            for (SlaveInfo slaveInfo : slaveList) {
                slaveInfo.getSlaveService().stopInference();
            }
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at stopInference");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error!");
        }
    }

    //Overrides


    //Internal Functions
    /**
     * Builds a problem files map
     * @return list of all relevant parameter files for a specified Problem
     */
    private ArrayList<JobFile> buildProblemFileMap() {
        ArrayList<JobFile> jobFiles = new ArrayList<>();
        File[] filesList = objectListDatabase.getSelectedProblem().getRootFolder().listFiles();

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
        } catch (Exception e) {
            System.err.println("Exception at buildProblemFileMap");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error!");
        }

        return null;
    }

    /**
     * Locates and retrieves the remote interface of a registered slave.
     * After a slave is registered in the system, it is necessary to retrieve its remote interface
     * to latter invoke methods.
     * @param slaveInfo Object which represents the registered Slave
     */
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
        } catch (Exception e) {
            System.err.println("Exception at locateSlave");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error!");
        }
    }

    /**
     * Runnable for the Slave Listener service.
     * Processes incoming slave registering requests and creates the SlaveInfo object which
     * represents them.
     */
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

                    // Show notification
                    //System.out.println(objectListDatabase.getMainLayout());
                    showNotification(objectListDatabase.getMainLayout().getUI().orElseThrow(), "New Slave connected!");
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
            } catch (Exception e) {
                System.err.println("Exception at slaveListenerRun");
                e.printStackTrace();
                ErrorNotification.showErrorNotification("Error!");
            }
        }
    };

    /**
     * Runnable for the heartbeat system functioning.
     * This system calls a remote method on all registered slaves and checks their response.
     * If no response or error is received, the slave is removed from the slaves list.
     */
    private Runnable slaveStatusCheckerRun = new Runnable() {
        @Override
        public void run() {
            //System.out.println(slaveList.size());
            for (int i=0; i<slaveList.size(); i++) {
                try {
                    slaveList.get(i).getSlaveService().checkStatus();
                } catch (RemoteException e) {
                    System.err.println("Remote Exception");
                    System.err.println("Dead or Blocked slave");
                    slaveList.remove(i);
                } catch (Exception e) {
                    System.err.println("Exception at slaveStatusCheckerRun");
                    e.printStackTrace();
                    ErrorNotification.showErrorNotification("Error!");
                }
            }
        }
    };

    /**
     * Vaadin data provider for the automatic updates of the dashboard slave list grid visual component
     */
    private DataProvider<SlaveInfo, Void> slaveListDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchSlaveList().stream();
                    },
                    query -> getConnectedSlaves()
            );

    /**
     * Shows a notification with the specified content
     * @param ui UI object where the notification must be displayed
     * @param content Textual content of the notification
     */
    private void showNotification(UI ui, String content) {
        ui.access(() -> {
            Notification notification = Notification.show(content);
            notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        });
    }
}