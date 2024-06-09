package com.decs.application;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("jobDashboardTest")
public class JobDashboardTests extends PlaywrightIT {
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
}