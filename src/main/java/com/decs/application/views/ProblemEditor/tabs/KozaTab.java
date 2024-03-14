package com.decs.application.views.ProblemEditor.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import org.apache.tomcat.Jar;

public class KozaTab extends Tab implements ParamTab {
    //Internal Data
    private VerticalLayout kozaTabLayout;
    // Initial Creation Group
    private VerticalLayout initialCreationLayoutGroup;
    private Span initialCreationTitle;
    private Span initialCreationSubtitle;
    private HorizontalLayout initialCreationLayout;
    // Min Depth
    private HorizontalLayout initialCreationMinDepthLayout;
    private IntegerField initialCreationMinDepth;
    private Tooltip initialCreationMinDepthTooltip;
    private Button initialCreationMinDepthHelpBtn;
    // Max Depth
    private HorizontalLayout initialCreationMaxDepthLayout;
    private IntegerField initialCreationMaxDepth;
    private Tooltip initialCreationMaxDepthTooltip;
    private Button initialCreationMaxDepthHelpBtn;
    // Grow Probability
    private HorizontalLayout initialCreationGrowProbLayout;
    private NumberField initialCreationGrowProb;
    private Tooltip initialCreationGrowProbTooltip;
    private Button initialCreationGrowProbHelpBtn;
    // Pipelines Group
    private VerticalLayout pipelinesLayoutGroup;
    private Span pipelinesTitle;
    // Crossover Pipeline Prob
    private HorizontalLayout pipelinesLayout;
    private HorizontalLayout crossoverPipelineProbLayout;
    private NumberField crossoverPipelineProb;
    private Tooltip crossoverPipelineProbTooltip;
    private Button crossoverPipelineProbHelpBtn;
    // Reproduction Pipeline Prob
    private HorizontalLayout reproductionPipelineProbLayout;
    private NumberField reproductionPipelineProb;
    private Tooltip reproductionPipelineProbTooltip;
    private Button reproductionPipelineProbHelpBtn;
    // Crossover Pipeline Group
    private VerticalLayout crossoverPipelineLayoutGroup;
    private Span crossoverPipelineTitle;
    private Span crossoverPipelineSubtitle;
    private HorizontalLayout crossoverPipelineLayout;
    // Max Depth
    private HorizontalLayout crossoverPipelineMaxDepthLayout;
    private IntegerField crossoverPipelineMaxDepth;
    private Tooltip crossoverPipelineMaxDepthTooltip;
    private Button crossoverPipelineMaxDepthHelpBtn;
    // Tries
    private HorizontalLayout crossoverPipelineTriesLayout;
    private IntegerField crossoverPipelineTries;
    private Tooltip crossoverPipelineTriesTooltip;
    private Button crossoverPipelineTriesHelpBtn;
    // Point Mutation Group
    private VerticalLayout pointMutationLayoutGroup;
    private Span pointMutationTitle;
    private Span pointMutationSubtitle;
    private HorizontalLayout pointMutationLayout;
    // Max Depth
    private HorizontalLayout pointMutationMaxDepthLayout;
    private IntegerField pointMutationMaxDepth;
    private Tooltip pointMutationMaxDepthTooltip;
    private Button pointMutationMaxDepthHelpBtn;
    // Tries
    private HorizontalLayout pointMutationTriesLayout;
    private IntegerField pointMutationTries;
    private Tooltip pointMutationTriesTooltip;
    private Button pointMutationTriesHelpBtn;
    // Tournament Group
    private VerticalLayout tournamentLayoutGroup;
    private Span tournamentTitle;
    private HorizontalLayout tournamentSizeLayout;
    private IntegerField tournamentSize;
    private Tooltip tournamentSizeTooltip;
    private Button tournamentSizeHelpBtn;
    // Subtree Mutation Group
    private VerticalLayout subtreeMutationLayoutGroup;
    private Span subtreeMutationTitle;
    private Span subtreeMutationSubtitle;
    private HorizontalLayout subtreeMutationLayout;
    // Min Depth
    private HorizontalLayout subtreeMutationMinDepthLayout;
    private IntegerField subtreeMutationMinDepth;
    private Tooltip subtreeMutationMinDepthTooltip;
    private Button subtreeMutationMinDepthHelpBtn;
    // Max Depth
    private HorizontalLayout subtreeMutationMaxDepthLayout;
    private IntegerField subtreeMutationMaxDepth;
    private Tooltip subtreeMutationMaxDepthTooltip;
    private Button subtreeMutationMaxDepthHelpBtn;
    // Koza Node Selection Group
    private VerticalLayout kozaNodeSelectionLayoutGroup;
    private Span kozaNodeSelectionTitle;
    private HorizontalLayout kozaNodeSelectionLayout;
    // Terminals Prob
    private HorizontalLayout kozaNodeSelectionTerminalsProbLayout;
    private NumberField kozaNodeSelectionTerminalsProb;
    private Tooltip kozaNodeSelectionTerminalsProbTooltip;
    private Button kozaNodeSelectionTerminalsProbHelpBtn;
    // Non-Terminals Prob
    private HorizontalLayout kozaNodeSelectionNonTerminalsProbLayout;
    private NumberField kozaNodeSelectionNonTerminalsProb;
    private Tooltip kozaNodeSelectionNonTerminalsProbTooltip;
    private Button kozaNodeSelectionNonTerminalsProbHelpBtn;
    // Root Prob
    private HorizontalLayout kozaNodeSelectionRootProbLayout;
    private NumberField kozaNodeSelectionRootProb;
    private Tooltip kozaNodeSelectionRootProbTooltip;
    private Button kozaNodeSelectionRootProbHelpBtn;

