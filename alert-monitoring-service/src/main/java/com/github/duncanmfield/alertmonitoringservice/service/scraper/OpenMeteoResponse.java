package com.github.duncanmfield.alertmonitoringservice.service.scraper;

import lombok.Data;

/**
 * Data representation of the parts of the JSON response from OpenMeteo which we're interested in.
 * Full example JSON response:
 * {
 *      "latitude": 52.52,
 *      "longitude": 13.419998,
 *      "generationtime_ms": 0.04506111145019531,
 *      "utc_offset_seconds": 0,
 *      "timezone": "GMT",
 *      "timezone_abbreviation": "GMT",
 *      "elevation": 38.0,
 *      "current_units": {
 *          "time": "iso8601",
 *          "interval": "seconds",
 *          "temperature_2m": "°C"
 *      },
 *      "current": {
 *          "time": "2023-10-14T12:15",
 *          "interval": 900,
 *          "temperature_2m": 15.6
 *      }
 * }
 */
@Data
public class OpenMeteoResponse {

    private Current current;

    @Data
    public static class Current {

        private double temperature_2m;
    }
}
