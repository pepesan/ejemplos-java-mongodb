package com.cursosdedesarrollo;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

public class Ejemplo11Agregaciones {

    public static void main(String[] args) {

        // Obtenemos el cliente (Singleton)
        MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();

        // Accedemos a la base de datos
        MongoDatabase database = mongoClient.getDatabase("curso_java");

        // Colección de trabajo
        MongoCollection<Document> productos = database.getCollection("productos");

        // Limpieza para poder re-ejecutar el ejemplo sin duplicar datos
        productos.drop();

        // --------------------------------
        // 1) INSERTAMOS DATOS DE EJEMPLO
        // --------------------------------
        productos.insertMany(Arrays.asList(
                new Document("nombre", "Teclado")
                        .append("categoria", "Perifericos")
                        .append("precio", 25.0)
                        .append("stock", 50),

                new Document("nombre", "Raton")
                        .append("categoria", "Perifericos")
                        .append("precio", 15.0)
                        .append("stock", 120),

                new Document("nombre", "Monitor 24")
                        .append("categoria", "Pantallas")
                        .append("precio", 140.0)
                        .append("stock", 20),

                new Document("nombre", "Monitor 27")
                        .append("categoria", "Pantallas")
                        .append("precio", 220.0)
                        .append("stock", 10),

                new Document("nombre", "Portatil")
                        .append("categoria", "Ordenadores")
                        .append("precio", 850.0)
                        .append("stock", 5),

                new Document("nombre", "Cable HDMI")
                        .append("categoria", "Accesorios")
                        .append("precio", 9.0)
                        .append("stock", 200)
        ));

        // --------------------------------
        // 2) DEFINIMOS LAS FASES DEL PIPELINE COMO VARIABLES (Bson)
        // --------------------------------

        // Fase $match (Aggregates.match + Filters.gte)
        // - Objetivo: filtrar los documentos que entran al pipeline
        // - Solo pasarán a la siguiente fase los productos con precio >= 50
        // - Importante: aplicar $match lo antes posible suele mejorar rendimiento,
        //   porque reduce la cantidad de documentos que se agrupan después
        Bson matchStage = match(gte("precio", 50.0));

        // Fase $group (Aggregates.group + Accumulators.*)
        // - Objetivo: agrupar los documentos ya filtrados por una clave
        // - Aquí la clave es "$categoria" (el valor del campo categoria)
        // - El resultado de $group crea un documento por cada grupo (categoría)
        // - El campo "_id" del resultado será la clave de agrupación (la categoría)
        // - Además, calculamos métricas por cada grupo:
        //   * numProductos: cuenta documentos (sumando 1 por cada documento)
        //   * totalStock: suma del campo stock
        //   * precioMedio: media del campo precio
        Bson groupStage = group(
                "$categoria",
                sum("numProductos", 1),
                sum("totalStock", "$stock"),
                avg("precioMedio", "$precio")
        );

        // --------------------------------
        // 3) CONSTRUIMOS Y EJECUTAMOS EL PIPELINE
        // --------------------------------
        // Un pipeline es una lista ordenada de fases.
        // Aquí SOLO usamos:
        //   1) $match  -> filtra documentos
        //   2) $group  -> agrupa y calcula agregados
        AggregateIterable<Document> resultado = productos.aggregate(
                Arrays.asList(matchStage, groupStage)
        );

        // --------------------------------
        // 4) MOSTRAMOS RESULTADOS
        // --------------------------------
        // Cada documento impreso representa una categoría.
        // Recuerda: el valor de la categoría estará en "_id"
        // (porque así funciona $group cuando agrupas por un campo).
        System.out.println("Resultados de la agregación:");
        for (Document doc : resultado) {
            System.out.println(doc.toJson());
        }

        // =========================================================
        // 5) : $match + $sort + $project
        // =========================================================

        // --------------------------------
        // DEFINICIÓN DE FASES
        // --------------------------------

        // 6) Fase $match
        // Filtramos productos con stock mayor o igual que 20
        Bson matchStockStage = match(gte("stock", 20));

        // 7) Fase $sort
        // Ordenamos los documentos resultantes por precio
        // en orden descendente (de más caro a más barato)
        Bson sortByPrecioStage = sort(descending("precio"));

        // 8) Fase $project
        // Definimos la forma del documento de salida:
        //  - incluimos solo nombre, precio y stock
        //  - excluimos explícitamente el campo _id
        Bson projectStage = project(
                fields(
                        excludeId(),
                        include("nombre", "precio", "stock")
                )
        );

        // --------------------------------
        // 9) PIPELINE DE AGREGACIÓN
        // --------------------------------
        // Orden de las fases:
        // - $match   -> reduce documentos
        // - $sort    -> ordena los documentos filtrados
        // - $project -> selecciona los campos finales
        AggregateIterable<Document> resultadoMatchSortProject =
                productos.aggregate(
                        Arrays.asList(
                                matchStockStage,
                                sortByPrecioStage,
                                projectStage
                        )
                );

        // --------------------------------
        // 10) MOSTRAR RESULTADOS
        // --------------------------------
        System.out.println("\nResultados $match + $sort + $project:");
        for (Document doc : resultadoMatchSortProject) {
            System.out.println(doc.toJson());
        }

    }
}
