package com.decs.application;

import com.microsoft.playwright.Page;

/**
 * <b>Login Procedure Class</b>
 * <p>
 *     This class implements an automatic initial login procedure.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class LoginPO {
    //Internal Data
    private final Page page;

    /**
     * Class Constructor
     * @param page DECS Web page instance
     */
    public LoginPO(Page page) {
        this.page = page;
    }

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Login procedure
     * @param username Username field
     * @param password Password field
     */
    public void login(String username, String password) {
        page.fill("vaadin-text-field[name='username'] > input", username);
        page.fill("vaadin-password-field[name='password'] > input", password);
        page.click("vaadin-button");
    }

    //Overrides


    //Internal Functions


}