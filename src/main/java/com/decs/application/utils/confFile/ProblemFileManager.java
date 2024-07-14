package com.decs.application.utils.confFile;

import com.decs.application.data.distribution.DistributionType;
import org.springframework.security.core.parameters.P;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * <b>Problem File Manager Class</b>
 * <p>
 *     This class manages the files related with each available problem.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class ProblemFileManager {
    //Internal Data


    /**
     * Default Class Constructor
     */
    public ProblemFileManager() {}

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Constructs and retrieves all available problems.
     * This method searches for problem configuration files (.conf).
     * @param path Path of the root problem folder
     * @return List of all available problems
     */
    public static ArrayList<HashMap<FileConfigAttr, String>> getProblemList(String path) {
        ArrayList<HashMap<FileConfigAttr, String>> problemList = new ArrayList<>();
        try {
            FilenameFilter confFilter = (f, name) -> name.endsWith(".conf");

            File f = new File(path);
            File[] folderList = f.listFiles();
            ArrayList<File> fileList = new ArrayList<>();

            if (folderList != null) {
                for (File file : folderList) {
                    if (file.isDirectory()) {
                        fileList.add(Objects.requireNonNull(file.listFiles(confFilter))[0]);
                    }
                }
            }

            for (File file : fileList) {
                HashMap<FileConfigAttr, String> h = extractMap(file);
                if (h != null) {
                    h.put(FileConfigAttr.PARAMS_FILE, file.getParent() + "/" + h.get(FileConfigAttr.CODE) + ".params");
                    problemList.add(h);
                }
            }
            return problemList;
        } catch (Exception e) {
            System.out.println("Exception in getProblemList");
            e.printStackTrace();
        }
        return problemList;
    }

    /**
     * Writes problem configuration file (.conf) with the specified information
     * @param path Destination path for the file
     * @param problemInfo Hashmap with the file contents
     */
    public static void writeConfFile(File path, HashMap<FileConfigAttr, String> problemInfo) {
        try {
            FileOutputStream f = new FileOutputStream(path);
            ObjectOutputStream ob = new ObjectOutputStream(f);
            ob.writeObject(problemInfo);
            f.close();
        } catch (IOException e) {
            System.err.println("IO Exception in writeConfFile");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception in writeConfFile");
            e.printStackTrace();
        }
    }

    /**
     * Utility method to manually create problem configuration files
     */
    public static void createFile() {
        try {
            FileOutputStream f = new FileOutputStream("C:\\Projects\\DECS\\src\\main\\resources\\ECJ\\params\\problems\\factory\\Mult11Isla5\\Mult11Isla5.conf");
            HashMap<FileConfigAttr, String> d = new HashMap<>();
            d.put(FileConfigAttr.CODE, "Mult11Isla5");
            d.put(FileConfigAttr.FULL_NAME, "Multiplexer 11bit Problem 5 Islands");
            d.put(FileConfigAttr.TYPE, "GP");
            d.put(FileConfigAttr.ORIGIN, "factory");
            d.put(FileConfigAttr.DISTRIBUTION, DistributionType.ISLANDS.toString());
            d.put(FileConfigAttr.PARAMS_FILE, "Mult11Isla5.params");
            // Islands Problem
            d.put(FileConfigAttr.SERVER_ISLAND, "src/main/resources/ECJ/params/problems/factory/Mult11Isla5/island1.params");
            d.put(FileConfigAttr.ISLAND_LIST, "island1;island2;island3;island4;island5");
            ObjectOutputStream ob = new ObjectOutputStream(f);
            ob.writeObject(d);
            f.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * @param rootFolder Root folder
     * @return List of all files inside the root folder
     */
    public static ArrayList<File> getFilesInFolder(File rootFolder) {
        try {
            return new ArrayList<>(Arrays.asList(Objects.requireNonNull(rootFolder.listFiles())));
        } catch (Exception e) {
            System.err.println("Exception in getFilesInFolder");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param rootFolder Root folder
     * @return List of all file names inside the root folder
     */
    public static ArrayList<String> getFileNamesInFolder(File rootFolder) {
        try {
            return new ArrayList<>(Arrays.asList(Objects.requireNonNull(rootFolder.list())));
        } catch (Exception e) {
            System.err.println("Exception in getFileNamesInFolder");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Replaces the textual content of a file
     * @param filePath Path of the file
     * @param content New textual content
     */
    public static void replaceFileContent(String filePath, String content) {
        try {
            File file = new File(filePath);
            PrintWriter pWriter = new PrintWriter(file);
            pWriter.write(content);
            pWriter.close();
        } catch (IOException e) {
            System.err.println("IO Exception while replacing file content");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception in replaceFileContent");
            e.printStackTrace();
        }
    }

    //Overrides


    //Internal Functions
    /**
     * Extract all information from a problem configuration file (.conf)
     * @param confFile Problem configuration file
     * @return Hashmap with the file contents
     */
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
        } catch (Exception e) {
            System.err.println("Exception in extractMap");
            e.printStackTrace();
        }
        return null;
    }
}