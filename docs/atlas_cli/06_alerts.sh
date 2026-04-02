#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
HOST_PORT=atlas-11490e-shard-00-00.bijb43x.mongodb.net:27017
# Obtener las alertas configuradas para el proyecto
atlas alerts settings list \
   --projectId $PROJ_ID   \
   --output json
