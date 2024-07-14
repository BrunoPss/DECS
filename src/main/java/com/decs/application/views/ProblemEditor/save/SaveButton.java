package com.decs.application.views.ProblemEditor.save;

import com.decs.application.views.ProblemEditor.tabs.SaveTab;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;

/**
 * <b>Save Button Class</b>
 * <p>
 *     This class represents the problem editor save button.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class SaveButton extends Button {
    //Internal Data
    private SaveTab saveTab;

    /**
     * Class Constructor
     * @param saveTab Save tab instance
     * @param label Textual label for the button
     */
    public SaveButton(SaveTab saveTab, String label) {
        this.saveTab = saveTab;
        this.setText(label);
    }

    //Get Methods
    public String getProblemCode() { return this.saveTab.getProblemCode(); }
    public String getProblemFullName() { return this.saveTab.getProblemFullName(); }
    public String getProblemType() { return this.saveTab.getProblemType(); }
    public String getProblemOrigin() { return this.saveTab.getProblemOrigin(); }
    public String getProblemDistribution() { return this.saveTab.getProblemDistribution(); }

    //Set Methods


    //Methods

    /**
     * Adds a component event listener to the button
     * @param listener Listener object
     */
    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }

    //Overrides


    //Internal Functions


}