package com.cursosdedesarrollo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public final class MongoClientSingleton {

    private static volatile MongoClient instance;

    private static final String CONNECTION_STRING =
            "mongodb://localhost:27017/admin?retryWrites=true&w=majority";

    // Constructor privado: clave del patrón Singleton
    private MongoClientSingleton() {
    }

    public static MongoClient getClient() {
        if (instance == null) {
            synchronized (MongoClientSingleton.class) {
                if (instance == null) {
                    instance = createClient();
                    registerShutdownHook();
                }
            }
        }
        return instance;
    }

    private static MongoClient createClient() {
        try {
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .serverApi(serverApi)
                    .build();

            return MongoClients.create(settings);

        } catch (MongoException e) {
            System.err.println("Error creando MongoClient: " + e.getMessage());
            throw e; // fallo crítico: mejor propagar
        }
    }

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                instance.close();
                System.out.println("MongoClient cerrado correctamente.");
            }
        }));
    }
}