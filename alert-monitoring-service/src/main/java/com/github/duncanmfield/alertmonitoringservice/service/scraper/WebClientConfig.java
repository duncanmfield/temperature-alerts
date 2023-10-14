package com.github.duncanmfield.alertmonitoringservice.service.scraper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Defines {@link WebClient} beans.
 */
@Configuration
public class WebClientConfig {

    @Bean(name = "open-meteo")
    public WebClient openMeteoWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
