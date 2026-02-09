package com.cursosdedesarrollo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;


import java.util.List;


public class Ejemplo07Consultas {
    public static void main(String[] args) {
        // 1. Obtener MongoClient desde el Singleton
        MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();

        // 2. Obtener base de datos (se crea si no existe)
        MongoDatabase database = mongoClient.getDatabase("curso_java");

        // 3. Obtener colección (se crea si no existe)
        MongoCollection<Document> collection =
                database.getCollection("usuarios");

        // 4) Consulta más sencilla posible: find() sin filtros
        System.out.println("Documentos encontrados:");

        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }

        // 5) Consulta sencilla con cursor explícito
        System.out.println("Documentos encontrados:");

        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        } finally {
            // 6) Cierre explícito del cursor (buena práctica)
            cursor.close();
        }

        // =================================================
        // 7) Consulta con filtro simple
        // =================================================
        System.out.println("Documentos con nombre = Ana:");

        MongoCursor<Document> cursorFiltrado =
                collection.find(new Document("nombre", "Ana")).iterator();

        try {
            while (cursorFiltrado.hasNext()) {
                Document doc = cursorFiltrado.next();
                System.out.println(doc.toJson());
            }
        } finally {
            cursorFiltrado.close();
        }
        // =================================================
        // 8) Consulta con AND y dos condiciones
        // =================================================
        System.out.println("Documentos con activo=true AND edad >= 30:");

        Document filtroAnd = new Document("$and", List.of(
                new Document("activo", true),
                new Document("edad", new Document("$gte", 30))
        ));

        MongoCursor<Document> cursorAnd =
                collection.find(filtroAnd).iterator();

        try {
            while (cursorAnd.hasNext()) {
                Document doc = cursorAnd.next();
                System.out.println(doc.toJson());
            }
        } finally {
            cursorAnd.close();
        }

        // =================================================
        // 9) Consulta AND usando Filters (forma recomendada)
        // =================================================
        System.out.println("Documentos con activo=true AND edad >= 30 (Filters):");

        MongoCursor<Document> cursorAndFilters =
                collection.find(
                        and(
                                eq("activo", true),
                                gte("edad", 30),
                                lte("edad", 40)
                        )
                ).iterator();

        try {
            while (cursorAndFilters.hasNext()) {
                Document doc = cursorAndFilters.next();
                System.out.println(doc.toJson());
            }
        } finally {
            cursorAndFilters.close();
        }

        // =================================================
        // 10) Consulta que devuelve un único documento (first)
        // =================================================
        System.out.println("Primer documento con nombre = Ana:");

        Document primerDocumento =
                collection.find(new Document("nombre", "Ana")).first();

        if (primerDocumento != null) {
            System.out.println(primerDocumento.toJson());
        } else {
            System.out.println("No se encontró ningún documento.");
        }
        // =================================================
        // 11) Consulta con ordenación, skip y limit
        // =================================================
        System.out.println("Consulta con sort (edad desc), skip y limit:");

        MongoCursor<Document> cursorOrdenado =
                collection.find()
                        .sort(new Document("edad", -1)) // -1 desc, 1 asc
                        .skip(1)
                        .limit(2)
                        .iterator();

        try {
            while (cursorOrdenado.hasNext()) {
                Document doc = cursorOrdenado.next();
                System.out.println(doc.toJson());
            }
        } finally {
            cursorOrdenado.close();
        }
        // =================================================
        // 12) Consulta con proyección de campos
        // =================================================
        System.out.println("Consulta con proyección (solo nombre y edad, sin _id):");

        MongoCursor<Document> cursorProyeccion =
                collection.find()
                        .projection(
                                new Document("nombre", 1)
                                        .append("edad", 1)
                                        .append("_id", 0)
                        )
                        .iterator();

        try {
            while (cursorProyeccion.hasNext()) {
                Document doc = cursorProyeccion.next();
                System.out.println(doc.toJson());
            }
        } finally {
            cursorProyeccion.close();
        }

    }
}
