package com.decs.application.data.problem;

import com.decs.application.data.distribution.DistributionType;

import java.io.File;
import java.util.ArrayList;

/**
 * <b>Problem Class</b>
 * <p>
 *     This class represents an evolutionary problem.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class Problem {
    //Internal Data
    private File paramsFile;
    private File rootFolder;
    private String code;
    private String fullName;
    private String type;
    private String origin;
    private DistributionType distribution;
    private ArrayList<String> islandList;

    /**
     * Problem Class default constructor
     */
    public Problem() {}

    /**
     * Problem Class simple constructor
     * @param paramsFile Main parameters file
     */
    public Problem(File paramsFile) {
        this.paramsFile = paramsFile;
        this.fullName = paramsFile.getName();
    }

    /**
     * Problem Class extensive constructor
     * @param paramsFile Main parameters file
     * @param code Short textual identification code
     * @param fullName Full name
     * @param type Evolutionary type
     * @param origin Creation origin
     * @param distribution Type of distribution
     * @param rootFolder Problem main folder
     */
    public Problem(File paramsFile, String code, String fullName, String type, String origin, DistributionType distribution, File rootFolder) {
        //System.out.println("PARAMS FLE : " + paramsFile);
        this.paramsFile = paramsFile;
        this.code = code;
        this.fullName = fullName;
        this.type = type;
        this.origin = origin;
        this.distribution = distribution;
        this.rootFolder = rootFolder;
    }


    //Get Methods
    public File getParamsFile() { return paramsFile; }
    public String getCode() { return code; }
    public String getFullName() { return fullName; }
    public String getType() { return type; }
    public String getOrigin() { return origin; }
    public DistributionType getDistribution() { return distribution; }
    public File getRootFolder() { return rootFolder; }
    public ArrayList<String> getIslandList() { return islandList; }

    //Set Methods
    public void setCode(String code) { this.code = code; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setType(String type) { this.type = type; }
    public void setIslandList(ArrayList<String> islandList) { this.islandList = islandList; }

    //Overrides


    //Internal Functions

}