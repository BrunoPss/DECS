package com.decs.application.views.ProblemEditor;

import com.decs.application.data.ParameterGroupType;
import com.decs.application.data.Problem;
import com.decs.application.data.ProblemType;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.MainLayout;
import com.decs.application.views.ProblemEditor.tabs.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.parameters.P;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

@PageTitle("Problem Editor")
@Route(value = "problem-editor", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ProblemEditorView extends Composite<VerticalLayout> {
    private TabSheet tabs;
    private ArrayList<ParamTab> tabsList;
    private ProblemType selectedProblem;
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
        GeneralTab generalTab = new GeneralTab();
        VerticalLayout generalTabContent = generalTab.buildLayout();
        generalTab.getProblemSelector().addValueChangeListener(this::problemChangeEvent);
        tabs.add(generalTab, generalTabContent);
        tabsList.add(generalTab);

        // Save Tab
        SaveTab saveTab = new SaveTab();
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
        for (int i=1; i<tabsList.size(); i++) {
            tabs.remove((Tab) tabsList.get(i));
        }
        tabsList.subList(1, tabsList.size()).clear();

        // Add Problem Tabs
        for (ParameterGroupType p : event.getValue().getParameterGroups()) {
            ParamTab newTab = createTab(p);
            tabs.add((Tab) newTab, newTab.buildLayout());
        }

        // Add Save Tab
        SaveTab saveTab = new SaveTab();
        VerticalLayout saveTabContent = saveTab.buildLayout();
        saveTab.getSaveButton().addSaveListener(this::saveProblem);
        tabs.add(saveTab, saveTabContent);
        tabsList.add(saveTab);
    }

    private void saveProblem(SaveEvent event) {
        System.out.println("SAVE");
        // Create Problem Folder
        File problemFolder = new File(FilePathConstants.USER_PARAMS_FOLDER + "/" + event.getSource().getProblemName());
        try {
            Files.createDirectory(problemFolder.toPath());
        } catch (IOException e) {
            System.err.println("IO Exception while creating problem folder");
            e.printStackTrace();
        }

        // Create Problem Files
        for (ParamTab tab : tabsList) {
            if (tab.getFileName() != null) {
                try (FileWriter fwriter = new FileWriter(problemFolder.getPath() + "/" + tab.getFileName());
                     PrintWriter pwriter = new PrintWriter(fwriter)) {

                    // Write Params File from ParameterDatabase Object
                    ParameterDatabase paramDatabase = tab.createParamDatabase(this.selectedProblem);
                    paramDatabase.list(pwriter);

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
            Path baseFileDestinationPath = Paths.get(problemFolder.getPath() + "/" + selectedProblem.getCode()+".params");
            Files.copy(baseFileOriginalPath, baseFileDestinationPath, StandardCopyOption.REPLACE_EXISTING);
            // Copy .conf file
            Path confFileOriginalPath = Paths.get(FilePathConstants.FACTORY_PARAMS_FOLDER+"/"+selectedProblem.getCode()+"/"+selectedProblem.getCode()+".conf");
            Path confFileDestinationPath = Paths.get(problemFolder.getPath() + "/" + selectedProblem.getCode()+".conf");
            Files.copy(confFileOriginalPath, confFileDestinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("IO Exception");
            e.printStackTrace();
        }
    }

    // Private Functions
    private ParamTab createTab(ParameterGroupType groupType) {
        ParamTab newTab = switch (groupType) {
            case EC -> new GeneralTab();
            case SIMPLE -> new SimpleTab();
            case KOZA -> new KozaTab();
            case ANT -> new AntTab();
        };

        tabsList.add(newTab);
        return newTab;
    }

}