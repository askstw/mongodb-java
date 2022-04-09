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
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;

public class WriteCryptoSell {
    public static void run(String uri, int count, int date) {
        // Replace the uri string with your MongoDB deployment's connection string

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("crypto");
            MongoCollection<Document> collections = database.getCollection("sell");

            try {
                System.out.println("Connected successfully to server.");

                Document sellDoc = new Document().append("cat", "BTC/USDT").append("tag", "sell");
                for(int i = 0; i<count ; i++){
                    sellDoc.append("date", (int)(Math.random()*date) );
                    sellDoc.append("price", (int)(Math.random()*1000) + 1000);
                    sellDoc.append("quantity", (int)(Math.random()*100));
                    collections.insertOne(sellDoc);
                    sellDoc.remove("date");
                    sellDoc.remove("quantity");
                    sellDoc.remove("price");
                    sellDoc.remove("_id");
                }

                /*
                collectiond.createIndex(Indexes.ascending("date","price","quantity"));
                collectiond.createIndex(Indexes.ascending("date","quantity","price"));
                collections.createIndex(Indexes.ascending("date","price","quantity"));
                collections.createIndex(Indexes.ascending("date","quantity","price"));
                */

            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }
}
