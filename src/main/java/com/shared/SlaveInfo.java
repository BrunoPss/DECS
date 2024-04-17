package com.shared;

import java.io.Serializable;

public class SlaveInfo implements Serializable {
    //Internal Data
    private static final long serialVersionUID = 1L;
    private String id;
    private String address;
    private int port;
    private SlaveService slaveService;

    //Constructor
    public SlaveInfo(String id, String address, int port) {
        this.id = id;
        this.address = address;
        this.port = port;
    }

    //Get Methods
    public String getId() { return this.id; }
    public String getAddress() { return address; }
    public int getPort() { return port; }
    public SlaveService getSlaveService() { return this.slaveService; }

    //Set Methods
    public void setSlaveService(SlaveService slaveService) { this.slaveService = slaveService; }

    //Methods


    //Overrides


    //Internal Functions

}