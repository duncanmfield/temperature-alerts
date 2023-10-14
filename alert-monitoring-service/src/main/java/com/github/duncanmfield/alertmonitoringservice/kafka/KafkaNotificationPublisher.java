package com.github.duncanmfield.alertmonitoringservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.alertmonitoringservice.data.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Publishes to Kafka's notification topic.
 */
@Service
public class KafkaNotificationPublisher {

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Publishes notifications to Kafka.
     *
     * @param notification The notification to publish.
     */
    public void publish(Notification notification) {
        try {
            String notificationJson = mapper.writeValueAsString(notification);
            kafkaTemplate.send(kafkaConfig.notificationTopic().name(), notificationJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
