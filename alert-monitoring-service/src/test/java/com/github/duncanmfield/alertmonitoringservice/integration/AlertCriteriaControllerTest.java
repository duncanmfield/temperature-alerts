package com.github.duncanmfield.alertmonitoringservice.integration;

import com.github.duncanmfield.alertmonitoringservice.controller.AlertCriteriaController;
import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AlertCriteriaController.class)
public class AlertCriteriaControllerTest {

    @MockBean
    private AlertCriteriaService alertCriteriaService;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void shouldReturn201FromCreateWhenInputIsValid() {
        // Given
        AlertCriteria alertCriteria = createValidCriteria();

        // When / Then
        webClient.post()
                .uri("/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(alertCriteria))
                .exchange()
                .expectStatus().isCreated();

        verify(alertCriteriaService).handle(alertCriteria);
    }

    @Test
    public void shouldReturn400FromCreateWhenInputIsInvalid() {
        // Given
        AlertCriteria alertCriteria = createValidCriteria();
        alertCriteria.setLongitude(-181);

        // When / Then
        webClient.post()
                .uri("/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(alertCriteria))
                .exchange()
                .expectStatus().isBadRequest();

        verifyNoInteractions(alertCriteriaService);
    }

    @Test
    public void shouldReturn200ListResponseFromGetAll() {
        // Given
        AlertCriteria alertCriteriaA = createValidCriteria();
        alertCriteriaA.setDescription("a");
        AlertCriteria alertCriteriaB = createValidCriteria();
        alertCriteriaB.setDescription("b");
        given(alertCriteriaService.getAll()).willReturn(Set.of(alertCriteriaA, alertCriteriaB));

        // When
        webClient.get()
                .uri("/alerts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AlertCriteria.class)
                .contains(alertCriteriaA, alertCriteriaB)
                .hasSize(2);

        verify(alertCriteriaService).getAll();
    }

    private AlertCriteria createValidCriteria() {
        AlertCriteria alertCriteria = new AlertCriteria();
        alertCriteria.setDescription("description");
        alertCriteria.setLatitude(1);
        alertCriteria.setLongitude(2);
        alertCriteria.setTemperature(3);
        return alertCriteria;
    }
}