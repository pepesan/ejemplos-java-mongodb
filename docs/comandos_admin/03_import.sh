#!/bin/bash

# Importar datos desde un archivo JSON a MongoDB usando mongoimport
mongoimport \
  --uri="mongodb://localhost:27017" \
  --db=testDB \
  --collection=users \
  --type=json \
  --mode=insert \
  --drop \
  --file=users.json