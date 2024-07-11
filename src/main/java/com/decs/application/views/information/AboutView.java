package com.decs.application.views.information;

import com.decs.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.*;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class AboutView extends Composite<VerticalLayout> {
    //Internal Data
    private VerticalLayout aboutLayoutGroup;
    private Image decsLogo;
    private Paragraph aboutText;
    private Paragraph buildInfo;
    private VerticalLayout authorSection;
    private Span authorName;
    private Span authorEmail;
    private Span authorGithub;
    private VerticalLayout repositorySection;
    private Span decsRepo;
    private Span decsSlaveRepo;
    private VerticalLayout licenceSection;
    private Span licenceName;

    //Constructor
    public AboutView() {
        createAboutSection();

        getContent().add(aboutLayoutGroup);
    }

    //Internal Functions
    private void createAboutSection() {
        // Layout Group
        aboutLayoutGroup = new VerticalLayout();
        aboutLayoutGroup.setWidthFull();

        // DECS Logo
        StreamResource decsLogoContent = new StreamResource("DECS_logo_letters.png",
                () -> getClass().getResourceAsStream("/logos/DECS_logo_letters.png"));
        decsLogo = new Image(decsLogoContent, "DECS Logo Full");

        // Text Field
        try {
            InputStream textContentFile = getClass().getResourceAsStream("/textContent/about_text.txt");
            InputStream buildContentFile = getClass().getResourceAsStream("/textContent/build_info.txt");
            String textContent = new String(textContentFile.readAllBytes());
            String buildContent = new String(buildContentFile.readAllBytes());

            if (textContent != null && buildContent != null) {
                aboutText = new Paragraph(textContent);
                aboutText.getElement().getStyle().set("white-space", "pre");
                aboutText.setWidth("53.5%");

                buildInfo = new Paragraph();
                buildInfo.setText(buildContent);
                buildInfo.getElement().getStyle().set("white-space", "pre");
                buildInfo.setWidth("53.5%");
            }
        } catch (IOException e) {
            System.err.println("IO Exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception");
            e.printStackTrace();
        }

        // Author Section
        Anchor authorEmailLink = new Anchor("mailto:example@mail.com?subject=DECS%20Inquire", "example@mail.com");
        authorEmailLink.setTarget("_blank");
        Anchor authorGithubLink = new Anchor("https://github.com/BrunoPss", "BrunoPss");
        authorGithubLink.setTarget("_blank");

        authorSection = new VerticalLayout();
        authorSection.setSpacing(false);
        authorSection.getThemeList().add("spacing-xs");
        authorName = new Span("Author: Bruno Guiomar");
        authorEmail = new Span("Email: ");
        authorEmail.add(authorEmailLink);
        authorGithub = new Span("GitHub: ");
        authorGithub.add(authorGithubLink);
        authorSection.add(authorName, authorEmail, authorGithub);

        // Repository Section
        Anchor decsRepoLink = new Anchor("https://github.com/BrunoPss/DECS", "DECS");
        decsRepoLink.setTarget("_blank");
        Anchor decsSlaveRepoLink = new Anchor("https://github.com/BrunoPss/DECS-Slave", "DECS-Slave");
        decsSlaveRepoLink.setTarget("_blank");

        repositorySection = new VerticalLayout();
        repositorySection.setSpacing(false);
        repositorySection.getThemeList().add("spacing-xs");
        decsRepo = new Span("DECS Repository: ");
        decsRepo.add(decsRepoLink);
        decsSlaveRepo = new Span("DECS-Slave Repository: ");
        decsSlaveRepo.add(decsSlaveRepoLink);
        repositorySection.add(decsRepo, decsSlaveRepo);

        // Licence Section
        licenceSection = new VerticalLayout();
        licenceSection.setSpacing(false);
        licenceSection.getThemeList().add("spacing-xs");
        licenceName = new Span("Dummy Licence...");
        licenceSection.add(licenceName);

        aboutLayoutGroup.add(decsLogo, aboutText, buildInfo, authorSection, repositorySection, licenceSection);
    }
}