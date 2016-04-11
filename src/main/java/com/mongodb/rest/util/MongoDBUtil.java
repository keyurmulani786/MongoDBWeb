package com.mongodb.rest.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBUtil {

	private static MongoDBUtil dbUtil;

	private static MongoClient mongoClient;

	private static DB db;

	private static final String dbHost = "localhost";
	private static final int dbPort = 27017;
	private static final String dbName = "testdb";
	private static final String dbUser = "";
	private static final String dbPassword = "";

	private MongoDBUtil(){};

	public static MongoDBUtil getInstance() {
		if (dbUtil == null) {
			dbUtil = new MongoDBUtil();
		}
		return dbUtil;
	}

	public DB getTestdb() {
		if (mongoClient == null) {
			try {
				mongoClient = new MongoClient(dbHost, dbPort);
			} catch (UnknownHostException e) {
				return null;
			}
		}
		if (db == null)
			db = mongoClient.getDB(dbName);
		if (!db.isAuthenticated()) {
			boolean auth = db.authenticate(dbUser, dbPassword.toCharArray());
		}
		return db;
	}
}
