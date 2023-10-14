package com.github.duncanmfield.alertmonitoringservice.service;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service layer for requests pertaining to {@link AlertCriteria} instances.
 */
@Service
public class AlertCriteriaService {

    @Autowired
    private AlertCriteriaRepository alertCriteriaRepository;

    /**
     * Handles new {@link AlertCriteria} requests.
     *
     * @param alert The alert.
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
