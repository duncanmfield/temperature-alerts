curl --silent --location 'localhost:8080/alert' \
--header 'Content-Type: application/json' \
--data '{
    "description": "London",
    "latitude": 51.5072,
    "longitude": 0.1276,
    "temperature": 12
}'