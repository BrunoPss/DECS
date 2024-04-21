package com.decs.application.services;

import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

@Service
public class Timer {
    //Internal Data
    private ThreadMXBean threadMXBean;

    //Constructor
    public Timer() {
        this.threadMXBean = ManagementFactory.getThreadMXBean();
    }

    //Get Methods


    //Set Methods


    //Methods
    // Wall-Clock Time
    public static long computeTime(long initial, long finish) { return finish - initial; }
    public static long getWallClockTimestamp() {
        return System.nanoTime();
    }
    // CPU Time
    public static long getCPUTimestamp(ThreadMXBean threadMXBean) { return threadMXBean.getCurrentThreadCpuTime(); }

    public static long nano2milis(long value) {
        System.out.println(value);
        return TimeUnit.NANOSECONDS.toMillis(value);
    }
    public static long nano2seconds(long value) {
        return TimeUnit.NANOSECONDS.toSeconds(value);
    }

    //Overrides


    //Internal Functions


}