package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.utils.EnhancedBoolean;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

public class SimpleTab extends Tab implements ParamTab {
    //Internal Data
    private VerticalLayout simpleTabLayout;
    // Stopping Condition
    private VerticalLayout stoppingCondLayoutGroup;
    private Span stoppingCondTitle;
    private HorizontalLayout stoppingCondLayout;
    private HorizontalLayout stoppingTypeSelectorLayout;
    private Select<String> stoppingVariableTypeSelector;
    private Tooltip stoppingTooltip;
    private Button stoppingCondHelpBtn;
    private IntegerField stoppingVariableValue;
    // Subpopulation
    private VerticalLayout subpopulationLayoutGroup;
    private Span subpopulationTitle;
    private HorizontalLayout subpopulationLayout;
    private HorizontalLayout subpopulationSizeLayout;
    private IntegerField subpopulationSize;
    private Tooltip subpopulationSizeTooltip;
    private Button subpopulationHelpBtn;
    private HorizontalLayout subpopulationDupRetriesLayout;
    private IntegerField subpopulationDupRetries;
    private Tooltip subpopulationDupRetriesTooltip;
    private Button subpopulationDupRetriesHelpBtn;
    // Breeder
    private VerticalLayout breederLayoutGroup;
    private Span breederTitle;
    private HorizontalLayout breederLayout;
    // Breeder Elite
    private HorizontalLayout breederEliteLayout;
    private IntegerField breederElite;
    private Tooltip breederEliteTooltip;
    private Button breederEliteHelpBtn;
    // Breeder Elite Reevaluation
    private HorizontalLayout breederEliteReevalLayout;
    private Select<EnhancedBoolean> breederEliteReeval;
    private Tooltip breederEliteReevalTooltip;
    private Button breederEliteReevalHelpBtn;
    // Breeder Sequential
    private HorizontalLayout breederSequentialLayout;
    private Select<EnhancedBoolean> breederSequential;
    private Tooltip breederSequentialTooltip;
    private Button breederSequentialHelpBtn;
    // Statistics
    private VerticalLayout statisticsLayoutGroup;
    private Span statisticsTitle;
    // Main Upper Layout
    private HorizontalLayout statisticsMainLayout;
    // Statistics Class
    private HorizontalLayout statisticsClassLayout;
    private Select<StatisticsType> statisticsClass;
    private Tooltip statisticsClassTooltip;
    private Button statisticsClassHelpBtn;
    // Statistics File
    private HorizontalLayout statisticsFileLayout;
    private TextField statisticsFile;
    private Tooltip statisticsFileTooltip;
    private Button statisticsFileHelpBtn;
    // Variable Lower Layout
    private HorizontalLayout statisticsVariableLayout;

    //Constructor
    public SimpleTab() {
        setLabel("Simple");
    }

    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        simpleTabLayout = new VerticalLayout();

        createStoppingConditionGroup();
        createSubpopulationGroup();
        createBreederGroup();
        createStatisticsGroup();

        simpleTabLayout.add(stoppingCondLayoutGroup, subpopulationLayoutGroup, breederLayoutGroup, statisticsLayoutGroup);

