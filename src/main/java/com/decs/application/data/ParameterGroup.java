package com.decs.application.data;

import java.util.ArrayList;

public class ParameterGroup {
    //Internal Data
    private ParameterGroupType type;
    private ArrayList<Parameter> parameters;

    //Constructor


    //Get Methods
    public ParameterGroupType getType() { return this.type; }
    public String getName() { return this.type.toString(); }

    //Set Methods
    public void setName(ParameterGroupType type) { this.type = type; }

    //Methods


    //Overrides


    //Internal Functions

}