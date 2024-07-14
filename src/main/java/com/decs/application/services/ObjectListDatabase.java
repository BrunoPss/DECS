package com.decs.application.services;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.distribution.Island;
import com.decs.application.data.job.Job;
import com.decs.application.data.problem.Problem;
import com.decs.application.data.problem.ProblemType;
import com.decs.application.utils.ProblemCreator;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Object List Database Class</b>
 * <p>
 *     This class contains a collection of objects that should be visible to all system classes.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
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
    private MainLayout mainLayout;

    /**
     * Class Constructor
     */
    public ObjectListDatabase() {
        availableProblemsList = initializeAvailableProblems();
        jobActivityList = new ArrayList<>();
    }

    //Fetch Methods
    /**
     * @return List of all Job objects
     */
    public ArrayList<Job> fetchJobActivityList() {
        return jobActivityList;
    }
    /**
     * @return Size of the list containing all Job objects
     */
    public int jobActivitySize() {
        return jobActivityList.size();
    }

    // Available Problems List

    /**
     * @return List of all available Problems
     */
    public ArrayList<Problem> fetchAvailableProblemsList() {
        return availableProblemsList;
    }
    /**
     * @return Size of the list containing all available Problems
     */
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
    public MainLayout getMainLayout() { return mainLayout; }

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
    public void setMainLayout(MainLayout mainLayout) { this.mainLayout = mainLayout; }

    //Data Providers
    /**
     * Vaadin data provider used for automatic updating of the web app Job Activity grid visual component
     */
    private DataProvider<Job, Void> jobActivityDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return fetchJobActivityList().stream();
                    },
                    query -> jobActivitySize()
            );

    /**
     * Vaadin data provider used for automatic updating o the web app Available Problems grid visual component
     */
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
    /**
     * Refresh and update of the available problems list
     */
    public void updateAvailableProblems() {
        availableProblemsList.clear();
        availableProblemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER));
        availableProblemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER));
    }

    //Overrides


    //Internal Functions
    /**
     * Initializes the list of available problems
     * @return List of available problems
     */
    private ArrayList<Problem> initializeAvailableProblems() {
        ArrayList<Problem> problemsList = new ArrayList<>(ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER));
        problemsList.addAll(ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER));
        return problemsList;
    }
}