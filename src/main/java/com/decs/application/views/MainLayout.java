package com.decs.application.views;

import com.decs.application.data.user.User;
import com.decs.application.security.AuthenticatedUser;
import com.decs.application.services.ObjectListDatabase;
import com.decs.application.services.SlaveManager;
import com.decs.application.views.ProblemEditor.ProblemEditorView;
import com.decs.application.views.information.AboutView;
import com.decs.application.views.information.HelpView;
import com.decs.application.views.jobdashboard.JobDashboardView;
import com.decs.application.views.nodemanager.NodeManagerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Optional;

/**
 * <b>Main Layout Class</b>
 * <p>
 *     This class implements the main layout of the web application.
 *     It represents the higher level in the structure of the application's visual layouts.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class MainLayout extends AppLayout {
    private H2 viewTitle;
    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    /**
     * Class Constructor
     * @param authenticatedUser Authenticated user object
     * @param accessChecker Access Check object
     * @param slaveManager Slave manager instance
     * @param objectListDatabase Object list Database instance
     */
    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, SlaveManager slaveManager, ObjectListDatabase objectListDatabase) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        objectListDatabase.setMainLayout(this);

        // Initialize Slave Manager
        slaveManager.startSlaveListener();

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    /**
     * Builds the header section of the layout
     */
    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    /**
     * Builds the drawer content
     */
    private void addDrawerContent() {
        StreamResource decsLogo = new StreamResource("DECS_logo.png",
                () -> getClass().getResourceAsStream("/logos/DECS_logo.png"));
        Image appLogo = new Image(decsLogo, "DECS Logo");
        appLogo.setWidth("90%");
        appLogo.setHeight("90%");
        Header header = new Header(appLogo);

        VerticalLayout navGroup = new VerticalLayout(createNavigation());
        navGroup.setWidthFull();

        Scroller scroller = new Scroller(navGroup);

        addToDrawer(header, scroller, createFooter());
    }

    /**
     * Builds the navigation menu
     * @return Side navigation object
     */
    private SideNav[] createNavigation() {
        SideNav nav = new SideNav();

        SideNav infoNav = new SideNav();
        infoNav.setLabel("Information");
        infoNav.setCollapsible(true);
        infoNav.setExpanded(false);

        if (accessChecker.hasAccess(JobDashboardView.class)) {
            SideNavItem jobDashboardNavItem = new SideNavItem("Job Dashboard", JobDashboardView.class, VaadinIcon.HOME.create());
            jobDashboardNavItem.setId("jobDashboardNavItem");
            nav.addItem(jobDashboardNavItem);
        }
        if (accessChecker.hasAccess(NodeManagerView.class)) {
            SideNavItem nodeManagerNavItem = new SideNavItem("Node Manager", NodeManagerView.class, VaadinIcon.CLUSTER.create());
            nodeManagerNavItem.setId("nodeManagerNavItem");
            nav.addItem(nodeManagerNavItem);
        }
        if (accessChecker.hasAccess(ProblemEditorView.class)) {
            SideNavItem problemEditorNavItem = new SideNavItem("Problem Editor", ProblemEditorView.class, VaadinIcon.SLIDERS.create());
            problemEditorNavItem.setId("problemEditorNavItem");
            nav.addItem(problemEditorNavItem);
        }

        if (accessChecker.hasAccess(HelpView.class)) {
            SideNavItem helpNavItem = new SideNavItem("Help", HelpView.class);
            helpNavItem.setId("helpNavItem");
            infoNav.addItem(helpNavItem);
        }
        if (accessChecker.hasAccess(AboutView.class)) {
            SideNavItem aboutNavItem = new SideNavItem("About", AboutView.class);
            aboutNavItem.setId("helpNavItem");
            infoNav.addItem(aboutNavItem);
        }

        return new SideNav[]{nav, infoNav};
    }

    /**
     * Builds the footer of the layout
     * @return Footer object
     */
    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}