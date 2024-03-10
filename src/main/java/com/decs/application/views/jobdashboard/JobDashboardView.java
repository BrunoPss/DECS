package com.decs.application.views.jobdashboard;

import com.decs.application.data.Job;
import com.decs.application.data.Problem;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;

@PageTitle("Job Dashboard")
@Route(value = "job-dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class JobDashboardView extends Composite<VerticalLayout> {

    public JobDashboardView() {
        //VerticalLayout layoutColumn2 = new VerticalLayout();
        //HorizontalLayout layoutRow = new HorizontalLayout();
        //getContent().setWidth("100%");
        //getContent().getStyle().set("flex-grow", "1");
        //layoutColumn2.setWidth("100%");
        //layoutColumn2.getStyle().set("flex-grow", "1");
        //layoutRow.addClassName(Gap.MEDIUM);
        //layoutRow.setWidth("100%");
        //layoutRow.setHeight("min-content");
        //getContent().add(layoutColumn2);
        //getContent().add(layoutRow);

        // Main Page Layout (Vertical)
        VerticalLayout mainVerticalLayout = new VerticalLayout();

        // Available Problems
        Grid<Problem> availableProblemsGrid = new Grid<>(Problem.class, false);
        availableProblemsGrid.setMaxHeight("300px");
        availableProblemsGrid.addColumn(Problem::getCode).setHeader("Code");
        availableProblemsGrid.addColumn(Problem::getFullName).setHeader("Name");
        availableProblemsGrid.addColumn(Problem::getType).setHeader("Type");

        ArrayList<Problem> problemList = new ArrayList<>();
        problemList.add(new Problem("P1", "Problem 1", "GP"));
        problemList.add(new Problem("P2", "Problem 2", "GP"));
        problemList.add(new Problem("P3", "Problem 3", "GA"));
        problemList.add(new Problem("P4", "Problem 4", "GA"));
        problemList.add(new Problem("P5", "Problem 5", "GP"));
        Problem p6 = new Problem("P6", "Problem 6", "GP");
        problemList.add(p6);

        GridListDataView<Problem> availableProblemsUpdater = availableProblemsGrid.setItems(problemList);

        // Job Progress Bar
        ProgressBar jobProgressBar = new ProgressBar();
        jobProgressBar.setValue(0.5);

        NativeLabel jobProgressBarLabelText = new NativeLabel("Job Progress");
        jobProgressBarLabelText.setId("jpbLabel");
        jobProgressBar.getElement().setAttribute("aria-labelledby", "jpbLabel");

        Span jobProgressBarLabelValue = new Span("50%");

        HorizontalLayout jobProgressBarLabel = new HorizontalLayout(jobProgressBarLabelText, jobProgressBarLabelValue);
        jobProgressBarLabel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        VerticalLayout jobProgressBarComp = new VerticalLayout(jobProgressBarLabel, jobProgressBar);

        // Lower Widget Group
        HorizontalLayout lowerWidgetGroup = new HorizontalLayout();
        lowerWidgetGroup.setWidthFull();

        // Job Queue
        Grid<Job> jobQueueGrid = new Grid<>(Job.class, false);
        jobQueueGrid.addColumn(Job::getName).setHeader("Name");
        jobQueueGrid.setItems(new Job("Job 1"));
        jobQueueGrid.setMinWidth("250px");

        Span jobQueueLabel = new Span("Job Queue");

        VerticalLayout jobQueue = new VerticalLayout(jobQueueLabel, jobQueueGrid);

        // Job History list
        Grid<Job> jobHistoryGrid = new Grid<>(Job.class, false);
        jobHistoryGrid.addColumn(Job::getName).setHeader("Name");
        jobHistoryGrid.setItems(new Job("Job 1"));
        jobHistoryGrid.setMinWidth("250px");

        Span jobHistoryGridLabel = new Span("Job History");

        VerticalLayout jobHistory = new VerticalLayout(jobHistoryGridLabel, jobHistoryGrid);

        // Vertical Separator
        Div verticalSeparator = new Div();
        verticalSeparator.getStyle().set("border", "1px solid black");

        // Job Metrics
        VerticalLayout jobMetricsLayout = new VerticalLayout();
        jobMetricsLayout.getStyle().set("border", "1px solid black");
        jobMetricsLayout.setMinWidth("250px");

        NativeLabel jobMetricsTitleLabel = new NativeLabel("Metrics");
        Span info1 = new Span("Info 1");
        Span info2 = new Span("Info 2");
        Span info3 = new Span("Info 3");
        Span info4 = new Span("Info 4");

        jobMetricsLayout.add(info1, info2, info3);
        VerticalLayout jobMetrics = new VerticalLayout(jobMetricsTitleLabel, jobMetricsLayout);

        // Job Results
        VerticalLayout jobResultsLayout = new VerticalLayout();
        jobResultsLayout.getStyle().set("border", "1px solid black");
        jobResultsLayout.setMinWidth("250px");

        NativeLabel jobResultsTitleLabel = new NativeLabel("Results");
        Span result1 = new Span("Result 1");
        Span result2 = new Span("Result 2");
        Span result3 = new Span("Result 3");
        Span result4 = new Span("Result 4");

        jobResultsLayout.add(result1, result2, result3);
        VerticalLayout jobResults = new VerticalLayout(jobResultsTitleLabel, jobResultsLayout);

        VerticalLayout metricsResultsGroup = new VerticalLayout(jobMetrics, jobResults);

        // Start / Stop Buttons
        VerticalLayout actionBtnGroup = new VerticalLayout();
        actionBtnGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        actionBtnGroup.setAlignItems(FlexComponent.Alignment.CENTER);

        Button startBtn = new Button("Start");
        startBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        startBtn.setWidth("130px");
        startBtn.setHeight("50px");
        startBtn.setDisableOnClick(true);
        Button stopBtn = new Button("Stop");
        stopBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        stopBtn.setWidth("100px");
        stopBtn.setHeight("40px");
        stopBtn.setEnabled(false);

        actionBtnGroup.add(startBtn, stopBtn);

        // Lower Widget Group Builder
        lowerWidgetGroup.add(jobQueue, jobHistory, verticalSeparator, metricsResultsGroup, actionBtnGroup);

        // Page Builder
        mainVerticalLayout.add(availableProblemsGrid, jobProgressBarComp, lowerWidgetGroup);
        getContent().add(mainVerticalLayout);
    }
}
