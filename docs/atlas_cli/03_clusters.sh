#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
CUSTER_NAME=Cluster0
# Listar los clusters asociados al proyecto
echo "Clusters asociados al proyecto con ID: $PROJ_ID"
atlas clusters list --projectId $PROJ_ID
echo "Detalles del cluster con nombre: <CLUSTER_NAME>"
atlas clusters describe \
$CUSTER_NAME \
--projectId $PROJ_ID \
--output json


