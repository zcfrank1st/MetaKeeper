package com.metakeeper.test;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class UnitTest {
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongos = new Mongo(Arrays.asList(new ServerAddress("127.0.0.1")));
        DB tmp = mongos.getDB("new");
        for (String str : tmp.getCollectionNames()){
            System.out.println(str);
        }
    }
}
