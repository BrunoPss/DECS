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

/**
 * <b>Help View Class</b>
 * <p>
 *     This class implements the web application help page.
 *     It is responsible for all visual components and their behavior.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
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

    /**
     * Class Constructor
     */
    public HelpView() {
        createHelpSection();
        getContent().add(helpLayoutGroup);
    }

    //Internal Functions
    /**
     * Builds the main help section view
     */
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

    /**
     * Builds the job dashboard section of the help view
     * @return Layout of the section
     */
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

    /**
     * Builds the node manager section of the help view
     * @return Layout of the section
     */
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

    /**
     * Builds the problem editor section of the help view
     * @return Layout of the section
     */
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