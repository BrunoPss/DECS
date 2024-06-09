package com.decs.application;

import com.microsoft.playwright.Page;

public class LoginPO {
    //Internal Data
    private final Page page;

    //Constructor
    public LoginPO(Page page) {
        this.page = page;
    }

    //Get Methods


    //Set Methods


    //Methods
    public void login(String username, String password) {
        page.fill("vaadin-text-field[name='username'] > input", username);
        page.fill("vaadin-password-field[name='password'] > input", password);
        page.click("vaadin-button");
    }

    //Overrides


    //Internal Functions


}