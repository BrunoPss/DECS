package com.decs.application.data;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Job {
    //Internal Data
    private static AtomicInteger uniqueId = new AtomicInteger();
    private String name;
    private int id;
    private JobStatus status;
    private File logFile;
    private File statsFile;

    //Constructor
    public Job() {}
    public Job(String name) {
        this.name = name;
        this.id = uniqueId.getAndIncrement();
        this.status = JobStatus.QUEUED;
    }

    //Get Methods
    public String getName() { return this.name; }
    public int getId() { return this.id; }
    public JobStatus getStatus() { return this.status; }
    public File getLogFile() { return this.logFile; }
    public File getStatsFile() { return this.statsFile; }

    //Set Methods
    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    public void setStatus(JobStatus status) { this.status = status; }
    public void setLogFile(File logFile) { this.logFile = logFile; }
    public void setStatsFile(File statsFile) { this.statsFile = statsFile; }

    //Methods
    public String getJobLog() {
        try {
            Scanner scanner = new Scanner(this.logFile);
            scanner.useDelimiter("\\Z");
            return processLogText(scanner.next());
        } catch (IOException e) { e.printStackTrace(); }
        return "";
    }
    public String getStatisticsLog() {
        try {
            Scanner scanner = new Scanner(this.statsFile);
            scanner.useDelimiter("\\Z");
            return scanner.next();
        } catch (IOException e) { e.printStackTrace(); }
        return "";
    }

    //Overrides


    //Internal Functions
    private String processLogText(String text) {
        return text
                .replaceAll("\\|.*?\\n", "");
    }
}