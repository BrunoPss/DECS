package com.decs.application.engines;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.generation.Generation;
import com.decs.application.data.job.Job;
import com.decs.application.services.SessionManager;
import com.decs.application.services.SlaveManager;
import com.decs.application.services.Timer;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.jobdashboard.JobDashboardView;
import com.decs.application.views.notifications.ErrorNotification;
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
import java.lang.management.ManagementFactory;

/**
 * <b>Evolution Engine Class</b>
 * <p>
 *     This class represents the system's evolution engine.
 *     The engine manages all evolutionary operations and establishes a bridge between DECS and ECJ engine.
 *     Since its operations are resource-intensive, it is processed concurrently.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class EvolutionEngine extends Thread {
    //Internal Data
    private ParameterDatabase paramDatabase;
    private File paramsFile;
    private Job job;
    private JobDashboardView jobDashboard;
    private UI ui;
    private Statistics results;
    private EvolutionState evaluatedState;
    private SlaveManager slaveManager;
    private Timer timer;
    private SessionManager sessionManager;
    private String[] args;

    /**
     * Evolution engine class constructor
     * @param paramsFile Main parameters file
     * @param job Job object to be processed
     * @param ui Main Vaadin UI object
     * @param jobDashboard Job dashboard view object
     * @param slaveManager Slave manager object
     * @param timer Timer object
     * @param sessionManager Session manager object
     * @param args Command line arguments
     */
    public EvolutionEngine(File paramsFile, Job job, UI ui, JobDashboardView jobDashboard, SlaveManager slaveManager, Timer timer, SessionManager sessionManager, String[] args) {
        this.paramsFile = paramsFile;
        this.job = job;
        this.jobDashboard = jobDashboard;
        this.ui = ui;
        this.slaveManager = slaveManager;
        this.timer = timer;
        this.sessionManager = sessionManager;
        this.args = args;
    }

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Engine thread starting point
     * <p>
     *     It is responsible for the initial configuration and inference initialization.
     * </p>
     */
    @Override
    public void run() {
        // Set Evolution Engine Busy
        sessionManager.setEvolutionEngineBusy(true, this.ui);

        // Distributed ?
        if (job.getDistribution() != DistributionType.LOCAL) {
            if (slaveManager.getConnectedSlaves() >= 1) {
                System.out.println("Distributed");
                slaveManager.initializeSlaves();
                slaveManager.startInference();
            }
            else {
                return;
            }
        }
        System.out.println("Start Inference");
        startInference();
        jobDashboard.updateInferenceResults(this.ui);

        // Set Evolution Engine Free
        sessionManager.setEvolutionEngineBusy(false, this.ui);
    }

    /**
     * Inference initialization
     * <p>
     *     This method processes all internal evolutionary operations required for a solution
     *     to be found and it interacts directly with ECJ's API.
     * </p>
     */
    public void startInference() {
        try {
            System.out.println(paramsFile);
            System.out.println(args[0]);
            paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            // Manually setting the local IP address
            // This was intentionally changed in ECJ source code
            // The original implementation is problematic...
            Evolve.myAddress = args[0];

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
        } catch (IOException e) {
            System.err.println("IO Exception in startInference");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception in startInference");
            e.printStackTrace();
            ErrorNotification.showErrorNotification("Error in problem execution!");
        }
    }

    //Overrides


    //Internal Functions

    /**
     * Cleans and resets the evolutionary engine
     * @param evaluatedState Object that represents the evolution state
     */
    private void cleanup(EvolutionState evaluatedState) {
        Evolve.cleanup(evaluatedState);
    }
}