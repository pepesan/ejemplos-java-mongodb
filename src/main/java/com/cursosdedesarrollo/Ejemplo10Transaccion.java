package com.cursosdedesarrollo;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.TransactionBody;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;

public class Ejemplo10Transaccion {

    public static void main(String[] args) {

        // Esto sólo debería funcionar en un entorno de MongoDB con replica set o cluster (no en un servidor standalone)
        MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();
        MongoDatabase database = mongoClient.getDatabase("curso_java");
        MongoCollection<Document> cuentas =
                database.getCollection("cuentas_bancarias_tx");

        // Datos iniciales (idempotentes)
        cuentas.updateOne(
                eq("cuenta", "A"),
                new Document("$setOnInsert",
                        new Document("cuenta", "A").append("saldo", 1000)),
                new com.mongodb.client.model.UpdateOptions().upsert(true)
        );
        cuentas.updateOne(
                eq("cuenta", "B"),
                new Document("$setOnInsert",
                        new Document("cuenta", "B").append("saldo", 300)),
                new com.mongodb.client.model.UpdateOptions().upsert(true)
        );

        ClientSession session = mongoClient.startSession();

        try {

            TransactionBody<Void> transferencia = () -> {

                Document origen = cuentas.find(session, eq("cuenta", "A")).first();
                if (origen.getInteger("saldo") < 200) {
                    throw new RuntimeException("Saldo insuficiente");
                }

                cuentas.updateOne(session, eq("cuenta", "A"), inc("saldo", -200));
                cuentas.updateOne(session, eq("cuenta", "B"), inc("saldo", 200));

                return null;
            };

            session.withTransaction(transferencia);
            System.out.println("Transacción COMMIT OK");

        }
        // ⬇⬇⬇ ERRORES DE MONGODB (infraestructura, conexión, replica set, etc.)
        catch (MongoException e) {
            System.out.println("ERROR MongoDB:");
            System.out.println("  mensaje = " + e.getMessage());
            System.out.println("  código  = " + e.getCode());
        }
        finally {
            if (session != null) {
                session.close();
                System.out.println("ClientSession cerrada");
            }
        }

        // Mostrar resultado
        for (Document d : cuentas.find()) {
            System.out.println(d.toJson());
        }
    }
}
