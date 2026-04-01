#!/bin/bash

# Instala mongostat si no lo tienes instalado
mongostat --uri="mongodb://localhost:27017" \
  --rowcount=3 \
  -o='host,opcounters.insert.rate()=Insert Rate,opcounters.query.rate()=Query Rate,opcounters.command.rate()=Command Rate' \
  2 # cada dos segundos
