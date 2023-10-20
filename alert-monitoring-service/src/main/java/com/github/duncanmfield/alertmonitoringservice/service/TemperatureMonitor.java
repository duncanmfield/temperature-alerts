package com.github.duncanmfield.alertmonitoringservice.service;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.data.Notification;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaNotificationPublisher;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import com.github.duncanmfield.alertmonitoringservice.service.scraper.TemperatureScraper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Handles logic for monitoring temperatures at locations specified by all alert criteria, and triggers notifications if
 * any alert criteria are met.
 */
@Service
@RequiredArgsConstructor
public class TemperatureMonitor {

    private final AlertCriteriaRepository alertCriteriaRepository;
    private final TemperatureScraper temperatureScraper;
    private final KafkaNotificationPublisher notificationPublisher;

    @Scheduled(fixedRateString = "${temperature.rate.ms}")
    public void executeMonitorTask() {
        for (AlertCriteria alertCriteria : alertCriteriaRepository.getAll()) {
            try {
                double actualTemperature = temperatureScraper.lookUp(alertCriteria);
                if (actualTemperature >= alertCriteria.getTemperature()) {
                    notificationPublisher.publish(new Notification(alertCriteria, actualTemperature));
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Look up failed for alert criteria: " + alertCriteria);
            }
        }
    }
}
