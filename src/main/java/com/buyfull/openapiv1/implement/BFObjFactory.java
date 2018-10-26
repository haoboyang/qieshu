package com.buyfull.openapiv1.implement;

import com.buyfull.openapiv1.BFException;
import com.buyfull.openapiv1.BFOpenAPI;

import java.text.ParseException;
import java.util.List;

class BFObjFactory {
	public static BFApp_Implement createBFApp(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFApp_Implement app = new BFApp_Implement(context, uuid);
						app.fetch();
		return app;
	}
	
	public static BFGroup_Implement createBFGroup(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFGroup_Implement group  = new BFGroup_Implement(context, uuid);
						  group.fetch();
		return group;
	}
	
	public static BFItem_Implement createBFItem(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {

		BFItem_Implement item = new BFItem_Implement(context, uuid);
		                 item.fetch();
		return item;
	}

	public static BFDynamicDevice_Implement createBFDynamicDevice(BFOpenAPI_Implement context, String uuid) throws BFException, ParseException {
		BFDynamicDevice_Implement   item = new BFDynamicDevice_Implement(  context ,uuid  ) ;
									item.fetch();
		return item;
	}

	public static BFPage_Implement createBFPage(  int currentPage, int pageSize, int totalNum, boolean hasMore, int totalPage, List<Object> data ) {
		return new BFPage_Implement( currentPage , pageSize , totalNum , hasMore , totalPage , data ) ;
	}
}
