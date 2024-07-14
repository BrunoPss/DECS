package com.decs.application.services;

import com.decs.application.views.jobdashboard.JobDashboardView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/**
 * <b>Session Manager Class</b>
 * <p>
 *     This class establishes a bridge between the global system state and each user session.
 *     The active queue system is implemented and managed, which restricts the execution of Jobs
 *     to just one at a time.
 * </p>
 */
@Service
public class SessionManager {
    //Internal Data
    public static final String EVOLUTION_ENGINE_TYPE = "evolutionEngineType";
    private PropertyChangeSupport pcs;
    private boolean evolutionEngineBusy;
    private UI starterUI;
    private HashMap<UI, PropertyChangeListener> propertyChangeListenerMap;

    /**
     * Class Constructor
     */
    public SessionManager() {
        this.pcs = new PropertyChangeSupport(this);
        this.evolutionEngineBusy = false;
        this.propertyChangeListenerMap = new HashMap<>();
    }

    //Get Methods
    public UI getStarterUI() { return this.starterUI; }
    public boolean getEvolutionEngineBusy() { return this.evolutionEngineBusy; }

    //Set Methods
    public void setEvolutionEngineBusy(boolean value, UI starterUI) {
        //System.out.println("SETT BUSSY");
        this.evolutionEngineBusy = value;
        this.starterUI = starterUI;
        pcs.firePropertyChange(EVOLUTION_ENGINE_TYPE, null, value);
    }

    //Methods
    /**
     * Add or replace a property change listener to the respective property change support
     * @param property Property name
     * @param newListener Property change listener object
     * @param ui UI object of the user to be connected with the property change listener
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener newListener, UI ui) {
        // Search for a previous client entry (client is defined by its UI object)
        PropertyChangeListener oldListener;
        if ((oldListener = propertyChangeListenerMap.get(ui)) != null) {
            pcs.removePropertyChangeListener(property, oldListener);
            propertyChangeListenerMap.replace(ui, newListener);
        }
        else {
            propertyChangeListenerMap.put(ui, newListener);
        }

        pcs.addPropertyChangeListener(property, newListener);

        //System.out.println("LENGTH : " + pcs.getPropertyChangeListeners().length);
    }

    //Overrides


    //Internal Functions


}