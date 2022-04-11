package com.example.mongodb.demo;

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

public class Watch711tx {
    public static void main( String[] args ) {

        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://caspar:c@bil.cxn3q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            
            MongoDatabase database = mongoClient.getDatabase("711");
            MongoCollection<Document> transaction = database.getCollection("transaction");
            MongoCollection<Document> tx = database.getCollection("tx");
            MongoCollection<Document> store = database.getCollection("store");

            ////監聽 711 transaction, 行為 insert 與 update
            List<Bson> pipeline = Arrays.asList( Aggregates.match(
                        Filters.in("operationType", Arrays.asList("insert", "update"))));
            ChangeStreamIterable<Document> changeStream = transaction.watch(pipeline)
                .fullDocument(FullDocument.UPDATE_LOOKUP);
            
            changeStream.forEach(event -> {
                // 找出對應的 document
                Document doc = event.getFullDocument();
                // 找出對應的 store_no
                Document store_doc = store.find(Filters.eq("store_no", doc.get("store_no"))).first();
                // 寫入寫入 store_name 到 tx
                doc.put("store_name", store_doc.get("store_name"));
                tx.insertOne(doc);
            });
        }
    }
}