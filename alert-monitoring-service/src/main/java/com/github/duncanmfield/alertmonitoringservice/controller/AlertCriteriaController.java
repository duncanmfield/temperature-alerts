package com.github.duncanmfield.alertmonitoringservice.controller;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller exposing the /alert endpoint.
 */
@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
@Slf4j
public class AlertCriteriaController {

    private final AlertCriteriaService alertCriteriaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createAlertCriteria(@RequestBody @Valid AlertCriteria alertCriteriaRequest) {
        log.info("Creating alert criteria " + alertCriteriaRequest);
        alertCriteriaService.handle(alertCriteriaRequest);
        return true;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<AlertCriteria> getAllAlertCriteria() {
        log.info("Retrieving all alert criteria");
        return alertCriteriaService.getAll();
    }
}