package com.github.duncanmfield.alertmonitoringservice.controller.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Presentation layer representation of an alert criteria.
 */
@Data
public class AlertCriteriaView {

    private long id;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @DecimalMin("-90")
    @DecimalMax("90")
    private double latitude;

    @NotNull
    @DecimalMin("-180")
    @DecimalMax("180")
    private double longitude;

    @NotNull
    private double temperature;
}