package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.problem.ProblemType;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import ec.util.ParameterDatabase;

public class AntTab extends Tab implements ParamTab {
    //Internal Data
    private static final String PARAMS_FILENAME = "ant.params";
    private VerticalLayout antTabLayout;

    //Constructor


    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        antTabLayout = new VerticalLayout();

        setLabel("Ant");

        return antTabLayout;
    }

    //Overrides
    @Override
    public String[] getFileName() { return new String[]{PARAMS_FILENAME}; }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        return null;
    }

    //Internal Functions

}