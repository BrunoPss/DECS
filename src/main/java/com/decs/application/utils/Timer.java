package com.decs.application.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public class Timer {
    //Internal Data


    //Constructor
    private Timer() {}

    //Get Methods


    //Set Methods


    //Methods
    // Wall-Clock Time
    public static long computeTime(long initial, long finish) { return finish - initial; }
    public static long getWallClockTimestamp() {
        return System.nanoTime();
    }
    // CPU Time
    public static long getCPUTimestamp(ThreadMXBean mxBean) { return mxBean.getCurrentThreadCpuTime(); }

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