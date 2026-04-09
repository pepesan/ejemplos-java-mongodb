#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
HOST_PORT=atlas-11490e-shard-00-00.bijb43x.mongodb.net:27017
# Crear una alerta que se active cuando el porcentaje de CPU del sistema supere el 30% durante un período de tiempo específico, y enviar una notificación por correo electrónico cada 5 minutos mientras la condición persista.
atlas alerts settings create \
  --projectId "$PROJ_ID" \
  --enabled \
  --event OUTSIDE_METRIC_THRESHOLD \
  --metricName NORMALIZED_SYSTEM_CPU_USER \
  --metricOperator GREATER_THAN \
  --metricThreshold 30 \
  --metricUnits RAW \
  --notificationType EMAIL \
  --notificationEmailEnabled \
  --notificationEmailAddress "tu-correo@empresa.com" \
  --notificationIntervalMin 5
