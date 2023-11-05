curl -X "POST" --silent \
--location 'localhost:8080/alerts' \
--header 'Content-Type: application/json' \
--data '{
    "description": "Bath",
    "latitude": 51.3781,
    "longitude": -2.3597,
    "temperature": 10
}'