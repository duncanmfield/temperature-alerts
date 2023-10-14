package com.github.duncanmfield.alertmonitoringservice.controller;

import com.github.duncanmfield.alertmonitoringservice.data.AlertCriteria;
import com.github.duncanmfield.alertmonitoringservice.service.AlertCriteriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller exposing the /alert endpoint.
 */
@RestController
@RequestMapping("/alert")
public class AlertCriteriaController {

    @Autowired
    private AlertCriteriaService alertCriteriaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createAlertCriteria(@RequestBody @Valid AlertCriteria alertCriteriaRequest) {
        System.out.println("Creating alert criteria " + alertCriteriaRequest);
        alertCriteriaService.handle(alertCriteriaRequest);
        return true;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<AlertCriteria> getAllAlertCriteria() {
        System.out.println("Retrieving all alert criteria");
        return alertCriteriaService.getAll();
    }
}