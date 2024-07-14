package com.decs.application.views.ProblemEditor.tabs;

import com.decs.application.data.distribution.Island;
import com.decs.application.data.problem.ProblemType;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.utils.constants.TooltipText;
import com.decs.application.utils.types.EnhancedBoolean;
import com.decs.application.utils.constants.FilePathConstants;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import ec.util.Parameter;
import ec.util.ParameterDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <b>Islands Tab Class</b>
 * <p>
 *     This class implements the problem editor islands parameter tab.
 *     It is responsible for all visual components and their behavior.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class IslandsTab extends Tab implements ParamTab {
    //Internal Data
    /**
     * Name of the server island parameter file this tab will generate
     */
    private static final String SERVER_PARAMS_FILENAME = "server.params";
    /**
     * Name of the client islands parameter file this tab will generate
     */
    private static final String CLIENT_PARAMS_FILENAME = "client.params";
    /**
     * Name of the regular islands parameter file this tab will generate
     */
    private static final String ISLAND_PARAMS_FILENAME = "island.params";
    private ArrayList<ParameterDatabase> islandParamDatabaseList;
    private ObjectListDatabase objectListDatabase;
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
    private ComboBox<String> islandSeed;
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

    /**
     * Class Constructor
     * @param objectListDatabase Object list database object
     */
    public IslandsTab(ObjectListDatabase objectListDatabase) {
        setLabel("Islands");
        this.objectListDatabase = objectListDatabase;
        this.islandList = new ArrayList<>();
    }

    //Get Methods


    //Set Methods


    //Overrides
    @Override
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

    @Override
    public String[] getFileName() {
        String[] filenameList = new String[islandList.size()+2];
        int i;
        for (i=0; i<islandList.size(); i++) {
            filenameList[i] = islandList.get(i).getId()+".params";
        }
        filenameList[i] = SERVER_PARAMS_FILENAME;
        i++;
        filenameList[i] = CLIENT_PARAMS_FILENAME;

        return filenameList;
    }

    @Override
    public ParameterDatabase[] createParamDatabase(ProblemType selectedProblem) {
        ParameterDatabase serverParamDatabase;
        ParameterDatabase clientParamDatabase;

        try {
            File serverParamsFile = new File(FilePathConstants.ISLANDS_FOLDER+"/"+SERVER_PARAMS_FILENAME);
            File clientParamsFile = new File(FilePathConstants.ISLANDS_FOLDER+"/"+CLIENT_PARAMS_FILENAME);

            serverParamDatabase = new ParameterDatabase(serverParamsFile,
                    new String[]{"-file", serverParamsFile.getCanonicalPath()});
            clientParamDatabase = new ParameterDatabase(clientParamsFile,
                    new String[]{"-file", clientParamsFile.getCanonicalPath()});

            // Problem (Client)
            //clientParamDatabase.set(new Parameter("parent.0"), objectListDatabase.getProblemCreatorSelector().getCode()+".params");

            // Compression (Client)
            clientParamDatabase.set(new Parameter("exch.compressed"), compressionInput.getValue().valueString());

            // Synchronization (Server)
            serverParamDatabase.set(new Parameter("exch.sync"), syncInput.getValue().valueString());

            // Island Number (Server)
            serverParamDatabase.set(new Parameter("exch.num-islands"), String.valueOf(islandList.size()));

            // Migration Settings
            for (int i=0; i<islandList.size(); i++) {
                serverParamDatabase.set(new Parameter(String.format("exch.island.%d.id", i)), islandList.get(i).getId());
                serverParamDatabase.set(new Parameter(String.format("exch.island.%d.num-mig", i)), String.valueOf(islandList.get(i).getMigrationNumber()));
                for (int ii=0; ii<islandList.get(i).getMigrationNumber(); ii++) {
                    //System.out.println(ii);
                    serverParamDatabase.set(new Parameter(String.format("exch.island.%d.mig.%d", i, ii)), islandList.get(i).getMigrationDestination().get(ii).getId());
                }
                serverParamDatabase.set(new Parameter(String.format("exch.island.%d.size", i)), String.valueOf(islandList.get(i).getMigrationSize()));
                serverParamDatabase.set(new Parameter(String.format("exch.island.%d.mod", i)), String.valueOf(islandList.get(i).getMigrationOffset()));
                serverParamDatabase.set(new Parameter(String.format("exch.island.%d.start", i)), String.valueOf(islandList.get(i).getMigrationStart()));
                serverParamDatabase.set(new Parameter(String.format("exch.island.%d.mailbox-capacity", i)), String.valueOf(islandList.get(i).getMailboxSize()));
            }

            // Create Islands Param Files
            islandParamDatabaseList = new ArrayList<>();
            for (int i=0; i<islandList.size(); i++) {
                //File islandParamFile = new File(FilePathConstants.ISLANDS_FOLDER+"/"+ISLAND_PARAMS_FILENAME);
                //ParameterDatabase newParamDatabase = new ParameterDatabase(islandParamFile,
                //        new String[]{"-file", islandParamFile.getCanonicalPath()});
                ParameterDatabase newParamDatabase = new ParameterDatabase();

                // Parent
                if (i == 0) {
                    newParamDatabase.set(new Parameter("parent.0"), "server.params");
                    objectListDatabase.setServerIsland(islandList.get(i).getId()+".params");
                }
                else {
                    newParamDatabase.set(new Parameter("parent.0"), "client.params");
                }

                // ID
                newParamDatabase.set(new Parameter("exch.id"), islandList.get(i).getId());

                // Stat File
                newParamDatabase.set(new Parameter("stat.file"), islandList.get(i).getId()+".stat");

                // Seed
                newParamDatabase.set(new Parameter("seed.0"), islandList.get(i).getSeed());

                // Port
                newParamDatabase.set(new Parameter("exch.client-port"), String.valueOf(9000 + i));

                islandParamDatabaseList.add(newParamDatabase);
            }
            islandParamDatabaseList.add(serverParamDatabase);
            islandParamDatabaseList.add(clientParamDatabase);
            return islandParamDatabaseList.toArray(ParameterDatabase[]::new);

        } catch (IOException e) {
            System.err.println("IO Exception while opening params file");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception at create parameter database");
            e.printStackTrace();
        }

        return null;
    }

    //Internal Functions
    /**
     * Builds the island list components group
     */
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
            // Layout
            HorizontalLayout manageLayout = new HorizontalLayout();

            // Edit Button
            Button editButton = new Button("Edit");
            editButton.addClickListener( event -> {
                currentIsland = island;
                buildIslandEditor(island.getId());
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

    /**
     * Builds the global settings components group
     */
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
        syncTooltip.setText(TooltipText.islandSyncTooltipText);
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
        compressionInput.setEnabled(false); // Compression isn't supported yet
        compressionTooltip = compressionInput.getTooltip().withManual(true);
        compressionTooltip.setText(TooltipText.islandCompressionTooltipText);
        compressionHelpBtn = new Button(new Icon(VaadinIcon.QUESTION));
        compressionHelpBtn.addClickListener(event -> {
            compressionTooltip.setOpened(!compressionTooltip.isOpened());
        });
        compressionInputLayout.add(compressionInput, compressionHelpBtn);

        // Global Settings Builder
        globalSettingsLayout.add(syncInputLayout, compressionInputLayout);
    }

    /**
     * Refreshes the island list grid component
     */
    private void refreshIslandList() {
        islandGrid.getDataProvider().refreshAll();
    }

    /**
     * Builds the island editor components group
     * @param islandID Identification of the selected island
     */
    private void buildIslandEditor(String islandID) {
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
        islandIDInput.setValue(islandID);
        //islandIDInput.setClearButtonVisible();

        // Island Migration Number
        islandMigrationNumberInput = new IntegerField();
        islandMigrationNumberInput.setLabel("Migration Number");
        islandMigrationNumberInput.setMin(1);
        islandMigrationNumberInput.setValue(1);
        islandMigrationNumberInput.setStepButtonsVisible(true);
        islandMigrationNumberInput.addValueChangeListener(event -> {
            fillMigrationDestinationList(event.getValue(), islandID);
            islandMigDestGrid.getDataProvider().refreshAll();
        });

        // Island Migration Destination
        VerticalLayout islandMigDestLayout = new VerticalLayout();
        Span islandMigDestLabel = new Span("Migration List");
        migrationDestinationIslandList = new ArrayList<>();
        islandMigDestGrid = new Grid<>();
        islandMigDestGrid.setAllRowsVisible(true);
        islandMigDestGridEditor = islandMigDestGrid.getEditor();
        islandMigDestLayout.add(islandMigDestLabel, islandMigDestGrid);

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

        fillMigrationDestinationList(islandMigrationNumberInput.getValue(), islandID);

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
        islandMigrationOffsetInput.setValue(12);

        // Island Mailbox Size
        islandMailboxSizeInput = new IntegerField();
        islandMailboxSizeInput.setLabel("Mailbox Size");
        islandMailboxSizeInput.setMin(1);
        islandMailboxSizeInput.setValue(40);

        // Island Seed
        islandSeed = new ComboBox<>();
        islandSeed.setLabel("Random Seed");
        islandSeed.setAllowCustomValue(true);
        islandSeed.setItems("time");
        islandSeed.setValue("time");

        // Save Btn
        saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        saveBtn.addClickListener(this::saveIsland);

        // Layout Builder
        islandEditorLayout.add(
                islandIDInput,
                islandMigrationNumberInput,
                islandMigDestLayout,
                islandMigrationSizeInput,
                islandMigrationStartInput,
                islandMigrationOffsetInput,
                islandMailboxSizeInput,
                islandSeed,
                saveBtn
        );

        // Dialog Builder
        islandEditor.add(islandEditorLayout);
    }

    // Event Handlers
    /**
     * Creates a new island object
     * @param event Source event instance
     */
    private void createNewIsland(ClickEvent<Button> event) {
        Island newIsland = new Island("isla" + (islandList.size()+1));
        islandList.add(newIsland);
        refreshIslandList();
        objectListDatabase.setIslandList(islandList);
    }

    /**
     * Fills the migration destination list with default values
     * @param n Total number of islands in the list
     * @param islandID Identification of the selected island
     */
    private void fillMigrationDestinationList(int n, String islandID) {
        int it = Math.abs(n - migrationDestinationIslandList.size());
        int size = migrationDestinationIslandList.size();
        for (int i=0; i < it; i++) {
            if (n > size) {
                migrationDestinationIslandList.add(new Island(islandID));
            }
            else if (n < size) {
                migrationDestinationIslandList.remove(migrationDestinationIslandList.size()-1);
            }
        }
    }

    /**
     * Saves the configuration of the respective island
     * @param event Source event
     */
    private void saveIsland(ClickEvent<Button> event) {
        // ID
        currentIsland.setId(islandIDInput.getValue());

        // Migration Number
        currentIsland.setMigrationNumber(islandMigrationNumberInput.getValue());

        // Migration Destination
        currentIsland.setMigrationDestination(migrationDestinationIslandList);

        // Migration Size
        currentIsland.setMigrationSize(islandMigrationSizeInput.getValue());

        // Migration Start
        currentIsland.setMigrationStart(islandMigrationStartInput.getValue());

        // Migration Offset
        currentIsland.setMigrationOffset(islandMigrationOffsetInput.getValue());

        // Mailbox Size
        currentIsland.setMailboxSize(islandMailboxSizeInput.getValue());

        // Seed
        currentIsland.setSeed(islandSeed.getValue());

        // Refresh Island Grid
        islandGrid.getDataProvider().refreshAll();

        // Close Dialog
        islandEditor.close();
    }

    // Data Providers
    /**
     * Data provider for the island list grid
     */
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