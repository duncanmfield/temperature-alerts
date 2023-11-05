package com.github.duncanmfield.alertmonitoringservice;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertCriteriaServiceTest {

    @Mock
    private AlertCriteriaRepository alertCriteriaRepository;

    @InjectMocks
    private AlertCriteriaService alertCriteriaService;

    @Test
    public void shouldSaveInRepositoryWhenHandleCalled() {
        // Given
        AlertCriteria alertCriteria = mock(AlertCriteria.class);

        // When
        alertCriteriaService.handleCreate(alertCriteria);

        // Then
        verify(alertCriteriaRepository).save(alertCriteria);
    }

    @Test
    public void shouldReadFromRepositoryWhenGetByIdCalled() {
        // When
        alertCriteriaService.getById(1);

        // Then
        verify(alertCriteriaRepository).findById(1L);
    }

    @Test
    public void shouldReadFromRepositoryWhenGetAllCalled() {
        // When
        alertCriteriaService.getAll();

        // Then
        verify(alertCriteriaRepository).findAll();
    }

    @Test
    public void shouldDeleteFromRepositoryWhenDeleteCalledAndIdExists() {
        // Given
        given(alertCriteriaRepository.existsById(1L)).willReturn(true);

        // When
        alertCriteriaService.handleDelete(1);

        // Then
        verify(alertCriteriaRepository).deleteById(1L);
    }

    @Test
    public void shouldDeleteFromRepositoryWhenDeleteCalledAndIdDoesNotExist() {
        // Given
        given(alertCriteriaRepository.existsById(1L)).willReturn(false);

        // When / Then
        assertThat(alertCriteriaService.handleDelete(1)).isEqualTo(false);
    }
}
