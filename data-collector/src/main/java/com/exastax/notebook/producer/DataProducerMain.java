package com.exastax.notebook.producer;

import com.exastax.notebook.commons.MongoSingletonClient;
import com.exastax.notebook.commons.UnsafeOkHtppClient;
import com.exastax.notebook.model.CityModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bson.Document;
import org.json.JSONObject;
import java.io.IOException;

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
        JSONObject responseObject=new JSONObject(body);

        JSONObject requestObject=new JSONObject(request.body().toString());
        JSONObject object1=new JSONObject();
        object1.put("request",request);
        object1.put("response",responseObject);

        DocumentContext bodyParse = JsonPath.parse(body);
        Integer size= bodyParse.read("$.data.1.length()");
        Gson gson = new GsonBuilder().create();

        for (int i=0;i<size;i++){
            Object object = bodyParse.read("$.data.1[" + i + "]");
            String cityCode = JsonPath.read(object, "$.id");
            String cityName = JsonPath.read(object, "$.name");
            CityModel cityModel=new CityModel();
            cityModel.setCityCode(cityCode);
            cityModel.setCityName(cityName);
            JSONObject jsonObject=new JSONObject(gson.toJson(cityModel));

            Document document = (Document) JSON.parse(jsonObject.toString());
            cityCollection.insertOne(document);

        }
    }
}
