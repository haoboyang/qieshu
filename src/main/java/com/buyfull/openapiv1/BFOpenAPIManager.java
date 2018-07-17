package com.buyfull.openapiv1;

import java.util.HashMap;

import com.buyfull.openapiv1.implement.BFOpenAPI_Implement;

import okhttp3.OkHttpClient;

public class BFOpenAPIManager {
	public static final String ROOT_URL = "https://openapi.buyfull.cc/yhb";

	static HashMap<String, BFOpenAPI_Implement> instanceMap = new HashMap<String, BFOpenAPI_Implement>();
	/**
	 * 创建一个BFOpenAPI的实例，如果已经存在，会返回缓存的实例
	 * @param accessKey 从百蝠帐号中获得
	 * @param secretKey 从百蝠帐号中获得
	 * @return BFOpenAPI的实例
	 */
	public static  BFOpenAPI createBFOpenAPInstance(String accessKey, String secretKey){
		synchronized(instanceMap){
			if (accessKey == null || accessKey == "" || secretKey == null || secretKey == "")
				return null;
			
			String instanceKey = accessKey+secretKey;
			BFOpenAPI_Implement instance = instanceMap.get(instanceKey);
			if (instance == null){
				instance = new BFOpenAPI_Implement(accessKey, secretKey);
				instanceMap.put(instanceKey, instance);
			}
			return instance;
		}
	}

	/**
	 * 销毁一个BFOpenAPI的实例，并释放所有缓存的对象
	 * @param accessKey
	 * @param secretKey
	 */
	public static synchronized void destoryBFOpenAPInstance(String accessKey, String secretKey){
		synchronized(instanceMap){
			if (accessKey == null || accessKey == "" || secretKey == null || secretKey == "")
				return ;
			
			String instanceKey = accessKey+secretKey;
			BFOpenAPI_Implement instance = instanceMap.get(instanceKey);
			if (instance != null){
				instanceMap.remove(instanceKey);
				instance.destory();
			}
		}
	}
}
