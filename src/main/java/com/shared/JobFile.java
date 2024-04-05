package com.shared;

import java.io.Serializable;

public class JobFile implements Serializable {
    //Internal Data
    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private byte[] content;

    //Constructor
    public JobFile(String name, String type, byte[] content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    //Get Methods
    public String getName() { return this.name; }
    public String getType() { return this.type; }
    public byte[] getFileBytes() { return this.content; }

    //Set Methods


    //Methods


    //Overrides


    //Internal Functions

}