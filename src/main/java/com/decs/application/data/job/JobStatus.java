package com.decs.application.data.job;

/**
 * <b>Job Status Enumeration</b>
 * <p>
 *     Enumeration of the possible Job status
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public enum JobStatus {
    /**
     * Job created and waiting to be processed
     */
    QUEUED,
    /**
     * Job is being processed
     */
    RUNNING,
    /**
     * Job has been processed and the solutions can be accessed
     */
    FINISHED,
    /**
     * Job has encountered a critical error during execution and was aborted
     */
    ERROR;

    @Override
    public String toString() {
        return switch (this) {
            case QUEUED -> "Queued";
            case RUNNING -> "Running";
            case FINISHED -> "Finished";
            case ERROR -> "Error";
        };
    }

    /**
     * Selects the correct Vaadin badge type for each status
     * @return Vaadin badge type
     */
    public String getBadgeType() {
        return switch (this) {
            case QUEUED -> "";
            case RUNNING -> "success";
            case FINISHED -> "contrast";
            case ERROR -> "error";
        };
    }
}