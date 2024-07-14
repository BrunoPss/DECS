package com.decs.application.utils;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.problem.Problem;
import com.decs.application.utils.confFile.FileConfigAttr;
import com.decs.application.utils.confFile.ProblemFileManager;
import com.decs.application.views.notifications.ErrorNotification;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <b>Problem Creator Class</b>
 * <p>
 *     This class handles the problem creation process.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public final class ProblemCreator {
    //Internal Data


    /**
     * Class Private Constructor
     * <p>This class cannot be instantiated.</p>
     */
    private ProblemCreator() {}

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Scans for problems contained in the given path and includes them in a list of Problem objects
     * @param path Root folder
     * @return List of available Problem objects
     */
    public static ArrayList<Problem> problemScanner(String path) {
        ArrayList<HashMap<FileConfigAttr, String>> configList = ProblemFileManager.getProblemList(path);
        ArrayList<Problem> problemList = new ArrayList<>();

        try {
            if (configList != null) {
                for (HashMap<FileConfigAttr, String> h : configList) {
                    //System.out.println("PROBLEM CREATOR PARAMS FILE: " + h.get(FileConfigAttr.PARAMS_FILE));

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
                    } else {
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
            }
        } catch (Exception e) {
            System.err.println("Exception in problemScanner");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error while creating problem files!");
        }
        return problemList;
    }

    //Overrides


    //Internal Functions
}