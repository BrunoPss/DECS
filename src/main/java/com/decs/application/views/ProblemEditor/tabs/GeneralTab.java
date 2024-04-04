package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.ParameterGroupType;
import com.decs.application.data.ProblemType;
import com.decs.application.utils.EnhancedBoolean;
import com.decs.application.utils.constants.FilePathConstants;
import com.decs.application.views.ProblemEditor.ProblemEditorView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import org.springframework.security.core.parameters.P;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GeneralTab extends Tab implements ParamTab {
    //Internal Data
    private static final String PARAMS_FILENAME = "ec.params";
    // General Tab
    private VerticalLayout generalTabLayout;
    // Problem Selector
    private Select<ProblemType> problemSelector;
    // Jobs and Seed Group
    private VerticalLayout jobSeedGroupLayout;
    private HorizontalLayout jobSeedLayout;
    private Span jobSeedGroupTitle;
    // Jobs
    private HorizontalLayout jobInputLayout;
    private IntegerField jobInput;
    private Tooltip jobTooltip;
    private Button jobHelpBtn;
    // Seed
    private HorizontalLayout seedInputLayout;
    private ComboBox<String> seedInput;
    private Tooltip seedTooltip;
    private Button seedHelpBtn;
    // Multithreading Group
    private VerticalLayout multithreadingGroupLayout;
    private Span multithreadingGroupTitle;
    private HorizontalLayout evalBreedLayout;
    private HorizontalLayout evalInputLayout;
    private IntegerField evalInput;
    private Tooltip evalTooltip;
    private Button evalHelpBtn;
    private HorizontalLayout breedInputLayout;
    private IntegerField breedInput;
    private Tooltip breedTooltip;
    private Button breedHelpBtn;
    // Checkpoint Group
    private VerticalLayout checkpointGroupLayout;
    private Span checkpointGroupTitle;
    private HorizontalLayout moduloPrefixLayout;
    private HorizontalLayout checkpointInputLayout;
    private Select<EnhancedBoolean> checkpointInput;
    private Tooltip checkpointTooltip;
    private Button checkpointHelpBtn;
    private HorizontalLayout moduloInputLayout;
    private IntegerField moduloInput;
    private Tooltip moduloTooltip;
    private Button moduloHelpBtn;
    private HorizontalLayout prefixInputLayout;
    private TextField prefixInput;
    private Tooltip prefixTooltip;
    private Button prefixHelpBtn;

    //Constructor
    public GeneralTab() {
        setLabel("General");
    }

    //Get Methods
    public Select<ProblemType> getProblemSelector() { return this.problemSelector; }

    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        // General Tab
        generalTabLayout = new VerticalLayout();
        generalTabLayout.setSpacing(true);
        generalTabLayout.getThemeList().add("spacing-xl");

        createProblemSelector();

        createJobsSeedGroup();

        createMultithreadingGroup();

        createCheckpointGroup();

        // General Tab Builder
        generalTabLayout.add(problemSelector, jobSeedGroupLayout, multithreadingGroupLayout, checkpointGroupLayout);

        return generalTabLayout;
    }

    //Overrides
    @Override
    public String getFileName() { return PARAMS_FILENAME; }

    @Override
    public ParameterDatabase createParamDatabase(ProblemType selectedProblem) {
        ParameterDatabase paramDatabase;
        try {
            File paramsFile = new File(FilePathConstants.FACTORY_PARAMS_FOLDER + "/" + selectedProblem.getCode() + "/" + PARAMS_FILENAME);
            paramDatabase = new ParameterDatabase(paramsFile,
                    new String[]{"-file", paramsFile.getCanonicalPath()});

            // Jobs
            //...

            // Eval / Breed Threads
            paramDatabase.set(new Parameter("evalthreads"), evalInput.getValue().toString());
            paramDatabase.set(new Parameter("breedthreads"), breedInput.getValue().toString());

            // Random Seed
            paramDatabase.set(new Parameter("seed.0"), seedInput.getValue());

            // Checkpoint
            paramDatabase.set(new Parameter("checkpoint"), checkpointInput.getValue().valueString());
            paramDatabase.set(new Parameter("checkpoint-modulo"), moduloInput.getValue().toString());
            paramDatabase.set(new Parameter("checkpoint-prefix"), prefixInput.getValue());

            return paramDatabase;

        } catch (IOException e) {
            System.err.println("IO Exception while opening params file");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception");
            e.printStackTrace();
        }

        return null;
    }

    //Internal Functions
    private void createProblemSelector() {
        problemSelector = new Select<>();
        problemSelector.setLabel("Problem");
        problemSelector.setPlaceholder("Select Problem");
        // Select Values -> .param files available in the folder or ProblemType values
        problemSelector.setItems(ProblemType.values());
        //problemSelector.addValueChangeListener();
    }

    private void createJobsSeedGroup() {
        jobSeedGroupLayout = new VerticalLayout();
        jobSeedGroupLayout.setPadding(true);
        jobSeedLayout = new HorizontalLayout();
        jobSeedLayout.setAlignItems(FlexComponent.Alignment.START);
        jobSeedLayout.setSpacing(true);
        jobSeedLayout.getThemeList().add("spacing-xl");

        jobSeedGroupTitle = new Span("Misc");

        // Jobs
        jobInputLayout = new HorizontalLayout();
        jobInputLayout.setAlignItems(FlexComponent.Alignment.END);

        jobInput = new IntegerField();
        jobInput.setLabel("Jobs");
        jobInput.setMin(1);
        jobInput.setValue(1);
        jobInput.setStepButtonsVisible(true);
        jobTooltip = jobInput.getTooltip().withManual(true);
        jobTooltip.setText("test tooltip");

        jobHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        jobHelpBtn.addClickListener(event -> {
            jobTooltip.setOpened(!jobTooltip.isOpened());
        });

        jobInputLayout.add(jobInput, jobHelpBtn);

        // Seed
        seedInputLayout = new HorizontalLayout();
        seedInputLayout.setAlignItems(FlexComponent.Alignment.END);
        jobSeedLayout.setSpacing(true);
        jobSeedLayout.getThemeList().add("spacing-s");

        seedInput = new ComboBox<>();
        seedInput.setLabel("Random Seed");
        seedInput.setAllowCustomValue(true);
        seedInput.setItems("time");
        seedTooltip = seedInput.getTooltip().withManual(true);
        seedTooltip.setText("test tooltip");

        seedHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        seedHelpBtn.addClickListener(event -> {
            seedTooltip.setOpened(!seedTooltip.isOpened());
        });

        seedInputLayout.add(seedInput, seedHelpBtn);

        // Jobs / Seed Group Builder
        jobSeedLayout.add(jobInputLayout, seedInputLayout);
        jobSeedGroupLayout.add(jobSeedGroupTitle, jobSeedLayout);
    }

    private void createMultithreadingGroup() {
        multithreadingGroupLayout = new VerticalLayout();
        multithreadingGroupLayout.setPadding(true);

        multithreadingGroupTitle = new Span("Multithreading");

        evalBreedLayout = new HorizontalLayout();
        evalBreedLayout.setSpacing(true);
        evalBreedLayout.getThemeList().add("spacing-xl");

        evalInputLayout = new HorizontalLayout();
        evalInputLayout.setAlignItems(FlexComponent.Alignment.END);
        evalInput = new IntegerField();
        evalInput.setLabel("Eval Threads");
        evalInput.setMin(1);
        evalInput.setValue(1);
        evalInput.setStepButtonsVisible(true);
        evalTooltip = evalInput.getTooltip().withManual(true);
        evalTooltip.setText("test tooltip");
        evalHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        evalHelpBtn.addClickListener(event -> {
            evalTooltip.setOpened(!evalTooltip.isOpened());
        });
        evalInputLayout.add(evalInput, evalHelpBtn);

        breedInputLayout = new HorizontalLayout();
        breedInputLayout.setAlignItems(FlexComponent.Alignment.END);
        breedInput = new IntegerField();
        breedInput.setLabel("Breed Threads");
        breedInput.setMin(1);
        breedInput.setValue(1);
        breedInput.setStepButtonsVisible(true);
        breedTooltip = breedInput.getTooltip().withManual(true);
        breedTooltip.setText("test tooltip");
        breedHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        breedHelpBtn.addClickListener(event -> {
            breedTooltip.setOpened(!breedTooltip.isOpened());
        });
        breedInputLayout.add(breedInput, breedHelpBtn);

        evalBreedLayout.add(evalInputLayout, breedInputLayout);
        multithreadingGroupLayout.add(multithreadingGroupTitle, evalBreedLayout);
    }

    private void createCheckpointGroup() {
        checkpointGroupLayout = new VerticalLayout();
        checkpointGroupLayout.setPadding(true);

        checkpointGroupTitle = new Span("Checkpoint");

        moduloPrefixLayout = new HorizontalLayout();
        moduloPrefixLayout.setSpacing(true);
        moduloPrefixLayout.getThemeList().add("spacing-xl");

        checkpointInputLayout = new HorizontalLayout();
        checkpointInputLayout.setAlignItems(FlexComponent.Alignment.END);
        checkpointInput = new Select<>();
        checkpointInput.setLabel("Checkpoint");
        checkpointInput.setItems(EnhancedBoolean.TRUE, EnhancedBoolean.FALSE);
        checkpointInput.setValue(EnhancedBoolean.FALSE);
        checkpointTooltip = checkpointInput.getTooltip().withManual(true);
        checkpointTooltip.setText("test tooltip");
        checkpointHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        checkpointHelpBtn.addClickListener(event -> {
            checkpointTooltip.setOpened(!checkpointTooltip.isOpened());
        });
        checkpointInputLayout.add(checkpointInput, checkpointHelpBtn);

        moduloInputLayout = new HorizontalLayout();
        moduloInputLayout.setAlignItems(FlexComponent.Alignment.END);
        moduloInput = new IntegerField();
        moduloInput.setLabel("Modulo");
        moduloInput.setMin(1);
        moduloInput.setValue(1);
        moduloInput.setStepButtonsVisible(true);
        moduloTooltip = moduloInput.getTooltip().withManual(true);
        moduloTooltip.setText("test tooltip");
        moduloHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        moduloHelpBtn.addClickListener(event -> {
            moduloTooltip.setOpened(!moduloTooltip.isOpened());
        });
        moduloInputLayout.add(moduloInput, moduloHelpBtn);

        prefixInputLayout = new HorizontalLayout();
        prefixInputLayout.setAlignItems(FlexComponent.Alignment.END);
        prefixInput = new TextField();
        prefixInput.setLabel("Prefix");
        prefixInput.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        prefixInput.setClearButtonVisible(true);
        prefixInput.setRequired(true);
        prefixInput.setRequiredIndicatorVisible(true);
        prefixInput.setMaxLength(50);
        prefixTooltip = prefixInput.getTooltip().withManual(true);
        prefixTooltip.setText("test tooltip");
        prefixHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        prefixHelpBtn.addClickListener(event -> {
            prefixTooltip.setOpened(!prefixTooltip.isOpened());
        });
        prefixInputLayout.add(prefixInput, prefixHelpBtn);

        moduloPrefixLayout.add(checkpointInputLayout, moduloInputLayout, prefixInputLayout);
        checkpointGroupLayout.add(checkpointGroupTitle, moduloPrefixLayout);
    }

    // Event Listeners
}