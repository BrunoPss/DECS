package com.decs.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.ArrayList;

/**
 * <b>Stress Tests Class</b>
 * <p>
 *     This class implements end-to-end stress tests to the global system.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Tag("jobDashboardTest")
public class StressTests extends PlaywrightIT {
    private static final int CLIENTS = 2;
    private ArrayList<Client> clientList;

    /**
     * Class Constructor
     */
    public StressTests() {
        this.clientList = new ArrayList<>();
    }

    /**
     * Simulates the execution of concurrent jobs and verifies the system's behavior
     */
    @Test
    void concurrentJobsTest() {
        for (int i=0; i<CLIENTS; i++) {
            Browser br = browserType.launch(launchOptions);
            Page page = br.newPage();
            page.navigate("http://localhost:" + port + "/");
            new LoginPO(page).login("user", "user");
            clientList.add(new Client(br, page));
        }

        page.getByText("B11MFast", new Page.GetByTextOptions().setExact(true)).click();
        page.locator("//vaadin-button[@id='startBtn']").click();
        page.locator("//input[@id='input-vaadin-text-field-13']").fill("1");
        page.locator("//vaadin-button[@id='jobDialogBtn']").click();

        for (Client client : clientList) {
            // Check if start button is disabled for every client when an execution is already running
            assertThat(client.getPage().locator("//vaadin-button[@id='startBtn']")).isDisabled();
            // Check if busy dialog is shown
            assertThat(client.getPage().getByText("Server is busy at the moment!")).isVisible();
        }

        page.waitForFunction("() => document.getElementById('startBtn') && !document.getElementById('startBtn').disabled");

        for (Client client : clientList) {
            // Check if start button is enabled when server ends execution
            assertThat(client.getPage().locator("//vaadin-button[@id='startBtn']")).isEnabled();
            // Check if free dialog is shown
            assertThat(client.getPage().getByText("Server is now free!")).isVisible();
        }

        // Close all clients
        for (Client client : clientList) {
            client.getPage().close();
            client.getBrowser().close();
        }
    }

    /**
     * Simulates the concurrent access to the web application and verifies if the system
     * successfully handles the load.
     */
    @Test
    void concurrentAccessTest() {
        for (int i=0; i<CLIENTS; i++) {
            Browser br = browserType.launch(launchOptions);
            Page page = br.newPage();
            page.navigate("http://localhost:" + port + "/");
            new LoginPO(page).login("user", "user");
            clientList.add(new Client(br, page));
        }

        page.waitForTimeout(1000);

        for (Client client : clientList) {
            // Check if all browsers are alive
            assert client.getBrowser().isConnected();
            // Check if all clients have a stable view of the application
            // Main Title
            assertThat(client.getPage().getByText("DECS")).isVisible();
            // Three Main Views
            // Job Dashboard View
            client.getPage().locator("//vaadin-side-nav-item[@id='jobDashboardNavItem']").click();
            assertThat(client.getPage().locator("//vaadin-side-nav-item[@id='jobDashboardNavItem']")).isVisible();
            assertThat(client.getPage().locator("h2:has-text('Job Dashboard')")).isVisible();
            // Node Manager View
            client.getPage().locator("//vaadin-side-nav-item[@id='nodeManagerNavItem']").click();
            assertThat(client.getPage().locator("//vaadin-side-nav-item[@id='nodeManagerNavItem']")).isVisible();
            assertThat(client.getPage().locator("h2:has-text('Node Manager')")).isVisible();
            // Problem Editor View
            client.getPage().locator("//vaadin-side-nav-item[@id='problemEditorNavItem']").click();
            assertThat(client.getPage().locator("//vaadin-side-nav-item[@id='problemEditorNavItem']")).isVisible();
            assertThat(client.getPage().locator("h2:has-text('Problem Editor')")).isVisible();
        }

    }
}