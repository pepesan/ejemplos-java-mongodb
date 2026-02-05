package com.cursosdedesarrollo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Ejemplo06Insercion {
    public static void main(String[] args) {
        // 1. Obtener MongoClient desde el Singleton
        MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();

        // 2. Obtener base de datos (se crea si no existe)
        MongoDatabase database = mongoClient.getDatabase("curso_java");

        // 3. Obtener colección (se crea si no existe)
        MongoCollection<Document> collection =
                database.getCollection("usuarios");

        // 4. Crear el Document a insertar
        Document doc = new Document("_id", new ObjectId())
                .append("nombre", "Ana")
                .append("edad", 30)
                .append("activo", true)
                .append("fechaAlta", new Date())
                .append("ultimoCambio",
                        new BsonTimestamp(
                                (int) (System.currentTimeMillis() / 1000),
                                1
                        )
                )
                .append("roles", List.of("ADMIN", "USER"))
                .append("direccion",
                        new Document("ciudad", "Madrid")
                                .append("cp", "28001")
                );

        // 5. Insertar en MongoDB
        InsertOneResult result = collection.insertOne(doc);
        // 6. Obtener el ID del documento insertado
        BsonValue id = result.getInsertedId();
        System.out.println("ID del documento insertado: " + id);
        // 7. Mostrar el documento insertado
        System.out.println("Documento insertado correctamente:");
        System.out.println(doc.toJson());


        // 8) Documentos complejos
        Document doc1 = new Document("_id", new ObjectId())
                .append("nombre", "Ana")
                .append("edad", 30)
                .append("activo", true)
                .append("fechaAlta", new Date())
                .append("ultimoCambio", new BsonTimestamp((int) (System.currentTimeMillis() / 1000), 1))
                .append("roles", List.of("ADMIN", "USER"))
                .append("direccion", new Document("ciudad", "Madrid").append("cp", "28001"));

        Document doc2 = new Document("_id", new ObjectId())
                .append("nombre", "Luis")
                .append("edad", 35)
                .append("activo", true)
                .append("fechaAlta", new Date())
                .append("ultimoCambio", new BsonTimestamp((int) (System.currentTimeMillis() / 1000), 2))
                .append("roles", List.of("USER"))
                .append("direccion", new Document("ciudad", "Barcelona").append("cp", "08001"));

        // 9) InsertMany + capturar ids desde InsertManyResult
        InsertManyResult result2 = collection.insertMany(List.of(doc1, doc2));

        // 10) Mostrar IDs por consola (los devuelve como Map<Integer, BsonValue>)
        System.out.println("IDs devueltos por InsertManyResult.getInsertedIds():");
        Map<Integer, BsonValue> insertedIds = result2.getInsertedIds();
        insertedIds.forEach((index, bsonId) -> {
            // Normalmente será ObjectId -> {"$oid": "..."} si lo imprimes como BsonValue
            System.out.println("Documento " + index + " -> _id (BsonValue) = " + bsonId);

            // Si quieres extraer el ObjectId como tal:
            if (bsonId != null && bsonId.isObjectId()) {
                System.out.println("Documento " + index + " -> _id (ObjectId) = " + bsonId.asObjectId().getValue());
            }
        });

        // 11) (Opcional) Confirmación extra: los docs también contienen el _id
        System.out.println("\nIDs leídos desde los Document tras insertar:");
        System.out.println("doc1 _id = " + doc1.getObjectId("_id"));
        System.out.println("doc2 _id = " + doc2.getObjectId("_id"));
    }
}
