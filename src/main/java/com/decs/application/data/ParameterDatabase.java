package com.decs.application.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParameterDatabase {
    //Internal Data
    private ArrayList<ParameterGroup> parameterList;

    //Constructor
    public ParameterDatabase() {
        this.parameterList = new ArrayList<>();
    }

    //Get Methods


    //Set Methods


    //Methods
    public ArrayList<String> getGroupNames() {
        ArrayList<String> groupNames = new ArrayList<>();
        for (ParameterGroup pg : parameterList) {
            groupNames.add(pg.getName());
        }
        return groupNames;
    }

    //Overrides


    //Internal Functions

}