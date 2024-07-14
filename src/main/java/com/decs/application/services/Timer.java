package com.decs.application.services;

import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * <b>Timer Class</b>
 * <p>
 *     This class handles time measurements in the system's execution environment
 *     (Java Virtual Machine)
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Service
public class Timer {
    //Internal Data
    private ThreadMXBean threadMXBean;

    /**
     * Class Constructor
     */
    public Timer() {
        this.threadMXBean = ManagementFactory.getThreadMXBean();
    }

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Computes the elapsed wall-clock time between two instants
     * @param initial Initial instant
     * @param finish Final instant
     * @return Elapsed wall-clock time between the final and initial instants
     */
    public static long computeTime(long initial, long finish) { return finish - initial; }

    /**
     * @return Wall-clock timestamp in nanoseconds
     */
    public static long getWallClockTimestamp() {
        return System.nanoTime();
    }

    /**
     * @param threadMXBean Management interface object for the thread system of the JVM
     * @return CPU timestamp in nanoseconds
     */
    // CPU Time
    public static long getCPUTimestamp(ThreadMXBean threadMXBean) { return threadMXBean.getCurrentThreadCpuTime(); }

    /**
     * Converts a value expressed in nanoseconds to milliseconds
     * @param value Value in nanoseconds
     * @return Value in milliseconds
     */
    public static long nano2milis(long value) {
        //System.out.println(value);
        return TimeUnit.NANOSECONDS.toMillis(value);
    }

    /**
     * Converts a value expressed in nanoseconds to seconds
     * @param value Value in nanoseconds
     * @return Value in seconds
     */
    public static long nano2seconds(long value) {
        return TimeUnit.NANOSECONDS.toSeconds(value);
    }

    //Overrides


    //Internal Functions


}