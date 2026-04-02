#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
HOST_PORT=atlas-11490e-shard-00-00.bijb43x.mongodb.net:27017
ALERT_ID=69ce98831866530bf63e335a
# Actualizar la configuración de una alerta específica utilizando su ID
atlas alerts settings update $ALERT_ID \
  --projectId "$PROJ_ID" \
  --enabled \
  --event OUTSIDE_METRIC_THRESHOLD \
  --metricName NORMALIZED_SYSTEM_CPU_USER \
  --metricOperator GREATER_THAN \
  --metricThreshold 40 \
  --metricUnits RAW \
  --notificationType EMAIL \
  --notificationEmailEnabled \
  --notificationEmailAddress "tu-otro-correo@empresa.com" \
  --notificationIntervalMin 5