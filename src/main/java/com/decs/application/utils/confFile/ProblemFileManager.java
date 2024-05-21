package com.decs.application.utils.confFile;

import com.decs.application.data.distribution.DistributionType;
import org.springframework.security.core.parameters.P;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
            FileOutputStream f = new FileOutputStream("C:\\Projects\\DECS\\src\\main\\resources\\ECJ\\params\\problems\\factory\\Mult11Isla3\\Mult11Isla3.conf");
            HashMap<FileConfigAttr, String> d = new HashMap<>();
            d.put(FileConfigAttr.CODE, "Mult11Isla3");
            d.put(FileConfigAttr.FULL_NAME, "Multiplexer 11bit Problem 3 Islands");
            d.put(FileConfigAttr.TYPE, "GP");
            d.put(FileConfigAttr.ORIGIN, "factory");
            d.put(FileConfigAttr.DISTRIBUTION, DistributionType.ISLANDS.toString());
            d.put(FileConfigAttr.PARAMS_FILE, "Mult11Isla3.params");
            // Islands Problem
            d.put(FileConfigAttr.SERVER_ISLAND, "src\\main\\resources\\ECJ\\params\\problems\\factory\\Mult11Isla3\\island1.params");
            d.put(FileConfigAttr.ISLAND_LIST, "island1;island2;island3");
            ObjectOutputStream ob = new ObjectOutputStream(f);
            ob.writeObject(d);
            f.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static ArrayList<File> getFilesInFolder(File rootFolder) {
        return new ArrayList<>(Arrays.asList(rootFolder.listFiles()));
    }
    public static ArrayList<String> getFileNamesInFolder(File rootFolder) {
        return new ArrayList<>(Arrays.asList(rootFolder.list()));
    }

    public static void replaceFileContent(String filePath, String content) {
        try {
            File file = new File(filePath);
            PrintWriter pWriter = new PrintWriter(file);
            pWriter.write(content);
            pWriter.close();
        } catch (IOException e) {
            System.err.println("IO Exception while replacing file content");
            e.printStackTrace();
        }
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