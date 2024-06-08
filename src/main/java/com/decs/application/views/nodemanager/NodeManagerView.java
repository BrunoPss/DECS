package com.decs.application.views.nodemanager;

import com.decs.application.services.SlaveManager;
import com.decs.application.views.MainLayout;
import com.decs.shared.SlaveInfo;
import com.decs.shared.SystemInformation;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.rmi.RemoteException;

@PageTitle("Node Manager")
@Route(value = "node-manager", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class NodeManagerView extends Composite<VerticalLayout> {
    //Internal Data
    private SlaveManager slaveManager;
    // Node List
    private HorizontalLayout nodeListLayoutGroup;
    private VerticalLayout nodeListGridLayout;
    private HorizontalLayout nodeListUpperGroup;
    private Span nodeListTitle;
    private Icon nodeListTitleUpdateBtnIcon;
    private Button nodeListTitleUpdateBtn;
    private Grid<SlaveInfo> nodeListGrid;
    private Span connectedSlaves;
    private GridListDataView<SlaveInfo> nodeListUpdater;

    //Constructor
    public NodeManagerView(SlaveManager slaveManager) {
        this.slaveManager = slaveManager;

        createNodeList();

        getContent().add(nodeListLayoutGroup);
    }

    //Get Methods


    //Set Methods


    //Methods


    //Override


    //Internal Functions
    private void createNodeList() {
        // Node List
        nodeListLayoutGroup = new HorizontalLayout();
        nodeListLayoutGroup.setWidth("100%");
        //nodeListLayoutGroup.setHeight("330px");

        // Grid Group
        nodeListGridLayout = new VerticalLayout();

        // Upper Group
        nodeListUpperGroup = new HorizontalLayout();
        nodeListUpperGroup.setWidth("100%");
        nodeListUpperGroup.setAlignItems(FlexComponent.Alignment.CENTER);
        nodeListUpperGroup.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        nodeListTitle = new Span("Node List");

        nodeListTitleUpdateBtnIcon = new Icon(VaadinIcon.REFRESH);
        nodeListTitleUpdateBtnIcon.setSize("20px");
        nodeListTitleUpdateBtn = new Button(nodeListTitleUpdateBtnIcon);
        nodeListTitleUpdateBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        nodeListTitleUpdateBtn.addClickListener(this::updateSlaveList);
        nodeListTitleUpdateBtn.setTooltipText("Refresh");
        nodeListTitleUpdateBtn.setWidth("15px");
        nodeListTitleUpdateBtn.setHeight("19px");

        nodeListUpperGroup.add(nodeListTitle, nodeListTitleUpdateBtn);

        // Node List Grid
        nodeListGrid = new Grid<>(SlaveInfo.class, false);
        nodeListGrid.setMaxHeight("300px");
        nodeListGrid.addColumn(SlaveInfo::getId).setHeader("ID");
        nodeListGrid.addColumn(SlaveInfo::getAddress).setHeader("Address");
        nodeListGrid.addColumn(SlaveInfo::getPort).setHeader("Port");
        nodeListGrid.addColumn(createNodeInfoRenderer()).setHeader("Info");

        // Connected Slaves number
        connectedSlaves = new Span(String.format("Connected Slaves: %d", slaveManager.getConnectedSlaves()));

        // Node List Build
        nodeListUpdater = nodeListGrid.getListDataView();
        nodeListGrid.setDataProvider(slaveManager.getSlaveListDataProvider());

        // Group Builder
        nodeListGridLayout.add(nodeListUpperGroup, nodeListGrid, connectedSlaves);
        nodeListLayoutGroup.add(nodeListGridLayout);
    }

    // Event Handlers
    private void updateSlaveList(ClickEvent<Button> event) {
        //System.out.println("Slave List Refresh");
        slaveManager.getSlaveListDataProvider().refreshAll();
        connectedSlaves.setText(String.format("Connected Slaves: %d", slaveManager.getConnectedSlaves()));
    }

    // Component Renderers
    private ComponentRenderer<Button, SlaveInfo> createNodeInfoRenderer() {
        return new ComponentRenderer<>(Button::new, nodeInfoButton);
    }
    private final SerializableBiConsumer<Button, SlaveInfo> nodeInfoButton = ( button, currentSlave ) -> {
        Icon btnIcon = new Icon(VaadinIcon.INFO_CIRCLE);
        button.setIcon(btnIcon);
        button.addClickListener(event -> {
            try {
                SystemInformation sysInf = currentSlave.getSlaveService().getSystemInformation();
                buildSlaveInfoDialog(currentSlave, sysInf).open();
            } catch (RemoteException e) {
                System.err.println("Remote Exception");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Exception at nodeInfoButton");
                e.printStackTrace();
            }
        });
    };

    // Dialogs
    private Dialog buildSlaveInfoDialog(SlaveInfo currentSlave, SystemInformation systemInformation) {
        Dialog slaveInfoDialog = new Dialog();
        slaveInfoDialog.setHeaderTitle("Slave Info");
        slaveInfoDialog.setMinWidth("60%");
        slaveInfoDialog.setMaxWidth("60%");
        slaveInfoDialog.setMinHeight("80%");
        slaveInfoDialog.setMaxHeight("80%");

        // Layout Group
        VerticalLayout slaveInfoLayoutGroup = new VerticalLayout();
        slaveInfoLayoutGroup.setHeightFull();
        slaveInfoLayoutGroup.setWidthFull();

        // System Layout
        VerticalLayout slaveInfoSystemLayout = new VerticalLayout();
        // Title
        Span slaveInfoSystemTitle = new Span("System");
        slaveInfoSystemTitle.getStyle().set("font-weight", "bold");
        // System Information Layout Group
        HorizontalLayout slaveInfoSystemValuesGroup = new HorizontalLayout();
        // Title Group
        VerticalLayout slaveInfoSystemTitleLayout = new VerticalLayout();
        Span userNameTitle = new Span("Username:");
        Span OSNameTitle = new Span("OS Name:");
        Span OSVersionTitle = new Span("OS Version:");
        Span OSArchTitle = new Span("OS Architecture:");
        slaveInfoSystemTitleLayout.add(userNameTitle, OSNameTitle, OSVersionTitle, OSArchTitle);
        // Values Group
        VerticalLayout slaveInfoSystemValuesLayout = new VerticalLayout();
        Span userNameValue = new Span(systemInformation.getUsername());
        Span OSNameValue = new Span(systemInformation.getOSName());
        Span OSVersionValue = new Span(systemInformation.getOSVersion());
        Span OSArchValue = new Span(systemInformation.getOSArchitecture());
        slaveInfoSystemValuesLayout.add(userNameValue, OSNameValue, OSVersionValue, OSArchValue);
        // System Information Layout Group Builder
        slaveInfoSystemValuesGroup.add(slaveInfoSystemTitleLayout, slaveInfoSystemValuesLayout);
        // System Layout Builder
        slaveInfoSystemLayout.add(slaveInfoSystemTitle, slaveInfoSystemValuesGroup);

        // Network Layout
        VerticalLayout slaveInfoNetworkLayout = new VerticalLayout();
        // Title
        Span slaveInfoNetworkTitle = new Span("Network");
        slaveInfoNetworkTitle.getStyle().set("font-weight", "bold");
        // Network Information Layout Group
        HorizontalLayout slaveInfoNetworkValuesGroup = new HorizontalLayout();
        // Title Group
        VerticalLayout slaveInfoNetworkTitleLayout = new VerticalLayout();
        Span IDTitle = new Span("Network ID:");
        Span addressTitle = new Span("Address:");
        Span portTitle = new Span("Port:");
        slaveInfoNetworkTitleLayout.add(IDTitle, addressTitle, portTitle);
        // Values Group
        VerticalLayout slaveInfoNetworkValuesLayout = new VerticalLayout();
        Span IDValue = new Span(currentSlave.getId());
        Span addressValue = new Span(currentSlave.getAddress());
        Span portValue = new Span(String.valueOf(currentSlave.getPort()));
        slaveInfoNetworkValuesLayout.add(IDValue, addressValue, portValue);
        // Network Information Layout Group Builder
        slaveInfoNetworkValuesGroup.add(slaveInfoNetworkTitleLayout, slaveInfoNetworkValuesLayout);
        // Network Layout Builder
        slaveInfoNetworkLayout.add(slaveInfoNetworkTitle, slaveInfoNetworkValuesGroup);

        // Hardware Layout
        VerticalLayout slaveInfoHardwareLayout = new VerticalLayout();
        // Title
        Span slaveInfoHardwareTitle = new Span("Hardware");
        slaveInfoHardwareTitle.getStyle().set("font-weight", "bold");
        // Hardware Information Layout Group
        HorizontalLayout slaveInfoHardwareValuesGroup = new HorizontalLayout();
        // Title Group
        VerticalLayout slaveInfoHardwareTitleLayout = new VerticalLayout();
        Span CPUCoreTitle = new Span("CPU Cores:");
        slaveInfoHardwareTitleLayout.add(CPUCoreTitle);
        // Values Group
        VerticalLayout slaveInfoHardwareValuesLayout = new VerticalLayout();
        Span CPUCoreValue = new Span(String.valueOf(systemInformation.getCPUCoreNumber()));
        slaveInfoHardwareValuesLayout.add(CPUCoreValue);
        // Hardware Information Layout Group Builder
        slaveInfoHardwareValuesGroup.add(slaveInfoHardwareTitleLayout, slaveInfoHardwareValuesLayout);
        // Hardware Layout Builder
        slaveInfoHardwareLayout.add(slaveInfoHardwareTitle, slaveInfoHardwareValuesGroup);

        // Info Layout Group Builder
        slaveInfoLayoutGroup.add(slaveInfoSystemLayout, slaveInfoNetworkLayout, slaveInfoHardwareLayout);

        // Dialog Builder
        slaveInfoDialog.add(slaveInfoLayoutGroup);

        return slaveInfoDialog;
    }
}