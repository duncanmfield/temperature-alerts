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
import java.util.Optional;

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
        AlertCriteriaView response = mapper.toView(alertCriteriaService.handleCreate(mapper.toDto(alertCriteriaRequest)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlertCriteriaView>> getAllAlertCriteria() {
        log.info("Retrieving all alert criteria");
        List<AlertCriteriaView> result = alertCriteriaService.getAll()
                .stream()
                .map(mapper::toView).toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlertCriteriaView> getAlertCriteria(@PathVariable long id) {
        log.info("Retrieving alert criteria with id {}", id);
        Optional<AlertCriteriaView> result = alertCriteriaService.getById(id).map(mapper::toView);
        return result
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AlertCriteriaView> deleteAlertCriteria(@PathVariable long id) {
        log.info("Deleting alert criteria with id {}", id);
        if (alertCriteriaService.handleDelete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}