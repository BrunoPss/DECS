package com.decs.application.views.ProblemEditor;

import com.decs.application.data.ParameterGroupType;
import com.decs.application.data.ProblemType;
import com.decs.application.views.MainLayout;
import com.decs.application.views.ProblemEditor.tabs.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;

@PageTitle("Problem Editor")
@Route(value = "problem-editor", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ProblemEditorView extends Composite<VerticalLayout> {
    private TabSheet tabs;
    private ArrayList<ParamTab> tabsList;

    public ProblemEditorView() {
        VerticalLayout mainVerticalLayout = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        mainVerticalLayout.setWidth("100%");
        mainVerticalLayout.getStyle().set("flex-grow", "1");

        // Tabs
        tabs = new TabSheet();
        tabs.setWidth("100%");

        tabsList = new ArrayList<>();

        // General Tab
        GeneralTab generalTab = new GeneralTab();
        VerticalLayout generalTabContent = generalTab.buildLayout();
        generalTab.getProblemSelector().addValueChangeListener(this::problemChangeEvent);
        tabs.add(generalTab, generalTabContent);
        tabsList.add(generalTab);

        // Page Builder
        getContent().add(mainVerticalLayout);
        mainVerticalLayout.add(tabs);
    }

    // Event Listeners
    private void problemChangeEvent(AbstractField.ComponentValueChangeEvent<Select<ProblemType>, ProblemType> event) {
        // Delete Current Tabs
        for (int i=1; i<tabsList.size(); i++) {
            tabs.remove((Tab) tabsList.get(i));
        }
        tabsList.subList(1, tabsList.size()).clear();

        // Add Problem Tabs
        for (ParameterGroupType p : event.getValue().getParameterGroups()) {
            ParamTab newTab = createTab(p);
            tabs.add((Tab) newTab, newTab.buildLayout());
        }
    }

    // Private Functions
    private ParamTab createTab(ParameterGroupType groupType) {
        ParamTab newTab = switch (groupType) {
            case SIMPLE -> new SimpleTab();
            case KOZA -> new KozaTab();
            case ANT -> new AntTab();
        };

        tabsList.add(newTab);
        return newTab;
    }

}