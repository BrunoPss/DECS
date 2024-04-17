package com.decs.application.data.distribution;

import java.util.ArrayList;

public class Island {
    //Internal Data
    private String id;
    private int migrationNumber;
    private ArrayList<Island> migrationDestination;
    private int migrationSize;
    private int migrationStart;
    private int migrationOffset;
    private int mailboxSize;
    private String seed;

    //Constructor
    public Island(String id) {
        this.id = id;
    }

    //Get Methods
    public String getId() { return this.id; }
    public int getMigrationNumber() { return migrationNumber; }
    public ArrayList<Island> getMigrationDestination() { return migrationDestination; }
    public int getMigrationSize() { return migrationSize; }
    public int getMigrationStart() { return migrationStart; }
    public int getMigrationOffset() { return migrationOffset; }
    public int getMailboxSize() { return mailboxSize; }
    public String getSeed() { return this.seed; }

    //Set Methods
    public void setId(String id) { this.id = id; }
    public void setMigrationNumber(int migrationNumber) { this.migrationNumber = migrationNumber; }
    public void setMigrationDestination(ArrayList<Island> migrationDestination) { this.migrationDestination = migrationDestination; }
    public void setMigrationSize(int migrationSize) { this.migrationSize = migrationSize; }
    public void setMigrationStart(int migrationStart) { this.migrationStart = migrationStart; }
    public void setMigrationOffset(int migrationOffset) { this.migrationOffset = migrationOffset; }
    public void setMailboxSize(int mailboxSize) { this.mailboxSize = mailboxSize; }
    public void setSeed(String seed) { this.seed = seed; }

    //Methods


    //Overrides


    //Internal Functions


}