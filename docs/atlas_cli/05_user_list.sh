#!/bin/bash

ORG_ID=61530a4c7944ef5b1f0c95d5
PROJ_ID=61530a5e2fec904a692e2823
# Listar los usuarios asociados a los proyectos
echo "Usuarios asociados a los proyectos"
atlas projects users list
echo "Usuarios asociados al proyecto con ID: $PROJ_ID"
atlas projects users list --projectId $PROJ_ID

