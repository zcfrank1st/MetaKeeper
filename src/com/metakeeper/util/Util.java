package com.metakeeper.util;

import java.util.List;

public interface Util {
    boolean writeMetaInfo(String ip_info, String path_info);
    List<String> readMetaInfo(String path_info);
    boolean delMetaInfo(String ip_info);
    
    boolean existMetaInfo(String ip_info, String path_info);
}
