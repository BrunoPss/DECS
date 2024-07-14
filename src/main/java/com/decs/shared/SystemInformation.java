package com.decs.shared;

import java.io.Serializable;

/**
 * <b>System Information Class</b>
 * <p>
 *     This class implements the extraction of system information.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class SystemInformation implements Serializable {
    //Internal Data
   private static final long serialVersionUID = 1L;
    private String username;
    private String OSName;
    private String OSVersion;
    private String OSArchitecture;
    private int CPUCoreNumber;

    /**
     * Class Constructor
     * @param username System username
     * @param OSName Operating system name
     * @param OSVersion Operating system version
     * @param OSArchitecture Operating system architecture
     * @param CPUCoreNumber Number of CPU cores
     */
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