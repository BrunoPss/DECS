package com.decs.application.views.nodemanager;

import com.decs.application.services.SlaveManager;
import com.decs.application.views.MainLayout;
import com.shared.SlaveInfo;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

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

        // Node List Build
        nodeListUpdater = nodeListGrid.getListDataView();
        nodeListGrid.setDataProvider(slaveManager.getSlaveListDataProvider());

        // Group Builder
        nodeListGridLayout.add(nodeListUpperGroup, nodeListGrid);
        nodeListLayoutGroup.add(nodeListGridLayout);
    }

    // Event Handlers
    private void updateSlaveList(ClickEvent<Button> event) {
        System.out.println("Slave List Refresh");
        slaveManager.getSlaveListDataProvider().refreshAll();
    }
}
