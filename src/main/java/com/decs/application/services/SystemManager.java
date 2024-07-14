package com.decs.application.services;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

/**
 * <b>System Manager Class</b>
 * <p>
 *     This class handles tasks related with the system's execution environment
 *     (Java Virtual Machine)
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Service
public class SystemManager {
    //Internal Data
    private MemoryMXBean memoryMXBean;

    /**
     * Class Constructor
     */
    public SystemManager() {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Computes the amount of used memory between two readings
     * @param initial Initial memory usage value
     * @param finish Final memory usage value
     * @return Difference between the final and initial vaues
     */
    public static long computeMemoryUsage(long initial, long finish) { return finish - initial; }

    /**
     * @return Amount of committed heap memory in bytes
     */
    public long getHeapMemoryUsed() {
        return memoryMXBean.getHeapMemoryUsage().getCommitted();
    }

    /**
     * @return Amount of committed non-heap memory in bytes
     */
    public long getNonHeapMemoryUsed() {
        return memoryMXBean.getNonHeapMemoryUsage().getCommitted();
    }

    /**
     * Converts a value expressed in Bytes to MegaBytes
     * @param value Value in Bytes
     * @return Value in MegaBytes
     */
    public static long bytes2megaBytes(long value) {
        return value / (1024 * 1024);
    }

    /**
     * Converts a value expressed in Bytes to GigaBytes
     * @param value Value in Bytes
     * @return Value in GigaBytes
     */
    public static long bytes2gigaBytes(long value) {
        return value / (1024 * 1024 * 1024);
    }

    //Overrides


    //Internal Functions


}