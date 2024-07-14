package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.problem.ProblemType;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.views.ProblemEditor.save.SaveButton;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import ec.util.ParameterDatabase;

/**
 * <b>Save Tab Class</b>
 * <p>
 *     This class implements the problem editor save tab.
 *     It is responsible for all visual components and their behavior.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class SaveTab extends Tab implements ParamTab {
    //Internal Data
    private String PARAMS_FILENAME;
    private ObjectListDatabase objectListDatabase;
    private VerticalLayout saveTabLayout;
    private VerticalLayout problemSaveGroupLayout;
    private Span problemSaveGroupTitle;
    HorizontalLayout upperInfoGroup;
    private TextField problemCode;
    private TextField problemName;
    private TextField problemType;
    private SaveButton saveProblemBtn;

    /**
     * Class Constructor
     * @param objectListDatabase Object list database object
     */
    public SaveTab(ObjectListDatabase objectListDatabase) {
        setLabel("Save");
        this.objectListDatabase = objectListDatabase;
    }

    //Get Methods
    public SaveButton getSaveButton() {
        return this.saveProblemBtn;
    }
    public String getProblemCode() { return this.problemCode.getValue(); }
    public String getProblemFullName() { return this.problemName.getValue(); }
    public String getProblemType() { return this.problemType.getValue(); }
    public String getProblemOrigin() { return "user"; }
    public String getProblemDistribution() { return this.objectListDatabase.getProblemCreatorDistribution().toString(); }

    //Set Methods


    //Overrides
    @Override
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

    @Override
    public String[] getFileName() { return null; }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        return null;
    }

    //Internal Functions
    /**
     * Builds the problem save components group
     */
    private void createProblemSaveGroup() {
        problemSaveGroupLayout = new VerticalLayout();
        problemSaveGroupLayout.setPadding(true);

        problemSaveGroupTitle = new Span("Problem Info");

        upperInfoGroup = new HorizontalLayout();

        problemCode =  new TextField();
        problemCode.setLabel("Problem Code");
        problemCode.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        problemCode.setClearButtonVisible(true);
        problemCode.setRequired(true);
        problemCode.setRequiredIndicatorVisible(true);
        problemCode.setMaxLength(100);

        problemName =  new TextField();
        problemName.setLabel("Problem Name");
        problemName.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        problemName.setClearButtonVisible(true);
        problemName.setRequired(true);
        problemName.setRequiredIndicatorVisible(true);
        problemName.setMaxLength(100);

        problemType =  new TextField();
        problemType.setLabel("Problem Type");
        problemType.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        problemType.setClearButtonVisible(true);
        problemType.setRequired(true);
        problemType.setRequiredIndicatorVisible(true);
        problemType.setMaxLength(100);

        upperInfoGroup.add(problemCode, problemName, problemType);

        saveProblemBtn = new SaveButton(this, "Save");
        saveProblemBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        problemSaveGroupLayout.add(problemSaveGroupTitle, upperInfoGroup, saveProblemBtn);
    }

    // Event Listeners

}