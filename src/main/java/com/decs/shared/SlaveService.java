package com.decs.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * <b>Slave Service Interface</b>
 * <p>
 *     This interface references the remote methods that are required to be implemented on each
 *     DECS-Slave instance.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public interface SlaveService extends Remote {
    /**
     * Checks the status of a connected DECS-Slave instance
     * @return Liveliness of the node
     * @throws RemoteException If the slave instance cannot be reached or any communication error
     * is raised.
     */
    boolean checkStatus() throws RemoteException;

    /**
     * Retrieves the system information of the slave instance
     * @return System information object
     * @throws RemoteException If the slave instance cannot be reached or any communication error
     * is raised.
     */
    SystemInformation getSystemInformation() throws RemoteException;

    /**
     * Sets up the execution environment in the Slave instance
     * @param jobFiles List of the files to be transfered
     * @param problemCode Problem short code
     * @param distribution Problem distribution type
     * @return Status of the operation
     * @throws RemoteException If the slave instance cannot be reached or any communication error
     * is raised.
     */
    boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles, String problemCode, String distribution) throws RemoteException;

    /**
     * Starts the inference process on each connected slave
     * @param problemCode Problem short code
     * @return Status of the operation
     * @throws RemoteException If the slave instance cannot be reached or any communication error
     * is raised.
     */
    boolean startInference(String problemCode) throws RemoteException;

    /**
     * Stops the inference process on each connected slave
     * @return Status of the operation
     * @throws RemoteException If the slave instance cannot be reached or any communication error
     * is raised.
     */
    boolean stopInference() throws RemoteException;
}