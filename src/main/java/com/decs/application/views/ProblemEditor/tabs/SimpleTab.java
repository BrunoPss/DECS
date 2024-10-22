package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.problem.ProblemType;
import com.decs.application.utils.constants.TooltipText;
import com.decs.application.utils.types.EnhancedBoolean;
import com.decs.application.utils.constants.FilePathConstants;
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
import ec.util.Parameter;
import ec.util.ParameterDatabase;

import java.io.File;
import java.io.IOException;

/**
 * <b>Simple Tab Class</b>
 * <p>
 *     This class implements the problem editor Simple parameter tab.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class SimpleTab extends Tab implements ParamTab {
    //Internal Data
    /**
     * Name of the parameter file this tab will generate
     */
    private static final String PARAMS_FILENAME = "simple.params";
    //Layouts
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

    /**
     * Class Constructor
     */
    public SimpleTab() {
        setLabel("Simple");
    }

    //Get Methods


    //Set Methods


    //Overrides
    @Override
    public VerticalLayout buildLayout() {
        simpleTabLayout = new VerticalLayout();

        createStoppingConditionGroup();
        createSubpopulationGroup();
        createBreederGroup();
        createStatisticsGroup();

        simpleTabLayout.add(stoppingCondLayoutGroup, subpopulationLayoutGroup, breederLayoutGroup, statisticsLayoutGroup);

        return simpleTabLayout;
    }

    @Override
    public String[] getFileName() { return new String[]{PARAMS_FILENAME}; }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        ParameterDatabase paramDatabase;

        try {
            File paramsFile = new File(FilePathConstants.DEFAULTS_PARAMS_FOLDER + "/" + PARAMS_FILENAME);
            paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            // Parent
            paramDatabase.set(new Parameter("parent.0"), "ec.params");

            // Generations / Evaluations
            paramDatabase.set(new Parameter(stoppingVariableTypeSelector.getValue().toLowerCase()), stoppingVariableValue.getValue().toString());

            // Subpopulation
            paramDatabase.set(new Parameter("pop.subpop.0.size"), subpopulationSize.getValue().toString());
            paramDatabase.set(new Parameter("pop.subpop.0.duplicate-retries"), subpopulationDupRetries.getValue().toString());

            // Breeder
            paramDatabase.set(new Parameter("breed.elite.0"), breederElite.getValue().toString());
            paramDatabase.set(new Parameter("breed.reevaluate-elites.0"), breederEliteReeval.getValue().valueString());
            paramDatabase.set(new Parameter("breed.sequential"), breederSequential.getValue().valueString());

            return new ParameterDatabase[]{paramDatabase};

        } catch (IOException e) {
            System.err.println("IO Exception while opening params file");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at createParameterDatabase");
            e.printStackTrace();
        }

        return null;
    }

    //Internal Functions

    /**
     * Builds the stopping condition components group
     */
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
        stoppingTooltip.setText(TooltipText.stoppingTooltipText);

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

    /**
     * Builds the subpopulation components group
     */
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
        subpopulationSizeTooltip.setText(TooltipText.subpopulationSizeTooltipText);
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
        subpopulationDupRetriesTooltip.setText(TooltipText.subpopulationDupRetriesTooltipText);
        subpopulationDupRetriesHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        subpopulationDupRetriesHelpBtn.addClickListener(event -> {
            subpopulationDupRetriesTooltip.setOpened(!subpopulationDupRetriesTooltip.isOpened());
        });
        subpopulationDupRetriesLayout.add(subpopulationDupRetries, subpopulationDupRetriesHelpBtn);

        subpopulationLayout.add(subpopulationSizeLayout, subpopulationDupRetriesLayout);
        subpopulationLayoutGroup.add(subpopulationTitle, subpopulationLayout);
    }

    /**
     * Builds the breeder components group
     */
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
        breederEliteTooltip.setText(TooltipText.breederEliteTooltipText);
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
        breederEliteReevalTooltip.setText(TooltipText.breederEliteReevalTooltipText);

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
        breederSequentialTooltip.setText(TooltipText.breederSequentialTooltipText);

        breederSequentialHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        breederSequentialHelpBtn.addClickListener(event -> {
            breederSequentialTooltip.setOpened(!breederSequentialTooltip.isOpened());
        });

        breederSequentialLayout.add(breederSequential, breederSequentialHelpBtn);

        breederLayout.add(breederEliteLayout, breederEliteReevalLayout, breederSequentialLayout);
        breederLayoutGroup.add(breederTitle, breederLayout);
    }

    /**
     * Builds the statistics components group
     */
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
        statisticsClass.setLabel("Statistics Class");
        statisticsClass.setItems(StatisticsType.values());
        statisticsClass.setValue(StatisticsType.SIMPLE);
        //statisticsClass.setValue();
        statisticsClassTooltip = statisticsClass.getTooltip().withManual(true);
        statisticsClassTooltip.setText(TooltipText.statisticsClassTooltipText);

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
        statisticsFile.setValue("stats");
        statisticsFileTooltip = statisticsFile.getTooltip().withManual(true);
        statisticsFileTooltip.setText(TooltipText.statisticsFileTooltipText);
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