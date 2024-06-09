package com.decs.shared;

import java.io.Serializable;

public class SystemInformation implements Serializable {
    //Internal Data
   private static final long serialVersionUID = 1L;
    private String username;
    private String OSName;
    private String OSVersion;
    private String OSArchitecture;
    private int CPUCoreNumber;


    //Constructor
    public SystemInformation(String username, String OSName, String OSVersion, String OSArchitecture, int CPUCoreNumber) {
        this.username = username;
        this.OSName = OSName;
        this.OSVersion = OSVersion;
        this.OSArchitecture = OSArchitecture;
        this.CPUCoreNumber = CPUCoreNumber;
    }

    //Get Methods
    public String getUsername() { return username; }
    public String getOSName() { return OSName; }
    public String getOSVersion() { return OSVersion; }
    public String getOSArchitecture() { return OSArchitecture; }
    public int getCPUCoreNumber() { return CPUCoreNumber; }


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions

}