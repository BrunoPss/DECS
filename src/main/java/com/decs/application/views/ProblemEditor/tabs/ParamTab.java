package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.problem.ProblemType;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ec.util.ParameterDatabase;

public interface ParamTab {
    VerticalLayout buildLayout();
    String[] getFileName();
    ParameterDatabase[] createParamDatabase(ProblemType selectedProblem);
}