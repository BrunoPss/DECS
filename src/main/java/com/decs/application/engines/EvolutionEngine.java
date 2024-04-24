package com.decs.application.engines;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.generation.Generation;
import com.decs.application.data.job.Job;
import com.decs.application.services.SlaveManager;
import com.decs.application.services.SystemManager;
import com.decs.application.services.Timer;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.ProblemEditor.tabs.StatisticsType;
import com.decs.application.views.jobdashboard.JobDashboardView;
import com.vaadin.flow.component.UI;
import ec.EvolutionState;
import ec.Evolve;
import ec.Statistics;
import ec.app.gui.SimpleXYSeriesChartStatistics;
import ec.display.chart.ChartableStatistics;
import ec.gp.koza.KozaShortStatistics;
import ec.simple.SimpleStatistics;
import ec.util.Output;
import ec.util.Parameter;
import ec.util.ParameterDatabase;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

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
    private Timer timer;
    private SystemManager systemManager;

    //Constructor
    public EvolutionEngine(File paramsFile, Job job, UI ui, JobDashboardView jobDashboard, SlaveManager slaveManager, Timer timer, SystemManager systemManager) {
        this.paramsFile = paramsFile;
        this.job = job;
        this.jobDashboard = jobDashboard;
        this.ui = ui;
        this.slaveManager = slaveManager;
        this.timer = timer;
        this.systemManager = systemManager;
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
            System.out.println(paramsFile);
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

            // Elapsed Time Collection
            // Get initial timestamp
            long initialWallClockTimestamp = Timer.getWallClockTimestamp();
            long initialCPUTimestamp = Timer.getCPUTimestamp(ManagementFactory.getThreadMXBean());
            // Memory Usage Collection
            // Get initial memory usage
            //long initialHeapMemoryUsage = systemManager.getHeapMemoryUsed();
            //long initialNonHeapMemoryUsage = systemManager.getNonHeapMemoryUsed();

            // Run all at once
            //evaluatedState.run(EvolutionState.C_STARTED_FRESH);
            // Run stage by stage
            evaluatedState.startFresh();
            int result = EvolutionState.R_NOTDONE;
            while (result == EvolutionState.R_NOTDONE) {
                result = evaluatedState.evolve();
                jobDashboard.updateProgressBar(this.ui, (float) evaluatedState.generation / (evaluatedState.numGenerations-1));
                jobDashboard.updateJobMetrics(this.ui, evaluatedState.evaluations, evaluatedState.generation);
                job.addGeneration(new Generation(evaluatedState.generation, ((SimpleStatistics)(evaluatedState.statistics)).getBestSoFar()[0].fitness.fitness()));
            }
            evaluatedState.finish(result);

            // Elapsed Time Collection
            // Get final timestamp
            long finalWallClockTimestamp = Timer.getWallClockTimestamp();
            long finalCPUTimestamp = Timer.getCPUTimestamp(ManagementFactory.getThreadMXBean());
            // Set Elapsed Time
            job.setWallClockTime(Timer.computeTime(initialWallClockTimestamp, finalWallClockTimestamp));
            job.setCpuTime(Timer.computeTime(initialCPUTimestamp, finalCPUTimestamp));
            System.out.println("Wall Clock Time: " + job.getWallClockTime());
            System.out.println("CPU Time: " + job.getCpuTime());
            // Memory Usage Collection
            // Get final memory usage
            //long finalHeapMemoryUsage = systemManager.getHeapMemoryUsed();
            //long finalNonHeapMemoryUsage = systemManager.getNonHeapMemoryUsed();
            // Set Memory Usage
            //job.setHeapMemoryUsage(SystemManager.computeMemoryUsage(initialHeapMemoryUsage, finalHeapMemoryUsage));
            //job.setNonHeapMemoryUsage(SystemManager.computeMemoryUsage(initialNonHeapMemoryUsage, finalNonHeapMemoryUsage));
            //System.out.println("Heap Memory Usage: " + job.getHeapMemoryUsage());
            //System.out.println("Non Heap Memory Usage: " + job.getNonHeapMemoryUsage());

            results = evaluatedState.statistics;

            // Write Generation Table file
            job.writeGenerationTableFile();

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