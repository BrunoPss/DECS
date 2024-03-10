package com.decs.application.data;

public class Parameter {
    //Internal Data
    private String name;
    private String value;
    private String restriction;
    private String description;

    //Constructor
    public Parameter() {}
    public Parameter(String name, String value, String restriction, String description) {
        this.name = name;
        this.value = value;
        this.restriction = restriction;
        this.description = description;
    }

    //Get Methods
    public String getName() { return this.name; }
    public String getValue() { return value; }
    public String getRestriction() { return this.restriction; }
    public String getDescription() { return this.description; }

    //Set Methods
    public void setName(String name) { this.name = name; }
    public void setValue(String value) { this.value = value; }
    public void setRestriction(String restriction) { this.restriction = restriction; }
    public void setDescription(String description) { this.description = description; }

    //Methods


    //Overrides


    //Internal Functions

}