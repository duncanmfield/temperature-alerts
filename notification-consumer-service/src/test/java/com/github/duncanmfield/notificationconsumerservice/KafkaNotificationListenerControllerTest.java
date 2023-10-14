package com.github.duncanmfield.notificationconsumerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duncanmfield.notificationconsumerservice.data.Notification;
import com.github.duncanmfield.notificationconsumerservice.kafka.KafkaNotificationListenerController;
import com.github.duncanmfield.notificationconsumerservice.output.OutputService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkaNotificationListenerControllerTest {

    @MockBean
    private OutputService outputService;

    @Autowired
    private KafkaNotificationListenerController kafkaNotificationListenerController;

    @Test
    public void shouldCallOutputServiceWhenListenerValidNotificationIsSent() throws IOException {
        // Given
        Notification notification = mock(Notification.class);
        given(notification.getActualTemperature()).willReturn(45.0);
        String notificationAsString = serialize(notification);
        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        // When
        kafkaNotificationListenerController.listener(notificationAsString);

        // Then
        verify(outputService).accept(notificationArgumentCaptor.capture());
        assertThat(notificationArgumentCaptor.getValue().getActualTemperature()).isEqualTo(45.0);
    }

    @Test
    public void shouldThrowIoExceptionWhenEmptyNotificationIsSent() {
        // Given
        String invalidNotification = "";

        // When / Then
        assertThrows(IOException.class, () -> kafkaNotificationListenerController.listener(invalidNotification));
    }

    @Test
    public void shouldThrowIoExceptionWhenMalformedNotificationIsSent() {
        // Given
        String malformedNotification = "{\"validJsonButInvalidNotification\": \"\"}";

        // When / Then
        assertThrows(IOException.class, () -> kafkaNotificationListenerController.listener(malformedNotification));
    }

    private String serialize(Notification notification) throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(notification);
    }
}