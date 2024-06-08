package com.decs.application;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaywrightIT {
    //Internal Data
    private static Playwright playwright;
    private static Browser browser;
    protected Page page;
    private BrowserContext browserContext;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.headless = false;
        browser = browserType.launch(launchOptions);
    }

    @AfterAll
    static void afterAll() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void beforeEach() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate("http://localhost:" + port + "/");
        new LoginPO(page).login("user", "user");
    }

    @AfterEach
    void afterEach() {
        page.close();
        browserContext.close();
    }
}