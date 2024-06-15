package com.decs.application;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args={"192.168.2.104"})
public class PlaywrightIT {
    //Internal Data
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserType browserType;
    protected static BrowserType.LaunchOptions launchOptions;
    protected Page page;
    protected BrowserContext browserContext;

    @LocalServerPort
    protected int port;

    @BeforeAll
    static void beforeAll() {
        playwright = Playwright.create();
        browserType = playwright.chromium();
        launchOptions = new BrowserType.LaunchOptions();
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