package com.example.mongodb.demo;

import org.bson.Document;

import java.util.Arrays;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;


public class Insert {
    public static void main( String[] args ) {

        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://caspar:c@bil.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("711");
            MongoCollection<Document> collection = database.getCollection("transaction");

            Document doc = new Document()
                .append("tx_date", "2021-03-22")
                .append("amount", "30");
            collection.insertOne(doc);

        }
    }
}