#MetaKeeper

`Author:zcfrank1st`

###Description

A keeper which is based on mongoDB uses to store a cluster's meta info.

The meta info in the mongoDB is like:<br>

	{ "_id" : 
	ObjectId("5285e89c3004054f8749ef8a"),
	"10|1|2|5" : 
	"/tmp3", 
	"/tmp3" : 
	"10|1|2|5" }

just `<k,v>,<v,k>` format

###How To Use

You can use it to store ip, file path and so on.

example:
	
	 MetaKeeper mk = new MetaKeeper("127.0.0.1"); 
	 mk.writeMetaInfo("10.0.0.1", "/cluster/tmp");
	 mk.readMetaInfo("/cluster/tmp");
	 mk.delMetaInfo("10.0.0.1");
	 mk.existMetaInfo("10.0.0.1", "/cluster/tmp");
	 
###Lisence
Using MIT Lisence



