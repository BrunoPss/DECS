package com.decs.application.views.jobdashboard;

import com.decs.application.data.Job;
import com.decs.application.data.Problem;
import com.decs.application.utils.EvolutionEngine;
import com.decs.application.utils.ProblemCreator;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.MainLayout;
import com.decs.application.views.ProblemEditor.tabs.StatisticsType;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import ec.EvolutionState;
import ec.Evolve;
import ec.app.majority.func.E;
import ec.util.Output;
import ec.util.ParameterDatabase;
import jakarta.annotation.security.PermitAll;

import java.io.IOException;
import java.util.ArrayList;

@PageTitle("Job Dashboard")
@Route(value = "job-dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class JobDashboardView extends Composite<VerticalLayout> {
    //Internal Data
    // Available Problems
    private VerticalLayout availableProblemsLayoutGroup;
    // Upper Group
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
    private Span jobQueueLabel;
    private VerticalLayout jobQueue;
    // Job History List
    private Grid<Job> jobHistoryGrid;
    private Span jobHistoryGridLabel;
    private VerticalLayout jobHistory;
    // Vertical Separator
    private Div verticalSeparator;
    // Job Metrics
    private VerticalLayout jobMetricsLayout;
    private NativeLabel jobMetricsTitleLabel;
    private VerticalLayout jobMetrics;
    // Job Results
    private VerticalLayout jobResultsLayout;
    private NativeLabel jobResultsTitleLabel;
    private VerticalLayout jobResults;
    private VerticalLayout metricsResultsGroup;
    // Start / Stop Buttons
    private VerticalLayout actionBtnGroup;
    private Button startBtn;
    private Button stopBtn;

    //Constructor
    public JobDashboardView() {
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
        availableProblemsLayoutGroup = new VerticalLayout();

        // Upper Group
        availableProblemsUpperGroup = new HorizontalLayout();
        availableProblemsUpperGroup.setWidth("100%");
        availableProblemsUpperGroup.setAlignItems(FlexComponent.Alignment.CENTER);
        availableProblemsUpperGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        availableProblemsTitle = new Span("Available Problems");

        availableProblemsTitleUpdateBtn = new Button(new Icon(VaadinIcon.REFRESH));
        availableProblemsTitleUpdateBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        availableProblemsTitleUpdateBtn.setTooltipText("Refresh");

        availableProblemsUpperGroup.add(availableProblemsTitle, availableProblemsTitleUpdateBtn);

        // Available Problems Grid
        availableProblemsGrid = new Grid<>(Problem.class, false);
        availableProblemsGrid.setMaxHeight("300px");
        availableProblemsGrid.addColumn(Problem::getCode).setHeader("Code");
        availableProblemsGrid.addColumn(Problem::getFullName).setHeader("Name");
        availableProblemsGrid.addColumn(Problem::getType).setHeader("Type");

        //problemList = new ArrayList<>();
        //problemList.add(new Problem("P1", "Problem 1", "GP"));
        //problemList.add(new Problem("P2", "Problem 2", "GP"));
        //problemList.add(new Problem("P3", "Problem 3", "GA"));
        //problemList.add(new Problem("P4", "Problem 4", "GA"));
        //problemList.add(new Problem("P5", "Problem 5", "GP"));
        //Problem p6 = new Problem("P6", "Problem 6", "GP");
        //problemList.add(p6);
        //availableProblemsUpdater = availableProblemsGrid.setItems(problemList);

        // Problems Scan
        factoryProblemsList = ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER);
        userProblemsList = ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER);

        // Problem List build
        availableProblemsUpdater = availableProblemsGrid.setItems(factoryProblemsList);

        // Group Builder
        availableProblemsLayoutGroup.add(availableProblemsUpperGroup, availableProblemsGrid);
    }

    private void createJobProgressBar() {
        // Job Progress Bar
        jobProgressBar = new ProgressBar();
        jobProgressBar.setValue(0.5);

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

        // Job Queue
        jobQueueGrid = new Grid<>(Job.class, false);
        jobQueueGrid.addColumn(Job::getId).setHeader("ID");
        jobQueueGrid.addColumn(Job::getName).setHeader("Name");
        Job job1 = new Job("Job 1");
        jobQueueGrid.setItems(job1);
        jobQueueGrid.setMinWidth("250px");

        jobQueueLabel = new Span("Job Queue");

        jobQueue = new VerticalLayout(jobQueueLabel, jobQueueGrid);

        // Job History list
        jobHistoryGrid = new Grid<>(Job.class, false);
        jobHistoryGrid.addColumn(Job::getId).setHeader("ID");
        jobHistoryGrid.addColumn(Job::getName).setHeader("Name");
        jobHistoryGrid.setItems(job1);
        jobHistoryGrid.setMinWidth("250px");

        jobHistoryGridLabel = new Span("Job History");

        jobHistory = new VerticalLayout(jobHistoryGridLabel, jobHistoryGrid);

        // Vertical Separator
        verticalSeparator = new Div();
        verticalSeparator.getStyle().set("border", "1px solid black");

        // Job Metrics
        jobMetricsLayout = new VerticalLayout();
        jobMetricsLayout.getStyle().set("border", "1px solid black");
        jobMetricsLayout.setMinWidth("250px");

        jobMetricsTitleLabel = new NativeLabel("Metrics");
        Span info1 = new Span("Info 1");
        Span info2 = new Span("Info 2");
        Span info3 = new Span("Info 3");

        jobMetricsLayout.add(info1, info2, info3);
        jobMetrics = new VerticalLayout(jobMetricsTitleLabel, jobMetricsLayout);

        // Job Results
        jobResultsLayout = new VerticalLayout();
        jobResultsLayout.getStyle().set("border", "1px solid black");
        jobResultsLayout.setMinWidth("250px");

        jobResultsTitleLabel = new NativeLabel("Results");
        Span result1 = new Span("Result 1");
        Span result2 = new Span("Result 2");

        jobResultsLayout.add(result1, result2);
        jobResults = new VerticalLayout(jobResultsTitleLabel, jobResultsLayout);

        metricsResultsGroup = new VerticalLayout(jobMetrics, jobResults);

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
        lowerWidgetGroup.add(jobQueue, jobHistory, verticalSeparator, metricsResultsGroup, actionBtnGroup);
    }

    // Event Handlers
    private void startProblem(ClickEvent<Button> event) {
        Problem selectedProblem = availableProblemsGrid.getSelectedItems().iterator().next();

        EvolutionEngine evolutionEngine = new EvolutionEngine(selectedProblem.getParamsFile());
        evolutionEngine.run();

        jobResultsLayout.add(new Span(String.valueOf(evolutionEngine.getFitness(StatisticsType.SIMPLE))));
    }
}