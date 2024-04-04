package com.decs.application.views.jobdashboard;

import com.decs.application.data.Job;
import com.decs.application.data.JobStatus;
import com.decs.application.data.Problem;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.services.SlaveManager;
import com.decs.application.utils.EvolutionEngine;
import com.decs.application.utils.ProblemCreator;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import ec.EvolutionState;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;

@PageTitle("Job Dashboard")
@Route(value = "job-dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class JobDashboardView extends Composite<VerticalLayout> {
    //Internal Data
    private EvolutionEngine evolutionEngine;
    private Job newJob;
    private SlaveManager slaveManager;
    private ObjectListDatabase objectListDatabase;
    // Available Problems
    private HorizontalLayout availableProblemsLayoutGroup;
    // Upper Group
    private VerticalLayout availableProblemsGridLayout;
    private HorizontalLayout availableProblemsUpperGroup;
    private Span availableProblemsTitle;
    private Button availableProblemsTitleUpdateBtn;
    private Grid<Problem> availableProblemsGrid;
    private ArrayList<Problem> factoryProblemsList;
    private ArrayList<Problem> userProblemsList;
    private GridListDataView<Problem> availableProblemsUpdater;
    // Job Progress Bar
    private ProgressBar jobProgressBar;
    private NativeLabel jobProgressBarLabelText;
    private Span jobProgressBarLabelValue;
    private HorizontalLayout jobProgressBarLabel;
    private VerticalLayout jobProgressBarComp;
    // Lower Widget Group
    private HorizontalLayout lowerWidgetGroup;
    // Job Queue
    private Grid<Job> jobQueueGrid;
    private GridListDataView<Job> jobQueueUpdater;
    private Span jobQueueLabel;
    private VerticalLayout jobQueue;
    // Job History List
    private Grid<Job> jobActivityGrid;
    private GridListDataView<Job> jobActivityUpdater;
    private Span jobActivityGridLabel;
    private VerticalLayout jobActivity;
    private Button jobActivitySolutionBtn;
    // Vertical Separator
    private Div verticalSeparator;
    // Job Metrics
    private HorizontalLayout jobMetricsGroupLayout;
    private VerticalLayout jobMetricsTitlesLayout;
    private VerticalLayout jobMetricsValuesLayout;
    private NativeLabel jobMetricsTitleLabel;
    private Span evaluationsTitle;
    private Span generationTitle;
    private Span breedThreadsTitle;
    private Span evaluationThreadsTitle;
    private Span randomSeedTitle;
    private Span connectedSlavesTitle;
    private Span evaluationsValue;
    private Span generationValue;
    private Span breedThreadsValue;
    private Span evaluationThreadsValue;
    private Span randomSeedValue;
    private Span connectedSlavesValue;
    private VerticalLayout jobMetrics;
    // Job Results
    private TextArea jobResults;
    private VerticalLayout jobResultsLayout;
    private NativeLabel jobResultsTitleLabel;
    // Start / Stop Buttons
    private VerticalLayout actionBtnGroup;
    private Button startBtn;
    private Button stopBtn;

    //Constructor
    public JobDashboardView(SlaveManager slaveManager, ObjectListDatabase objectListDatabase) {
        this.slaveManager = slaveManager;
        this.objectListDatabase = objectListDatabase;

        createAvailableProblems();
        createJobProgressBar();
        createLowerWidgetGroup();

        getContent().add(availableProblemsLayoutGroup, jobProgressBarComp, lowerWidgetGroup);
    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions
    private void createAvailableProblems() {
        // Available Problems
        availableProblemsLayoutGroup = new HorizontalLayout();
        availableProblemsLayoutGroup.setWidth("100%");
        availableProblemsLayoutGroup.setHeight("330px");

        // Grid Group
        availableProblemsGridLayout = new VerticalLayout();

        // Upper Group
        availableProblemsUpperGroup = new HorizontalLayout();
        availableProblemsUpperGroup.setWidth("100%");
        availableProblemsUpperGroup.setAlignItems(FlexComponent.Alignment.CENTER);
        availableProblemsUpperGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        availableProblemsTitle = new Span("Available Problems");

        Icon availableProblemsTitleUpdateBtnIcon = new Icon(VaadinIcon.REFRESH);
        availableProblemsTitleUpdateBtnIcon.setSize("20px");
        availableProblemsTitleUpdateBtn = new Button(availableProblemsTitleUpdateBtnIcon);
        availableProblemsTitleUpdateBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        availableProblemsTitleUpdateBtn.addClickListener(this::updateAvailableProblemsList);
        availableProblemsTitleUpdateBtn.setTooltipText("Refresh");
        availableProblemsTitleUpdateBtn.setWidth("15px");
        availableProblemsTitleUpdateBtn.setHeight("19px");

        availableProblemsUpperGroup.add(availableProblemsTitle, availableProblemsTitleUpdateBtn);

        // Available Problems Grid
        availableProblemsGrid = new Grid<>(Problem.class, false);
        availableProblemsGrid.setMaxHeight("300px");
        availableProblemsGrid.addColumn(Problem::getCode).setHeader("Code");
        availableProblemsGrid.addColumn(Problem::getFullName).setHeader("Name");
        availableProblemsGrid.addColumn(Problem::getType).setHeader("Type");

        // Problems Scan
        factoryProblemsList = ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER);
        userProblemsList = ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER);

        // Job Queue
        //jobQueueGrid = new Grid<>(Job.class, false);
        //jobQueueGrid.addColumn(Job::getId).setHeader("ID");
        //jobQueueGrid.addColumn(Job::getName).setHeader("Name");
        //jobQueueUpdater = jobQueueGrid.getListDataView();

        //jobQueueLabel = new Span("Job Queue");

        //jobQueue = new VerticalLayout(jobQueueLabel, jobQueueGrid);

        // Problem List build
        availableProblemsUpdater = availableProblemsGrid.getListDataView();
        availableProblemsGrid.setDataProvider(objectListDatabase.getAvailableProblemsDataProvider());

        // Grid Group Build
        availableProblemsGridLayout.add(availableProblemsUpperGroup, availableProblemsGrid);

        // Group Builder
        availableProblemsLayoutGroup.add(availableProblemsGridLayout);
    }

    private void createJobProgressBar() {
        // Job Progress Bar
        jobProgressBar = new ProgressBar();
        jobProgressBar.setValue(0);

        jobProgressBarLabelText = new NativeLabel("Job Progress");
        jobProgressBarLabelText.setId("jpbLabel");
        jobProgressBar.getElement().setAttribute("aria-labelledby", "jpbLabel");

        jobProgressBarLabelValue = new Span("50%");

        jobProgressBarLabel = new HorizontalLayout(jobProgressBarLabelText, jobProgressBarLabelValue);
        jobProgressBarLabel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        jobProgressBarComp = new VerticalLayout(jobProgressBarLabel, jobProgressBar);
    }

    private void createLowerWidgetGroup() {
        // Lower Widget Group
        lowerWidgetGroup = new HorizontalLayout();
        lowerWidgetGroup.setWidthFull();

        // Job Activity list
        jobActivityGrid = new Grid<>(Job.class, false);
        jobActivityGrid.addColumn(Job::getId).setHeader("ID").setSortable(true);
        jobActivityGrid.addColumn(Job::getName).setHeader("Name");
        jobActivityGrid.addColumn(createJobActivityStatusRenderer()).setHeader("Status");
        jobActivityGrid.addColumn(createJobActivitySolutionRenderer()).setHeader("Solution");
        jobActivityGrid.setMinWidth("280px");
        jobActivityUpdater = jobActivityGrid.getListDataView();
        jobActivityGrid.setDataProvider(objectListDatabase.getJobActivityDataProvider());


        jobActivityGridLabel = new Span("Job Activity");

        jobActivity = new VerticalLayout(jobActivityGridLabel, jobActivityGrid);
        jobActivity.setMinWidth("500px");

        // Vertical Separator
        verticalSeparator = new Div();
        verticalSeparator.getStyle().set("border", "1px solid black");

        // Job Metrics
        jobMetricsGroupLayout = new HorizontalLayout();
        jobMetricsGroupLayout.getStyle().set("border", "1px solid black");
        //jobMetricsGroupLayout.setMinWidth("200px");
        //jobMetricsGroupLayout.setMaxWidth("230px");

        jobMetricsTitlesLayout = new VerticalLayout();
        jobMetricsTitlesLayout.setMinWidth("180px");
        jobMetricsValuesLayout = new VerticalLayout();

        jobMetricsTitleLabel = new NativeLabel("Metrics");

        evaluationsTitle = new Span("Evaluations");
        generationTitle = new Span("Generation");
        breedThreadsTitle = new Span("Breed Threads");
        evaluationThreadsTitle = new Span("Evaluation Threads");
        randomSeedTitle = new Span("Random Seed");
        connectedSlavesTitle = new Span("Connected Slaves");

        evaluationsValue = new Span("0");
        generationValue = new Span("0");
        breedThreadsValue = new Span("0");
        evaluationThreadsValue = new Span("0");
        randomSeedValue = new Span("0");
        connectedSlavesValue = new Span("0");

        jobMetricsTitlesLayout.add(evaluationsTitle, breedThreadsTitle, evaluationThreadsTitle, randomSeedTitle, connectedSlavesTitle);
        jobMetricsValuesLayout.add(evaluationsValue, breedThreadsValue, evaluationThreadsValue, randomSeedValue, connectedSlavesValue);
        jobMetricsGroupLayout.add(jobMetricsTitlesLayout, jobMetricsValuesLayout);
        jobMetrics = new VerticalLayout(jobMetricsTitleLabel, jobMetricsGroupLayout);

        // Job Results
        //jobResults = new TextArea();
        //jobResults.setLabel("Results");
        //jobResults.setReadOnly(true);
        //jobResults.setWidthFull();

        // Start / Stop Buttons
        actionBtnGroup = new VerticalLayout();
        actionBtnGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        actionBtnGroup.setAlignItems(FlexComponent.Alignment.CENTER);

        startBtn = new Button("Start");
        startBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        startBtn.setWidth("130px");
        startBtn.setHeight("50px");
        startBtn.setDisableOnClick(true);
        startBtn.addClickListener(this::startProblem);

        stopBtn = new Button("Stop");
        stopBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        stopBtn.setWidth("100px");
        stopBtn.setHeight("40px");
        stopBtn.setEnabled(false);

        actionBtnGroup.add(startBtn, stopBtn);

        // Lower Widget Group Builder
        lowerWidgetGroup.add(jobActivity, verticalSeparator, jobMetrics, actionBtnGroup);
    }

    public void updateInferenceResults(UI ui, EvolutionState evaluatedState) {
        ui.access(() -> {
            newJob.setStatus(JobStatus.FINISHED);
            jobActivityUpdater.refreshItem(newJob);
            startBtn.setEnabled(true);
            jobActivitySolutionBtn.setEnabled(true);
        });
    }
    public void updateProgressBar(UI ui, float progress) {
        ui.access(() -> {
            jobProgressBar.setValue(progress);
            jobProgressBarLabelValue.setText(String.valueOf((int) (progress * 100)));
        });
    }
    public void setJobMetrics(UI ui, int breedThreads, int evalThreads) {
        ui.access(() -> {
            breedThreadsValue.setText(String.valueOf(breedThreads));
            evaluationThreadsValue.setText(String.valueOf(evalThreads));
            //randomSeedValue.setText(String.valueOf(randomSeed));
        });
    }
    public void updateJobMetrics(UI ui, int evaluations, int generation) {
        ui.access(() -> {
            evaluationsValue.setText(String.valueOf(evaluations));
            generationValue.setText(String.valueOf(generation));
            connectedSlavesValue.setText(String.valueOf(slaveManager.getConnectedSlaves()));
        });
    }

    // Event Handlers
    private void startProblem(ClickEvent<Button> event) {
        Problem selectedProblem = availableProblemsGrid.getSelectedItems().iterator().next();
        objectListDatabase.setSelectedProblem(selectedProblem);
        newJob = new Job(selectedProblem.getCode(), selectedProblem.getDistribution());
        newJob.setStatus(JobStatus.RUNNING);

        objectListDatabase.addJobActivity(newJob);
        jobActivityGrid.getDataProvider().refreshAll();
        evolutionEngine = new EvolutionEngine(selectedProblem.getParamsFile(), newJob, event.getSource().getUI().orElseThrow(), this, slaveManager);
        evolutionEngine.start();
    }

    private void updateAvailableProblemsList(ClickEvent<Button> event) {
        System.err.println("REFRESH");
        availableProblemsGrid.getDataProvider().refreshAll();
    }

    private Dialog buildSolutionsDialog(Job currentJob) {
        Dialog solutionDialog = new Dialog();
        solutionDialog.setHeaderTitle("Job Solution");
        solutionDialog.setMinWidth("60%");
        solutionDialog.setMaxWidth("60%");
        solutionDialog.setMinHeight("80%");
        solutionDialog.setMaxHeight("80%");

        // Upper Tabs
        TabSheet solutionTabs = new TabSheet();
        solutionTabs.setWidthFull();
        solutionTabs.setHeightFull();
        Tab logTab = new Tab("Short Log");
        Tab statisticsTab = new Tab("Extended Log");

        // Log Tab
        Paragraph logText = new Paragraph();
        logText.setText(currentJob.getJobLog());
        logText.getElement().getStyle().set("white-space", "pre");

        // Statistics Tab
        Paragraph statisticsText = new Paragraph();
        statisticsText.setText(currentJob.getStatisticsLog());
        statisticsText.getElement().getStyle().set("white-space", "pre");

        // Tabsheet Builder
        solutionTabs.add(logTab, logText);
        solutionTabs.add(statisticsTab, statisticsText);

        // Dialog Builder
        solutionDialog.add(solutionTabs);

        return solutionDialog;
    }

    // Component Renderers
    private static ComponentRenderer<Span, Job> createJobActivityStatusRenderer() {
        return new ComponentRenderer<>(Span::new, jobActivityStatusUpdater);
    }
    private static final SerializableBiConsumer<Span, Job> jobActivityStatusUpdater = ( span, job) -> {
        String theme = String.format("badge %s", job.getStatus().getBadgeType());
        span.getElement().setAttribute("theme", theme);
        span.setText(job.getStatus().toString());
    };

    private ComponentRenderer<Button, Job> createJobActivitySolutionRenderer() {
        return new ComponentRenderer<>(Button::new, jobActivitySolutionButton);
    }
    private final SerializableBiConsumer<Button, Job> jobActivitySolutionButton = ( button, currentJob) -> {
        jobActivitySolutionBtn = button;
        Icon btnIcon = new Icon(VaadinIcon.DASHBOARD);
        button.setIcon(btnIcon);
        //button.setEnabled(false);
        button.addClickListener(event -> {
            buildSolutionsDialog(currentJob).open();
        });
    };
}