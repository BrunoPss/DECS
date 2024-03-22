package com.decs.application.utils;

import com.decs.application.data.FileConfigAttr;
import com.decs.application.data.Problem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public final class ProblemCreator {
    //Internal Data


    //Constructor
    private ProblemCreator() {}

    //Get Methods


    //Set Methods


    //Methods
    public static ArrayList<Problem> problemScanner(String path) {
        ArrayList<HashMap<FileConfigAttr, String>> configList = ProblemFileManager.getProblemList(path);
        ArrayList<Problem> problemList = new ArrayList<>();

        for (HashMap<FileConfigAttr, String> h : configList) {
            problemList.add(new Problem(new File(h.get(FileConfigAttr.PARAMS_FILE)),
                    h.get(FileConfigAttr.CODE), h.get(FileConfigAttr.FULL_NAME), h.get(FileConfigAttr.TYPE)));
        }

        //File f = new File(path);
        //ArrayList<Problem> problemList = new ArrayList<>();

        // Filter directories
        //FilenameFilter filter = new FilenameFilter() {
        //    @Override
        //    public boolean accept(File f, String name) {
        //        return f.isDirectory();
        //    }
        //};

        // Create problem list
        //File[] fileList = f.listFiles(filter);

        //for (File file : fileList) {
        //    String[] fileInfo = filenameParser(file);
        //    problemList.add(new Problem(file, fileInfo[0], fileInfo[1], fileInfo[2]));
        //}
        return problemList;
    }

    //Overrides


    //Internal Functions
    private static String[] filenameParser(File file) {
        String[] parsedList = file.getName().replace(".params", "").split("_");

        if (parsedList.length != 3) {
            return new String[]{"", parsedList[0], ""};
        }
        return parsedList;
    }
}