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
import ec.util.Parameter;
import ec.util.ParameterDatabase;

import java.io.File;
import java.io.IOException;

public class DistEvalTab extends Tab implements ParamTab {
    //Internal Data
    private static final String MASTER_PARAMS_FILENAME = "master.params";
    private static final String SLAVE_PARAMS_FILENAME = "slave.params";
    // Slave Jobs
    private VerticalLayout slaveJobsGroupLayout;
    private HorizontalLayout slaveJobsGroup;
    private Span slaveJobsGroupTitle;
    private HorizontalLayout slaveJobQueueSizeLayout;
    private IntegerField slaveJobQueueSize;
    private Tooltip slaveJobQueueSizeTooltip;
    private Button slaveJobsHelpBtn;
    private HorizontalLayout slaveJobSizeLayout;
    private IntegerField slaveJobSize;
    private Tooltip slaveJobSizeTooltip;
    private Button slaveJobSizeHelpBtn;
    // Compression
    private VerticalLayout compressionGroupLayout;
    private Span compressionGroupTitle;
    private HorizontalLayout compressionLayout;
    private Select<EnhancedBoolean> compression;
    private Tooltip compressionTooltip;
    private Button compressionHelpBtn;
    // Opp Evol
    private VerticalLayout oppEvolGroupLayout;
    private HorizontalLayout oppEvolGroup;
    private Span oppEvolGroupTitle;
    private HorizontalLayout runEvolveLayout;
    private Select<EnhancedBoolean> runEvolve;
    private Tooltip runEvolveTooltip;
    private Button runEvolveHelpBtn;
    // Run-Evolve Runtime
    private HorizontalLayout runEvolveRuntimeLayout;
    private IntegerField runEvolveRuntime;
    private Tooltip runEvolveRuntimeTooltip;
    private Button runEvolveRuntimeHelpBtn;
    // Return Inds
    private HorizontalLayout returnIndsLayout;
    private Select<EnhancedBoolean> returnInds;
    private Tooltip returnIndsTooltip;
    private Button returnIndsHelpBtn;

    //Constructor
    public DistEvalTab() {
        setLabel("Distribution");
    }

    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        // Distribution Tab
        VerticalLayout distributionTabLayout = new VerticalLayout();
        distributionTabLayout.setSpacing(true);
        distributionTabLayout.getThemeList().add("spacing-xl");

        createSlaveJobsGroup();

        createCompressionGroup();

        createOpportunisticEvolutionGroup();

        // Distribution Tab Builder
        distributionTabLayout.add(slaveJobsGroupLayout, compressionGroupLayout, oppEvolGroupLayout);

