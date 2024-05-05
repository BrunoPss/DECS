package com.decs.application.views.ProblemEditor;

import com.decs.application.data.distribution.DistributionType;
import com.decs.application.data.distribution.Island;
import com.decs.application.data.parameter.ParameterGroupType;
import com.decs.application.data.problem.ProblemType;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.utils.confFile.FileConfigAttr;
import com.decs.application.utils.confFile.ProblemFileManager;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.MainLayout;
import com.decs.application.views.ProblemEditor.save.SaveEvent;
import com.decs.application.views.ProblemEditor.tabs.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ec.util.ParameterDatabase;
import jakarta.annotation.security.PermitAll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;

@PageTitle("Problem Editor")
@Route(value = "problem-editor", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ProblemEditorView extends Composite<VerticalLayout> {
    private TabSheet tabs;
    private ArrayList<ParamTab> tabsList;
    private ProblemType selectedProblem;
    private DistributionType selectedDistMethod;
    private ObjectListDatabase objectListDatabase;

    public ProblemEditorView(ObjectListDatabase objectListDatabase) {
        this.objectListDatabase = objectListDatabase;

        VerticalLayout mainVerticalLayout = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        mainVerticalLayout.setWidth("100%");
        mainVerticalLayout.getStyle().set("flex-grow", "1");

        // Tabs
        tabs = new TabSheet();
        tabs.setWidth("100%");

        tabsList = new ArrayList<>();

        // General Tab
        GeneralTab generalTab = new GeneralTab(objectListDatabase);
        VerticalLayout generalTabContent = generalTab.buildLayout();
        generalTab.getProblemSelector().addValueChangeListener(this::problemChangeEvent);
        generalTab.getDistSelector().addValueChangeListener(this::distributionChangeEvent);
        tabs.add(generalTab, generalTabContent);
        tabsList.add(generalTab);

        // Save Tab
        SaveTab saveTab = new SaveTab(objectListDatabase);
        VerticalLayout saveTabContent = saveTab.buildLayout();
        saveTab.getSaveButton().addSaveListener(this::saveProblem);
        tabs.add(saveTab, saveTabContent);
        tabsList.add(saveTab);

        // Page Builder
        mainVerticalLayout.add(tabs);
        getContent().add(mainVerticalLayout);
    }

    // Event Listeners
    private void problemChangeEvent(AbstractField.ComponentValueChangeEvent<Select<ProblemType>, ProblemType> event) {
        // Save Selected Problem
        this.selectedProblem = event.getValue();

        // Delete Current Tabs
        for (int i=1; i<tabsList.size()-1; i++) {
            tabs.remove((Tab) tabsList.get(i));
        }
        tabsList.subList(1, tabsList.size()-1).clear();

        // Add Problem Tabs
        ParamTab saveTab = tabsList.remove(tabsList.size()-1);
        int i=1;
        for (ParameterGroupType p : selectedProblem.getParameterGroups()) {
            ParamTab newTab = createParamTab(p);
            tabs.add((Tab) newTab, newTab.buildLayout(), i);
            tabsList.add(newTab);
            i++;
        }

        // Add Distribution Tab
        if (selectedDistMethod != null) {
            ParamTab newTab = switch (selectedDistMethod) {
                case DIST_EVAL -> new DistEvalTab();
                case ISLANDS -> new IslandsTab(objectListDatabase);
                case LOCAL -> null;
            };
            if (newTab != null) {
                tabs.add((Tab) newTab, newTab.buildLayout(), tabsList.size());
                tabsList.add(newTab);
            }
        }
        tabsList.add(saveTab);
    }

    private void distributionChangeEvent(AbstractField.ComponentValueChangeEvent<Select<DistributionType>, DistributionType> event) {
        // Save Selected Problem
        this.selectedDistMethod = event.getValue();

        // Delete Current Tabs
        for (int i=1; i<tabsList.size()-1; i++) {
            tabs.remove((Tab) tabsList.get(i));
        }
        tabsList.subList(1, tabsList.size()-1).clear();

        // Add Problem Tabs
        ParamTab saveTab = tabsList.remove(tabsList.size()-1);
        if (selectedProblem != null) {
            int i=1;
            for (ParameterGroupType p : selectedProblem.getParameterGroups()) {
                ParamTab newTab = createParamTab(p);
                tabs.add((Tab) newTab, newTab.buildLayout(), i);
                tabsList.add(newTab);
                i++;
            }
        }

        // Add Distribution Tab
        ParamTab newTab = switch (selectedDistMethod) {
            case DIST_EVAL -> new DistEvalTab();
            case ISLANDS -> new IslandsTab(objectListDatabase);
            case LOCAL -> null;
        };
        if (newTab != null) {
            tabs.add((Tab) newTab, newTab.buildLayout(), tabsList.size());
            tabsList.add(newTab);
        }
        tabsList.add(saveTab);
    }

    private void saveProblem(SaveEvent event) {
        System.out.println("SAVE");

        try {
            // Create Problem Folder
            File problemFolder = new File(FilePathConstants.USER_PARAMS_FOLDER + "/" + event.getSource().getProblemCode());
            try {
                Files.createDirectory(problemFolder.toPath());
            } catch (IOException e) {
                System.err.println("IO Exception while creating problem folder");
                e.printStackTrace();
            }

            // Create Problem Files
            for (ParamTab tab : tabsList) {
                if (tab.getFileName() != null) {
                    try {
                        ParameterDatabase[] paramDatabase = tab.createParamDatabase(this.selectedProblem);
                        FileWriter fwriter;
                        for (int i=0; i< paramDatabase.length; i++) {
                            fwriter = new FileWriter(problemFolder.getPath() + "/" + tab.getFileName()[i]);
                            PrintWriter pwriter = new PrintWriter(fwriter);
                            // Write Params File from ParameterDatabase Object
                            paramDatabase[i].list(pwriter);
                            fwriter.close();
                        }
                    } catch (IOException e) {
                        System.out.println("IO Exception");
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            }

            // Copy Base Files
            try {
                // Copy Base Problem Params File
                Path baseFileOriginalPath = Paths.get(FilePathConstants.FACTORY_PARAMS_FOLDER+"/"+selectedProblem.getCode()+"/"+selectedProblem.getCode()+".params");
                Path baseFileDestinationPath;

                // Distributed Eval
                if (event.getSource().getProblemDistribution().equals(DistributionType.DIST_EVAL.toString())) {
                    System.out.println("Dist Eval");
                    baseFileDestinationPath = Paths.get(problemFolder.getPath() + "/" + selectedProblem.getCode() + ".params");
                    // Create Main param File
                    try {
                        File mainParamFile = new File(problemFolder.getPath() + "/" + event.getSource().getProblemCode() + ".params");
                        FileWriter fwriter = new FileWriter(mainParamFile);
                        fwriter.write("parent.0 = " + selectedProblem.getCode()+".params"+"\n");
                        fwriter.write("parent.1 = master.params");
                        fwriter.close();
                    } catch (IOException e) {
                        System.err.println("IO Exception while creating main parameter file");
                        e.printStackTrace();
                    }
                }
                // Island
                else if (event.getSource().getProblemDistribution().equals(DistributionType.ISLANDS.toString())) {
                    System.out.println("Islands");

                    String text = "parent.0 = " + event.getSource().getProblemCode() + ".params" + "\n";
                    Files.write(Paths.get(problemFolder.getPath() + "/client.params"), text.getBytes(), StandardOpenOption.APPEND);
                    baseFileDestinationPath = Paths.get(problemFolder.getPath() + "/" + event.getSource().getProblemCode() + ".params");
                }
                // Local
                else {
                    System.out.println("Local Eval");
                    baseFileDestinationPath = Paths.get(problemFolder.getPath() + "/" + event.getSource().getProblemCode() + ".params");
                }
                Files.copy(baseFileOriginalPath, baseFileDestinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("IO Exception");
                e.printStackTrace();
            }


            // Create .conf File
            File confFilePath = new File(problemFolder.getPath() + "/" + event.getSource().getProblemCode() + ".conf");

            HashMap<FileConfigAttr, String> problemInfo = new HashMap<>();
            problemInfo.put(FileConfigAttr.CODE, event.getSource().getProblemCode());
            problemInfo.put(FileConfigAttr.FULL_NAME, event.getSource().getProblemFullName());
            problemInfo.put(FileConfigAttr.TYPE, event.getSource().getProblemType());
            problemInfo.put(FileConfigAttr.ORIGIN, event.getSource().getProblemOrigin());
            problemInfo.put(FileConfigAttr.DISTRIBUTION, event.getSource().getProblemDistribution());

            // Island
            if (event.getSource().getProblemDistribution().equals(DistributionType.ISLANDS.toString())) {
                ArrayList<Island> islandList = objectListDatabase.getIslandList();
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < islandList.size(); i++) {
                    strBuilder.append(islandList.get(i).getId());
                    if (i < islandList.size() - 1) {
                        strBuilder.append(";");
                    }
                }
                String resultString = strBuilder.toString();
                problemInfo.put(FileConfigAttr.ISLAND_LIST, resultString);

                problemInfo.put(FileConfigAttr.SERVER_ISLAND, confFilePath.getParent() + "/" + objectListDatabase.getServerIsland());
            }

            ProblemFileManager.writeConfFile(confFilePath, problemInfo);
        } catch (Exception e) {
            System.err.println("Exception while saving new problem");
            Notification errorNotification = Notification.show("Error while saving the problem!");
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    // Private Functions
    private ParamTab createParamTab(ParameterGroupType groupType) {
        ParamTab newTab = switch (groupType) {
            case EC -> new GeneralTab(objectListDatabase);
            case SIMPLE -> new SimpleTab();
            case KOZA -> new KozaTab();
            case ANT -> new AntTab();
        };
        return newTab;
    }
}