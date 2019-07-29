package com.exastax.notebook.commons;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.net.UnknownHostException;

/**
 * @author : Baran YILMAZ
 * @since : 29.07.2019
 **/

public class MongoSingletonClient {
    private MongoClient client;

    public MongoSingletonClient() throws UnknownHostException {

    }

    public MongoClient getInstance() throws UnknownHostException {
        if(client == null) return this.client = MongoClients.create("mongodb://35.242.253.97:27017/?serverSelectionTimeoutMS=5000&connectTimeoutMS=10000");
        else return client;
    }
}
