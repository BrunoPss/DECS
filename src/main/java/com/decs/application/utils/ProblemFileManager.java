package com.decs.application.utils;

import com.decs.application.data.DistributionType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProblemFileManager {
    //Internal Data


    //Constructor


    //Get Methods


    //Set Methods


    //Methods
    public static ArrayList<HashMap<FileConfigAttr, String>> getProblemList(String path) {
        ArrayList<HashMap<FileConfigAttr, String>> problemList = new ArrayList<>();

        FilenameFilter confFilter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".conf");
            }
        };

        File f = new File(path);
        File[] folderList = f.listFiles();
        ArrayList<File> fileList = new ArrayList<>();

        for (File file : folderList) {
            if (file.isDirectory()) {
                fileList.add(file.listFiles(confFilter)[0]);
            }
        }

        for (File file : fileList) {
            HashMap<FileConfigAttr, String> h = extractMap(file);
            h.put(FileConfigAttr.PARAMS_FILE, file.getParent()+"/"+h.get(FileConfigAttr.CODE)+".params");
            problemList.add(h);
        }
        return problemList;
    }

    public static void writeConfFile(File path, HashMap<FileConfigAttr, String> problemInfo) {
        try {
            FileOutputStream f = new FileOutputStream(path);
            ObjectOutputStream ob = new ObjectOutputStream(f);
            ob.writeObject(problemInfo);
            f.close();
        } catch (IOException e) {
            System.err.println("IO Exception");
            e.printStackTrace();
        }
    }

    public static void createFile() {
        try {
            FileOutputStream f = new FileOutputStream("C:\\Projects\\DECS\\src\\main\\resources\\ECJ\\params\\problems\\user\\B11MFastDist\\B11MFastDist.conf");
            HashMap<FileConfigAttr, String> d = new HashMap<>();
            d.put(FileConfigAttr.CODE, "B11MFastDist");
            d.put(FileConfigAttr.FULL_NAME, "Boolean 11 Multiplexer Fast Distributed");
            d.put(FileConfigAttr.TYPE, "GP");
            d.put(FileConfigAttr.ORIGIN, "factory");
            d.put(FileConfigAttr.DISTRIBUTION, DistributionType.DIST_EVAL.toString());
            ObjectOutputStream ob = new ObjectOutputStream(f);
            ob.writeObject(d);
            f.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    //Overrides


    //Internal Functions
    private static HashMap<FileConfigAttr, String> extractMap(File confFile) {
        try {
            FileInputStream fileIn = new FileInputStream(confFile);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            HashMap<FileConfigAttr, String> obj = (HashMap<FileConfigAttr, String>) objIn.readObject();
            objIn.close();
            fileIn.close();
            return obj;
        } catch (IOException e) {
            System.err.println("Error when deserializing .conf file");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Class Not Found");
            e.printStackTrace();
        }
        return null;
    }
}