package com.metakeeper.metainfo;

import com.mongodb.BasicDBObject;


public class Meta extends BasicDBObject{
    private static final long serialVersionUID = 1L;

    public Meta(){
        super();
    }
    
    public Meta(String k, Object v){
        super(k, v);
    }
}
