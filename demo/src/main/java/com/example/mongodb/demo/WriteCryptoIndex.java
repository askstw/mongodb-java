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

public class WriteCryptoIndex {
    public static void run(String uri, int count, int date) {
        // Replace the uri string with your MongoDB deployment's connection string

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("crypto");
            MongoCollection<Document> collectiond = database.getCollection("buy");
            MongoCollection<Document> collections = database.getCollection("sell");
            try {
                System.out.println("Connected successfully to server.");

                collectiond.createIndex(Indexes.ascending("date","price","quantity"));
                collectiond.createIndex(Indexes.ascending("date","quantity","price"));
                collections.createIndex(Indexes.ascending("date","price","quantity"));
                collections.createIndex(Indexes.ascending("date","quantity","price"));

            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }
}
