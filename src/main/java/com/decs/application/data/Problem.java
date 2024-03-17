package com.decs.application.data;

import java.io.File;
import java.util.ArrayList;

public class Problem {
    //Internal Data
    private File paramsFile;
    private String code;
    private String fullName;
    private String type;
    private ParameterDatabase parameterDatabase;

    //Constructor
    public Problem() {}
    public Problem(File paramsFile) {
        this.paramsFile = paramsFile;
        this.fullName = paramsFile.getName();
    }
    public Problem(File paramsFile, String code, String fullName, String type) {
        this.paramsFile = paramsFile;
        this.code = code;
        this.fullName = fullName;
        this.type = type;
        this.parameterDatabase = new ParameterDatabase();
    }


    //Get Methods
    public File getParamsFile() { return paramsFile; }
    public String getCode() { return code; }
    public String getFullName() { return fullName; }
    public String getType() { return type; }

    //Set Methods
    public void setCode(String code) { this.code = code; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setType(String type) { this.type = type; }

    //Overrides


    //Internal Functions

}