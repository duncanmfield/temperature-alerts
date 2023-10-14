package com.github.duncanmfield.notificationconsumerservice.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.notificationconsumerservice.data.Notification;
import com.github.duncanmfield.notificationconsumerservice.output.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Listens for Kafka events on the notification topic, and passes them to an appropriate service for handling.
 */
@Component
public class KafkaNotificationListenerController {

    @Autowired
    private OutputService outputService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "notification", groupId = "notification-1")
    public void listener(String serializedNotification) throws IOException {
        Notification notification;
        try (JsonParser parser = mapper.createParser(serializedNotification)) {
            notification = parser.readValueAs(Notification.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        System.out.println("Notification received: " + notification);
        this.outputService.accept(notification);
    }
}
