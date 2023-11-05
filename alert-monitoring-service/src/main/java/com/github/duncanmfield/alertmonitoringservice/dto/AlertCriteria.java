package com.github.duncanmfield.alertmonitoringservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Service layer representation of an alert criteria.
 */
@Data
@Document("alertCriteria")
@RequiredArgsConstructor
public class AlertCriteria {

    @Id
    private String id;

    private String description;
    private double latitude;
    private double longitude;
    private double temperature;
}
