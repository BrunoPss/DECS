package com.decs.application.views.jobbuilder;

import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

@PageTitle("Job Builder")
@Route(value = "job-builder", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class JobBuilderView extends Composite<VerticalLayout> {

    public JobBuilderView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Tabs tabs = new Tabs();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        tabs.setWidth("100%");
        tabs.add(new Tab("General"));
        getContent().add(layoutColumn2);
        layoutColumn2.add(tabs);
    }

    private void setTabsSampleData(Tabs tabs) {

    }
}