        return simpleTabLayout;
    }

    //Overrides


    //Internal Functions
    private void createStoppingConditionGroup() {
        // Stopping Condition
        stoppingCondLayoutGroup = new VerticalLayout();
        stoppingCondLayoutGroup.setPadding(true);

        stoppingCondTitle = new Span("Stopping Condition");

        stoppingCondLayout = new HorizontalLayout();
        stoppingCondLayout.setAlignItems(FlexComponent.Alignment.START);
        stoppingCondLayout.setSpacing(true);
        stoppingCondLayout.getThemeList().add("spacing-xl");

        // Stopping Variable type Selector
        stoppingTypeSelectorLayout = new HorizontalLayout();
        stoppingTypeSelectorLayout.setAlignItems(FlexComponent.Alignment.END);
        stoppingVariableTypeSelector = new Select<>();
        stoppingVariableTypeSelector.setLabel("Variable Type");
        stoppingVariableTypeSelector.setItems("Generations", "Evaluations");
        stoppingVariableTypeSelector.setValue("Generations");
        stoppingTooltip = stoppingVariableTypeSelector.getTooltip().withManual(true);
        stoppingTooltip.setText("test tooltip");

        stoppingCondHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        stoppingCondHelpBtn.addClickListener(event -> {
            stoppingTooltip.setOpened(!stoppingTooltip.isOpened());
        });

        stoppingTypeSelectorLayout.add(stoppingVariableTypeSelector, stoppingCondHelpBtn);

        // Stopping Variable Value
        stoppingVariableValue = new IntegerField();
        stoppingVariableValue.setLabel("Value");
        stoppingVariableValue.setMin(1);
        stoppingVariableValue.setValue(50);
        stoppingVariableValue.setStepButtonsVisible(true);

        stoppingCondLayout.add(stoppingTypeSelectorLayout, stoppingVariableValue);
        stoppingCondLayoutGroup.add(stoppingCondTitle, stoppingCondLayout);
    }

    private void createSubpopulationGroup() {
        // Subpopulation
        subpopulationLayoutGroup = new VerticalLayout();
        subpopulationLayoutGroup.setPadding(true);

        subpopulationTitle = new Span("Subpopulation");

        subpopulationLayout = new HorizontalLayout();
        subpopulationLayout.setAlignItems(FlexComponent.Alignment.START);
        subpopulationLayout.setSpacing(true);
        subpopulationLayout.getThemeList().add("spacing-xl");

        // Subpopulation Size
        subpopulationSizeLayout = new HorizontalLayout();
        subpopulationSizeLayout.setAlignItems(FlexComponent.Alignment.END);
        subpopulationSize = new IntegerField();
        subpopulationSize.setLabel("Size");
        subpopulationSize.setMin(1);
        subpopulationSize.setValue(1024);
        subpopulationSize.setStepButtonsVisible(true);
        subpopulationSizeTooltip = subpopulationSize.getTooltip().withManual(true);
        subpopulationSizeTooltip.setText("test tooltip");
        subpopulationHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        subpopulationHelpBtn.addClickListener(event -> {
            subpopulationSizeTooltip.setOpened(!subpopulationSizeTooltip.isOpened());
        });
        subpopulationSizeLayout.add(subpopulationSize, subpopulationHelpBtn);

        // Subpopulation Duplicate Retries
        subpopulationDupRetriesLayout = new HorizontalLayout();
        subpopulationDupRetriesLayout.setAlignItems(FlexComponent.Alignment.END);
        subpopulationDupRetries = new IntegerField();
        subpopulationDupRetries.setLabel("Duplicate Retries");
        subpopulationDupRetries.setMin(0);
        subpopulationDupRetries.setValue(0);
        subpopulationDupRetries.setStepButtonsVisible(true);
        subpopulationDupRetriesTooltip = subpopulationDupRetries.getTooltip().withManual(true);
        subpopulationDupRetriesTooltip.setText("test tooltip");
        subpopulationDupRetriesHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        subpopulationDupRetriesHelpBtn.addClickListener(event -> {
            subpopulationDupRetriesTooltip.setOpened(!subpopulationDupRetriesTooltip.isOpened());
        });
        subpopulationDupRetriesLayout.add(subpopulationDupRetries, subpopulationDupRetriesHelpBtn);

        subpopulationLayout.add(subpopulationSizeLayout, subpopulationDupRetriesLayout);
        subpopulationLayoutGroup.add(subpopulationTitle, subpopulationLayout);
    }

    private void createBreederGroup() {
        // Breeder
        breederLayoutGroup = new VerticalLayout();
        breederLayoutGroup.setPadding(true);

        breederTitle = new Span("Breeder");

        breederLayout = new HorizontalLayout();
        breederLayout.setAlignItems(FlexComponent.Alignment.START);
        breederLayout.setSpacing(true);
        breederLayout.getThemeList().add("spacing-xl");

        // Elite
        breederEliteLayout = new HorizontalLayout();
        breederEliteLayout.setAlignItems(FlexComponent.Alignment.END);
        breederElite = new IntegerField();
        breederElite.setLabel("Elite");
        breederElite.setMin(0);
        breederElite.setValue(10);
        breederElite.setStepButtonsVisible(true);
        breederEliteTooltip = breederElite.getTooltip().withManual(true);
        breederEliteTooltip.setText("test tooltip");
        breederEliteHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        breederEliteHelpBtn.addClickListener(event -> {
            breederEliteTooltip.setOpened(!breederEliteTooltip.isOpened());
        });
        breederEliteLayout.add(breederElite, breederEliteHelpBtn);

        // Elite Reevaluation
        breederEliteReevalLayout = new HorizontalLayout();
        breederEliteReevalLayout.setAlignItems(FlexComponent.Alignment.END);
        breederEliteReeval = new Select<>();
        breederEliteReeval.setLabel("Elite Reevaluation");
        breederEliteReeval.setItems(EnhancedBoolean.values());
        breederEliteReeval.setValue(EnhancedBoolean.FALSE);
        breederEliteReevalTooltip = breederEliteReeval.getTooltip().withManual(true);
        breederEliteReevalTooltip.setText("test tooltip");

        breederEliteReevalHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        breederEliteReevalHelpBtn.addClickListener(event -> {
            breederEliteReevalTooltip.setOpened(!breederEliteReevalTooltip.isOpened());
        });

        breederEliteReevalLayout.add(breederEliteReeval, breederEliteReevalHelpBtn);

        // Sequential Breeding
        breederSequentialLayout = new HorizontalLayout();
        breederSequentialLayout.setAlignItems(FlexComponent.Alignment.END);
        breederSequential = new Select<>();
        breederSequential.setLabel("Sequential Breeding");
        breederSequential.setItems(EnhancedBoolean.values());
        breederSequential.setValue(EnhancedBoolean.FALSE);
        breederSequentialTooltip = breederSequential.getTooltip().withManual(true);
        breederSequentialTooltip.setText("test tooltip");

        breederSequentialHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        breederSequentialHelpBtn.addClickListener(event -> {
            breederSequentialTooltip.setOpened(!breederSequentialTooltip.isOpened());
        });

        breederSequentialLayout.add(breederSequential, breederSequentialHelpBtn);

        breederLayout.add(breederEliteLayout, breederEliteReevalLayout, breederSequentialLayout);
        breederLayoutGroup.add(breederTitle, breederLayout);
    }

    private void createStatisticsGroup() {
        // Statistics
        statisticsLayoutGroup = new VerticalLayout();
        statisticsLayoutGroup.setPadding(true);

        statisticsTitle = new Span("Statistics");

        // Main Upper Layout
        statisticsMainLayout = new HorizontalLayout();
        statisticsMainLayout.setAlignItems(FlexComponent.Alignment.START);
        statisticsMainLayout.setSpacing(true);
        statisticsMainLayout.getThemeList().add("spacing-xl");

        // Statistics Class
        statisticsClassLayout = new HorizontalLayout();
        statisticsClassLayout.setAlignItems(FlexComponent.Alignment.END);
        statisticsClass = new Select<>();
        statisticsClass.setLabel("Elite Reevaluation");
        statisticsClass.setItems(StatisticsType.values());
        //statisticsClass.setValue();
        statisticsClassTooltip = statisticsClass.getTooltip().withManual(true);
        statisticsClassTooltip.setText("test tooltip");

        statisticsClassHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        statisticsClassHelpBtn.addClickListener(event -> {
            statisticsClassTooltip.setOpened(!statisticsClassTooltip.isOpened());
        });

        statisticsClassLayout.add(statisticsClass, statisticsClassHelpBtn);

        // Statistics File
        statisticsFileLayout = new HorizontalLayout();
        statisticsFileLayout.setAlignItems(FlexComponent.Alignment.END);
        statisticsFile = new TextField();
        statisticsFile.setLabel("Filename");
        statisticsFile.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        statisticsFile.setClearButtonVisible(true);
        statisticsFileTooltip = statisticsFile.getTooltip().withManual(true);
        statisticsFileTooltip.setText("test tooltip");
        statisticsFileHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        statisticsFileHelpBtn.addClickListener(event -> {
            statisticsFileTooltip.setOpened(!statisticsFileTooltip.isOpened());
        });
        statisticsFileLayout.add(statisticsFile, statisticsFileHelpBtn);

        // Main Upper Layout Builder
        statisticsMainLayout.add(statisticsClassLayout, statisticsFileLayout);

        // Variable Lower Layout
        statisticsVariableLayout = new HorizontalLayout();
        statisticsVariableLayout.setAlignItems(FlexComponent.Alignment.START);
        statisticsVariableLayout.setSpacing(true);
        statisticsVariableLayout.getThemeList().add("spacing-xl");



        // Statistics Layout Builder
        statisticsLayoutGroup.add(statisticsTitle, statisticsMainLayout, statisticsVariableLayout);
    }
}