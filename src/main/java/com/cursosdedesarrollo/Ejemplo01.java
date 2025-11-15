package com.cursosdedesarrollo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Ejemplo01 {
    public static void main(String[] args) {
        System.out.println("Primer Ejemplo de MongoDB!");
        // 1. Conectar al servidor
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {

            // 2. Seleccionar base de datos
            MongoDatabase db = client.getDatabase("miBD");

            // 3. Seleccionar colección
            MongoCollection<Document> coleccion = db.getCollection("usuarios");

            // 4. Insertar un documento
            Document doc = new Document("nombre", "Ana")
                    .append("edad", 30);
            coleccion.insertOne(doc);

            // 5. Leer el primer documento de la colección
            Document encontrado = coleccion.find().first();

            System.out.println("Documento encontrado: " + encontrado);
        }
    }
}
