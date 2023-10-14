package com.github.duncanmfield.notificationconsumerservice.output;

import com.github.duncanmfield.notificationconsumerservice.data.Notification;

/**
 * Defines the contract for any services which wish to accept notifications.
 */
public interface OutputService {

    /**
     * Accepts notifications.
     *
     * @param notification The notification to consume.
     */
    void accept(Notification notification);
}
