package com.decs.application.utils;

import java.util.concurrent.TimeUnit;

public class Timer {
    //Internal Data


    //Constructor
    private Timer() {}

    //Get Methods


    //Set Methods


    //Methods
    public static long getTimestamp() {
        return System.nanoTime();
    }

    public static long getElapsedTime(long start, long finish) {
        return finish - start;
    }

    public static long nano2milis(long value) {
        return TimeUnit.NANOSECONDS.toMillis(value);
    }
    public static long nano2seconds(long value) {
        return TimeUnit.NANOSECONDS.toSeconds(value);
    }

    //Overrides


    //Internal Functions


}