package com.decs.application.utils;

import com.decs.application.views.ProblemEditor.tabs.StatisticsType;
import ec.EvolutionState;
import ec.Evolve;
import ec.Statistics;
import ec.simple.SimpleStatistics;
import ec.util.Output;
import ec.util.ParameterDatabase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;

public class EvolutionEngine {
    //Internal Data
    private ParameterDatabase parameterDatabase;
    private File paramsFile;
    private Statistics results;
    private EvolutionState evaluatedState;

    //Constructor
    public EvolutionEngine(File paramsFile) {
        this.paramsFile = paramsFile;
    }

    //Get Methods


    //Set Methods


    //Methods
    @Async
    public ListenableFuture<Statistics> startInference() {
        try {
            ParameterDatabase paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            Output out = Evolve.buildOutput();
            evaluatedState = Evolve.initialize(paramDatabase, 0, out);
            evaluatedState.run(EvolutionState.C_STARTED_FRESH);

            results = evaluatedState.statistics;

            cleanup(evaluatedState);
        } catch (IOException e) { e.printStackTrace(); }

        return AsyncResult.forValue(results);
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