package com.github.duncanmfield.alertmonitoringservice.service;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for handling of alert criteria.
 */
@Service
@RequiredArgsConstructor
public class AlertCriteriaService {

    private final AlertCriteriaRepository alertCriteriaRepository;

    /**
     * Handles new {@link AlertCriteria} requests.
     *
     * @param alertCriteria The alert criteria.
     */
    public void handleCreate(AlertCriteria alertCriteria) {
        alertCriteriaRepository.save(alertCriteria);
    }

    /**
     * Retrieves all {@link AlertCriteria} instances in the repository.
     *
     * @return All alert criteria instances.
     */
    public List<AlertCriteria> getAll() {
        return alertCriteriaRepository.findAll();
    }
}
