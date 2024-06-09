package com.decs.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

public class Client {
    //Internal Data
    private Browser browser;
    private Page page;

    //Constructor
    Client(Browser br, Page page) {
        this.browser = br;
        this.page = page;
    }

    //Get Methods

    public Browser getBrowser() { return browser; }
    public Page getPage() { return page; }


    //Set Methods

    public void setBrowser(Browser browser) { this.browser = browser; }
    public void setPage(Page page) { this.page = page; }

    //Methods


    //Overrides


    //Internal Functions


}