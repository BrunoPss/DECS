package com.decs.application.views.ProblemEditor.tabs;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;

public class KozaTab extends Tab implements ParamTab {
    //Internal Data
    private VerticalLayout kozaTabLayout;

    //Constructor


    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        kozaTabLayout = new VerticalLayout();

        setLabel("Koza");

        return kozaTabLayout;
    }

    //Overrides


    //Internal Functions

}