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
 * <b>Error Notification Class</b>
 * <p>
 *     This class builds an error notification.
 *     It is responsible for all visual components and their behavior.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class ErrorNotification extends Notification {
    //Internal Data

    /**
     * Class Private Constructor
     * <p>This class cannot be instantiated.</p>
     */
    private ErrorNotification() {}

    //Get Methods


    //Set Methods


    //Methods
    /**
     * Creates and displays an error notification
     * <p>Configures the visual contents of the notification.</p>
     * @param message Textual content to be displayed in the notification
     */
    public static void showErrorNotification(String message) {
        Notification notification = new Notification();

        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(message));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.setDuration(3000);

        notification.add(layout);
        notification.open();
    }

    //Overrides


    //Internal Functions


}