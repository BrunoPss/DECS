package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.ProblemType;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.ProblemEditor.SaveButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import ec.util.ParameterDatabase;

import java.io.File;
import java.io.IOException;

public class SaveTab extends Tab implements ParamTab {
    //Internal Data
    private String PARAMS_FILENAME;
    private VerticalLayout saveTabLayout;
    private VerticalLayout problemSaveGroupLayout;
    private Span problemSaveGroupTitle;
    private TextField problemName;
    private SaveButton saveProblemBtn;

    //Constructor
    public SaveTab() {
        setLabel("Save");
    }

    //Get Methods
    public SaveButton getSaveButton() {
        return this.saveProblemBtn;
    }
    public String getProblemName() { return this.problemName.getValue(); }

    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        // Save Tab Layout
        saveTabLayout = new VerticalLayout();
        saveTabLayout.setSpacing(true);
        saveTabLayout.getThemeList().add("spacing-xl");

        createProblemSaveGroup();

        // Save Tab Builder
        saveTabLayout.add(problemSaveGroupLayout);

        return saveTabLayout;
    }

    //Overrides
    @Override
    public String getFileName() { return null; }

    @Override
    public ParameterDatabase createParamDatabase(ProblemType selectedProblem) {
        return null;
    }

    //Internal Functions
    private void createProblemSaveGroup() {
        problemSaveGroupLayout = new VerticalLayout();
        problemSaveGroupLayout.setPadding(true);

        problemSaveGroupTitle = new Span("Problem Info");

        problemName =  new TextField();
        problemName.setLabel("Problem Name");
        problemName.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        problemName.setClearButtonVisible(true);
        problemName.setRequired(true);
        problemName.setRequiredIndicatorVisible(true);
        problemName.setMaxLength(100);

        saveProblemBtn = new SaveButton(this, "Save");
        saveProblemBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        problemSaveGroupLayout.add(problemSaveGroupTitle, problemName, saveProblemBtn);
    }

    // Event Listeners

}