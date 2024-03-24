package com.shared;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface SlaveService extends Remote {
    boolean checkStatus() throws RemoteException;

    // Transfer Problem Files
    // FileType, JobFile(fileName, content)
    // <jobFiles>
    // .conf file
    // .params files
    boolean setupProblemEnvironment(ArrayList<JobFile> jobFiles, String problemCode) throws RemoteException;

    // Start Inference
    boolean startInference(String problemCode) throws RemoteException;
}