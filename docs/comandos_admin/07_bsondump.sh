#!/bin/bash

# este comando se utiliza para mostrar el contenido de un archivo BSON (Binary JSON)

bsondump --pretty backup_tienda/testDB/users.bson

# bsondump --type=debug backup_tienda/testDB/users.bson

# bsondump --gzip backup_tienda/testDB/users.bson.gz

