#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
HOST_PORT=atlas-11490e-shard-00-00.bijb43x.mongodb.net:27017
ALERT_ID=69ce98831866530bf63e335a
# Acknowledge de una alerta específica utilizando su ID, indicando que se ha reconocido el problema y se está trabajando en su resolución
atlas alerts acknowledge $ALERT_ID \
  --until "2028-07-01T00:00:00Z" \
  --comment "Acknowledging the alert until the issue is resolved"
