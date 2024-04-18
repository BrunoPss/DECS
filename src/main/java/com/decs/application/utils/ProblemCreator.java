package com.decs.application.utils;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.problem.Problem;
import com.decs.application.utils.confFile.FileConfigAttr;
import com.decs.application.utils.confFile.ProblemFileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
            System.out.println("PROBLEM CREATOR PARAMS FILE: " + h.get(FileConfigAttr.PARAMS_FILE));

            if (h.get(FileConfigAttr.DISTRIBUTION).equals(DistributionType.ISLANDS.toString())) {
                Problem newProblem = new Problem(
                        new File(h.get(FileConfigAttr.SERVER_ISLAND)),
                        h.get(FileConfigAttr.CODE), h.get(FileConfigAttr.FULL_NAME), h.get(FileConfigAttr.TYPE),
                        h.get(FileConfigAttr.ORIGIN), DistributionType.valueOf(h.get(FileConfigAttr.DISTRIBUTION)),
                        new File(h.get(FileConfigAttr.PARAMS_FILE)).getParentFile()
                );

                String wordList = h.get(FileConfigAttr.ISLAND_LIST);
                newProblem.setIslandList(new ArrayList<>(Arrays.asList(wordList.split(";"))));

                problemList.add(newProblem);
            }
            else {
                problemList.add(
                        new Problem(
                                new File(h.get(FileConfigAttr.PARAMS_FILE)),
                                h.get(FileConfigAttr.CODE), h.get(FileConfigAttr.FULL_NAME), h.get(FileConfigAttr.TYPE),
                                h.get(FileConfigAttr.ORIGIN), DistributionType.valueOf(h.get(FileConfigAttr.DISTRIBUTION)),
                                new File(h.get(FileConfigAttr.PARAMS_FILE)).getParentFile()
                        )
                );
            }
        }
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