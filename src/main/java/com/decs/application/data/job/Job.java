package com.decs.application.data.job;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.generation.Generation;
import com.decs.application.utils.csvFile.CSVGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <b>Job Class</b>
 * <p>
 *     This class represents a DECS Job.
 *     The complete process of computing a Problem in DECS is managed by the respective Job object.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 *
 */
public class Job {
    //Internal Data
    private static AtomicInteger uniqueId = new AtomicInteger();
    private String name;
    private int id;
    private JobStatus status;
    private File logFile;
    private File statsFile;
    private DistributionType distribution;
    private ArrayList<Generation> generationList;
    /**
     * Wall-Clock Time in nanoseconds
     */
    private long wallClockTime;
    /**
     * CPU Time in nanoseconds
     */
    private long cpuTime;
    //private long heapMemoryUsage; // Heap memory used in bytes
    //private long nonHeapMemoryUsage; // Non heap memory used in bytes

    /**
     * Job Class default constructor
     */
    public Job() {}
    /**
     * Job Class Constructor
     * @param name Job Name
     * @param distribution Distribution Type
     */
    public Job(String name, DistributionType distribution) {
        this.name = name;
        this.id = uniqueId.getAndIncrement();
        this.status = JobStatus.QUEUED;
        this.distribution = distribution;
        this.generationList = new ArrayList<>();
    }

    //Get Methods
    public String getName() { return this.name; }
    public int getId() { return this.id; }
    public JobStatus getStatus() { return this.status; }
    public File getLogFile() { return this.logFile; }
    public File getStatsFile() { return this.statsFile; }
    public DistributionType getDistribution() { return this.distribution; }
    public long getWallClockTime() { return this.wallClockTime; }
    public long getCpuTime() { return this.cpuTime; }
    //public long getHeapMemoryUsage() { return heapMemoryUsage; }
    //public long getNonHeapMemoryUsage() { return nonHeapMemoryUsage; }

    //Set Methods
    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    public void setStatus(JobStatus status) { this.status = status; }
    public void setLogFile(File logFile) { this.logFile = logFile; }
    public void setStatsFile(File statsFile) { this.statsFile = statsFile; }
    public void setWallClockTime(long elapsedTime) { this.wallClockTime = elapsedTime; }
    public void setCpuTime(long cpuTime) { this.cpuTime = cpuTime; }
    //public void setHeapMemoryUsage(long heapMemoryUsage) { this.heapMemoryUsage = heapMemoryUsage; }
    //public void setNonHeapMemoryUsage(long nonHeapMemoryUsage) { this.nonHeapMemoryUsage = nonHeapMemoryUsage; }

    //Methods

    /**
     * Retrieves the Job log file content
     * @return textual content of the Job Log file
     */
    public String getJobLog() {
        try {
            Scanner scanner = new Scanner(this.logFile);
            scanner.useDelimiter("\\Z");
            return processLogText(scanner.next());
        } catch (IOException e) { e.printStackTrace(); }
        return "";
    }

    /**
     * Retrieves the statistics log file content
     * @return textual content of te stats log file
     */
    public String getStatisticsLog() {
        try {
            Scanner scanner = new Scanner(this.statsFile);
            scanner.useDelimiter("\\Z");
            return scanner.next();
        } catch (IOException e) { e.printStackTrace(); }
        return "";
    }

    /**
     * Includes a new Generation object in the generation list
     * @param generation generation to include
     */
    public void addGeneration(Generation generation) {
        generationList.add(generation);
    }

    /**
     * Generates a CSV file with the information of each Generation object in the generation list
     */
    public void writeGenerationTableFile() {
        CSVGenerator.generateCSVFile(generationList, "job" + id + "_" + name + "_table.csv");
    }

    //Overrides


    //Internal Functions

    /**
     * Removes the first part of ECJ's log output.
     * This is done by replacing all text contained between a '|' and '\n' characters.
     * @param text raw textual input
     * @return the processed text
     */
    private String processLogText(String text) {
        return text.replaceAll("\\|.*?\\n", "");
    }
}