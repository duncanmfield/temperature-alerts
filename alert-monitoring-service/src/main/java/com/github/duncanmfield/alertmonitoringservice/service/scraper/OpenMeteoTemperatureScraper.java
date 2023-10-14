package com.github.duncanmfield.alertmonitoringservice.service.scraper;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

/**
 * Handles scraping of temperature data from OpenMeteo API.
 * Example request: curl "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m"
 */
@Service
public class OpenMeteoTemperatureScraper implements TemperatureScraper {

    @Autowired
    private @Qualifier("open-meteo") WebClient client;

    public OpenMeteoTemperatureScraper(WebClient client) {
        this.client = client;
    }

    @Override
    public double lookUp(AlertCriteria alertCriteria) throws IOException {
        try {
            OpenMeteoResponse result = client.get().uri(uriBuilder ->
                            uriBuilder.path("/v1/forecast")
                                    .queryParam("latitude", "{latitude}")
                                    .queryParam("longitude", "{longitude}")
                                    .queryParam("current", "{query}")
                                    .build(alertCriteria.getLatitude(), alertCriteria.getLongitude(),
                                            "temperature_2m"))
                    .retrieve()
                    .bodyToMono(OpenMeteoResponse.class)
                    .block();

            if (result == null || result.getCurrent() == null) {
                throw new IOException("Invalid response from OpenMeteo API call");
            }
            return result.getCurrent().getTemperature_2m();
        } catch (Exception e) {
            throw new IOException("Network error during OpenMeteo API call");
        }
    }
}
