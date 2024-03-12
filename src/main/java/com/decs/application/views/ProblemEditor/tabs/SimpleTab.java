package com.decs.application.views.ProblemEditor.tabs;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;

public class SimpleTab extends Tab implements ParamTab {
    //Internal Data
    private VerticalLayout simpleTabLayout;

    //Constructor


    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        simpleTabLayout = new VerticalLayout();

        setLabel("Simple");

        return simpleTabLayout;
    }

    //Overrides


    //Internal Functions

}