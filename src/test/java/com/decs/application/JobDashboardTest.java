package com.decs.application;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("jobDashboardTest")
public class JobDashboardTest extends PlaywrightIT {
    @Test
    void visibleElements() {
        assertThat(page.locator("//vaadin-grid[@id='availableProblemsGrid']")).isVisible();

        assertThat(page.locator("//vaadin-progress-bar[@id='jobProgressBar']")).isVisible();
    }
}