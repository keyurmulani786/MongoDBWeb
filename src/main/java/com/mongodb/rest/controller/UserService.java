package com.mongodb.rest.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.DB;
import com.mongodb.rest.util.MongoDBUtil;
import com.mongodb.rest.vo.User;
import com.mongodb.util.JSON;
import com.mongodb.BasicDBObject;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
public class UserService {

    @GET
    @Path("/{msg}")
    public Response getMsg(@PathParam("msg") String msg) {

        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();

    }

    
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(InputStream incomingData) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("sb--->" + sb);
            MongoDBUtil dbSingleton = MongoDBUtil.getInstance();
            DB db = dbSingleton.getTestdb();
            DBCollection coll = db.getCollection("user");
            DBObject dbObject = (DBObject) JSON.parse(sb.toString());

            coll.insert(dbObject);

            DBCursor cursorDocJSON = coll.find();
            while (cursorDocJSON.hasNext()) {
                System.out.println(cursorDocJSON.next());
            }

        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
            e.printStackTrace();
        }
        System.out.println("Data Received: " + sb.toString());

        // return HTTP response 200 in case of success
        return Response.status(200).entity(sb.toString()).build();
    }

    @GET
    @Path("/getRecordsXml")
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getPersons() {
        System.out.println("enter...");
        MongoDBUtil dbSingleton = MongoDBUtil.getInstance();
        DB db = dbSingleton.getTestdb();
        DBCollection coll = db.getCollection("user");

        DBCursor cursor = coll.find().sort(new BasicDBObject("firstName", 1));
        List<User> list = new ArrayList<User>();
        while (cursor.hasNext()) {
            DBObject o = cursor.next();
            User user = new User();
            user.setFirstName((String) o.get("firstName"));
            user.setLastName((String) o.get("lastName"));
            user.setEmail((String) o.get("email"));
            user.setDateCreated((String) o.get("dateCreated"));
            user.setProfilePic((String) o.get("profilePic"));
            list.add(user);
        }
        System.out.println("list..." + list);
        for (User u : list) {
            System.out.println("F..." + u.getFirstName());
        }
        return list;
    }

    @GET
    @Path("/getRecords")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getRecords() {
        System.out.println("enter...");
        MongoDBUtil dbSingleton = MongoDBUtil.getInstance();
        DB db = dbSingleton.getTestdb();
        DBCollection coll = db.getCollection("user");

        DBCursor cursor = coll.find().sort(new BasicDBObject("firstName", 1));
        List<User> list = new ArrayList<User>();
        while (cursor.hasNext()) {
            DBObject o = cursor.next();
            User user = new User();
            System.out.println("");
            user.setFirstName((String) o.get("firstName"));
            user.setLastName((String) o.get("lastName"));
            user.setEmail((String) o.get("email"));
            user.setDateCreated((String) o.get("dateCreated"));
            user.setProfilePic((String) o.get("profilePic"));
            list.add(user);
        }
        System.out.println("list..." + list);
        for (User u : list) {
            System.out.println("F..." + u.getFirstName());
        }
        return list;
    }

}
