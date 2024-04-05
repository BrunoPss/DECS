package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.ProblemType;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ec.util.ParameterDatabase;

public interface ParamTab {
    public VerticalLayout buildLayout();
    public String getFileName();
    public ParameterDatabase createParamDatabase(ProblemType selectedProblem);
}