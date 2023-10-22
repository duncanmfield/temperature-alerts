package com.github.duncanmfield.notificationconsumerservice.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.notificationconsumerservice.data.Notification;
import com.github.duncanmfield.notificationconsumerservice.output.OutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Listens for Kafka events on the notification topic, and passes them to an appropriate service for handling.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationListenerController {

    private final OutputService outputService;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "notification", groupId = "notification-1")
    public void listener(String serializedNotification) throws IOException {
        Notification notification;

        try (JsonParser parser = mapper.createParser(serializedNotification)) {
            notification = parser.readValueAs(Notification.class);
        } catch (IOException e) {
            log.error("Failed to parse Kafka notification: {}", serializedNotification, e);
            throw e;
        }

        log.debug("Notification received {}", notification);
        this.outputService.accept(notification);
    }
}
