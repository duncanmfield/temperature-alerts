package com.github.duncanmfield.alertmonitoringservice.data;

import lombok.Data;

/**
 * Data representation of a notification which is produced when the corresponding {@link AlertCriteria} is satisfied.
 * Contains necessary information from the alert criteria, as well as the temperature which satisfied it.
 */
@Data
public class Notification {

    private String description;
    private double latitude;
    private double longitude;
    private double alertTemperature;
    private double actualTemperature;

    /**
     * Constructs a notification.
     *
     * @param alertCriteria The alert criteria which was satisfied.
     * @param actualTemperature The actual temperature, which satisfied the alert criteria.
     */
    public Notification(AlertCriteria alertCriteria, double actualTemperature) {
        this.description = alertCriteria.getDescription();
        this.latitude = alertCriteria.getLatitude();
        this.longitude = alertCriteria.getLongitude();
        this.alertTemperature = alertCriteria.getTemperature();
        this.actualTemperature = actualTemperature;
    }
}