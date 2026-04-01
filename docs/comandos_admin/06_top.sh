#!/bin/bash

# Este comando muestra el uso de la base de datos en tiempo real, incluyendo el número de operaciones de lectura y escritura, así como el tiempo que cada operación tarda en completarse

mongotop --uri "mongodb://localhost:27017/testDB" \
  --rowcount 3 \
  2 # cada dos segundos

# Para mostrar la salida en formato JSON, puedes usar la opción --json:
mongotop --uri "mongodb://localhost:27017/testDB" \
  --rowcount 3 \
  --json \
  2 # cada dos segundos