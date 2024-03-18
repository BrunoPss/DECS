package com.decs.application.utils;

import com.decs.application.data.Job;
import com.decs.application.data.JobStatus;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.ProblemEditor.tabs.StatisticsType;
import com.vaadin.flow.component.UI;
import ec.EvolutionState;
import ec.Evolve;
import ec.Statistics;
import ec.simple.SimpleStatistics;
import ec.util.Log;
import ec.util.Output;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class EvolutionEngine extends Thread {
    // UI Updater Interface
    public interface UIUpdaterInterface {
        public void updateInferenceResults(UI ui, EvolutionState evaluatedState);
    }

    //Internal Data
    private ParameterDatabase parameterDatabase;
    private File paramsFile;
    private Job job;
    private UIUpdaterInterface uiUpdater;
    private UI ui;
    private Statistics results;
    private EvolutionState evaluatedState;

    //Constructor
    public EvolutionEngine(File paramsFile, Job job, UI ui, UIUpdaterInterface uiUpdater) {
        this.paramsFile = paramsFile;
        this.job = job;
        this.uiUpdater = uiUpdater;
        this.ui = ui;
    }

    //Get Methods


    //Set Methods


    //Methods
    @Override
    public void run() {
        startInference();
        uiUpdater.updateInferenceResults(this.ui, evaluatedState);
    }
    public void startInference() {
        try {
            ParameterDatabase paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            // Stats file param
            paramDatabase.set(new Parameter("stat.file"), "../../../stats/job" + job.getId() + "_" + job.getName() + "_stats.txt");
            job.setStatsFile(new File(FilePathConstants.JOB_STATS_FOLDER + "/job" + job.getId() + "_" + job.getName() + "_stats.txt"));

            Output out = Evolve.buildOutput();

            // Silence STD output
            //out.getLog(0).silent = true;
            //out.getLog(1).silent = true;

            File jobLogFile = new File("src/main/resources/ECJ/logs/job" + job.getId() + "_" + job.getName() + "_log.txt");
            out.addLog(jobLogFile, false);
            out.getLog(2).postAnnouncements = true;
            job.setLogFile(jobLogFile);

            evaluatedState = Evolve.initialize(paramDatabase, 0, out);
            evaluatedState.run(EvolutionState.C_STARTED_FRESH);

            results = evaluatedState.statistics;

            cleanup(evaluatedState);
        } catch (IOException e) { e.printStackTrace(); }
    }
    public double getFitness(StatisticsType statType) {
        switch (statType) {
            case SIMPLE -> {
                return ((SimpleStatistics)(evaluatedState.statistics)).getBestSoFar()[0].fitness.fitness();
            }
        }
        return 0;
    }

    //Overrides


    //Internal Functions
    private void cleanup(EvolutionState evaluatedState) {
        Evolve.cleanup(evaluatedState);
    }
}