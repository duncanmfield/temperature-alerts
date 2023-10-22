package com.github.duncanmfield.notificationconsumerservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EnableKafka
@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = "notification",
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        })
public class NotificationConsumerServiceApplicationTest {

    @Test
    void contextLoads() {
    }
}
