#!/bin/bash

# Restaurar la base de datos desde el archivo de respaldo
mongorestore \
  --uri="mongodb://localhost:27017" \
  --archive=tienda_backup.gz \
  --gzip \
  --drop