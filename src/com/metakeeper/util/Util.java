package com.metakeeper.util;

public interface Util {
    boolean writeMetaInfo(String ip_info, String path_info);
    String readMetaInfo(String path_info);
    boolean delMetaInfo(String ip_info);
    
    boolean existMetaInfo(String ip_info, String path_info);
}
