package com.cursosdedesarrollo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import org.bson.Document;

import java.util.List;

public class Ejemplo08Actualizacion {

    public static void main(String[] args) {

        // 1) Cliente desde el Singleton
        MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();

        // 2) Base de datos y colección
        MongoDatabase database = mongoClient.getDatabase("curso_java");
        MongoCollection<Document> collection =
                database.getCollection("usuarios");

        // =================================================
        // 3) updateOne: ACTUALIZAR un campo existente
        // =================================================
        System.out.println("updateOne: actualizar campo existente (activo)");

        Document filtro1 = new Document("nombre", "Ana");
        Document updateCampoExistente =
                new Document("$set", new Document("activo", false));

        UpdateResult result1 =
                collection.updateOne(filtro1, updateCampoExistente);

        System.out.println("matched: " + result1.getMatchedCount()
                + ", modified: " + result1.getModifiedCount());

        // =================================================
        // 4) updateOne: CREAR un campo nuevo
        // =================================================
        System.out.println("\nupdateOne: crear campo nuevo (categoria)");

        Document filtro2 = new Document("nombre", "Luis");
        Document updateNuevoCampo =
                new Document("$set", new Document("categoria", "usuario"));

        UpdateResult result2 =
                collection.updateOne(filtro2, updateNuevoCampo);

        System.out.println("matched: " + result2.getMatchedCount()
                + ", modified: " + result2.getModifiedCount());

        // =================================================
        // 5) updateMany: actualizar VARIOS documentos
        // =================================================
        System.out.println("\nupdateMany: crear/actualizar campo 'nivel' en usuarios activos");

        Document filtroMany = new Document("activo", true);
        Document updateMany =
                new Document("$set", new Document("nivel", "basico"));

        UpdateResult resultMany =
                collection.updateMany(filtroMany, updateMany);

        System.out.println("updateMany - matched: " + resultMany.getMatchedCount()
                + ", modified: " + resultMany.getModifiedCount());

        // =================================================
        // 6) updateOne usando Filters.eq (forma recomendada)
        // =================================================
        System.out.println("\nupdateOne usando Filters.eq (nombre = María)");

        UpdateResult resultConFilters =
                collection.updateOne(
                        eq("nombre", "María"),
                        new Document("$set", new Document("activo", true))
                );

        System.out.println("matched: " + resultConFilters.getMatchedCount()
                + ", modified: " + resultConFilters.getModifiedCount());

        // =================================================
        // 7) updateOne usando Updates (set, inc, combine)
        // =================================================
        System.out.println("\nupdateOne usando Updates.combine, set e inc");

        UpdateResult resultUpdates =
                collection.updateOne(
                        eq("nombre", "Ana"),
                        combine(
                                set("categoria", "premium"),
                                inc("edad", 1)
                        )
                );

        System.out.println("matched: " + resultUpdates.getMatchedCount()
                + ", modified: " + resultUpdates.getModifiedCount());

        // =================================================
        // 8) updateOne: crear un campo de tipo ARRAY usando Updates.set
        // =================================================
        System.out.println("\nupdateOne: crear campo array 'rolesExtra'");

        UpdateResult resultArray =
                collection.updateOne(
                        eq("nombre", "Luis"),
                        set("rolesExtra", List.of("EDITOR", "REVISOR"))
                );

        System.out.println("matched: " + resultArray.getMatchedCount()
                + ", modified: " + resultArray.getModifiedCount());

        // =================================================
        // 9) updateOne: PUSH sobre un array (añadir elemento)
        // =================================================
        System.out.println("\nupdateOne: push sobre array 'rolesExtra'");

        UpdateResult resultPush =
                collection.updateOne(
                        eq("nombre", "Luis"),
                        push("rolesExtra", "ADMIN")
                );

        System.out.println("push - matched: " + resultPush.getMatchedCount()
                + ", modified: " + resultPush.getModifiedCount());


        // =================================================
        // 10) updateOne: PULL sobre un array (eliminar elemento)
        // =================================================
        System.out.println("\nupdateOne: pull sobre array 'rolesExtra'");

        UpdateResult resultPull =
                collection.updateOne(
                        eq("nombre", "Luis"),
                        pull("rolesExtra", "EDITOR")
                );

        System.out.println("pull - matched: " + resultPull.getMatchedCount()
                + ", modified: " + resultPull.getModifiedCount());


        // =================================================
        // 11) updateOne: POP LAST sobre un array (eliminar último)
        // =================================================
        System.out.println("\nupdateOne: popLast sobre array 'rolesExtra'");

        UpdateResult resultPopLast =
                collection.updateOne(
                        eq("nombre", "Luis"),
                        popLast("rolesExtra")
                );

        System.out.println("popLast - matched: " + resultPopLast.getMatchedCount()
                + ", modified: " + resultPopLast.getModifiedCount());

    }
}
