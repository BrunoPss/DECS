package com.decs.application.services;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@Service
public class SystemManager {
    //Internal Data
    private MemoryMXBean memoryMXBean;

    //Constructor
    public SystemManager() {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    //Get Methods


    //Set Methods


    //Methods
    public static long computeMemoryUsage(long initial, long finish) { return finish - initial; }
    public long getHeapMemoryUsed() {
        return memoryMXBean.getHeapMemoryUsage().getCommitted();
    }

    public long getNonHeapMemoryUsed() {
        return memoryMXBean.getNonHeapMemoryUsage().getCommitted();
    }

    public static long bytes2megaBytes(long value) {
        return value / (1024 * 1024);
    }
    public static long bytes2gigaBytes(long value) {
        return value / (1024 * 1024 * 1024);
    }

    //Overrides


    //Internal Functions


}