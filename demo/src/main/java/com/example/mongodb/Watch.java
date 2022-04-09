package com.example.mongodb;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;

public class Watch {
    public static void main( String[] args ) {

        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://caspar:c@cry.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("POCDB");
            MongoCollection<Document> collection = database.getCollection("POCCOLL");
            MongoCollection<Document> collection1 = database.getCollection("POCCOLL1");

            List<Bson> pipeline = Arrays.asList(
                Aggregates.match(
                        Filters.in("operationType",
                                Arrays.asList("insert", "update"))));
            ChangeStreamIterable<Document> changeStream = collection.watch(pipeline)
                .fullDocument(FullDocument.UPDATE_LOOKUP);
            // variables referenced in a lambda must be final; final array gives us a mutable integer
            //final int[] numberOfEvents = {0};
            changeStream.forEach(event -> {
            
            //System.out.println("Received a change to the collection: " + event);
            Document doc = event.getFullDocument();
            collection1.insertOne(doc);

            /*
            if (++numberOfEvents[0] >= 2) {
                  System.exit(0);
            }
            */
            });
            
        }
    }
}