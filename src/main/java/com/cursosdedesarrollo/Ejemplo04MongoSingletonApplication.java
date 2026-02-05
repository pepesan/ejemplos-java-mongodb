package com.cursosdedesarrollo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Ejemplo04MongoSingletonApplication {

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = Ejemplo03MongoClientSingleton.getClient();

            MongoDatabase database = mongoClient.getDatabase("admin");
            database.runCommand(new Document("ping", 1));

            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

            mongoClient.close();

        } catch (MongoException e) {
            System.err.println("Error usando MongoDB: " + e.getMessage());
            System.exit(1);
        }
    }
}
