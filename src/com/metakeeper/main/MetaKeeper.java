package com.metakeeper.main;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metakeeper.metainfo.Meta;
import com.metakeeper.util.Util;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

//TODO 由于Mongo为线程池，故可修改为单例
public class MetaKeeper implements Util {
    private Logger log = LoggerFactory.getLogger(MetaKeeper.class);
    private DBCollection table = null;

    public MetaKeeper(String ...hostnames) throws UnknownHostException {
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
        String newIp_info = dotToVerticalBar(ip_info);
        try {
            Meta meta = new Meta();
            meta.append(newIp_info, path_info);
            meta.append(path_info, newIp_info);
            table.insert(meta);
        } catch (Exception e) {
            log.error("write metaInfo failed: " + e.getMessage());
            return false;
        }
        log.info("write metaInfo successfully");
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> readMetaInfo(String path_info) {
        List<String> ipList = new ArrayList<String>();
        for(String str : (List<String>)table.distinct(path_info)){
            String newIps = verticalBarToDot(str);
            ipList.add(newIps);
        }
        return ipList;
    }

    @Override
    public boolean delMetaInfo(String ip_info) {
        String newIp_info = dotToVerticalBar(ip_info);
        try {
            Meta ip_o = new Meta(newIp_info, new Meta(
                    "$exists", true));
            DBCursor ip_c = table.find(ip_o);
            while (ip_c.hasNext()) {
                DBObject dbo = ip_c.next();
                String path_info = (String) dbo.get(newIp_info);
                Meta path_o = new Meta(path_info, newIp_info);
                DBObject one = table.find(path_o).next();
                table.remove(one);
            }
            table.remove(ip_o);
        } catch (Exception e) {
            log.info("del metaInfo successfully");
            return false;
        }
        return true;
    }

    @Override
    public boolean existMetaInfo(String ip_info, String path_info) {
        String newIp_info = dotToVerticalBar(ip_info);
        Meta o = new Meta(newIp_info,path_info);
        Meta o1 = new Meta(path_info,newIp_info);
        if (table.find(o).count() != 0 && table.find(o1).count() != 0){
            return true;
        }else
            return false;
        }
    
    
    // inner Class Utils
    private String dotToVerticalBar(String str){
        return str.replaceAll("\\.", "|");
    }
    
    private String verticalBarToDot(String str){
        return str.replaceAll("\\|", ".");
    }
}
