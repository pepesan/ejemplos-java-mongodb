#!/bin/bash

# Este script utiliza mongofiles para subir un archivo a GridFS en MongoDB.

mongofiles --uri "mongodb://localhost:27017/testDB" \
  put 08_files_put.sh

# Listar los archivos almacenados en GridFS
mongofiles --uri "mongodb://localhost:27017/testDB" \
  list
# Buscar un archivo específico en GridFS
mongofiles --uri "mongodb://localhost:27017/testDB" \
  search 08_files_put.sh
# Descargar el archivo desde GridFS a un nuevo nombre local
mongofiles --uri "mongodb://localhost:27017/testDB" \
  get 08_files_put.sh --local copia.sh
#  Eliminar el archivo de GridFS
mongofiles --uri "mongodb://localhost:27017/testDB" \
  delete 08_files_put.sh

