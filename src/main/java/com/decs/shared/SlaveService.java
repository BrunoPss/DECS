package com.decs.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SlaveService extends Remote {
    boolean checkStatus() throws RemoteException;
    SystemInformation getSystemInformation() throws RemoteException;

    // Transfer Problem Files
    // FileType, JobFile(fileName, content)
    // <jobFiles>
    // .conf file
    // .params files
    boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles, String problemCode, String distribution) throws RemoteException;

    // Start Inference
    boolean startInference(String problemCode) throws RemoteException;

    // Stop Inference
    boolean stopInference() throws RemoteException;
}