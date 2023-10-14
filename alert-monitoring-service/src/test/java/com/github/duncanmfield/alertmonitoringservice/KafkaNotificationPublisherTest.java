package com.github.duncanmfield.alertmonitoringservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.alertmonitoringservice.data.Notification;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaConfig;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaNotificationPublisher;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkaNotificationPublisherTest {

    @MockBean
    private KafkaConfig kafkaConfig;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaNotificationPublisher kafkaNotificationPublisher;

    @Test
    public void shouldSendNotificationToKafkaTemplate() throws JsonProcessingException {
        // Given
        Notification notification = mock(Notification.class);
        String notificationAsString = serialize(notification);
        NewTopic mockTopic = mock(NewTopic.class);
        given(kafkaConfig.notificationTopic()).willReturn(mockTopic);
        given(mockTopic.name()).willReturn("notification");

        // When
        kafkaNotificationPublisher.publish(notification);

        // Then
        verify(kafkaTemplate).send(eq("notification"), eq(notificationAsString));
    }

    private String serialize(Notification notification) throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(notification);
    }
}