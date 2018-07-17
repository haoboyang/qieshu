package com.buyfull.openapiv1.implement;

import com.buyfull.openapiv1.BFException;

import java.text.ParseException;

public class BFObjFactory {
	public static BFApp_Implement createBFApp(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {
		BFApp_Implement app = new BFApp_Implement(context, uuid);

		BFApp_Implement cache_app = (BFApp_Implement) BFCacheManager.getSetCacheObj(context.accessKey(), app);
		if (cache_app == null)
			cache_app = app;
		if (!cache_app.isValid())
			cache_app.fetch();
		return cache_app;
	}
	
	public static BFScene_Implement createBFScene(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFScene_Implement scene = new BFScene_Implement(context, uuid);

		BFScene_Implement cache_scene = (BFScene_Implement) BFCacheManager.getSetCacheObj(context.accessKey(), scene);
		if( cache_scene == null )
			cache_scene = scene ;
		if (!cache_scene.isValid())
			cache_scene.fetch();
		return cache_scene;
	}
	
	public static BFInstallSite_Implement createBFInstallSite(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFInstallSite_Implement is = new BFInstallSite_Implement(context, uuid);

		BFInstallSite_Implement cache_is = (BFInstallSite_Implement) BFCacheManager.getSetCacheObj(context.accessKey(), is);
		if(cache_is == null )
			cache_is = is ;
		if (!cache_is.isValid())
			cache_is.fetch();
		return cache_is;
	}
}
