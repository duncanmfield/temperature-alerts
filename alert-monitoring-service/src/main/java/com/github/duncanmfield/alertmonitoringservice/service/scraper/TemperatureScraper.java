package com.github.duncanmfield.alertmonitoringservice.service.scraper;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;

import java.io.IOException;

/**
 * Defines the contract for any temperature scraper.
 */
public interface TemperatureScraper {

    /**
     * Returns the temperature at the latitude and longitude of the provided {@link AlertCriteria}
     *
     * @param alertCriteria The alert criteria object.
     * @return The temperature, in degrees celsius.
     */
    double lookup(AlertCriteria alertCriteria) throws IOException;
}