        return distributionTabLayout;
    }

    //Overrides
    @Override
    public String[] getFileName() { return new String[]{MASTER_PARAMS_FILENAME, SLAVE_PARAMS_FILENAME}; }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        ParameterDatabase masterParamDatabase;
        ParameterDatabase slaveParamDatabase;
        try {
            File masterParamsFile = new File(FilePathConstants.DISTRIBUTED_EVAL_FOLDER+"/"+MASTER_PARAMS_FILENAME);
            File slaveParamsFile = new File(FilePathConstants.DISTRIBUTED_EVAL_FOLDER+"/"+SLAVE_PARAMS_FILENAME);

            masterParamDatabase = new ParameterDatabase(masterParamsFile,
                    new String[]{"-file", masterParamsFile.getCanonicalPath()});
            slaveParamDatabase = new ParameterDatabase(slaveParamsFile,
                    new String[]{"-file", slaveParamsFile.getCanonicalPath()});

            // Queue Size (master)
            masterParamDatabase.set(new Parameter("eval.masterproblem.max-jobs-per-slave"), slaveJobQueueSize.getValue().toString());

            // Job Size (master)
            masterParamDatabase.set(new Parameter("eval.masterproblem.job-size"), slaveJobSize.getValue().toString());

            // Compression (master)
            masterParamDatabase.set(new Parameter("eval.compression"), compression.getValue().valueString());

            // Run-Evolve (slave)
            slaveParamDatabase.set(new Parameter("eval.slave.run-evolve"), runEvolve.getValue().valueString());

            // Run-Evolve Runtime (slave)
            slaveParamDatabase.set(new Parameter("eval.slave.runtime"), runEvolveRuntime.getValue().toString());

            // Return Individuals (slave)
            slaveParamDatabase.set(new Parameter("eval.return-inds"), returnInds.getValue().valueString());

            return new ParameterDatabase[]{masterParamDatabase, slaveParamDatabase};

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
    private void createSlaveJobsGroup() {
        slaveJobsGroupLayout = new VerticalLayout();
        slaveJobsGroupLayout.setPadding(true);
        slaveJobsGroup = new HorizontalLayout();
        slaveJobsGroup.setAlignItems(FlexComponent.Alignment.START);
        slaveJobsGroup.setSpacing(true);
        slaveJobsGroup.getThemeList().add("spacing-xl");

        slaveJobsGroupTitle = new Span("Slave Jobs");

        // Queue Size
        slaveJobQueueSizeLayout = new HorizontalLayout();
        slaveJobQueueSizeLayout.setAlignItems(FlexComponent.Alignment.END);

        slaveJobQueueSize = new IntegerField();
        slaveJobQueueSize.setLabel("Queue Size");
        slaveJobQueueSize.setMin(1);
        slaveJobQueueSize.setValue(2);
        slaveJobQueueSize.setStepButtonsVisible(true);
        slaveJobQueueSizeTooltip = slaveJobQueueSize.getTooltip().withManual(true);
        slaveJobQueueSizeTooltip.setText(TooltipText.slaveJobQueueSizeTooltipText);

        slaveJobsHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        slaveJobsHelpBtn.addClickListener(event -> {
            slaveJobQueueSizeTooltip.setOpened(!slaveJobQueueSizeTooltip.isOpened());
        });

        slaveJobQueueSizeLayout.add(slaveJobQueueSize, slaveJobsHelpBtn);

        // Job Size
        slaveJobSizeLayout = new HorizontalLayout();
        slaveJobSizeLayout.setAlignItems(FlexComponent.Alignment.END);

        slaveJobSize = new IntegerField();
        slaveJobSize.setLabel("Job Size");
        slaveJobSize.setMin(1);
        slaveJobSize.setValue(1);
        slaveJobSize.setStepButtonsVisible(true);
        slaveJobSizeTooltip = slaveJobSize.getTooltip().withManual(true);
        slaveJobSizeTooltip.setText(TooltipText.slaveJobSizeTooltipText);

        slaveJobSizeHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        slaveJobSizeHelpBtn.addClickListener(event -> {
            slaveJobSizeTooltip.setOpened(!slaveJobSizeTooltip.isOpened());
        });

        slaveJobQueueSizeLayout.add(slaveJobSize, slaveJobSizeHelpBtn);

        // Group Builder
        slaveJobsGroup.add(slaveJobQueueSizeLayout, slaveJobSizeLayout);
        slaveJobsGroupLayout.add(slaveJobsGroupTitle, slaveJobsGroup);
    }

    private void createCompressionGroup() {
        compressionGroupLayout = new VerticalLayout();
        compressionGroupLayout.setPadding(true);

        compressionGroupTitle = new Span("Compression");

        // Compression
        compressionLayout = new HorizontalLayout();
        compressionLayout.setAlignItems(FlexComponent.Alignment.END);

        compression = new Select<>();
        compression.setItems(EnhancedBoolean.values());
        compression.setValue(EnhancedBoolean.FALSE);
        compression.setEnabled(false);// JZLib is not installed yet
        compressionTooltip = compression.getTooltip().withManual(true);
        compressionTooltip.setText(TooltipText.compressionTooltipText);

        compressionHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        compressionHelpBtn.addClickListener(event -> {
            compressionTooltip.setOpened(!compressionTooltip.isOpened());
        });

        compressionLayout.add(compression, compressionHelpBtn);

        // Group Builder
        compressionGroupLayout.add(compressionGroupTitle, compression);
    }

    private void createOpportunisticEvolutionGroup() {
        oppEvolGroupLayout = new VerticalLayout();
        oppEvolGroupLayout.setPadding(true);
        oppEvolGroup = new HorizontalLayout();
        oppEvolGroup.setAlignItems(FlexComponent.Alignment.START);
        oppEvolGroup.setSpacing(true);
        oppEvolGroup.getThemeList().add("spacing-xl");

        oppEvolGroupTitle = new Span("Opportunistic Evolution");

        // Run-Evolve
        runEvolveLayout = new HorizontalLayout();
        runEvolveLayout.setAlignItems(FlexComponent.Alignment.END);

        runEvolve = new Select<>();
        runEvolve.setLabel("Run-Evolve");
        runEvolve.setItems(EnhancedBoolean.values());
        runEvolve.setValue(EnhancedBoolean.FALSE);
        runEvolveTooltip = runEvolve.getTooltip().withManual(true);
        runEvolveTooltip.setText(TooltipText.runEvolveTooltipText);

        runEvolveHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        runEvolveHelpBtn.addClickListener(event -> {
            runEvolveTooltip.setOpened(!runEvolveTooltip.isOpened());
        });

        runEvolveLayout.add(runEvolve, runEvolveHelpBtn);

        // Run-Evolve Runtime
        runEvolveRuntimeLayout = new HorizontalLayout();
        runEvolveRuntimeLayout.setAlignItems(FlexComponent.Alignment.END);

        runEvolveRuntime = new IntegerField();
        runEvolveRuntime.setLabel("Run-Evolve Runtime");
        runEvolveRuntime.setMin(1);
        runEvolveRuntime.setValue(6000);
        runEvolveRuntime.setStepButtonsVisible(true);
        runEvolveRuntimeTooltip = runEvolveRuntime.getTooltip().withManual(true);
        runEvolveRuntimeTooltip.setText(TooltipText.runEvolveRuntimeTooltipText);

        runEvolveRuntimeHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        runEvolveRuntimeHelpBtn.addClickListener(event -> {
            runEvolveRuntimeTooltip.setOpened(!runEvolveRuntimeTooltip.isOpened());
        });

        runEvolveRuntimeLayout.add(runEvolveRuntime, runEvolveRuntimeHelpBtn);

        // Return Individuals
        returnIndsLayout = new HorizontalLayout();
        returnIndsLayout.setAlignItems(FlexComponent.Alignment.END);

        returnInds = new Select<>();
        returnInds.setLabel("Return Individuals");
        returnInds.setItems(EnhancedBoolean.values());
        returnInds.setValue(EnhancedBoolean.FALSE);
        returnIndsTooltip = runEvolveRuntime.getTooltip().withManual(true);
        returnIndsTooltip.setText(TooltipText.returnIndsTooltipText);

        returnIndsHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        returnIndsHelpBtn.addClickListener(event -> {
            returnIndsTooltip.setOpened(!returnIndsTooltip.isOpened());
        });

        returnIndsLayout.add(returnInds, returnIndsHelpBtn);

        // Group Builder
        oppEvolGroup.add(runEvolveLayout, runEvolveRuntimeLayout, returnIndsLayout);
        oppEvolGroupLayout.add(oppEvolGroupTitle, oppEvolGroup);
    }
}