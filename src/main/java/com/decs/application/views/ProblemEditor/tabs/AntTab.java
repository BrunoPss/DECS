package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.problem.ProblemType;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import ec.util.ParameterDatabase;

/**
 * <b>Ant Tab Class</b>
 * <p>
 *     This class implements the problem editor Ant parameter tab.
 *     It is responsible for all visual components and their behavior.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class AntTab extends Tab implements ParamTab {
    //Internal Data
    /**
     * Name of the parameter file this tab will generate
     */
    private static final String PARAMS_FILENAME = "ant.params";
    private VerticalLayout antTabLayout;

    //Constructor


    //Get Methods


    //Set Methods


    //Overrides
    @Override
    public VerticalLayout buildLayout() {
        antTabLayout = new VerticalLayout();

        setLabel("Ant");

        return antTabLayout;
    }

    @Override
    public String[] getFileName() { return new String[]{PARAMS_FILENAME}; }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        return null;
    }

    //Internal Functions

}