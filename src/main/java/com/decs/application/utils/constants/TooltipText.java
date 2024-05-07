package com.decs.application.utils.constants;

public final class TooltipText {
    // Dist Eval Tab
    public static final String slaveJobQueueSizeTooltipText =
            """
            Size of each Slave job queue.
            Increasing this number is likely to be more efficient.
            """;
    public static final String slaveJobSizeTooltipText =
            """
            Size of each job.
            Max number of individuals placed in a single job.
            """;
    public static final String compressionTooltipText =
            """
            Compression of the streams between Coordinator and Slave.
            """;
    public static final String runEvolveTooltipText =
            """
            Mode the Slave runs in.
            False:  Slave just evaluates each individual.
            True: Slave runs in Evolve mode (evaluation + evolution).
            """;
    public static final String runEvolveRuntimeTooltipText =
            """
            Length of (wall clock) time, in milliseconds, that the\s
            slave should do "evolution" on its individuals.
            """;
    public static final String returnIndsTooltipText =
            """
            True: Slave returns the whole individuals.
            False; Slave just return the fitness values
            """;

    // General Tab
    public static final String jobTooltipText =
            """
            Number of Jobs an execution should have.
            One Job designates a complete evolution.
            """;
    public static final String seedTooltipText =
            """
            Random seed value for the evolution.
            """;
    public static final String evalTooltipText =
            """
            Number of threads used for evaluation.
            Tip: how many threads does your CPU have?
            """;
    public static final String breedTooltipText =
            """
            Number of threads used for breeding.
            Tip: how many threads does your CPU have?
            """;
    public static final String checkpointTooltipText =
            """
            Save recurrent checkpoints during the evolution process?
            """;
    public static final String moduloTooltipText =
            """
            How often do you like to save checkpoints?
            """;
    public static final String prefixTooltipText =
            """
            Prefix for the checkpoint file.
            """;

    // Islands Tab
    public static final String islandSyncTooltipText =
            """
            Should the evolution of all islands be synchronized?
            Tip: this could have an implication on efficiency.
            """;
    public static final String islandCompressionTooltipText =
            """
            Compression of the streams between Coordinator and Islands.
            """;

    // Koza Tab
    public static final String initialCreationMinDepthTooltipText =
            """
            Smallest "maximum" depth the builder may use for building a tree.
            """;
    public static final String initialCreationMaxDepthTooltipText =
            """
            Largest "maximum" depth the builder may use for building a tree.
            """;
    public static final String initialCreationGrowProbTooltipText =
            """
            The likelihood of choosing GROW (as opposed to FULL).
            """;
    public static final String crossoverPipelineProbTooltipText =
            """
            Probability of an individual to be subjected to the crossover pipeline.
            """;
    public static final String reproductionPipelineProbTooltipText =
            """
            Probability of an individual to be subjected to the reproduction pipeline.
            """;
    public static final String crossoverPipelineMaxDepthTooltipText =
            """
            Maximum valid depth of a crossed-over subtree.
            """;
    public static final String crossoverPipelineTriesTooltipText =
            """
            Number of times to try finding valid pairs of nodes.
            """;
    public static final String pointMutationMaxDepthTooltipText =
            """
            Maximum valid depth of a crossed-over subtree.
            """;
    public static final String pointMutationTriesTooltipText =
            """
            Number of times to try finding valid pairs of nodes.
            """;
    public static final String tournamentSizeTooltipText =
            """
            The tournament size.
            """;
    public static final String subtreeMutationMinDepthTooltipText =
            """
            
            """;
    public static final String subtreeMutationMaxDepthTooltipText =
            """
            Maximum valid depth of a crossed-over subtree.
            """;
    public static final String kozaNodeSelectionTerminalsProbTooltipText =
            """
            Minimum valid depth of a crossed-over subtree.
            """;
    public static final String kozaNodeSelectionNonTerminalsProbTooltipText =
            """
            The probability we must pick a nonterminal if possible.
            """;
    public static final String kozaNodeSelectionRootProbTooltipText =
            """
            The probability we must pick the root.
            """;

    // Simple Tab
    public static final String stoppingTooltipText =
            """
            Job stop condition.
            """;
    public static final String subpopulationSizeTooltipText =
            """
            Size of each sub-population.
            """;
    public static final String subpopulationDupRetriesTooltipText =
            """
            Maximum number of tries to reach unique individuals.
            """;
    public static final String breederEliteTooltipText =
            """
            The number of elitist individuals for subpopulation i.
            """;
    public static final String breederEliteReevalTooltipText =
            """
            Should we reevaluate the elites of subpopulation i each generation?
            """;
    public static final String breederSequentialTooltipText =
            """
            Should we breed just one subpopulation each generation (as opposed to all of them)?
            """;
    public static final String statisticsClassTooltipText =
            """
            Type of statistics class should be used.
            """;
    public static final String statisticsFileTooltipText =
            """
            Name of the statistics file.
            """;
}