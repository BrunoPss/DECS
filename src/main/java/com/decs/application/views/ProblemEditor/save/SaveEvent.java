package com.decs.application.views.ProblemEditor.save;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;

/**
 * <b>Save Event Class</b>
 * <p>
 *     This class represents the event handler for the save action.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@DomEvent("click")
public class SaveEvent extends ComponentEvent<SaveButton> {
    //Internal Data


    /**
     * Class Constructor
     * @param source Save button source component
     * @param fromClient Event source
     */
    public SaveEvent(SaveButton source, boolean fromClient) {
        super(source, fromClient);
    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions


}