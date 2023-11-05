package com.github.duncanmfield.alertmonitoringservice.dto.mapper;

import com.github.duncanmfield.alertmonitoringservice.controller.request.AlertCriteriaView;
import com.github.duncanmfield.alertmonitoringservice.dto.AlertCriteria;
import org.springframework.stereotype.Service;

/**
 * Maps between presentation layer ({@link AlertCriteriaView} and service layer ({@link AlertCriteria})
 * representations.
 */
@Service
public class AlertCriteriaMapper {

    /**
     * Convert from presentation layer to service layer representation of an alert criteria.
     *
     * @param view The presentation layer representation.
     * @return The service layer representation.
     */
    public AlertCriteria toDto(AlertCriteriaView view) {
        AlertCriteria dto = new AlertCriteria();
        dto.setId(view.getId());
        dto.setDescription(view.getDescription());
        dto.setLatitude(view.getLatitude());
        dto.setLongitude(view.getLongitude());
        dto.setTemperature(view.getTemperature());
        return dto;
    }

    /**
     * Convert from service layer to presentation layer representation of an alert criteria.
     *
     * @param dto The service layer representation.
     * @return The presentation layer representation.
     */
    public AlertCriteriaView toView(AlertCriteria dto) {
        AlertCriteriaView view = new AlertCriteriaView();
        view.setId(dto.getId());
        view.setDescription(dto.getDescription());
        view.setLatitude(dto.getLatitude());
        view.setLongitude(dto.getLongitude());
        view.setTemperature(dto.getTemperature());
        return view;
    }
}
