package com.decs.application.views.jobdashboard;

import com.decs.application.data.Job;
import com.decs.application.data.JobStatus;
import com.decs.application.data.Problem;
import com.decs.application.data.databases.MainDatabase;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.communication.PushMode;
import ec.EvolutionState;
import ec.Statistics;
import ec.simple.SimpleStatistics;
import jakarta.annotation.security.PermitAll;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@PageTitle("Job Dashboard")
@Route(value = "job-dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class JobDashboardView extends Composite<VerticalLayout> {
    //Internal Data
    private EvolutionEngine evolutionEngine;
    private Job newJob;
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
    private VerticalLayout jobMetricsLayout;
    private NativeLabel jobMetricsTitleLabel;
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
        availableProblemsGrid.setDataProvider(MainDatabase.getAvailableProblemsDataProvider());

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
        jobActivityGrid.setMinWidth("250px");
        jobActivityUpdater = jobActivityGrid.getListDataView();
        jobActivityGrid.setDataProvider(MainDatabase.getJobActivityDataProvider());


        jobActivityGridLabel = new Span("Job Activity");

        jobActivity = new VerticalLayout(jobActivityGridLabel, jobActivityGrid);

        // Vertical Separator
        verticalSeparator = new Div();
        verticalSeparator.getStyle().set("border", "1px solid black");

        // Job Metrics
        jobMetricsLayout = new VerticalLayout();
        jobMetricsLayout.getStyle().set("border", "1px solid black");
        jobMetricsLayout.setMinWidth("200px");
        jobMetricsLayout.setMaxWidth("230px");

        jobMetricsTitleLabel = new NativeLabel("Metrics");
        Span info1 = new Span("Evaluations");
        Span info2 = new Span("Breed Threads");
        Span info3 = new Span("Evaluation Threads");
        Span info4 = new Span("Random Seed");
        Span info5 = new Span("Connected Slaves");

        jobMetricsLayout.add(info1, info2, info3, info4, info5);
        jobMetrics = new VerticalLayout(jobMetricsTitleLabel, jobMetricsLayout);

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

    // Event Handlers
    private void startProblem(ClickEvent<Button> event) {
        Problem selectedProblem = availableProblemsGrid.getSelectedItems().iterator().next();
        newJob = new Job(selectedProblem.getCode());
        newJob.setStatus(JobStatus.RUNNING);

        MainDatabase.addJobActivity(newJob);
        jobActivityGrid.getDataProvider().refreshAll();

        evolutionEngine = new EvolutionEngine(selectedProblem.getParamsFile(), newJob, event.getSource().getUI().orElseThrow(), this);
        evolutionEngine.start();
    }

    private void updateAvailableProblemsList(ClickEvent<Button> event) {
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