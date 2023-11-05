package com.github.duncanmfield.alertmonitoringservice.service;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.repository.AlertCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public AlertCriteria handleCreate(AlertCriteria alertCriteria) {
        return alertCriteriaRepository.save(alertCriteria);
    }

    /**
     * Retrieves all {@link AlertCriteria} instances in the repository.
     *
     * @return All alert criteria instances.
     */
    public List<AlertCriteria> getAll() {
        return alertCriteriaRepository.findAll();
    }

    /**
     * Retrieves the {@link AlertCriteria} instance with the provided id, if it exists.
     *
     * @param id The id to look up.
     * @return The alert criteria instance, if it exists.
     */
    public Optional<AlertCriteria> getById(long id) {
        return alertCriteriaRepository.findById(id);
    }

    /**
     * Handles {@link AlertCriteria} deletion requests.
     *
     * @param id The alert criteria.
     * @return True if a deletion was performed, otherwise false.
     */
    public boolean handleDelete(long id) {
        if (alertCriteriaRepository.existsById(id)) {
            alertCriteriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
