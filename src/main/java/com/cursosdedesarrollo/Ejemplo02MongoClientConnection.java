package com.cursosdedesarrollo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ConnectionPoolSettings;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class Ejemplo02MongoClientConnection {
    public static void main(String[] args) {
        // String connectionString = "mongodb+srv://<db_username>:<db_password>@cluster0.xxxx.mongodb.net/?appName=Cluster0";
        String connectionString = "mongodb://localhost:27017/admin?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        // Configuración del pool de conexiones
        ConnectionPoolSettings poolSettings = ConnectionPoolSettings.builder()
                .maxSize(50)                 // <-- maxPoolSize
                .minSize(5)                  // opcional, recomendado
                .maxWaitTime(10, TimeUnit.SECONDS)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .applyToConnectionPoolSettings(builder ->
                        builder.applySettings(poolSettings)
                )
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                System.err.println("Error haciendo ping a MongoDB: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (MongoException e) {
            System.err.println("Error conectando a MongoDB: " + e.getMessage());
            System.exit(1);
        }
    }
}
