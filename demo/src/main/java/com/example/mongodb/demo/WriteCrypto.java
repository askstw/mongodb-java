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

public class WriteCrypto {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string

            try {
                if ("buy".equals(args[0])){
                    WriteCryptoBuy.run("mongodb+srv://caspar:c@cry.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority",1000000,10000);
                }else if ("sell".equals(args[0])){
                    WriteCryptoSell.run("mongodb+srv://caspar:c@cry.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority",1000000,10000);
                }else if ("index".equals(args[0])){
                    WriteCryptoIndex.run("mongodb+srv://caspar:c@cry.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority",1000000,10000);
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
    }
}