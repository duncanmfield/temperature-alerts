package com.github.duncanmfield.alertmonitoringservice;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.repository.DummyAlertCriteriaRepository;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertCriteriaServiceTest {

    @Mock
    private DummyAlertCriteriaRepository alertCriteriaRepository;

    @InjectMocks
    private AlertCriteriaService alertCriteriaService;

    @Test
    public void shouldSaveInRepositoryWhenHandleCalled() {
        // Given
        AlertCriteria alertCriteria = mock(AlertCriteria.class);

        // When
        alertCriteriaService.handle(alertCriteria);

        // Then
        verify(alertCriteriaRepository).save(alertCriteria);
    }

    @Test
    public void shouldReadFromRepositoryWhenGetAllCalled() {
        // When
        alertCriteriaService.getAll();

        // Then
        verify(alertCriteriaRepository).getAll();
    }
}
