package com.github.duncanmfield.alertmonitoringservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteriaNotification;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaConfig;
import com.github.duncanmfield.alertmonitoringservice.kafka.KafkaNotificationPublisher;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class KafkaNotificationPublisherTest {

    @Mock
    private KafkaConfig kafkaConfig;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private KafkaNotificationPublisher kafkaNotificationPublisher;

    @Test
    public void shouldSendNotificationToKafkaTemplate() throws JsonProcessingException {
        // Given
        AlertCriteriaNotification notification = mock(AlertCriteriaNotification.class);
        String notificationAsString = objectMapper.writeValueAsString(notification);
        NewTopic mockTopic = mock(NewTopic.class);
        given(kafkaConfig.notificationTopic()).willReturn(mockTopic);
        given(mockTopic.name()).willReturn("notification");

        // When
        kafkaNotificationPublisher.publish(notification);

        // Then
        verify(kafkaTemplate).send(eq("notification"), eq(notificationAsString));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenNotificationIsInvalid() throws Exception {
        // Given
        AlertCriteriaNotification notification = mock(AlertCriteriaNotification.class);
        NewTopic mockTopic = mock(NewTopic.class);
        given(kafkaConfig.notificationTopic()).willReturn(mockTopic);
        given(mockTopic.name()).willReturn("notification");
        given(objectMapper.writeValueAsString(any())).willThrow(JsonProcessingException.class);

        // When / Then
        assertThrows(RuntimeException.class, () -> kafkaNotificationPublisher.publish(notification));
    }
}