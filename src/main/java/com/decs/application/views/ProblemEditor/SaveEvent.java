package com.decs.application.views.ProblemEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.button.Button;

@DomEvent("click")
public class SaveEvent extends ComponentEvent<SaveButton> {
    //Internal Data


    //Constructor
    public SaveEvent(SaveButton source, boolean fromClient) {
        super(source, fromClient);
    }

    //Get Methods


    //Set Methods


    //Methods


    //Overrides


    //Internal Functions


}