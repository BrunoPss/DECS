package com.decs.application;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * <b>Node Manager Tests Class</b>
 * <p>
 *     This class implements end-to-end tests to the node manager page.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Tag("NodeManagerTests")
public class NodeManagerTests extends PlaywrightIT {
    /**
     * Verifies if all expected components are visible in the client's browser
     */
    @Test
    void visibleComponentsTest() {
        // Select Node Manager View
        page.locator("//vaadin-side-nav-item[@id='nodeManagerNavItem']").click();

        assertThat(page.locator("span:has-text('Node List')")).isVisible();
        assertThat(page.locator("//vaadin-button[@id='nodeListTitleUpdateBtn']")).isVisible();
        assertThat(page.locator("//vaadin-button[@id='nodeListTitleUpdateBtn']")).isEnabled();
        assertThat(page.locator("//vaadin-grid[@id='nodeListGrid']")).isVisible();
        assertThat(page.locator("//vaadin-grid[@id='nodeListGrid']")).isEnabled();
        assertThat(page.locator("span:has-text('Connected Slaves: 0')")).isVisible();
    }
}