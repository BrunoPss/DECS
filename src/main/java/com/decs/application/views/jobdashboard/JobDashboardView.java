package com.decs.application.views.jobdashboard;

import com.decs.application.data.Job;
import com.decs.application.data.Problem;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
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
        availableProblemsGrid.addColumn(Problem::getCode).setHeader("Code");
        availableProblemsGrid.addColumn(Problem::getFullName).setHeader("Name");
        availableProblemsGrid.addColumn(Problem::getType).setHeader("Type");

        ArrayList<Problem> problemList = new ArrayList<>();
        problemList.add(new Problem("P1", "Problem 1", "GP"));
        problemList.add(new Problem("P2", "Problem 2", "GP"));
        problemList.add(new Problem("P3", "Problem 3", "GA"));
        problemList.add(new Problem("P4", "Problem 4", "GA"));
        problemList.add(new Problem("P5", "Problem 5", "GP"));
        problemList.add(new Problem("P6", "Problem 6", "GA"));

        availableProblemsGrid.setItems(problemList);

        // Job Progress Bar
        ProgressBar jobProgressBar = new ProgressBar();
        jobProgressBar.setValue(0.5);

        NativeLabel jobProgressBarLabelText = new NativeLabel("Job Progress");
        jobProgressBarLabelText.setId("jpbLabel");
        jobProgressBar.getElement().setAttribute("aria-labelledby", "pblabel");

        Span jobProgressBarLabelValue = new Span("50%");

        HorizontalLayout jobProgressBarLabel = new HorizontalLayout(jobProgressBarLabelText, jobProgressBarLabelValue);
        jobProgressBarLabel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        VerticalLayout jobProgressBarComp = new VerticalLayout(jobProgressBarLabel, jobProgressBar);

        // Lower Widget Group
        HorizontalLayout lowerWidgetGroup = new HorizontalLayout();

        // Job List
        Grid<Job> jobGrid = new Grid<>(Job.class, false);
        jobGrid.addColumn(Job::getName).setHeader("Name");
        jobGrid.setItems(new Job("Job 1"));

        // Job Information


        // Start / Stop Buttons


        // History list

        

        // Page Builder
        mainVerticalLayout.add(availableProblemsGrid, jobProgressBarComp);
        getContent().add(mainVerticalLayout);
    }
}
