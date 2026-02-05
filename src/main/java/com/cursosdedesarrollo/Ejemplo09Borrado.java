package com.cursosdedesarrollo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;

public class Ejemplo09Borrado {

    public static void main(String[] args) {

        // 1) Cliente desde el Singleton
        MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();

        // 2) Base de datos y colección
        MongoDatabase database = mongoClient.getDatabase("curso_java");
        MongoCollection<Document> collection =
                database.getCollection("usuarios");

        // =================================================
        // 3) deleteOne: borrar UN solo documento
        // =================================================
        System.out.println("deleteOne: borrar usuario con nombre = Juan");

        DeleteResult resultDeleteOne =
                collection.deleteOne(eq("nombre", "Juan"));

        System.out.println("deleteOne - deleted: "
                + resultDeleteOne.getDeletedCount());

        // =================================================
        // 4) deleteMany: borrar VARIOS documentos
        // =================================================
        System.out.println("\ndeleteMany: borrar usuarios activos con edad >= 40");

        DeleteResult resultDeleteMany =
                collection.deleteMany(
                        and(
                                eq("activo", true),
                                gte("edad", 40)
                        )
                );

        System.out.println("deleteMany - deleted: "
                + resultDeleteMany.getDeletedCount());
    }
}

