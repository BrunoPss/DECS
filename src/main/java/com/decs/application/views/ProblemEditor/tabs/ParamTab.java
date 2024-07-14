package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.problem.ProblemType;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ec.util.ParameterDatabase;

/**
 * <b>Parameter Tab Interface</b>
 * <p>
 *     This interface defines the mandatory methods for each parameter tab to implement.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public interface ParamTab {
    /**
     * This method is responsible for building the respective tab global layout
     * @return Output layout object
     */
    VerticalLayout buildLayout();

    /**
     * This method is responsible for retrieving the output parameter file name
     * @return Output parameter file name
     */
    String[] getFileName();

    /**
     * This method is responsible for creating a parameter database with the compilation
     * of all parameters and values in the respective tab
     * @param selectedProblem Type of the problem
     * @return Parameter database object
     */
    ParameterDatabase[] createParamDatabase(ProblemType selectedProblem);
}