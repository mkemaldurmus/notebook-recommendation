package com.exastax.notebook.producer;

import com.exastax.notebook.commons.MongoSingletonClient;
import com.exastax.notebook.commons.UnsafeOkHtppClient;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bson.Document;

import java.io.IOException;
import java.net.UnknownHostException;

public class DataProducerMain {

    public static void main(String[] args) throws IOException {
        MongoSingletonClient mongoSingletonClient=new MongoSingletonClient();
        MongoClient mongoClient = mongoSingletonClient.getInstance();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("notebook-prediction");
        MongoCollection<Document> cityCollection = mongoDatabase.getCollection("cityCode");
        OkHttpClient client = UnsafeOkHtppClient.init();


        Request request = new Request.Builder()
                .url("https://www.sahibinden.com/ajax/location/loadCitiesByCountryId?vcIncluded=true&lang=tr&time=1564400063793_0.5326986045631339&address_country=1&_=1564400061110")
                .get()
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();


        DocumentContext bodyParse = JsonPath.parse(body);
        Integer size= bodyParse.read("$.data.1.length()");

        for (int i=0;i<size;i++){
            Object object = bodyParse.read("$.data.1[" + i + "]");
            Object read = JsonPath.read(object, "$.id");
            System.out.println();
        }

    }

}
