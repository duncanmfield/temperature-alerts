package com.github.duncanmfield.notificationconsumerservice.data;

import lombok.Data;

/**
 * Data representation of a notification for an alert which was satisfied.
 */
@Data
public class Notification {

    private String description;
    private double latitude;
    private double longitude;
    private double alertTemperature;
    private double actualTemperature;
}