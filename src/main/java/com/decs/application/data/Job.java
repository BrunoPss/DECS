package com.decs.application.data;

import java.util.concurrent.atomic.AtomicInteger;

public class Job {
    //Internal Data
    private static AtomicInteger uniqueId = new AtomicInteger();
    private String name;
    private int id;

    //Constructor
    public Job() {}
    public Job(String name) {
        this.name = name;
        id = uniqueId.getAndIncrement();
    }

    //Get Methods
    public String getName() { return this.name; }
    public int getId() { return this.id; }

    //Set Methods
    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }

    //Methods


    //Overrides


    //Internal Functions

}