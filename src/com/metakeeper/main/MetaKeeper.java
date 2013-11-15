package com.metakeeper.main;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.metakeeper.metainfo.Meta;
import com.metakeeper.util.Util;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;


//TODO 由于Mongo为线程池，故可修改为单例
public class MetaKeeper implements Util {
    private DBCollection table = null;

    public MetaKeeper(List<String> hostnames) throws UnknownHostException {
        List<ServerAddress> hl = new ArrayList<ServerAddress>();
        for (String str : hostnames) {
            hl.add(new ServerAddress(str));
        }
        Mongo mogos = new Mongo(hl);
        DB metaBase = mogos.getDB("MetaBase");
        this.table = metaBase.createCollection("meta", null);
    }

    @Override
    public boolean writeMetaInfo(String ip_info, String path_info) {
        try {
            Meta meta = new Meta();
            meta.append(ip_info, path_info);
            table.insert(meta);
        } catch (Exception e) {
            //TODO add log
            return false;
        }
        return true;
    }

    @Override
    public String readMetaInfo(String path_info) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public boolean delMetaInfo(String ip_info) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean existMetaInfo(String ip_info, String path_info) {
        // TODO Auto-generated method stub
        return false;
    }

}
