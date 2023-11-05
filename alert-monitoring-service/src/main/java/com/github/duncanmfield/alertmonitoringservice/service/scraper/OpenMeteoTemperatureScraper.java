package com.github.duncanmfield.alertmonitoringservice.service.scraper;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

/**
 * Handles scraping of temperature data from OpenMeteo API.
 * Example request: {@code curl "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m"}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpenMeteoTemperatureScraper implements TemperatureScraper {

    private final WebClient client;

    @Override
    public double lookup(AlertCriteria alertCriteria) throws IOException {
        OpenMeteoResponse result;
        try {
            result = client.get().uri(uriBuilder ->
                            uriBuilder.path("/v1/forecast")
                                    .queryParam("latitude", "{latitude}")
                                    .queryParam("longitude", "{longitude}")
                                    .queryParam("current", "{query}")
                                    .build(alertCriteria.getLatitude(), alertCriteria.getLongitude(),
                                            "temperature_2m"))
                    .retrieve()
                    .bodyToMono(OpenMeteoResponse.class)
                    .block();
        } catch (Exception e) {
            throw new IOException("Error processing OpenMeteo API call");
        }

        if (result == null || result.getCurrent() == null) {
            throw new IOException("Invalid response from OpenMeteo API call");
        }

        return result.getCurrent().getTemperature_2m();

    }
}
