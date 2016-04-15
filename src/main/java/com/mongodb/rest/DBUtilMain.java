package com.mongodb.rest;


import java.util.Set;

import com.mongodb.DB;
import com.mongodb.rest.util.MongoDBUtil;

public class DBUtilMain {
	public static void main(String args[]) {
		MongoDBUtil dbSingleton = MongoDBUtil.getInstance();
		DB db = dbSingleton.getTestdb();
		Set<String> tables = db.getCollectionNames();
		System.out.println("====================get table colums========================");			
		for(String coll : tables){
			System.out.println(coll);
		}
		System.out.println("============================================");
	}
}
