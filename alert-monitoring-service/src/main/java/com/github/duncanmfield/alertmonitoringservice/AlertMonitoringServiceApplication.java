package com.github.duncanmfield.alertmonitoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for the alert monitoring service application.
 */
@SpringBootApplication
@EnableScheduling
public class AlertMonitoringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertMonitoringServiceApplication.class, args);
    }
}
