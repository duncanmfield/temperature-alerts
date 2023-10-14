package com.github.duncanmfield.alertmonitoringservice.data;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Data representation of alert criteria.
 */
@Data
public class AlertCriteria {

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