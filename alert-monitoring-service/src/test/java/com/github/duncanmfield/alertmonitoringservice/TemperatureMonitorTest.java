package com.github.duncanmfield.alertmonitoringservice;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteriaNotification;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaNotificationPublisher;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import com.github.duncanmfield.alertmonitoringservice.service.TemperatureMonitor;
import com.github.duncanmfield.alertmonitoringservice.service.scraper.TemperatureScraper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TemperatureMonitorTest {

    @Mock
    private AlertCriteriaRepository alertCriteriaRepository;
    @Mock
    private TemperatureScraper temperatureScraper;
    @Mock
    private KafkaNotificationPublisher notificationPublisher;

    @InjectMocks
    private TemperatureMonitor temperatureMonitor;

    @Test
    public void shouldNotPublishNotificationWhenNoCriteriaIsSet() throws IOException {
        // Given
        given(alertCriteriaRepository.findAll()).willReturn(List.of());

        // When
        temperatureMonitor.executeMonitorTask();

        // Then
        verifyNoInteractions(notificationPublisher);
    }

    @Test
    public void shouldNotPublishNotificationWhenTemperatureIsBelowThreshold() throws IOException {
        // Given
        AlertCriteria mockCriteria = mock(AlertCriteria.class);
        given(alertCriteriaRepository.findAll()).willReturn(List.of(mockCriteria));
        given(temperatureScraper.lookup(any())).willReturn(10.0);
        given(mockCriteria.getTemperature()).willReturn(11.0);

        // When
        temperatureMonitor.executeMonitorTask();

        // Then
        verifyNoInteractions(notificationPublisher);
    }

    @Test
    public void shouldPublishNotificationWhenTemperatureEqualsThreshold() throws IOException {
        // Given
        AlertCriteria mockCriteria = mock(AlertCriteria.class);
        given(alertCriteriaRepository.findAll()).willReturn(List.of(mockCriteria));
        given(temperatureScraper.lookup(any())).willReturn(10.0);
        given(mockCriteria.getTemperature()).willReturn(10.0);
        ArgumentCaptor<AlertCriteriaNotification> notificationArgumentCaptor = ArgumentCaptor.forClass(AlertCriteriaNotification.class);

        // When
        temperatureMonitor.executeMonitorTask();

        // Then
        verify(notificationPublisher).publish(notificationArgumentCaptor.capture());
        assertThat(notificationArgumentCaptor.getValue().getActualTemperature()).isEqualTo(10.0);
        assertThat(notificationArgumentCaptor.getValue().getAlertTemperature()).isEqualTo(10.0);
    }

    @Test
    public void shouldPublishNotificationWhenTemperatureIsAboveThreshold() throws IOException {
        // Given
        AlertCriteria mockCriteria = mock(AlertCriteria.class);
        given(alertCriteriaRepository.findAll()).willReturn(List.of(mockCriteria));
        given(temperatureScraper.lookup(any())).willReturn(10.0);
        given(mockCriteria.getTemperature()).willReturn(9.0);
        ArgumentCaptor<AlertCriteriaNotification> notificationArgumentCaptor = ArgumentCaptor.forClass(AlertCriteriaNotification.class);

        // When
        temperatureMonitor.executeMonitorTask();

        // Then
        verify(notificationPublisher).publish(notificationArgumentCaptor.capture());
        assertThat(notificationArgumentCaptor.getValue().getActualTemperature()).isEqualTo(10.0);
        assertThat(notificationArgumentCaptor.getValue().getAlertTemperature()).isEqualTo(9.0);
    }

    @Test
    public void shouldPublishTwoNotificationWhenTwoThresholdsAreMet() throws IOException {
        // Given
        AlertCriteria mockCriteriaA = mock(AlertCriteria.class);
        AlertCriteria mockCriteriaB = mock(AlertCriteria.class);
        given(alertCriteriaRepository.findAll()).willReturn(List.of(mockCriteriaA, mockCriteriaB));
        given(temperatureScraper.lookup(any())).willReturn(10.0);
        given(mockCriteriaA.getTemperature()).willReturn(9.0);
        given(mockCriteriaB.getTemperature()).willReturn(9.0);

        // When
        temperatureMonitor.executeMonitorTask();

        // Then
        verify(notificationPublisher, times(2)).publish(any(AlertCriteriaNotification.class));
    }

    @Test
    public void shouldThrowIoExceptionWhenLookupFails() throws IOException {
        // Given
        AlertCriteria mockCriteria = mock(AlertCriteria.class);
        given(alertCriteriaRepository.findAll()).willReturn(List.of(mockCriteria));
        given(temperatureScraper.lookup(any())).willThrow(IOException.class);

        // When / Then
        assertThrows(IOException.class, () -> temperatureMonitor.executeMonitorTask());
    }
}
