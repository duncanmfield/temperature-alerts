package com.github.duncanmfield.alertmonitoringservice.service;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service layer for requests pertaining to {@link AlertCriteria} instances.
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
    public void handle(AlertCriteria alertCriteria) {
        alertCriteriaRepository.save(alertCriteria);
    }

    /**
     * Retrieves all {@link AlertCriteria} instances in the repository.
     *
     * @return All alert criteria instances.
     */
    public Set<AlertCriteria> getAll() {
        return alertCriteriaRepository.getAll();
    }
}
