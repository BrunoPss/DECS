package com.decs.application.views.ProblemEditor;

import com.decs.application.views.ProblemEditor.tabs.SaveTab;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;

public class SaveButton extends Button {
    //Internal Data
    private SaveTab saveTab;

    //Constructor
    public SaveButton(SaveTab saveTab) {}
    public SaveButton(SaveTab saveTab, String label) {
        this.saveTab = saveTab;
        this.setText(label);
    }

    //Get Methods
    public String getProblemName() { return this.saveTab.getProblemName(); }

    //Set Methods


    //Methods
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    //Overrides


    //Internal Functions


}