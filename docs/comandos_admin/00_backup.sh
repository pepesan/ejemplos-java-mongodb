#!/bin/bash

# Realiza un backup de la base de datos MongoDB utilizando mongodump
mongodump  \
 --uri="mongodb://localhost:27017"   \
 --archive=tienda_backup.gz   \
 --gzip

mongodump \
 --uri="mongodb://localhost:27017" \
 --out=./backup_tienda