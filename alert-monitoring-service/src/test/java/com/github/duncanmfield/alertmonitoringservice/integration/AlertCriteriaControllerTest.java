package com.github.duncanmfield.alertmonitoringservice.integration;

import com.github.duncanmfield.alertmonitoringservice.controller.AlertCriteriaController;
import com.github.duncanmfield.alertmonitoringservice.controller.request.AlertCriteriaView;
import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.dto.mapper.AlertCriteriaMapper;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AlertCriteriaController.class)
public class AlertCriteriaControllerTest {

    @MockBean
    private AlertCriteriaMapper mapper;

    @MockBean
    private AlertCriteriaService alertCriteriaService;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void setUpMapper() {
        given(mapper.toDto(any())).willCallRealMethod();
        given(mapper.toView(any())).willCallRealMethod();
    }

    @Test
    public void shouldReturn201AndObjectWithIdFromCreateWhenInputIsValid() {
        // Given
        AlertCriteriaView alertCriteriaView = createValidCriteriaRequest();
        AlertCriteria alertCriteria = createCriteria();
        alertCriteria.setId(1);
        given(alertCriteriaService.handleCreate(any())).willReturn(alertCriteria);

        // When / Then
        webClient.post()
                .uri("/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(alertCriteriaView))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AlertCriteriaView.class)
                .value(view -> {
                    assertThat(view.getId()).isEqualTo(1);
                });

        verify(alertCriteriaService).handleCreate(any());
    }

    @Test
    public void shouldReturn400FromCreateWhenInputIsInvalid() {
        // Given
        AlertCriteriaView alertCriteria = createValidCriteriaRequest();
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
        AlertCriteria alertCriteriaA = createCriteria();
        alertCriteriaA.setDescription("a");
        AlertCriteria alertCriteriaB = createCriteria();
        alertCriteriaB.setDescription("b");
        given(alertCriteriaService.getAll()).willReturn(List.of(alertCriteriaA, alertCriteriaB));

        // When / Then
        webClient.get()
                .uri("/alerts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AlertCriteriaView.class)
                .hasSize(2);

        verify(alertCriteriaService).getAll();
    }

    @Test
    public void shouldReturn200ResponseFromGetByIdWhenIdIsValid() {
        // Given
        AlertCriteria alertCriteriaA = createCriteria();
        given(alertCriteriaService.getById(1)).willReturn(Optional.of(alertCriteriaA));

        // When / Then
        webClient.get()
                .uri("/alerts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AlertCriteriaView.class);

        verify(alertCriteriaService).getById(1);
    }

    @Test
    public void shouldReturn400ResponseFromGetByIdWhenIdIsInvalid() {
        // Given
        given(alertCriteriaService.getById(1)).willReturn(Optional.empty());

        // When / Then
        webClient.get()
                .uri("/alerts/1")
                .exchange()
                .expectStatus().isBadRequest();

        verify(alertCriteriaService).getById(1);
    }

    @Test
    public void shouldReturn204ResponseFromDeleteWhenIdExists() {
        // Given
        given(alertCriteriaService.handleDelete(1)).willReturn(true);

        // When / Then
        webClient.delete()
                .uri("/alerts/1")
                .exchange()
                .expectStatus().isNoContent();

        verify(alertCriteriaService).handleDelete(1);
    }

    @Test
    public void shouldReturn410ResponseFromDeleteWhenIdExists() {
        // Given
        given(alertCriteriaService.handleDelete(1)).willReturn(false);

        // When / Then
        webClient.delete()
                .uri("/alerts/1")
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(HttpStatus.GONE.value()));

        verify(alertCriteriaService).handleDelete(1);
    }

    private AlertCriteriaView createValidCriteriaRequest() {
        AlertCriteriaView alertCriteria = new AlertCriteriaView();
        alertCriteria.setDescription("description");
        alertCriteria.setLatitude(1);
        alertCriteria.setLongitude(2);
        alertCriteria.setTemperature(3);
        return alertCriteria;
    }

    private AlertCriteria createCriteria() {
        AlertCriteria alertCriteria = new AlertCriteria();
        alertCriteria.setDescription("description");
        alertCriteria.setLatitude(1);
        alertCriteria.setLongitude(2);
        alertCriteria.setTemperature(3);
        return alertCriteria;
    }
}