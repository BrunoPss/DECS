package com.decs.application.views.ProblemEditor;

import com.decs.application.data.ParameterGroupType;
import com.decs.application.data.ProblemType;
import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
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
import java.util.Arrays;

@PageTitle("Problem Editor")
@Route(value = "problem-editor", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class ProblemEditorView extends Composite<VerticalLayout> {

    public ProblemEditorView() {
        VerticalLayout mainVerticalLayout = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        mainVerticalLayout.setWidth("100%");
        mainVerticalLayout.getStyle().set("flex-grow", "1");

        // Tabs
        TabSheet tabs = new TabSheet();
        tabs.setWidth("100%");

        // General Tab
        Tab generalTab = new Tab("General");
        VerticalLayout generalTabLayout = new VerticalLayout();
        generalTabLayout.setSpacing(true);
        generalTabLayout.getThemeList().add("spacing-xl");

        // Problem Selector
        Select<String> problemSelector = new Select<>();
        problemSelector.setLabel("Problem");
        problemSelector.setPlaceholder("Select Problem");
        // Select Values -> .param files available in the folder or ProblemType values
        problemSelector.setItems(ProblemType.getValueList());

        // Jobs and Seed Group
        VerticalLayout jobSeedGroupLayout = new VerticalLayout();
        jobSeedGroupLayout.setPadding(true);
        HorizontalLayout jobSeedLayout = new HorizontalLayout();
        jobSeedLayout.setAlignItems(FlexComponent.Alignment.START);
        jobSeedLayout.setSpacing(true);
        jobSeedLayout.getThemeList().add("spacing-xl");

        Span jobSeedGroupTitle = new Span("Misc");

        // Jobs
        HorizontalLayout jobInputLayout = new HorizontalLayout();
        jobInputLayout.setAlignItems(FlexComponent.Alignment.END);

        IntegerField jobInput = new IntegerField();
        jobInput.setLabel("Jobs");
        jobInput.setMin(1);
        jobInput.setValue(1);
        jobInput.setStepButtonsVisible(true);
        Tooltip jobTooltip = jobInput.getTooltip().withManual(true);
        jobTooltip.setText("test tooltip");

        Button jobHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        jobHelpBtn.addClickListener(event -> {
            jobTooltip.setOpened(!jobTooltip.isOpened());
        });

        jobInputLayout.add(jobInput, jobHelpBtn);

        // Seed
        HorizontalLayout seedInputLayout = new HorizontalLayout();
        seedInputLayout.setAlignItems(FlexComponent.Alignment.END);
        jobSeedLayout.setSpacing(true);
        jobSeedLayout.getThemeList().add("spacing-s");

        ComboBox<String> seedInput = new ComboBox<>();
        seedInput.setLabel("Random Seed");
        seedInput.setAllowCustomValue(true);
        Tooltip seedTooltip = seedInput.getTooltip().withManual(true);
        seedTooltip.setText("test tooltip");

        Button seedHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        seedHelpBtn.addClickListener(event -> {
            seedTooltip.setOpened(!seedTooltip.isOpened());
        });

        seedInputLayout.add(seedInput, seedHelpBtn);

        // Jobs / Seed Group Builder
        jobSeedLayout.add(jobInputLayout, seedInputLayout);
        jobSeedGroupLayout.add(jobSeedGroupTitle, jobSeedLayout);

        // Multithreading Group
        VerticalLayout multithreadingGroupLayout = new VerticalLayout();
        multithreadingGroupLayout.setPadding(true);

        Span multithreadingGroupTitle = new Span("Multithreading");

        HorizontalLayout evalBreedLayout = new HorizontalLayout();
        evalBreedLayout.setSpacing(true);
        evalBreedLayout.getThemeList().add("spacing-xl");

        HorizontalLayout evalInputLayout = new HorizontalLayout();
        evalInputLayout.setAlignItems(FlexComponent.Alignment.END);
        IntegerField evalInput = new IntegerField();
        evalInput.setLabel("Eval Threads");
        evalInput.setMin(1);
        evalInput.setValue(1);
        evalInput.setStepButtonsVisible(true);
        Tooltip evalTooltip = evalInput.getTooltip().withManual(true);
        evalTooltip.setText("test tooltip");
        Button evalHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        evalHelpBtn.addClickListener(event -> {
            evalTooltip.setOpened(!evalTooltip.isOpened());
        });
        evalInputLayout.add(evalInput, evalHelpBtn);

        HorizontalLayout breedInputLayout = new HorizontalLayout();
        breedInputLayout.setAlignItems(FlexComponent.Alignment.END);
        IntegerField breedInput = new IntegerField();
        breedInput.setLabel("Breed Threads");
        breedInput.setMin(1);
        breedInput.setValue(1);
        breedInput.setStepButtonsVisible(true);
        Tooltip breedTooltip = breedInput.getTooltip().withManual(true);
        breedTooltip.setText("test tooltip");
        Button breedHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        breedHelpBtn.addClickListener(event -> {
            breedTooltip.setOpened(!breedTooltip.isOpened());
        });
        breedInputLayout.add(breedInput, breedHelpBtn);

        evalBreedLayout.add(evalInputLayout, breedInputLayout);
        multithreadingGroupLayout.add(multithreadingGroupTitle, evalBreedLayout);

        // Checkpoint Group
        VerticalLayout checkpointGroupLayout = new VerticalLayout();
        checkpointGroupLayout.setPadding(true);

        Span checkpointGroupTitle = new Span("Checkpoint");

        HorizontalLayout moduloPrefixLayout = new HorizontalLayout();
        moduloPrefixLayout.setSpacing(true);
        moduloPrefixLayout.getThemeList().add("spacing-xl");

        HorizontalLayout moduloInputLayout = new HorizontalLayout();
        moduloInputLayout.setAlignItems(FlexComponent.Alignment.END);
        IntegerField moduloInput = new IntegerField();
        moduloInput.setLabel("Modulo");
        moduloInput.setMin(1);
        moduloInput.setValue(1);
        moduloInput.setStepButtonsVisible(true);
        Tooltip moduloTooltip = moduloInput.getTooltip().withManual(true);
        moduloTooltip.setText("test tooltip");
        Button moduloHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        moduloHelpBtn.addClickListener(event -> {
            moduloTooltip.setOpened(!moduloTooltip.isOpened());
        });
        moduloInputLayout.add(moduloInput, moduloHelpBtn);

        HorizontalLayout prefixInputLayout = new HorizontalLayout();
        prefixInputLayout.setAlignItems(FlexComponent.Alignment.END);
        TextField prefixInput = new TextField();
        prefixInput.setLabel("Prefix");
        prefixInput.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        prefixInput.setClearButtonVisible(true);
        Tooltip prefixTooltip = prefixInput.getTooltip().withManual(true);
        prefixTooltip.setText("test tooltip");
        Button prefixHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        prefixHelpBtn.addClickListener(event -> {
            prefixTooltip.setOpened(!prefixTooltip.isOpened());
        });
        prefixInputLayout.add(prefixInput, prefixHelpBtn);

        moduloPrefixLayout.add(moduloInputLayout, prefixInputLayout);
        checkpointGroupLayout.add(checkpointGroupTitle, moduloPrefixLayout);

        // General Tab Builder
        generalTabLayout.add(problemSelector, jobSeedGroupLayout, multithreadingGroupLayout, checkpointGroupLayout);

        // Tabs Builder
        tabs.add(generalTab, generalTabLayout);

        // Page Builder
        getContent().add(mainVerticalLayout);
        mainVerticalLayout.add(tabs);
    }

    private void setTabsSampleData(Tabs tabs) {

    }
}
