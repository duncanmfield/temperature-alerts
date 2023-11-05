package com.github.duncanmfield.alertmonitoringservice.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Internal representation of an alert criteria.
 */
@Data
@Entity
@RequiredArgsConstructor
@Table(name = "alerts")
public class AlertCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String description;
    private double latitude;
    private double longitude;
    private double temperature;
}
