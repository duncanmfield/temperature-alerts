package com.github.duncanmfield.alertmonitoringservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteriaNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Publishes to Kafka's notification topic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationPublisher {

    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    /**
     * Publishes notifications to Kafka.
     *
     * @param notification The notification to publish.
     */
    public void publish(AlertCriteriaNotification notification) {
        try {
            String notificationJson = mapper.writeValueAsString(notification);
            kafkaTemplate.send(kafkaConfig.notificationTopic().name(), notificationJson);
        } catch (JsonProcessingException e) {
            log.error("Failed to publish notification {}", notification, e);
            throw new RuntimeException(e);
        }
    }
}
