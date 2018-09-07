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
	
	public static BFGroup_Implement createBFGroup(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFGroup_Implement GroupName = new BFGroup_Implement(context, uuid);

		BFGroup_Implement cache_GroupName = (BFGroup_Implement) BFCacheManager.getSetCacheObj(context.accessKey(), GroupName);
		if( cache_GroupName == null )
			cache_GroupName = GroupName ;
		if (!cache_GroupName.isValid())
			cache_GroupName.fetch();
		return cache_GroupName;
	}
	
	public static BFItem_Implement createBFItem(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFItem_Implement is = new BFItem_Implement(context, uuid);

		BFItem_Implement cache_is = (BFItem_Implement) BFCacheManager.getSetCacheObj(context.accessKey(), is);
		if(cache_is == null )
			cache_is = is ;
		if (!cache_is.isValid())
			cache_is.fetch();
		return cache_is;
	}
}
