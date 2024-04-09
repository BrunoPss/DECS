package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.Island;
import com.decs.application.data.ProblemType;
import com.decs.application.utils.EnhancedBoolean;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.editor.Editor;
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
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataView;
import ec.util.ParameterDatabase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class IslandsTab extends Tab implements ParamTab {
    //Internal Data
    private ArrayList<Island> islandList;
    private Island currentIsland;
    // Island Editor
    private ArrayList<Island> migrationDestinationIslandList;
    private Dialog islandEditor;
    private VerticalLayout islandEditorLayout;
    private TextField islandIDInput;
    private IntegerField islandMigrationNumberInput;
    private Grid<Island> islandMigDestGrid;
    private Editor<Island> islandMigDestGridEditor;
    private Binder<Island> islandMigDestGridBinder;
    private TextField idField;
    private Button saveButton;
    private Button cancelButton;
    private HorizontalLayout actions;
    // Islands Tab Layout
    private HorizontalLayout islandListLayoutGroup;
    private VerticalLayout islandListLayout;
    private HorizontalLayout islandListUpperGroup;
    private Span islandListTitle;
    private Icon islandCreateBtnIcon;
    private Button islandCreateBtn;
    private Grid<Island> islandGrid;
    private IntegerField islandMigrationSizeInput;
    private IntegerField islandMigrationStartInput;
    private IntegerField islandMigrationOffsetInput;
    private IntegerField islandMailboxSizeInput;
    private Button saveBtn;
    // Global Settings
    private HorizontalLayout globalSettingsLayout;
    private HorizontalLayout syncInputLayout;
    private Select<EnhancedBoolean> syncInput;
    private Tooltip syncTooltip;
    private Button syncHelpBtn;
    private HorizontalLayout compressionInputLayout;
    private Select<EnhancedBoolean> compressionInput;
    private Tooltip compressionTooltip;
    private Button compressionHelpBtn;

    //Constructor
    public IslandsTab() {
        setLabel("Islands");

        this.islandList = new ArrayList<>();
        buildIslandEditor();
    }

    //Get Methods


    //Set Methods


    //Methods
    public VerticalLayout buildLayout() {
        // Islands Tab
        VerticalLayout islandsTabLayout = new VerticalLayout();
        islandsTabLayout.setSpacing(true);
        islandsTabLayout.getThemeList().add("spacing-xl");

        createIslandListGroup();

        createGlobalSettingsGroup();

        // Islands Tab Builder
        islandsTabLayout.add(islandListLayoutGroup, globalSettingsLayout);

        return islandsTabLayout;
    }

    //Overrides
    @Override
    public String[] getFileName() {
        return null;
    }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        return null;
    }

    //Internal Functions
    private void createIslandListGroup() {
        // Island List
        islandListLayoutGroup = new HorizontalLayout();
        islandListLayoutGroup.setWidth("100%");

        // List Group
        islandListLayout = new VerticalLayout();

        // Upper Group
        islandListUpperGroup = new HorizontalLayout();
        islandListUpperGroup.setWidth("100%");
        islandListUpperGroup.setAlignItems(FlexComponent.Alignment.CENTER);
        islandListUpperGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Grid Title
        islandListTitle = new Span("Island List");

        // Create Button
        islandCreateBtnIcon = new Icon(VaadinIcon.PLUS);
        islandCreateBtnIcon.setSize("20px");
        islandCreateBtn = new Button(islandCreateBtnIcon);
        islandCreateBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        islandCreateBtn.addClickListener(this::createNewIsland);
        islandCreateBtn.setTooltipText("Create Island");
        islandCreateBtn.setWidth("15px");
        islandCreateBtn.setHeight("23px");

        islandListUpperGroup.add(islandListTitle, islandCreateBtn);

        // Island List Grid
        islandGrid = new Grid<>(Island.class, false);
        islandGrid.setMaxHeight("300px");
        islandGrid.addColumn(Island::getId).setHeader("ID");

        islandGrid.addComponentColumn(island -> {
            currentIsland = island;
            // Layout
            HorizontalLayout manageLayout = new HorizontalLayout();

            // Edit Button
            Button editButton = new Button("Edit");
            editButton.addClickListener( event -> {
                islandEditor.open();
            });

            // Delete Button
            Button deleteButton = new Button();
            deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            deleteButton.addClickListener( event -> {
                islandList.remove(island);
                refreshIslandList();
            });

            // Layout Builder
            manageLayout.add(editButton, deleteButton);

            return manageLayout;
        });

        // Island List Builder
        GridListDataView<Island> islandListUpdater = islandGrid.getListDataView();
        islandGrid.setDataProvider(islandGridDataProvider);

        // Group Builder
        islandListLayout.add(islandListUpperGroup, islandGrid);
        islandListLayoutGroup.add(islandListLayout);
    }

    private void createGlobalSettingsGroup() {
        // Global Settings Layout
        globalSettingsLayout = new HorizontalLayout();
        globalSettingsLayout.setWidth("100%");

        // Synchronization
        syncInputLayout = new HorizontalLayout();
        syncInputLayout.setAlignItems(FlexComponent.Alignment.END);
        syncInput = new Select<>();
        syncInput.setLabel("Synchronization");
        syncInput.setItems(EnhancedBoolean.TRUE, EnhancedBoolean.FALSE);
        syncInput.setValue(EnhancedBoolean.FALSE);
        syncTooltip = syncInput.getTooltip().withManual(true);
        syncTooltip.setText("test tooltip");
        syncHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        syncHelpBtn.addClickListener(event -> {
            syncTooltip.setOpened(!syncTooltip.isOpened());
        });
        syncInputLayout.add(syncInput, syncHelpBtn);

        // Compression
        compressionInputLayout = new HorizontalLayout();
        compressionInputLayout.setAlignItems(FlexComponent.Alignment.END);
        compressionInput = new Select<>();
        compressionInput.setLabel("Compression");
        compressionInput.setItems(EnhancedBoolean.TRUE, EnhancedBoolean.FALSE);
        compressionInput.setValue(EnhancedBoolean.FALSE);
        compressionTooltip = compressionInput.getTooltip().withManual(true);
        compressionTooltip.setText("test tooltip");
        compressionHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        compressionHelpBtn.addClickListener(event -> {
            compressionTooltip.setOpened(!compressionTooltip.isOpened());
        });
        compressionInputLayout.add(compressionInput, compressionHelpBtn);

        // Global Settings Builder
        globalSettingsLayout.add(syncInputLayout, compressionInputLayout);
    }

    private void refreshIslandList() {
        islandGrid.getDataProvider().refreshAll();
    }

    private void buildIslandEditor() {
        // Dialog
        islandEditor = new Dialog();
        islandEditor.setHeaderTitle("Island Editor");
        islandEditor.setWidth("50%");

        // Dialog Layout
        islandEditorLayout = new VerticalLayout();

        // Island ID
        islandIDInput = new TextField();
        islandIDInput.setLabel("ID");
        islandIDInput.setRequired(true);
        //islandIDInput.setClearButtonVisible();

        // Island Migration Number
        islandMigrationNumberInput = new IntegerField();
        islandMigrationNumberInput.setLabel("Migration Number");
        islandMigrationNumberInput.setMin(1);
        islandMigrationNumberInput.setValue(3);
        islandMigrationNumberInput.setStepButtonsVisible(true);
        islandMigrationNumberInput.addValueChangeListener(event -> {
            fillMigrationDestinationList(event.getValue());
            islandMigDestGrid.getDataProvider().refreshAll();
        });

        // Island Migration Destination
        migrationDestinationIslandList = new ArrayList<>();
        islandMigDestGrid = new Grid<>();
        islandMigDestGrid.setAllRowsVisible(true);
        islandMigDestGridEditor = islandMigDestGrid.getEditor();

        Grid.Column<Island> idColumn = islandMigDestGrid.addColumn(Island::getId);
        Grid.Column<Island> editColumn = islandMigDestGrid.addComponentColumn(island -> {
            Button editButton = new Button();
            editButton.setIcon(new Icon(VaadinIcon.EDIT));
            editButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            editButton.addClickListener( event -> {
                if (islandMigDestGridEditor.isOpen())
                    islandMigDestGridEditor.cancel();
                islandMigDestGrid.getEditor().editItem(island);
            });
            return editButton;
        });

        islandMigDestGridBinder = new Binder(Island.class);
        islandMigDestGridEditor.setBinder(islandMigDestGridBinder);
        islandMigDestGridEditor.setBuffered(true);

        idField = new TextField();
        islandMigDestGridBinder.forField(idField)
                .asRequired()
                .bind(Island::getId, Island::setId);
        idColumn.setEditorComponent(idField);

        saveButton = new Button("Save", e -> islandMigDestGridEditor.save());
        cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> islandMigDestGridEditor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        actions = new HorizontalLayout(saveButton,
                cancelButton);

        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        fillMigrationDestinationList(islandMigrationNumberInput.getValue());

        islandMigDestGrid.setItems(migrationDestinationIslandList);

        // Island Migration Size
        islandMigrationSizeInput = new IntegerField();
        islandMigrationSizeInput.setLabel("Migration Size");
        islandMigrationSizeInput.setMin(1);
        islandMigrationSizeInput.setValue(4);

        // Island Migration Start
        islandMigrationStartInput = new IntegerField();
        islandMigrationStartInput.setLabel("Migration Start");
        islandMigrationStartInput.setMin(1);
        islandMigrationStartInput.setValue(1);

        // Island Migration Offset
        islandMigrationOffsetInput = new IntegerField();
        islandMigrationOffsetInput.setLabel("Migration Offset");
        islandMigrationOffsetInput.setMin(1);
        islandMigrationOffsetInput.setValue(4);

        // Island Mailbox Size
        islandMailboxSizeInput = new IntegerField();
        islandMailboxSizeInput.setLabel("Mailbox Size");
        islandMailboxSizeInput.setMin(1);
        islandMailboxSizeInput.setValue(20);

        // Save Btn
        saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        saveBtn.addClickListener(this::saveIsland);

        // Layout Builder
        islandEditorLayout.add(
                islandIDInput,
                islandMigrationNumberInput,
                islandMigDestGrid,
                islandMigrationSizeInput,
                islandMigrationStartInput,
                islandMigrationOffsetInput,
                islandMailboxSizeInput,
                saveBtn
        );

        // Dialog Builder
        islandEditor.add(islandEditorLayout);
    }

    // Event Handlers
    private void createNewIsland(ClickEvent<Button> event) {
        Island newIsland = new Island("Island x");
        islandList.add(newIsland);
        refreshIslandList();
    }

    private void fillMigrationDestinationList(int n) {
        int it = Math.abs(n - migrationDestinationIslandList.size());
        int size = migrationDestinationIslandList.size();
        for (int i=0; i < it; i++) {
            if (n > size) {
                migrationDestinationIslandList.add(new Island("Island " + migrationDestinationIslandList.size()+i));
            }
            else if (n < size) {
                migrationDestinationIslandList.remove(migrationDestinationIslandList.size()-1);
            }
        }
    }

    private void saveIsland(ClickEvent<Button> event) {
        // ID
        currentIsland.setId(islandIDInput.getValue());

        // Refresh Island Grid
        islandGrid.getDataProvider().refreshAll();

        // Close Dialog
        islandEditor.close();
    }

    // Data Providers
    private DataProvider<Island, Void> islandGridDataProvider =
            DataProvider.fromCallbacks(
                    query -> {
                        int offset = query.getOffset();
                        int limit = query.getLimit();
                        return islandList.stream();
                    },
                    query -> islandList.size()
            );
}