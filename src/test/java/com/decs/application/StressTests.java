package com.decs.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import org.apache.commons.io.output.BrokenWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Tag("jobDashboardTest")
public class StressTests extends PlaywrightIT {
    private static final int CLIENTS = 1;
    private ArrayList<Client> clientList;

    public StressTests() {
        this.clientList = new ArrayList<>();
    }

    @Test
    void concurrentJobsTest() {
        for (int i=0; i<CLIENTS; i++) {
            HashMap<Browser, Page> client;
            Browser br = browserType.launch(launchOptions);
            Page page = br.newPage();
            page.navigate("http://localhost:" + port + "/");
            new LoginPO(page).login("user", "user");
            clientList.add(new Client(br, page));
        }

        clientList.get(0).getPage().getByText("B11MFast", new Page.GetByTextOptions().setExact(true)).click();
        clientList.get(0).getPage().locator("//vaadin-button[@id='startBtn']").click();

        //for (int i=1; i<clientList.size(); i++) {
        //    assertThat(clientList.get(i).getPage().locator("//vaadin-button[@id='startBtn']")).isDisabled();
        //}
    }
}