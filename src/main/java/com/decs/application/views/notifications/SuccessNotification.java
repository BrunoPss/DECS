package com.decs.application.views.notifications;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class SuccessNotification extends Notification {
    //Internal Data


    //Constructor
    public SuccessNotification(String message) {
        this.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        this.setText(message);
        this.setDuration(3000);
    }

    //Get Methods


    //Set Methods


    //Methods
    public static void showSuccessNotification(String message) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setText(message);
        notification.setDuration(3000);
        notification.open();
    }

    //Overrides


    //Internal Functions


}