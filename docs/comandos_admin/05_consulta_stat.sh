#!/bin/bash

# Realiza consultas para generar carga en la base de datos
for i in {1..1000}; do
  mongosh --eval '
    const dbApp = db.getSiblingDB("testDB");
    for (let j = 0; j < 1000; j++) {
      dbApp.users.find({ age: { $gt: 50 } }).toArray();
    }
  '
done