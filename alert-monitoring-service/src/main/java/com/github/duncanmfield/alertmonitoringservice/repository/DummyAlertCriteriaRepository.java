package com.github.duncanmfield.alertmonitoringservice.repository;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * A dummy {@link HashSet}-based implementation of a repository, for ease of developing a PoC/MVP.
 * Should be replaced with a proper repository.
 */
@Repository
public class DummyAlertCriteriaRepository implements AlertCriteriaRepository {

    private final Set<AlertCriteria> allAlertCriteria = new HashSet<>();

    @Override
    @Transactional
    public void save(AlertCriteria alert) {
        allAlertCriteria.add(alert);
    }

    @Override
    @Transactional
    public Set<AlertCriteria> getAll() {
        return Set.copyOf(allAlertCriteria);
    }
}
