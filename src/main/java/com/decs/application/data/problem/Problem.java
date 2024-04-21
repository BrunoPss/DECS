package com.decs.application.data.problem;

import com.decs.application.data.distribution.DistributionType;

import java.io.File;
import java.util.ArrayList;

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

    //Constructor
    public Problem() {}
    public Problem(File paramsFile) {
        this.paramsFile = paramsFile;
        this.fullName = paramsFile.getName();
    }
    public Problem(File paramsFile, String code, String fullName, String type, String origin, DistributionType distribution, File rootFolder) {
        System.out.println("PARAMS FLE : " + paramsFile);
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