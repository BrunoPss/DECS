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

/**
 * <b>Success Notification Class</b>
 * <p>
 *     This class builds a success notification.
 *     It is responsible for all visual components and their behavior.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class SuccessNotification extends Notification {
    //Internal Data

    /**
     * Class Private Constructor
     * <p>This class cannot be instantiated.</p>
     */
    private SuccessNotification() {}

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Creates and displays a success notification
     * <p>Configures the visual contents of the notification.</p>
     * @param message Textual content to be displayed in the notification
     */
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