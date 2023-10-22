# temperature-alerts

[![Java CI with Maven](https://github.com/duncanmfield/temperature-alerts/actions/workflows/maven.yml/badge.svg)](https://github.com/duncanmfield/temperature-alerts/actions/workflows/maven.yml)

This is a proof-of-concept demonstrating setting up alerts for when temperature thresholds are exceeded at specific co-ordinates.

### Example Usage
The following example demonstrates setting up an alert for Bath being above 10°C.
Once deployed, alert criteria may be set up by running `./demo-scripts/create-alert-bath.sh`, or the following curl command:
```
curl --silent --location 'localhost:8080/alerts' \
--header 'Content-Type: application/json' \
--data '{
    "description": "Bath",
    "latitude": 51.3781,
    "longitude": -2.3597,
    "temperature": 10
}'
```
The alert-monitoring-service has a scheduled task to loop through any configured alert criteria. If the
temperature at the specified co-ordinates for any of the alert criteria exceeds the temperature threshold provided (i.e. if the temperature
in Bath exceeds 10°C in the example above), then an event will be published to Kafka's notification topic.

The notification-consumer-service listens to Kafka's notification topic, and simply prints out the notification.

### Prerequisites
1. Docker installed, and docker daemon running

### Deploy:
1. Launch Kafka container: `docker-compose up -d`
2. Launch notification-consumer-service `cd notification-consumer-service && ./mvnw spring-boot:run`
3. Launch alert-monitoring-service `cd alert-monitoring-service && ./mvnw spring-boot:run`

### Stop:
1. Ctrl+C terminal running alert-monitoring-service
2. Ctrl+C terminal running notification-consumer-service
3. Stop Kafka container: `docker-compose down`