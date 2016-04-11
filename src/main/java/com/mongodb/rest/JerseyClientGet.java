package com.mongodb.rest;

import com.sun.jersey.api.client.GenericType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;

import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.rest.vo.User;

import com.sun.jersey.api.client.Client;

public class JerseyClientGet {

    public static void main(String[] args) {
        // System.out.println("===Create=====");
        // createUser();
        System.out.println("===Insert=====");
        insertUser();
        System.out.println("===Display=====");
        displayData();
        System.out.println("===Get Records =====");
        getAllRecords();
    }

    public static void insertUser() {

        try {
            String jsonLine = "		{"
                    + " \"id\":\"1630215c-2608-44b9-aad4-9d56d8aafd4c\", "
                    + " \"firstName\":\"Dorris\", "
                    + " \"lastName\":\"Keeling\", "
                    + " \"email\":\"Darby_Leffler68@gmail.com\", "
                    + " \"address\":{ "
                    + " \"street\":\"193 Talon Valley\", "
                    + " \"city\":\"South Tate furt\", "
                    + " \"zip\":\"47069\", "
                    + " \"state\":\"IA\", "
                    + " \"country\":\"US\" "
                    + " },"
                    + " \"dateCreated\":\"2016-03-15T07:02:40.896Z\", "
                    + " \"company\":{ "
                    + " \"name\":\"Denesik Group\", "
                    + " \"website\":\"http://jodie.org\" "
                    + " }, "
                    + " \"profilePic\":\"http://lorempixel.com/640/480/people\" "
                    + " }";

            JSONObject jsonObject = new JSONObject(jsonLine);
            System.out.println(jsonObject);

            // Step2: Now pass JSON File Data to REST Service
            try {
                URL url = new URL("http://localhost:8081/MongoDBWeb/rest/user/insert");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (in.readLine() != null) {
                }
                System.out.println("\n REST Service Invoked Successfully..");
                in.close();
            } catch (Exception e) {
                
                System.out.println(e);
                e.printStackTrace();
            }

            // br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayData() {
        MongoClient mongo;
        try {
            mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("testdb");
            DBCollection table = db.getCollection("user");
            DBCursor cursorDocJSON = table.find();
            while (cursorDocJSON.hasNext()) {
                System.out.println(cursorDocJSON.next());
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void getAllRecords() {
        try {

            List<User> list1 = Client.create().resource("http://localhost:8081/MongoDBWeb/rest/user/getRecordsXml")
                    .get(new GenericType<List<User>>() {
                    });

            System.out.println("===List===>" + list1);

        } catch (Exception e) {
            System.out.println("\nError while calling REST Service");
            System.out.println(e);
            e.printStackTrace();
        }

    }
}
