package com.example.mongodb.demo;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class Write {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb://13.214.135.136:27077/?maxPoolSize=20&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("crypto");
            MongoCollection<Document> collection = database.getCollection("exchange");
            try {
                System.out.println("Connected successfully to server.");

                Bson query = Filters.eq("date", "20190523");
                
                Document doc = collection.find(query).projection(Projections.excludeId()).first();
                System.out.println("query : " + doc.toJson());

                for(int i = 0; i<1000 ; i++){
                    collection.insertOne(doc);
                    doc.remove("_id");
                }

            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }
}
