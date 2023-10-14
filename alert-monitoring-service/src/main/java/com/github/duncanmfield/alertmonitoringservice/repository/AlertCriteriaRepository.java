package com.github.duncanmfield.alertmonitoringservice.repository;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;

import java.util.Set;

/**
 * Defines the contract for any repositories storing {@link AlertCriteria}.
 */
public interface AlertCriteriaRepository {

    /**
     * Saves the provided {@link AlertCriteria} instance.
     *
     * @param alert The alert criteria to save.
     */
    void save(AlertCriteria alert);

    /**
     * Returns all {@link AlertCriteria} in the repository.
     *
     * @return All alert criteria.
     */
    Set<AlertCriteria> getAll();
}
