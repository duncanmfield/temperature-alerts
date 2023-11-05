package com.github.duncanmfield.alertmonitoringservice.repository;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines the contract for communication with a database for storing {@link AlertCriteria} instances.
 */
public interface AlertCriteriaRepository extends JpaRepository<AlertCriteria, Long> {
}
