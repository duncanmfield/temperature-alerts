package com.github.duncanmfield.notificationconsumerservice.output;

import com.github.duncanmfield.notificationconsumerservice.data.Notification;
import org.springframework.stereotype.Service;

/**
 * Consumes notifications, and prints the details to the console.
 */
@Service
public class PrintService implements OutputService {

    @Override
    public void accept(Notification notification) {
        System.out.println("Printing notification.. " + notification);
    }
}
