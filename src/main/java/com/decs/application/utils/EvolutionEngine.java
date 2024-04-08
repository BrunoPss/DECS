package com.decs.application.utils;

import com.decs.application.data.DistributionType;
import com.decs.application.data.Job;
import com.decs.application.services.SlaveManager;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.ProblemEditor.tabs.StatisticsType;
import com.decs.application.views.jobdashboard.JobDashboardView;
import com.vaadin.flow.component.UI;
import ec.EvolutionState;
import ec.Evolve;
import ec.Statistics;
import ec.simple.SimpleStatistics;
import ec.util.Output;
import ec.util.Parameter;
import ec.util.ParameterDatabase;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EvolutionEngine extends Thread {
    //Internal Data
    private ParameterDatabase parameterDatabase;
    private File paramsFile;
    private Job job;
    private JobDashboardView jobDashboard;
    private UI ui;
    private Statistics results;
    private EvolutionState evaluatedState;
    private SlaveManager slaveManager;

    //Constructor
    public EvolutionEngine(File paramsFile, Job job, UI ui, JobDashboardView jobDashboard, SlaveManager slaveManager) {
        this.paramsFile = paramsFile;
        this.job = job;
        this.jobDashboard = jobDashboard;
        this.ui = ui;
        this.slaveManager = slaveManager;
    }

    //Get Methods


    //Set Methods


    //Methods
    @Override
    public void run() {
        // Distributed ?
        if (job.getDistribution() != DistributionType.LOCAL) {
            if (slaveManager.getConnectedSlaves() >= 1) {
                System.out.println("Distributed Eval");
                slaveManager.initializeSlaves();
                slaveManager.startInference();
            }
            else {
                return;
            }
        }
        System.out.println("Start Inference");
        startInference();
        jobDashboard.updateInferenceResults(this.ui, evaluatedState);
    }
    public void startInference() {
        try {
            ParameterDatabase paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            // Stats file param
            paramDatabase.set(new Parameter("stat.file"), "../../../../stats/job" + job.getId() + "_" + job.getName() + "_stats.txt");
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

            // Set Job Metrics
            jobDashboard.setJobMetrics(this.ui, evaluatedState.breedthreads, evaluatedState.evalthreads);

            // Run all at once
            //evaluatedState.run(EvolutionState.C_STARTED_FRESH);
            // Run stage by stage
            evaluatedState.startFresh();
            int result = EvolutionState.R_NOTDONE;
            while (result == EvolutionState.R_NOTDONE) {
                result = evaluatedState.evolve();
                jobDashboard.updateProgressBar(this.ui, (float) evaluatedState.generation / (evaluatedState.numGenerations-1));
                jobDashboard.updateJobMetrics(this.ui, evaluatedState.evaluations, evaluatedState.generation);
            }

            evaluatedState.finish(result);

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