package com.github.duncanmfield.alertmonitoringservice.repository;

import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Defines the contract for communication with MongoDB for storing {@link AlertCriteria} instances.
 */
public interface AlertCriteriaRepository extends MongoRepository<AlertCriteria, String> {
}
