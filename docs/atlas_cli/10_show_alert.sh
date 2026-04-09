#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
HOST_PORT=atlas-11490e-shard-00-00.bijb43x.mongodb.net:27017
ALERT_ID=69ce98831866530bf63e335a
# Mostrar los detalles de una alerta específica utilizando su ID
atlas alerts settings describe $ALERT_ID --projectId "$PROJ_ID"
