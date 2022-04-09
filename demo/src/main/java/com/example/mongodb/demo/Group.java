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


public class Group {
    public static void main( String[] args ) {

        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://caspar:c@bil.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("711");
            MongoCollection<Document> collection = database.getCollection("transaction");

            collection.aggregate(
                Arrays.asList(
                    Aggregates.group("$cust_no", 
                        Accumulators.sum("amount", "$amount"))
                )
            ).forEach(doc -> System.out.println(doc.toJson()));

            collection.aggregate(
                Arrays.asList(
                    Aggregates.match(Filters.eq("tx_date", "2022-03-21")),
                    Aggregates.group("$cust_no", 
                        Accumulators.sum("amount", "$amount"))
                )
            ).forEach(doc -> System.out.println(doc.toJson()));

        }
    }
}