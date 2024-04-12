package com.decs.application.services;

import com.decs.application.data.*;
import com.decs.application.utils.ProblemCreator;
import com.decs.application.utils.constants.FilePathConstants;
import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectListDatabase {
    //Internal Data
    private ArrayList<Problem> availableProblemsList;
    private ArrayList<Job> jobActivityList;
    private Problem selectedProblem;
    private ProblemType problemCreatorSelector;
    private DistributionType problemCreatorDistribution;
    private String problemCreatorCode;
    private String serverIsland;
    private ArrayList<Island> islandList;

    //Constructor
    public ObjectListDatabase() {
        availableProblemsList = initializeAvailableProblems();
        jobActivityList = new ArrayList<>();
    }

    //Fetch Methods
    // Job Activity List
    public ArrayList<Job> fetchJobActivityList() {
        return jobActivityList;
    }
    public int jobActivitySize() {
        return jobActivityList.size();
    }

    // Available Problems List
    public ArrayList<Problem> fetchAvailableProblemsList() {
        return availableProblemsList;
    }
    public int availableProblemsListSize() {
        return availableProblemsList.size();
    }

    //Get Methods
    public DataProvider<Job, Void> getJobActivityDataProvider() { return jobActivityDataProvider; }
    public DataProvider<Problem, Void> getAvailableProblemsDataProvider() { return availableProblemsDataProvider; }
    public Problem getSelectedProblem() { return this.selectedProblem; }
    public ProblemType getProblemCreatorSelector() { return this.problemCreatorSelector; }
    public DistributionType getProblemCreatorDistribution() { return this.problemCreatorDistribution; }
    public String getProblemCreatorCode() { return this.problemCreatorCode; }
    public String getServerIsland() { return serverIsland; }
    public ArrayList<Island> getIslandList() { return islandList; }

    //Set Methods
    public void addJobActivity(Job newJob) { jobActivityList.add(newJob); }
    public void addAvailableProblem(Problem problem) { availableProblemsList.add(problem); }
    public void addAvailableProblems(List<Problem> problems) { availableProblemsList.addAll(problems); }
    public void setSelectedProblem(Problem selectedProblem) { this.selectedProblem = selectedProblem; }
    public void setProblemCreatorSelector(ProblemType problem) { this.problemCreatorSelector = problem; }
    public void setProblemCreatorDistribution(DistributionType type) { this.problemCreatorDistribution = type; }
    public void setProblemCreatorCode(String code) { this.problemCreatorCode = code; }
    public void setServerIsland(String serverIsland) { this.serverIsland = serverIsland; }
    public void setIslandList(ArrayList<Island> islandList) { this.islandList = islandList; }

    //Data Providers
    // Job Activity Data Provider
    private DataProvider<Job, Void> jobActivityDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchJobActivityList().stream();
                    },
                    query -> jobActivitySize()
            );

    // Available Problems Data Provider
    private DataProvider<Problem, Void> availableProblemsDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchAvailableProblemsList().stream();
                    },
                    query -> availableProblemsListSize()
            );

    //Methods
    public void updateAvailableProblems() {
        availableProblemsList.clear();
        availableProblemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER));
        availableProblemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER));
    }

    //Overrides


    //Internal Functions
    private ArrayList<Problem> initializeAvailableProblems() {
        ArrayList<Problem> problemsList = new ArrayList<>(ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER));
        problemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER));
        return problemsList;
    }
}