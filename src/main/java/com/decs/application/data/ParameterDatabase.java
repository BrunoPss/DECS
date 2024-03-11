package com.decs.application.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParameterDatabase {
    //Internal Data
    private ArrayList<ParameterGroup> parameterGroupList;

    //Constructor
    public ParameterDatabase() {
        this.parameterGroupList = new ArrayList<>();
    }

    //Get Methods


    //Set Methods


    //Methods
    public ArrayList<String> getGroupNames() {
        ArrayList<String> groupNames = new ArrayList<>();
        for (ParameterGroup pg : parameterGroupList) {
            groupNames.add(pg.getName());
        }
        return groupNames;
    }

    //Overrides


    //Internal Functions

}