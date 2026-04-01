#!/bin/bash

# Exportar la colección "users" de la base de datos "testDB" a un archivo JSON
mongoexport  \
 --uri="mongodb://localhost:27017"   \
 --db=testDB   \
 --collection=users   \
 --type=json \
 --jsonFormat=canonical \
 --out=users.json
# Exportar solo los documentos que cumplen con ciertas condiciones (por ejemplo, edad mayor a 50 y puntaje mayor a 500)
mongoexport \
 --uri="mongodb://localhost:27017" \
 --db=testDB \
 --collection=users \
 --query='{"age": {"$gt": 50}, "score": {"$gt": 500}}' \
 --jsonFormat=canonical \
 --out=users_filtrados.json