    //Constructor
    public KozaTab() {
        setLabel("Koza");
    }

    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        kozaTabLayout = new VerticalLayout();

        createInitialCreationGroup();
        createPipelinesGroup();
        createCrossoverPipelineGroup();
        createPointMutationGroup();
        createTournamentGroup();
        createSubtreeMutationGroup();
        createKozaNodeSelectionGroup();

        kozaTabLayout.add(
                initialCreationLayoutGroup,
                pipelinesLayoutGroup,
                crossoverPipelineLayoutGroup,
                pointMutationLayoutGroup, tournamentLayoutGroup,
                subtreeMutationLayoutGroup,
                kozaNodeSelectionLayoutGroup
        );

        return kozaTabLayout;
    }

    //Overrides


    //Internal Functions
    private void createInitialCreationGroup() {
        // Initial Creation
        initialCreationLayoutGroup = new VerticalLayout();
        initialCreationLayoutGroup.setPadding(true);

        initialCreationTitle = new Span("Initial Creation");
        initialCreationSubtitle = new Span("Half Builder");
        initialCreationSubtitle.getStyle().set("font-size", "13px");

        initialCreationLayout = new HorizontalLayout();
        initialCreationLayout.setAlignItems(FlexComponent.Alignment.START);
        initialCreationLayout.setSpacing(true);
        initialCreationLayout.getThemeList().add("spacing-xl");

        // Min Depth
        initialCreationMinDepthLayout = new HorizontalLayout();
        initialCreationMinDepthLayout.setAlignItems(FlexComponent.Alignment.END);
        initialCreationMinDepth = new IntegerField();
        initialCreationMinDepth.setLabel("Min Depth");
        initialCreationMinDepth.setMin(1);
        initialCreationMinDepth.setValue(2);
        initialCreationMinDepth.setStepButtonsVisible(true);
        initialCreationMinDepthTooltip = initialCreationMinDepth.getTooltip().withManual(true);
        initialCreationMinDepthTooltip.setText("test tooltip");
        initialCreationMinDepthHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        initialCreationMinDepthHelpBtn.addClickListener(event -> {
            initialCreationMinDepthTooltip.setOpened(!initialCreationMinDepthTooltip.isOpened());
        });
        initialCreationMinDepthLayout.add(initialCreationMinDepth, initialCreationMinDepthHelpBtn);

        // Max Depth
        initialCreationMaxDepthLayout = new HorizontalLayout();
        initialCreationMaxDepthLayout.setAlignItems(FlexComponent.Alignment.END);
        initialCreationMaxDepth = new IntegerField();
        initialCreationMaxDepth.setLabel("Max Depth");
        initialCreationMaxDepth.setMin(initialCreationMinDepth.getValue());
        initialCreationMaxDepth.setValue(initialCreationMinDepth.getValue()+4);
        initialCreationMaxDepth.setStepButtonsVisible(true);
        initialCreationMaxDepthTooltip = initialCreationMaxDepth.getTooltip().withManual(true);
        initialCreationMaxDepthTooltip.setText("test tooltip");
        initialCreationMaxDepthHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        initialCreationMaxDepthHelpBtn.addClickListener(event -> {
            initialCreationMaxDepthTooltip.setOpened(!initialCreationMaxDepthTooltip.isOpened());
        });
        initialCreationMaxDepthLayout.add(initialCreationMaxDepth, initialCreationMaxDepthHelpBtn);

        // Grow Probability
        initialCreationGrowProbLayout = new HorizontalLayout();
        initialCreationGrowProbLayout.setAlignItems(FlexComponent.Alignment.END);
        initialCreationGrowProb = new NumberField();
        initialCreationGrowProb.setLabel("Grow Probability");
        initialCreationGrowProb.setMin(0);
        initialCreationGrowProb.setMax(1);
        initialCreationGrowProb.setStep(0.05);
        initialCreationGrowProb.setValue(0.9);
        initialCreationGrowProb.setStepButtonsVisible(true);
        initialCreationGrowProbTooltip = initialCreationGrowProb.getTooltip().withManual(true);
        initialCreationGrowProbTooltip.setText("test tooltip");
        initialCreationGrowProbHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        initialCreationGrowProbHelpBtn.addClickListener(event -> {
            initialCreationGrowProbTooltip.setOpened(!initialCreationGrowProbTooltip.isOpened());
        });
        initialCreationGrowProbLayout.add(initialCreationGrowProb, initialCreationGrowProbHelpBtn);

        // Group Builder
        initialCreationLayout.add(initialCreationMinDepthLayout, initialCreationMaxDepthLayout, initialCreationGrowProbLayout);
        initialCreationLayoutGroup.add(initialCreationTitle, initialCreationSubtitle, initialCreationLayout);
    }

    private void createPipelinesGroup() {
        // Pipelines
        pipelinesLayoutGroup = new VerticalLayout();
        pipelinesLayoutGroup.setPadding(true);

        pipelinesTitle = new Span("Pipelines");

        pipelinesLayout = new HorizontalLayout();
        pipelinesLayout.setAlignItems(FlexComponent.Alignment.START);
        pipelinesLayout.setSpacing(true);
        pipelinesLayout.getThemeList().add("spacing-xs");

        // Crossover Pipeline Probability
        crossoverPipelineProbLayout = new HorizontalLayout();
        crossoverPipelineProbLayout.setAlignItems(FlexComponent.Alignment.END);
        crossoverPipelineProb = new NumberField();
        crossoverPipelineProb.setLabel("Crossover Pipe Prob.");
        crossoverPipelineProb.setMin(0);
        crossoverPipelineProb.setMax(1);
        crossoverPipelineProb.setStep(0.05);
        crossoverPipelineProb.setValue(0.9);
        crossoverPipelineProb.setStepButtonsVisible(true);
        crossoverPipelineProbTooltip = crossoverPipelineProb.getTooltip().withManual(true);
        crossoverPipelineProbTooltip.setText("test tooltip");
        crossoverPipelineProbHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        crossoverPipelineProbHelpBtn.addClickListener(event -> {
            crossoverPipelineProbTooltip.setOpened(!crossoverPipelineProbTooltip.isOpened());
        });
        crossoverPipelineProbLayout.add(crossoverPipelineProb, crossoverPipelineProbHelpBtn);

        // Reproduction Pipeline Probability
        reproductionPipelineProbLayout = new HorizontalLayout();
        reproductionPipelineProbLayout.setAlignItems(FlexComponent.Alignment.END);
        reproductionPipelineProb = new NumberField();
        reproductionPipelineProb.setLabel("Repr. Pipe Prob.");
        reproductionPipelineProb.setMin(0);
        reproductionPipelineProb.setMax(1);
        reproductionPipelineProb.setStep(0.05);
        reproductionPipelineProb.setValue(0.1);
        reproductionPipelineProb.setStepButtonsVisible(true);
        reproductionPipelineProbTooltip = reproductionPipelineProb.getTooltip().withManual(true);
        reproductionPipelineProbTooltip.setText("test tooltip");
        reproductionPipelineProbHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        reproductionPipelineProbHelpBtn.addClickListener(event -> {
            reproductionPipelineProbTooltip.setOpened(!reproductionPipelineProbTooltip.isOpened());
        });
        crossoverPipelineProbLayout.add(reproductionPipelineProb, reproductionPipelineProbHelpBtn);

        // Group Builder
        pipelinesLayout.add(crossoverPipelineProbLayout, reproductionPipelineProbLayout);
        pipelinesLayoutGroup.add(pipelinesTitle, pipelinesLayout);
    }

    private void createCrossoverPipelineGroup() {
        // Crossover Pipeline
        crossoverPipelineLayoutGroup = new VerticalLayout();
        crossoverPipelineLayoutGroup.setPadding(true);

        crossoverPipelineTitle = new Span("Crossover Pipeline");
        crossoverPipelineSubtitle = new Span("Tournament Selection");
        crossoverPipelineSubtitle.getStyle().set("font-size", "13px");

        crossoverPipelineLayout = new HorizontalLayout();
        crossoverPipelineLayout.setAlignItems(FlexComponent.Alignment.START);
        crossoverPipelineLayout.setSpacing(true);
        crossoverPipelineLayout.getThemeList().add("spacing-xl");

        // Max Depth
        crossoverPipelineMaxDepthLayout = new HorizontalLayout();
        crossoverPipelineMaxDepthLayout.setAlignItems(FlexComponent.Alignment.END);
        crossoverPipelineMaxDepth = new IntegerField();
        crossoverPipelineMaxDepth.setLabel("Max Depth");
        crossoverPipelineMaxDepth.setMin(1);
        crossoverPipelineMaxDepth.setValue(17);
        crossoverPipelineMaxDepth.setStepButtonsVisible(true);
        crossoverPipelineMaxDepthTooltip = crossoverPipelineMaxDepth.getTooltip().withManual(true);
        crossoverPipelineMaxDepthTooltip.setText("test tooltip");
        crossoverPipelineMaxDepthHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        crossoverPipelineMaxDepthHelpBtn.addClickListener(event -> {
            crossoverPipelineMaxDepthTooltip.setOpened(!crossoverPipelineMaxDepthTooltip.isOpened());
        });
        crossoverPipelineMaxDepthLayout.add(crossoverPipelineMaxDepth, crossoverPipelineMaxDepthHelpBtn);

        // Tries
        crossoverPipelineTriesLayout = new HorizontalLayout();
        crossoverPipelineTriesLayout.setAlignItems(FlexComponent.Alignment.END);
        crossoverPipelineTries = new IntegerField();
        crossoverPipelineTries.setLabel("Tries");
        crossoverPipelineTries.setMin(1);
        crossoverPipelineTries.setValue(1);
        crossoverPipelineTries.setStepButtonsVisible(true);
        crossoverPipelineTriesTooltip = crossoverPipelineTries.getTooltip().withManual(true);
        crossoverPipelineTriesTooltip.setText("test tooltip");
        crossoverPipelineTriesHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        crossoverPipelineTriesHelpBtn.addClickListener(event -> {
            crossoverPipelineTriesTooltip.setOpened(!crossoverPipelineTriesTooltip.isOpened());
        });
        crossoverPipelineTriesLayout.add(crossoverPipelineTries, crossoverPipelineTriesHelpBtn);

        // Group Builder
        crossoverPipelineLayout.add(crossoverPipelineMaxDepthLayout, crossoverPipelineTriesLayout);
        crossoverPipelineLayoutGroup.add(crossoverPipelineTitle, crossoverPipelineSubtitle, crossoverPipelineLayout);
    }

    private void createPointMutationGroup() {
        // Point Mutation
        pointMutationLayoutGroup = new VerticalLayout();
        pointMutationLayoutGroup.setPadding(true);

        pointMutationTitle = new Span("Point Mutation");
        pointMutationSubtitle = new Span("Tournament Selection");
        pointMutationSubtitle.getStyle().set("font-size", "13px");

        pointMutationLayout = new HorizontalLayout();
        pointMutationLayout.setAlignItems(FlexComponent.Alignment.START);
        pointMutationLayout.setSpacing(true);
        pointMutationLayout.getThemeList().add("spacing-xl");

        // Max Depth
        pointMutationMaxDepthLayout = new HorizontalLayout();
        pointMutationMaxDepthLayout.setAlignItems(FlexComponent.Alignment.END);
        pointMutationMaxDepth = new IntegerField();
        pointMutationMaxDepth.setLabel("Max Depth");
        pointMutationMaxDepth.setMin(1);
        pointMutationMaxDepth.setValue(17);
        pointMutationMaxDepth.setStepButtonsVisible(true);
        pointMutationMaxDepthTooltip = pointMutationMaxDepth.getTooltip().withManual(true);
        pointMutationMaxDepthTooltip.setText("test tooltip");
        pointMutationMaxDepthHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        pointMutationMaxDepthHelpBtn.addClickListener(event -> {
            pointMutationMaxDepthTooltip.setOpened(!pointMutationMaxDepthTooltip.isOpened());
        });
        pointMutationMaxDepthLayout.add(pointMutationMaxDepth, pointMutationMaxDepthHelpBtn);

        // Tries
        pointMutationTriesLayout = new HorizontalLayout();
        pointMutationTriesLayout.setAlignItems(FlexComponent.Alignment.END);
        pointMutationTries = new IntegerField();
        pointMutationTries.setLabel("Tries");
        pointMutationTries.setMin(1);
        pointMutationTries.setValue(1);
        pointMutationTries.setStepButtonsVisible(true);
        pointMutationTriesTooltip = pointMutationTries.getTooltip().withManual(true);
        pointMutationTriesTooltip.setText("test tooltip");
        pointMutationTriesHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        pointMutationTriesHelpBtn.addClickListener(event -> {
            pointMutationTriesTooltip.setOpened(!pointMutationTriesTooltip.isOpened());
        });
        pointMutationTriesLayout.add(pointMutationTries, pointMutationTriesHelpBtn);

        // Group Builder
        pointMutationLayout.add(pointMutationMaxDepthLayout, pointMutationTriesLayout);
        pointMutationLayoutGroup.add(pointMutationTitle, pointMutationSubtitle, pointMutationLayout);
    }

    private void createTournamentGroup() {
        tournamentLayoutGroup = new VerticalLayout();
        tournamentLayoutGroup.setPadding(true);

        tournamentTitle = new Span("Tournament");

        // Size
        tournamentSizeLayout = new HorizontalLayout();
        tournamentSizeLayout.setAlignItems(FlexComponent.Alignment.END);
        tournamentSize = new IntegerField();
        tournamentSize.setLabel("Max Depth");
        tournamentSize.setMin(1);
        tournamentSize.setValue(7);
        tournamentSize.setStepButtonsVisible(true);
        tournamentSizeTooltip = tournamentSize.getTooltip().withManual(true);
        tournamentSizeTooltip.setText("test tooltip");
        tournamentSizeHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        tournamentSizeHelpBtn.addClickListener(event -> {
            tournamentSizeTooltip.setOpened(!tournamentSizeTooltip.isOpened());
        });
        tournamentSizeLayout.add(tournamentSize, tournamentSizeHelpBtn);

        // Group Builder
        tournamentLayoutGroup.add(tournamentTitle, tournamentSizeLayout);
    }

    private void createSubtreeMutationGroup() {
        subtreeMutationLayoutGroup = new VerticalLayout();
        subtreeMutationLayoutGroup.setPadding(true);

        subtreeMutationTitle = new Span("Subtree Mutation");
        subtreeMutationSubtitle = new Span("GROW");
        subtreeMutationSubtitle.getStyle().set("font-size", "13px");

        subtreeMutationLayout = new HorizontalLayout();
        subtreeMutationLayout.setAlignItems(FlexComponent.Alignment.START);
        subtreeMutationLayout.setSpacing(true);
        subtreeMutationLayout.getThemeList().add("spacing-xl");

        // Min Depth
        subtreeMutationMinDepthLayout = new HorizontalLayout();
        subtreeMutationMinDepthLayout.setAlignItems(FlexComponent.Alignment.END);
        subtreeMutationMinDepth = new IntegerField();
        subtreeMutationMinDepth.setLabel("Min Depth");
        subtreeMutationMinDepth.setMin(1);
        subtreeMutationMinDepth.setValue(5);
        subtreeMutationMinDepth.setStepButtonsVisible(true);
        subtreeMutationMinDepthTooltip = subtreeMutationMinDepth.getTooltip().withManual(true);
        subtreeMutationMinDepthTooltip.setText("test tooltip");
        subtreeMutationMinDepthHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        subtreeMutationMinDepthHelpBtn.addClickListener(event -> {
            subtreeMutationMinDepthTooltip.setOpened(!subtreeMutationMinDepthTooltip.isOpened());
        });
        subtreeMutationMinDepthLayout.add(subtreeMutationMinDepth, subtreeMutationMinDepthHelpBtn);

        // Max Depth
        subtreeMutationMaxDepthLayout = new HorizontalLayout();
        subtreeMutationMaxDepthLayout.setAlignItems(FlexComponent.Alignment.END);
        subtreeMutationMaxDepth = new IntegerField();
        subtreeMutationMaxDepth.setLabel("Max Depth");
        subtreeMutationMaxDepth.setMin(subtreeMutationMinDepth.getValue());
        subtreeMutationMaxDepth.setValue(subtreeMutationMinDepth.getValue());
        subtreeMutationMaxDepth.setStepButtonsVisible(true);
        subtreeMutationMaxDepthTooltip = subtreeMutationMaxDepth.getTooltip().withManual(true);
        subtreeMutationMaxDepthTooltip.setText("test tooltip");
        subtreeMutationMaxDepthHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        subtreeMutationMaxDepthHelpBtn.addClickListener(event -> {
            subtreeMutationMaxDepthTooltip.setOpened(!subtreeMutationMaxDepthTooltip.isOpened());
        });
        subtreeMutationMaxDepthLayout.add(subtreeMutationMaxDepth, subtreeMutationMaxDepthHelpBtn);

        // Group Builder
        subtreeMutationLayout.add(subtreeMutationMinDepthLayout, subtreeMutationMaxDepthLayout);
        subtreeMutationLayoutGroup.add(subtreeMutationTitle, subtreeMutationSubtitle, subtreeMutationLayout);
    }

    private void createKozaNodeSelectionGroup() {
        kozaNodeSelectionLayoutGroup = new VerticalLayout();
        kozaNodeSelectionLayoutGroup.setPadding(true);

        kozaNodeSelectionTitle = new Span("Koza Node Selection");

        kozaNodeSelectionLayout = new HorizontalLayout();
        kozaNodeSelectionLayout.setAlignItems(FlexComponent.Alignment.START);
        kozaNodeSelectionLayout.setSpacing(true);
        kozaNodeSelectionLayout.getThemeList().add("spacing-xl");

        // Terminals Prob
        kozaNodeSelectionTerminalsProbLayout = new HorizontalLayout();
        kozaNodeSelectionTerminalsProbLayout.setAlignItems(FlexComponent.Alignment.END);
        kozaNodeSelectionTerminalsProb = new NumberField();
        kozaNodeSelectionTerminalsProb.setLabel("Terminals Prob");
        kozaNodeSelectionTerminalsProb.setMin(0);
        kozaNodeSelectionTerminalsProb.setMax(1);
        kozaNodeSelectionTerminalsProb.setStep(0.05);
        kozaNodeSelectionTerminalsProb.setValue(0.1);
        kozaNodeSelectionTerminalsProb.setStepButtonsVisible(true);
        kozaNodeSelectionTerminalsProbTooltip = kozaNodeSelectionTerminalsProb.getTooltip().withManual(true);
        kozaNodeSelectionTerminalsProbTooltip.setText("test tooltip");
        kozaNodeSelectionTerminalsProbHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        kozaNodeSelectionTerminalsProbHelpBtn.addClickListener(event -> {
            kozaNodeSelectionTerminalsProbTooltip.setOpened(!kozaNodeSelectionTerminalsProbTooltip.isOpened());
        });
        kozaNodeSelectionTerminalsProbLayout.add(kozaNodeSelectionTerminalsProb, kozaNodeSelectionTerminalsProbHelpBtn);

        // Non-Terminals Prob
        kozaNodeSelectionNonTerminalsProbLayout = new HorizontalLayout();
        kozaNodeSelectionNonTerminalsProbLayout.setAlignItems(FlexComponent.Alignment.END);
        kozaNodeSelectionNonTerminalsProb = new NumberField();
        kozaNodeSelectionNonTerminalsProb.setLabel("Non-Terminals Prob");
        kozaNodeSelectionNonTerminalsProb.setMin(0);
        kozaNodeSelectionNonTerminalsProb.setMax(1);
        kozaNodeSelectionNonTerminalsProb.setStep(0.05);
        kozaNodeSelectionNonTerminalsProb.setValue(0.9);
        kozaNodeSelectionNonTerminalsProb.setStepButtonsVisible(true);
        kozaNodeSelectionNonTerminalsProbTooltip = kozaNodeSelectionNonTerminalsProb.getTooltip().withManual(true);
        kozaNodeSelectionNonTerminalsProbTooltip.setText("test tooltip");
        kozaNodeSelectionNonTerminalsProbHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        kozaNodeSelectionNonTerminalsProbHelpBtn.addClickListener(event -> {
            kozaNodeSelectionNonTerminalsProbTooltip.setOpened(!kozaNodeSelectionNonTerminalsProbTooltip.isOpened());
        });
        kozaNodeSelectionNonTerminalsProbLayout.add(kozaNodeSelectionNonTerminalsProb, kozaNodeSelectionNonTerminalsProbHelpBtn);

        // Root Prob
        kozaNodeSelectionRootProbLayout = new HorizontalLayout();
        kozaNodeSelectionRootProbLayout.setAlignItems(FlexComponent.Alignment.END);
        kozaNodeSelectionRootProb = new NumberField();
        kozaNodeSelectionRootProb.setLabel("Root Prob");
        kozaNodeSelectionRootProb.setMin(0);
        kozaNodeSelectionRootProb.setMax(1);
        kozaNodeSelectionRootProb.setStep(0.05);
        kozaNodeSelectionRootProb.setValue(0.0);
        kozaNodeSelectionRootProb.setStepButtonsVisible(true);
        kozaNodeSelectionRootProbTooltip = kozaNodeSelectionRootProb.getTooltip().withManual(true);
        kozaNodeSelectionRootProbTooltip.setText("test tooltip");
        kozaNodeSelectionRootProbHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        kozaNodeSelectionRootProbHelpBtn.addClickListener(event -> {
            kozaNodeSelectionRootProbTooltip.setOpened(!kozaNodeSelectionRootProbTooltip.isOpened());
        });
        kozaNodeSelectionNonTerminalsProbLayout.add(kozaNodeSelectionRootProb, kozaNodeSelectionRootProbHelpBtn);

        // Group Builder
        kozaNodeSelectionLayout.add(kozaNodeSelectionTerminalsProbLayout, kozaNodeSelectionNonTerminalsProbLayout, kozaNodeSelectionRootProbLayout);
        kozaNodeSelectionLayoutGroup.add(kozaNodeSelectionTitle, kozaNodeSelectionLayout);
    }
}