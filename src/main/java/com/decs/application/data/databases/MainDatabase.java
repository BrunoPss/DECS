package com.decs.application.data.databases;

import com.decs.application.data.Job;
import com.decs.application.data.Problem;
import com.decs.application.utils.ProblemCreator;
import com.decs.application.utils.constants.FilePathConstants;
import com.vaadin.flow.data.provider.DataProvider;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class MainDatabase {
    //Internal Data
    private static ArrayList<Problem> availableProblemsList = initializeAvailableProblems();
    private static ArrayList<Job> jobActivityList = new ArrayList<>();

    //Constructor

    //Fetch Methods
    // Job Activity List
    public static ArrayList<Job> fetchJobActivityList() {
        return jobActivityList;
    }
    public static int jobActivitySize() {
        return jobActivityList.size();
    }

    // Available Problems List
    public static ArrayList<Problem> fetchAvailableProblemsList() {
        return availableProblemsList;
    }
    public static int availableProblemsListSize() {
        return availableProblemsList.size();
    }

    //Getters
    public static DataProvider<Job, Void> getJobActivityDataProvider() { return jobActivityDataProvider; }
    public static DataProvider<Problem, Void> getAvailableProblemsDataProvider() { return availableProblemsDataProvider; }

    //Setters
    public static void addJobActivity(Job newJob) { jobActivityList.add(newJob); }
    public static void addAvailableProblem(Problem problem) { availableProblemsList.add(problem); }
    public static void addAvailableProblems(List<Problem> problems) { availableProblemsList.addAll(problems); }

    //Data Providers
    // Job Activity Data Provider
    private static DataProvider<Job, Void> jobActivityDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchJobActivityList().stream();
                    },
                    query -> jobActivitySize()
            );

    // Available Problems Data Provider
    private static DataProvider<Problem, Void> availableProblemsDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchAvailableProblemsList().stream();
                    },
                    query -> availableProblemsListSize()
            );

    //Methods


    //Overrides


    //Internal Functions
    private static ArrayList<Problem> initializeAvailableProblems() {
        ArrayList<Problem> problemsList = new ArrayList<>(ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER));
        problemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER));
        return problemsList;
    }
}