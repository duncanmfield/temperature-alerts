package com.github.duncanmfield.alertmonitoringservice.integration;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.service.scraper.OpenMeteoTemperatureScraper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OpenMeteoTemperatureScraperIT {

    private static MockWebServer mockWebServer;
    private static OpenMeteoTemperatureScraper openMeteoTemperatureScraper;

    @BeforeAll
    public static void setUpAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        WebClient client = WebClient.builder()
                .baseUrl(mockWebServer.url("/").url().toString())
                .build();
        openMeteoTemperatureScraper = new OpenMeteoTemperatureScraper(client);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void shouldMakeRequestToExpectedEndpoint() throws IOException, InterruptedException {
        // Given
        String jsonPayload = "{\"current\": { \"temperature_2m\": 123.0 }}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonPayload)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        AlertCriteria alertCriteria = mock(AlertCriteria.class);
        given(alertCriteria.getLatitude()).willReturn(10.0);
        given(alertCriteria.getLongitude()).willReturn(50.0);

        // When
        openMeteoTemperatureScraper.lookup(alertCriteria);

        // Then
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getPath())
                .isEqualTo("/v1/forecast?latitude=10.0&longitude=50.0&current=temperature_2m");
    }

    @Test
    public void shouldMakeRequestAndParseTemperatureResult() throws IOException {
        // Given
        String jsonPayload = "{\"current\": { \"temperature_2m\": 123.0 }}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonPayload)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        AlertCriteria alertCriteria = mock(AlertCriteria.class);
        given(alertCriteria.getLongitude()).willReturn(50.0);
        given(alertCriteria.getLatitude()).willReturn(10.0);

        // When
        double result = openMeteoTemperatureScraper.lookup(alertCriteria);

        // Then
        assertThat(result).isEqualTo(123.0);
    }

    @Test
    public void shouldThrowIoExceptionWhenCurrentEntryIsMissing() {
        // Given
        String jsonPayload = "{\"notCurrent\": { \"temperature_2m\": 123.0 }}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonPayload)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        AlertCriteria alertCriteria = mock(AlertCriteria.class);
        given(alertCriteria.getLongitude()).willReturn(50.0);
        given(alertCriteria.getLatitude()).willReturn(10.0);

        // When / Then
        assertThrows(IOException.class, () -> openMeteoTemperatureScraper.lookup(alertCriteria));
    }

    @Test
    public void shouldThrowIoExceptionWhenResponseIsMalformed() {
        // Given
        String jsonPayload = "{\"current\": { \"tempe";
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonPayload)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        AlertCriteria alertCriteria = mock(AlertCriteria.class);
        given(alertCriteria.getLongitude()).willReturn(50.0);
        given(alertCriteria.getLatitude()).willReturn(10.0);

        // When / Then
        assertThrows(IOException.class, () -> openMeteoTemperatureScraper.lookup(alertCriteria));
    }

    @Test
    public void shouldThrowIoExceptionWhenServiceIsUnavailable() {
        // Given
        mockWebServer.enqueue(new MockResponse().setResponseCode(503));
        AlertCriteria alertCriteria = mock(AlertCriteria.class);
        given(alertCriteria.getLatitude()).willReturn(10.0);
        given(alertCriteria.getLongitude()).willReturn(50.0);

        // When / Then
        assertThrows(IOException.class, () -> openMeteoTemperatureScraper.lookup(alertCriteria));
    }
}
