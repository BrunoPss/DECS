package com.decs.application.views.jobdashboard;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.job.Job;
import com.decs.application.data.job.JobStatus;
import com.decs.application.data.problem.Problem;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.services.SessionManager;
import com.decs.application.services.SlaveManager;
import com.decs.application.engines.EvolutionEngine;
import com.decs.application.utils.ProblemCreator;
import com.decs.application.services.Timer;
import com.decs.application.utils.confFile.ProblemFileManager;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import ec.EvolutionState;
import jakarta.annotation.security.PermitAll;
import org.springframework.boot.ApplicationArguments;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@PageTitle("Job Dashboard")
@Route(value = "job-dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class JobDashboardView extends Composite<VerticalLayout> {
    //Internal Data
    private UI ui;
    private EvolutionEngine evolutionEngine;
    private Job newJob;
    private SlaveManager slaveManager;
    private ObjectListDatabase objectListDatabase;
    private Timer timer;
    private SessionManager sessionManager;
    private String[] args;
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
    private Button problemEditorBtn;
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
    // Problem Editor
    private Dialog problemEditorDialog;
    private VerticalLayout problemEditorLayoutGroup;
    private HorizontalLayout fileActionsLayoutGroup;
    private HorizontalLayout fileActionsButtonLayout;
    private Select<String> fileSelector;
    private Button fileActionsOpenBtn;
    private Button fileActionsSaveBtn;
    private Button fileActionsDiscardBtn;
    private TextArea textEditor;
    // Busy Notification
    private Notification busyNotification;

    //Constructor
    public JobDashboardView(SlaveManager slaveManager, ObjectListDatabase objectListDatabase, Timer timer, SessionManager sessionManager, ApplicationArguments args) {
        this.slaveManager = slaveManager;
        this.objectListDatabase = objectListDatabase;
        this.timer = timer;
        this.sessionManager = sessionManager;
        this.args = args.getSourceArgs();

        createAvailableProblems();
        createJobProgressBar();
        createLowerWidgetGroup();

        checkServerStatus();

        // Add Busy Listeners
        this.ui = UI.getCurrent();
        //System.out.println("UIS : " + VaadinSession.getCurrent().getUIs());
        //System.out.println("UI VAR : " + this.ui);
        addEvolutionEngineBusyListener();

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
        availableProblemsGrid.setId("availableProblemsGrid");
        availableProblemsGrid.setMaxHeight("300px");
        availableProblemsGrid.addColumn(Problem::getCode).setHeader("Code");
        availableProblemsGrid.addColumn(Problem::getFullName).setHeader("Name");
        availableProblemsGrid.addColumn(Problem::getType).setHeader("Type");
        availableProblemsGrid.addColumn(Problem::getOrigin).setHeader("Origin");
        availableProblemsGrid.addColumn(Problem::getDistribution).setHeader("Distribution");
        availableProblemsGrid.addColumn(createProblemEditorRenderer()).setHeader("Editor");

        // Problems Scan
        factoryProblemsList = ProblemCreator.problemScanner(FilePathConstants.FACTORY_PARAMS_FOLDER);
        userProblemsList = ProblemCreator.problemScanner(FilePathConstants.USER_PARAMS_FOLDER);

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
        jobProgressBar.setId("jobProgressBar");
        jobProgressBar.setValue(0);

        jobProgressBarLabelText = new NativeLabel("Job Progress");
        jobProgressBarLabelText.setId("jpbLabel");
        jobProgressBar.getElement().setAttribute("aria-labelledby", "jpbLabel");

        jobProgressBarLabelValue = new Span("0%");

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

        // Start / Stop Buttons
        actionBtnGroup = new VerticalLayout();
        actionBtnGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        actionBtnGroup.setAlignItems(FlexComponent.Alignment.CENTER);

        startBtn = new Button("Start");
        startBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        startBtn.setWidth("130px");
        startBtn.setHeight("50px");
        startBtn.setDisableOnClick(true);
        startBtn.addClickListener( evt -> {
            try {
                Problem selectedProblem = availableProblemsGrid.getSelectedItems().iterator().next();
                buildJobNameDialog().open();
            } catch (NoSuchElementException e) {
                System.err.println("No problem selected!");
                Notification errNotification = Notification.show("No problem selected!");
                errNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                startBtn.setEnabled(true);
            } catch (Exception e) {
                System.err.println("Exception in startBtn Click Listener");
                e.printStackTrace();
            }
        });

        stopBtn = new Button("Stop");
        stopBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        stopBtn.setWidth("100px");
        stopBtn.setHeight("40px");
        stopBtn.setEnabled(false);

        actionBtnGroup.add(startBtn, stopBtn);

        // Lower Widget Group Builder
        lowerWidgetGroup.add(jobActivity, verticalSeparator, jobMetrics, actionBtnGroup);
    }

    private void checkServerStatus() {
        // Start Btn
        startBtn.setEnabled(!sessionManager.getEvolutionEngineBusy());
    }

    public void updateInferenceResults(UI ui, EvolutionState evaluatedState) {
        ui.access(() -> {
            //System.out.println("UI results : " + ui);
            newJob.setStatus(JobStatus.FINISHED);
            jobActivityGrid.setItems(objectListDatabase.getJobActivityDataProvider());
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
    private void startProblem(String jobName) {
        ui.access(() -> {
            try {
                Problem selectedProblem = availableProblemsGrid.getSelectedItems().iterator().next();
                objectListDatabase.setSelectedProblem(selectedProblem);
                newJob = new Job(jobName, selectedProblem.getDistribution());

                if (newJob.getDistribution() != DistributionType.LOCAL && slaveManager.getConnectedSlaves() == 0) {
                    System.err.println("No connected Slaves");
                    Notification noSlavesNotification = Notification.show("No Slaves Connected");
                    noSlavesNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    newJob.setStatus(JobStatus.FINISHED);
                    jobActivityUpdater.refreshItem(newJob);
                    startBtn.setEnabled(true);

                    return;
                }

                newJob.setStatus(JobStatus.RUNNING);

                objectListDatabase.addJobActivity(newJob);
                jobActivityGrid.setItems(objectListDatabase.getJobActivityDataProvider());

                evolutionEngine = new EvolutionEngine(selectedProblem.getParamsFile(), newJob, ui, this, slaveManager, timer, sessionManager, args);

                evolutionEngine.start();
            } catch (NoSuchElementException e) {
                System.err.println("No selected element at startProblem");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Exception at startProblem");
                e.printStackTrace();
            }
        });
    }

    private void updateAvailableProblemsList(ClickEvent<Button> event) {
        this.ui.access(() -> {
            System.err.println("Available Problems Refresh");
            objectListDatabase.updateAvailableProblems();
            availableProblemsGrid.setItems(objectListDatabase.getAvailableProblemsDataProvider());
        });
    }

    private Dialog buildJobNameDialog() {
        Dialog jobNameDialog = new Dialog();
        jobNameDialog.setHeaderTitle("Job Name");
        jobNameDialog.setCloseOnOutsideClick(false);
        jobNameDialog.setCloseOnEsc(false);

        Span jobNameText = new Span("Please insert the name for the job.");
        TextField jobName = new TextField();
        VerticalLayout jobNameLayout = new VerticalLayout(jobNameText, jobName);
        jobNameDialog.add(jobNameLayout);

        Button jobDialogBtn = new Button("Continue");
        jobDialogBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        jobDialogBtn.addClickListener( evt -> {
            if (!jobName.getValue().isBlank()) {
                jobNameDialog.close();
                startProblem(jobName.getValue());
            }
            else {
                Notification blankNotification = Notification.show("Please fill the job name field!");
                blankNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        Button jobNameCancelBtn = new Button("Cancel");
        jobNameCancelBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        jobNameCancelBtn.addClickListener( evt -> {
            startBtn.setEnabled(true);
            jobNameDialog.close();
        });

        jobNameDialog.getFooter().add(jobNameCancelBtn, jobDialogBtn);

        return jobNameDialog;
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
        Tab overviewTab = new Tab("Overview");
        Tab logTab = new Tab("Short Log");
        Tab statisticsTab = new Tab("Extended Log");

        // Overview Tab
        HorizontalLayout labelValueLayout = new HorizontalLayout();
        VerticalLayout labelLayout = new VerticalLayout();
        labelLayout.setWidth("30%");
        VerticalLayout valueLayout = new VerticalLayout();
        Span jobNameLabel = new Span("Job Name:");
        Span jobWallClockTimeLabel = new Span("Wall-Clock Time:");
        Span jobCPUTimeLabel = new Span("CPU Time:");
        //Span jobHeapMemoryLabel = new Span("Heap Memory Used:");
        //Span jobNonHeapMemoryLabel = new Span("Non Heap Memory Used:");
        labelLayout.add(jobNameLabel, jobWallClockTimeLabel, jobCPUTimeLabel);
        Span jobNameValue = new Span(currentJob.getName());
        Span jobWallClockTimeValue = new Span(String.format("%d ms (%d s)", Timer.nano2milis(currentJob.getWallClockTime()), Timer.nano2seconds(currentJob.getWallClockTime())));
        Span jobCPUTimeValue = new Span(String.format("%d ms (%d s)", Timer.nano2milis(currentJob.getCpuTime()), Timer.nano2seconds(currentJob.getCpuTime())));
        //Span jobHeapMemoryValue = new Span(String.format("%d bytes (%d mB)", SystemManager.bytes2megaBytes(currentJob.getHeapMemoryUsage()), SystemManager.bytes2gigaBytes(currentJob.getHeapMemoryUsage())));
        //Span jobNonHeapMemoryValue = new Span(String.format("%d bytes (%d mB)", SystemManager.bytes2megaBytes(currentJob.getNonHeapMemoryUsage()), SystemManager.bytes2gigaBytes(currentJob.getNonHeapMemoryUsage())));
        valueLayout.add(jobNameValue, jobWallClockTimeValue, jobCPUTimeValue);
        labelValueLayout.add(labelLayout, valueLayout);

        // Log Tab
        Paragraph logText = new Paragraph();
        logText.setText(currentJob.getJobLog());
        logText.getElement().getStyle().set("white-space", "pre");

        // Statistics Tab
        Paragraph statisticsText = new Paragraph();
        statisticsText.setText(currentJob.getStatisticsLog());
        statisticsText.getElement().getStyle().set("white-space", "pre");

        // Tabsheet Builder
        solutionTabs.add(overviewTab, labelValueLayout);
        solutionTabs.add(logTab, logText);
        solutionTabs.add(statisticsTab, statisticsText);

        // Dialog Builder
        solutionDialog.add(solutionTabs);

        return solutionDialog;
    }

    private Dialog buildProblemEditor(Problem currentProblem) {
        problemEditorDialog = new Dialog();
        problemEditorDialog.setHeaderTitle("Problem Editor");
        problemEditorDialog.setMinWidth("60%");
        problemEditorDialog.setMaxWidth("60%");
        problemEditorDialog.setMinHeight("80%");
        problemEditorDialog.setMaxHeight("80%");

        try {

            // Problem Editor Layout Group
            problemEditorLayoutGroup = new VerticalLayout();

            // File Actions Layout Group
            fileActionsLayoutGroup = new HorizontalLayout();
            fileActionsLayoutGroup.setWidthFull();
            fileActionsLayoutGroup.setAlignItems(FlexComponent.Alignment.END);
            fileActionsLayoutGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

            // File Actions Button Layout
            fileActionsButtonLayout = new HorizontalLayout();

            // File Selector
            fileSelector = new Select<>();
            fileSelector.setLabel("File Selector");

            // Get Files inside problem root folder and remove the .conf file
            ArrayList<String> filesList = ProblemFileManager.getFileNamesInFolder(currentProblem.getRootFolder());
            if (filesList != null) {
                filesList.removeIf(str -> str.endsWith(".conf"));
            }

            fileSelector.setItems(filesList);
            fileSelector.addValueChangeListener(event -> {
                fileActionsOpenBtn.setEnabled(true);
            });

            // File Actions Buttons
            // Open Button
            fileActionsOpenBtn = new Button();
            fileActionsOpenBtn.setText("Open");
            fileActionsOpenBtn.addClickListener(event -> {
                // Disable Open Button
                fileActionsOpenBtn.setEnabled(false);
                // Clear Text Editor
                textEditor.clear();
                try {
                    String content = Files.readString(Path.of(currentProblem.getRootFolder().getPath() + "/" + fileSelector.getValue()));
                    textEditor.setValue(content);
                    textEditor.setLabel(fileSelector.getValue());
                    //System.out.println("FILE TO OPEN: " + Path.of(currentProblem.getRootFolder().getPath()+"/"+fileSelector.getValue()));
                } catch (IOException e) {
                    System.err.println("IO Exception while opening file to edit");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.err.println("Exception at fileActionsOpenBtn Click Listener");
                    e.printStackTrace();
                }
            });

            // Save Button
            fileActionsSaveBtn = new Button();
            fileActionsSaveBtn.setText("Save");
            fileActionsSaveBtn.addClickListener(event -> {
                // Build confirmation Dialog
                ConfirmDialog fileSaveConfirmDialog = new ConfirmDialog();
                fileSaveConfirmDialog.setHeader("Save File");
                fileSaveConfirmDialog.setText(
                        String.format("Do you really want to save your changes to the file %s ?", textEditor.getLabel())
                );
                fileSaveConfirmDialog.setCancelable(true);

                fileSaveConfirmDialog.setConfirmText("Save");
                fileSaveConfirmDialog.addConfirmListener(saveEvent -> {
                    // Save File
                    ProblemFileManager.replaceFileContent(currentProblem.getRootFolder().getPath() + "/" + textEditor.getLabel(), textEditor.getValue());

                    // Confirmation Notification
                    Notification fileSavedNotification = Notification.show(String.format("File %s successfully saved!", textEditor.getLabel()));
                    fileSavedNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                });

                // Show Confirmation Dialog
                fileSaveConfirmDialog.open();
            });

            // Cancel Button
            fileActionsDiscardBtn = new Button();
            fileActionsDiscardBtn.setText("Discard");
            fileActionsDiscardBtn.addClickListener(event -> {
                fileSelector.clear();
                textEditor.clear();
            });

            // File Actions Layout Builder
            fileActionsButtonLayout.add(fileActionsOpenBtn, fileActionsSaveBtn, fileActionsDiscardBtn);
            fileActionsLayoutGroup.add(fileSelector, fileActionsButtonLayout);

            // Text Editor
            textEditor = new TextArea();
            textEditor.setWidthFull();
            textEditor.setLabel("File Name");

            // Layout Builder
            problemEditorLayoutGroup.add(fileActionsLayoutGroup, textEditor);
            // Dialog Builder
            problemEditorDialog.add(problemEditorLayoutGroup);

        } catch (NullPointerException e) {
            System.err.println("Null Pointer Exception at build Problem editor");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Null Pointer Exception at build Problem editor");
            e.printStackTrace();
        }

        return problemEditorDialog;
    }

    public void addEvolutionEngineBusyListener() {
        sessionManager.addPropertyChangeListener(SessionManager.EVOLUTION_ENGINE_TYPE, this::evolutioEngineBusyEvent, this.ui);
    }
    private void evolutioEngineBusyEvent(PropertyChangeEvent evt) {
        this.ui.access(() -> {
            //System.out.println("UI Listener : " + this.ui);
            //System.out.println("UI STARTER: " + sessionManager.getStarterUI());
            if (this.ui != sessionManager.getStarterUI()) {
                // Block Start Button
                startBtn.setEnabled(!((boolean) evt.getNewValue()));

                // Busy Notification
                if ((boolean) evt.getNewValue()) {
                    busyNotification = new Notification();
                    busyNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    busyNotification.setDuration(0);
                    busyNotification.setPosition(Notification.Position.TOP_CENTER);
                    Icon busyNotificationIcon = VaadinIcon.WARNING.create();
                    Button moreInfoBtn = new Button("Info", clickEvent -> {
                        createBusyInfoDialog().open();
                    });
                    moreInfoBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");
                    HorizontalLayout busyNotLayout = new HorizontalLayout(busyNotificationIcon, new Text("Server is busy at the moment!"), moreInfoBtn);
                    busyNotLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                    busyNotification.add(busyNotLayout);
                    busyNotification.open();
                }
                else {
                    // Close Busy Notification
                    busyNotification.close();

                    // Create Server Free Notification
                    Notification freeNotification = new Notification();
                    freeNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    freeNotification.setDuration(4000);
                    freeNotification.setPosition(Notification.Position.TOP_CENTER);
                    Icon freeIcon = VaadinIcon.CHECK_CIRCLE.create();
                    HorizontalLayout freeNotLayout = new HorizontalLayout(freeIcon, new Text("Server is now free!"), createCloseNotificatonBtn(freeNotification));
                    freeNotLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                    freeNotification.add(freeNotLayout);
                    freeNotification.open();
                }
            }
        });
    }

    private Dialog createBusyInfoDialog() {
        Dialog busyInfoDialog = new Dialog();
        busyInfoDialog.setWidth("50%");
        H2 busyInfoDialogHeadline = new H2("Server Busy");
        Paragraph busyInfoDialogText = new Paragraph(
                "The server is processing another task at this moment, please wait until the current task is finished." +
                        "You will be notified when the server is free again." +
                        "Thank you for understanding");
        Button busyInfoDialogCloseBtn = new Button("Close");
        busyInfoDialogCloseBtn.addClickListener(e -> busyInfoDialog.close());
        VerticalLayout busyInfoDialogLayout = new VerticalLayout(busyInfoDialogHeadline, busyInfoDialogText, busyInfoDialogCloseBtn);
        busyInfoDialogLayout.setAlignSelf(FlexComponent.Alignment.END, busyInfoDialogCloseBtn);
        busyInfoDialogLayout.setWidthFull();
        busyInfoDialog.add(busyInfoDialogLayout);

        return busyInfoDialog;
    }
    private static Button createCloseNotificatonBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(), clickEvent -> {
            notification.close();
        });
        closeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        return closeBtn;
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
    private final SerializableBiConsumer<Button, Job> jobActivitySolutionButton = ( button, currentJob ) -> {
        jobActivitySolutionBtn = button;
        Icon btnIcon = new Icon(VaadinIcon.DASHBOARD);
        jobActivitySolutionBtn.setIcon(btnIcon);
        //button.setEnabled(false);
        jobActivitySolutionBtn.addClickListener(event -> {
            buildSolutionsDialog(currentJob).open();
        });
    };

    private ComponentRenderer<Button, Problem> createProblemEditorRenderer() {
        return new ComponentRenderer<>(Button::new, problemEditorButton);
    }

    private final SerializableBiConsumer<Button, Problem> problemEditorButton = ( button, currentProblem ) -> {
        problemEditorBtn = button;
        problemEditorBtn.setText("Edit");
        problemEditorBtn.setTooltipText("Edit Parameters");
        problemEditorBtn.addClickListener(event -> {
            buildProblemEditor(currentProblem).open();
        });
    };
}