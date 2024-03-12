package com.decs.application.views.ProblemEditor.tabs;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;

public class AntTab extends Tab implements ParamTab {
    //Internal Data
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


    //Internal Functions

}