package com.github.duncanmfield.alertmonitoringservice.service;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteriaNotification;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaNotificationPublisher;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import com.github.duncanmfield.alertmonitoringservice.service.scraper.TemperatureScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Handles logic for monitoring temperatures at locations specified by all alert criteria, and triggers notifications if
 * any alert criteria are met.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TemperatureMonitor {

    private final AlertCriteriaRepository alertCriteriaRepository;
    private final TemperatureScraper temperatureScraper;
    private final KafkaNotificationPublisher notificationPublisher;

    @Scheduled(fixedRateString = "${temperature.rate.ms}")
    public void executeMonitorTask() throws IOException {
        for (AlertCriteria alertCriteria : alertCriteriaRepository.findAll()) {
            try {
                double actualTemperature = temperatureScraper.lookup(alertCriteria);
                if (actualTemperature >= alertCriteria.getTemperature()) {
                    notificationPublisher.publish(new AlertCriteriaNotification(alertCriteria, actualTemperature));
                }
            } catch (IOException e) {
                log.error("Lookup failed for alert criteria {}", alertCriteria, e);
                throw e;
            }
        }
    }
}
