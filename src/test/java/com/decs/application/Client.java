package com.decs.application;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

/**
 * <b>Client Test Class</b>
 * <p>
 *     This class represents a testing client with a respective browser and page.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class Client {
    //Internal Data
    private Browser browser;
    private Page page;

    /**
     * Class Constructor
     * @param br Browser instance
     * @param page Web page instance
     */
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