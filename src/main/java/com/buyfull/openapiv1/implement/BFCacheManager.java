package com.buyfull.openapiv1.implement;

import java.util.concurrent.ConcurrentHashMap;

public class BFCacheManager {
	static ConcurrentHashMap<String, ConcurrentHashMap<String, BFObjBaseV1_Implement> > objCaches = new ConcurrentHashMap<String, ConcurrentHashMap<String, BFObjBaseV1_Implement> >();
	
	public static BFObjBaseV1_Implement getSetCacheObj(String accessKey, BFObjBaseV1_Implement obj){
		ConcurrentHashMap<String, BFObjBaseV1_Implement> accessKeyCaches = objCaches.putIfAbsent(accessKey, new ConcurrentHashMap<String, BFObjBaseV1_Implement>());
		if(accessKeyCaches == null)
			accessKeyCaches = objCaches.get( accessKey ) ;
		return accessKeyCaches.putIfAbsent(obj.getCacheKey(), obj);
	}
	
	public static void deleteCacheObj(String accessKey, String cacheKey){
		ConcurrentHashMap<String, BFObjBaseV1_Implement> accessKeyCaches = objCaches.putIfAbsent(accessKey, new ConcurrentHashMap<String, BFObjBaseV1_Implement>());
		accessKeyCaches.remove(cacheKey);
	}

}
