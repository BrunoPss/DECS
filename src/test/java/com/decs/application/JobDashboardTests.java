package com.decs.application;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * <b>Job Dashboard Tests Class</b>
 * <p>
 *     This class implements end-to-end tests to the job dashboard page.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Tag("jobDashboardTests")
public class JobDashboardTests extends PlaywrightIT {
    /**
     * Verifies if all expected components are visible in the client's browser
     */
    @Test
    void visibleComponentsTest() {
        // Available Problems Grid
        assertThat(page.locator("//vaadin-grid[@id='availableProblemsGrid']")).isVisible();
        // Job Progress Bar
        assertThat(page.locator("//vaadin-progress-bar[@id='jobProgressBar']")).isVisible();
        // Job Activity Grid
        assertThat(page.locator("//vaadin-grid[@id='jobActivityGrid']")).isVisible();
        // Job Metrics Layout
        assertThat(page.locator("//vaadin-horizontal-layout[@id='jobMetricsGroupLayout']")).isVisible();
        // Start Button
        assertThat(page.locator("//vaadin-button[@id='startBtn']")).isVisible();
        // Stop Button
        assertThat(page.locator("//vaadin-button[@id='stopBtn']")).isVisible();
    }

    /**
     * Simulates a job execution and verifies its solution
     */
    @Test
    void jobExecutionTest() {
        // Job Execution
        page.getByText("B11MFast", new Page.GetByTextOptions().setExact(true)).click();
        page.locator("//vaadin-button[@id='startBtn']").click();
        page.locator("//input[@id='input-vaadin-text-field-13']").fill("Job1");
        page.locator("//vaadin-button[@id='jobDialogBtn']").click();

        // Job Activity Verification
        assertThat(page.locator("vaadin-grid-cell-content:has-text(\"Job1\")")).isVisible();
        assertThat(page.locator("vaadin-grid-cell-content span:has-text(\"finished\")")).isVisible();

        // Solution Verification
        page.locator("//vaadin-button[@id='jobActivitySolutionBtn']").click();

        Locator overviewTab = page.locator("vaadin-tab:has-text(\"Overview\")");
        assertThat(overviewTab).isVisible();
        overviewTab.click();
        assertThat(page.getByText("Job Name:")).isVisible();
        assertThat(page.getByText("Wall-Clock Time:")).isVisible();
        assertThat(page.getByText("CPU Time:")).isVisible();

        Locator shortLogTab = page.locator("vaadin-tab:has-text(\"Short Log\")");
        assertThat(shortLogTab).isVisible();
        shortLogTab.click();

        Locator extendedLogTab = page.locator("vaadin-tab:has-text(\"Extended Log\")");
        assertThat(extendedLogTab).isVisible();
        extendedLogTab.click();
    }
}