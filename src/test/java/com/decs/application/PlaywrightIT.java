package com.decs.application;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * <b>Playwright Interface Class</b>
 * <p>
 *     This class manages the playwright framework.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args={"192.168.2.104"})
public class PlaywrightIT {
    //Internal Data
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserType browserType;
    protected static BrowserType.LaunchOptions launchOptions;
    protected Page page;
    protected BrowserContext browserContext;

    /**
     * Retrieves the web application port
     */
    @LocalServerPort
    protected int port;

    /**
     * Initializes the testing environment
     */
    @BeforeAll
    static void beforeAll() {
        playwright = Playwright.create();
        browserType = playwright.chromium();
        launchOptions = new BrowserType.LaunchOptions();
        launchOptions.headless = false;
        browser = browserType.launch(launchOptions);
    }

    /**
     * Finalizes the testing environment
     */
    @AfterAll
    static void afterAll() {
        browser.close();
        playwright.close();
    }

    /**
     * Resets the testing environment before each test
     */
    @BeforeEach
    void beforeEach() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate("http://localhost:" + port + "/");
        new LoginPO(page).login("user", "user");
    }

    /**
     * Cleans the testing environment after each test
     */
    @AfterEach
    void afterEach() {
        page.close();
        browserContext.close();
    }
}