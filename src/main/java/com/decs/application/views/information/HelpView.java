package com.decs.application.views.information;

import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.IOException;
import java.io.InputStream;

@PageTitle("Help")
@Route(value = "help", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class HelpView extends Composite<VerticalLayout> {
    //Internal Data
    private VerticalLayout helpLayoutGroup;
    private TabSheet guideTabSheet;
    private Paragraph jobDashboardGuide;
    private Paragraph nodeManagerGuide;
    private Paragraph problemEditorGuide;

    //Constructor
    public HelpView() {
        createHelpSection();

        getContent().add(helpLayoutGroup);
    }

    //Internal Functions
    private void createHelpSection() {
        // Layout Group
        helpLayoutGroup = new VerticalLayout();
        helpLayoutGroup.setWidthFull();

        // Guide TabSheet
        guideTabSheet = new TabSheet();
        guideTabSheet.setWidthFull();
        guideTabSheet.add("Job Dashboard", createJobDashboardGuide());
        guideTabSheet.add("Node Manager", createNodeManagerGuide());
        guideTabSheet.add("Problem Editor", createProblemEditorGuide());

        helpLayoutGroup.add(guideTabSheet);
    }

    private VerticalLayout createJobDashboardGuide() {
        VerticalLayout jobDashboardGuideLayout = new VerticalLayout();

        try {
            InputStream guideContent = getClass().getResourceAsStream("/textContent/jobDashboard_guide.txt");
            String guideContentText = new String(guideContent.readAllBytes());

            jobDashboardGuide = new Paragraph();
            jobDashboardGuide.setText(guideContentText);
            jobDashboardGuide.getElement().getStyle().set("white-space", "pre");
            jobDashboardGuide.setWidth("53.5%");
        } catch (IOException e) {
            e.printStackTrace();
        }

        jobDashboardGuideLayout.add(jobDashboardGuide);

        return jobDashboardGuideLayout;
    }
    private VerticalLayout createNodeManagerGuide() {
        VerticalLayout nodeManagerGuideLayout = new VerticalLayout();

        try {
            InputStream guideContent = getClass().getResourceAsStream("/textContent/nodeManager_guide.txt");
            String guideContentText = new String(guideContent.readAllBytes());

            nodeManagerGuide = new Paragraph();
            nodeManagerGuide.setText(guideContentText);
            nodeManagerGuide.getElement().getStyle().set("white-space", "pre");
            nodeManagerGuide.setWidth("53.5%");
        } catch (IOException e) {
            e.printStackTrace();
        }

        nodeManagerGuideLayout.add(nodeManagerGuide);

        return nodeManagerGuideLayout;
    }
    private VerticalLayout createProblemEditorGuide() {
        VerticalLayout problemEditorGuideLayout = new VerticalLayout();

        try {
            InputStream guideContent = getClass().getResourceAsStream("/textContent/problemEditor_guide.txt");
            String guideContentText = new String(guideContent.readAllBytes());

            problemEditorGuide = new Paragraph();
            problemEditorGuide.setText(guideContentText);
            problemEditorGuide.getElement().getStyle().set("white-space", "pre");
            problemEditorGuide.setWidth("53.5%");
        } catch (IOException e) {
            e.printStackTrace();
        }

        problemEditorGuideLayout.add(problemEditorGuide);

        return problemEditorGuideLayout;
    }
}