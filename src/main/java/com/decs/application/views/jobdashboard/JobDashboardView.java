package com.decs.application.views.jobdashboard;

import com.decs.application.data.Job;
import com.decs.application.data.Problem;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;

import java.awt.*;
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
        jobGrid.setMinWidth("250px");

        // Job Information
        VerticalLayout jobInfoLayout = new VerticalLayout();
        jobInfoLayout.getStyle().set("border", "1px solid black");
        jobInfoLayout.setMinWidth("250px");

        H2 titleLabel = new H2("Info");
        NativeLabel info1 = new NativeLabel("Info 1");
        NativeLabel info2 = new NativeLabel("Info 2");

        jobInfoLayout.add(titleLabel, info1, info2);

        // Start / Stop Buttons
        VerticalLayout actionBtnGroup = new VerticalLayout();
        actionBtnGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        actionBtnGroup.setAlignItems(FlexComponent.Alignment.CENTER);

        Button startBtn = new Button("Start");
        startBtn.setDisableOnClick(true);
        startBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        Button stopBtn = new Button("Stop");
        stopBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        stopBtn.setEnabled(false);

        actionBtnGroup.add(startBtn, stopBtn);

        // History list


        // Lower Widget Group Builder
        lowerWidgetGroup.add(jobGrid, jobInfoLayout, actionBtnGroup);

        // Page Builder
        mainVerticalLayout.add(availableProblemsGrid, jobProgressBarComp, lowerWidgetGroup);
        getContent().add(mainVerticalLayout);
    }
}
