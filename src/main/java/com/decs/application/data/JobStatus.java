package com.decs.application.data;

public enum JobStatus {
    QUEUED,
    RUNNING,
    FINISHED,
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

    public String getBadgeType() {
        return switch (this) {
            case QUEUED -> "";
            case RUNNING -> "success";
            case FINISHED -> "contrast";
            case ERROR -> "error";
        };
    }
}