package com.github.duncanmfield.alertmonitoringservice.controller;

import com.github.duncanmfield.alertmonitoringservice.controller.request.AlertCriteriaView;
import com.github.duncanmfield.alertmonitoringservice.dto.mapper.AlertCriteriaMapper;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller exposing the /alerts endpoint.
 */
@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
@Slf4j
public class AlertCriteriaController {

    private final AlertCriteriaMapper mapper;
    private final AlertCriteriaService alertCriteriaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlertCriteriaView> createAlertCriteria(@RequestBody @Valid AlertCriteriaView alertCriteriaRequest) {
        log.info("Creating alert criteria {}", alertCriteriaRequest);
        alertCriteriaService.handleCreate(mapper.toDto(alertCriteriaRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlertCriteriaView> getAllAlertCriteria() {
        log.info("Retrieving all alert criteria");
        return alertCriteriaService.getAll()
                .stream()
                .map(mapper::toView).toList();
    }
}