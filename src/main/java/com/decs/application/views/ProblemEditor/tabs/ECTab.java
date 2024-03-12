package com.decs.application.views.ProblemEditor.tabs;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;

public class ECTab extends Tab implements ParamTab {
    //Internal Data
    private VerticalLayout ECTabLayout;

    //Constructor


    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        ECTabLayout = new VerticalLayout();

        setLabel("EC");

        return ECTabLayout;
    }

    //Overrides


    //Internal Functions

}