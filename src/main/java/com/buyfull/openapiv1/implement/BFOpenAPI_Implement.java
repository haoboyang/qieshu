package com.buyfull.openapiv1.implement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.buyfull.openapiv1.*;
import com.buyfull.openapiv1.implement.util.PageParam;
import com.buyfull.openapiv1.implement.util.ResultCode;
import com.buyfull.openapiv1.implement.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.buyfull.openapiv1.implement.BFOpenAPIManager.createBFOpenAPInstance;
import static com.buyfull.openapiv1.implement.util.ResultCode.GROUP_DATA_ERROR;
import static com.buyfull.openapiv1.implement.util.SignAndSend.sandPost;
import static com.buyfull.openapiv1.implement.util.SignAndSend.sandGet;
import static com.buyfull.openapiv1.implement.util.StringUtils.checkDeviceSN;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.*;

class BFOpenAPI_Implement implements BFOpenAPI{
	String accessKey;
	String secretKey;
    String rootUrl;

	public BFOpenAPI_Implement(String accessKey, String secretKey,String rootUrl){
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.rootUrl   = rootUrl ;
	}

	//////////////////////////////////////////////////////////////////

	//public interface methods

	public String accessKey() {
		return accessKey;
	}

	public String secretKey() {
		return secretKey;
	}

	@Override
	public String rootUrl() {
		return rootUrl;
	}

	@Override
	public BFGroup getGroup( String groupId  ) throws ParseException, BFException {
		return BFObjFactory.createBFGroup( this, groupId   );
	}

	/**
	 *
	 * 获取用户 app 列表default pagNum =1, limit =20
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFApp> getAppList(int pageNum, int limit) throws BFException, ParseException {
		StringBuilder  urlBuild = new StringBuilder( rootUrl + APP_LIST);
		if( pageNum != 0 && limit != 0){
			urlBuild.append("?pageNum=" + pageNum);
			urlBuild.append("&limit=" + limit ) ;
		}

		//通过网关转发请求数据
		String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;

        try {
            final JSONObject data = new JSONObject(result) ;
		    if(!data.get(CODE).equals(OK)){
                throw new BFException(BFException.ERRORS.NETWORK_ERROR,  data.getString(MESSAGE));
            }
		    JSONArray items =  data.getJSONObject(DATA).getJSONArray(ITEMS) ;
			//List<BFApp_Implement> list = JsonMapper.jsonToObject( item , new TypeReference<List<BFApp_Implement>>() {});
			List<BFApp> bf_appList = new ArrayList<BFApp>() ;


			//BFPage_Implement pageData = new BFPage_Implement( data.get()     ) ;
			for( int i = 0 ; i < items.length() ; i ++  ){
				bf_appList.add(  BFObjFactory. createBFApp((BFOpenAPI_Implement) createBFOpenAPInstance ( accessKey ,secretKey ), items.getJSONObject(i).getString("appKey")  )   ) ;
			}
			return PageParam.getAppPageResult( data.getJSONObject(DATA) , bf_appList   );
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.INVALID_JSON, "server return bad json");
		}
	}

	@Override
	public BFPage<String> getAuthAppKeys(int pageNum, int limit) throws BFException {
		StringBuilder  urlBuild = new StringBuilder( rootUrl + GROUP_ENPOWER_APP_LISTVO );
		urlBuild.append("?pageNum=" + pageNum);
		urlBuild.append("&limit=" + limit ) ;
		String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;
		try {
			com.alibaba.fastjson.JSONObject req   = com.alibaba.fastjson.JSONObject.parseObject( result )  ;
			if( !req.getString(CODE).equals(OK) ) {
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
			}
			else{
				com.alibaba.fastjson.JSONArray items =  req.getJSONObject(DATA).getJSONArray( ITEMS ) ;
				String js = com.alibaba.fastjson.JSONObject.toJSONString( items, SerializerFeature.WriteClassName  )  ;
				List<com.alibaba.fastjson.JSONObject>list =  com.alibaba.fastjson.JSONObject.parseArray( js ,  com.alibaba.fastjson.JSONObject.class  ) ;
				return PageParam.getAuthAppListVo( req.getJSONObject(DATA) , list   );
			}
		}catch (JSONException   jsonex){
			throw new BFException(BFException.ERRORS.INVALID_JSON, "server groupList return bad json");
		}
	}

    @Override
    public BFPage<String> getAuthAppGroupList(int pageNum, int limit, String appKey) throws BFException {
		if( limit > MAXLIMIT )
			throw new BFException(BFException.ERRORS.INVALID_WORK, "获取列表最大数为"+MAXLIMIT);
		if( StringUtils.isNullOrEmpty( appKey ) || appKey.trim().length()!=32  )
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request appKey can't be blank");
        StringBuilder   urlBuild = new StringBuilder( rootUrl + GROUP_ENPOWER_APP_groupList);
						urlBuild.append("?pageNum=" + pageNum);
						urlBuild.append("&limit=" + limit ) ;
						urlBuild.append("&appKey=" + appKey ) ;
        String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;
        try {
            com.alibaba.fastjson.JSONObject req   = com.alibaba.fastjson.JSONObject.parseObject( result )  ;
            if( !req.getString(CODE).equals(OK) ) {
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
            }
            else{
                com.alibaba.fastjson.JSONArray items =  req.getJSONObject(DATA).getJSONArray( ITEMS ) ;
                String js = com.alibaba.fastjson.JSONObject.toJSONString( items, SerializerFeature.WriteClassName  )  ;
                List<com.alibaba.fastjson.JSONObject>list =  com.alibaba.fastjson.JSONObject.parseArray( js ,  com.alibaba.fastjson.JSONObject.class  ) ;
                return PageParam.getAuthAppListVo( req.getJSONObject(DATA) , list   );
            }
        }catch (JSONException   jsonex){
            throw new BFException(BFException.ERRORS. HTTP_ERROR, "server groupList return bad json");
        }
    }


    /**
	 *
	 * 获取用户自由场景列表,不提供查询功能
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200,default=20
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFGroup> getGroupList(int pageNum, int limit) throws BFException, ParseException {
		return findGroupsByKeyword(null , null ,pageNum ,limit);
	}

	/**
	 *
	 *
	 * @param groupName 场景名称
	 * @param backup 根据品牌 ， 地址
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFGroup> findGroupsByKeyword(String groupName, String backup, int pageNum,
                                                         int limit) throws BFException, ParseException {
		StringBuilder  urlBuild = new StringBuilder( rootUrl + GROUP_LIST);
			urlBuild.append("?pageNum=" + pageNum);
			urlBuild.append("&limit=" + limit ) ;

			if(  !StringUtils.isNullOrEmpty( groupName  ) ){
                urlBuild.append("&groupName=" + groupName ) ;
            }

            if(  !StringUtils.isNullOrEmpty( backup ) ){
                urlBuild.append("&backup=" +  backup) ;
           }
		String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;
		try {
			JSONObject  req   = new JSONObject( result ) ;
			if( !req.getString(CODE).equals(OK) ) {
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
            }
			else{
                JSONArray items =  req.getJSONObject(DATA).getJSONArray( ITEMS ) ;
                List<BFGroup>bf_secelist =   new ArrayList<>();
                for( int i = 0 ; i < items.length() ; i ++   ){
                    bf_secelist.add(  BFObjFactory.createBFGroup((BFOpenAPI_Implement) createBFOpenAPInstance( this.accessKey , this.secretKey  ),items.getJSONObject(i).getString("o_groupId")   )  ) ;
                }
                return PageParam.getgroupPageResult( req.getJSONObject(DATA) , bf_secelist   );
            }
		}catch (JSONException   jsonex){
            throw new BFException(BFException.ERRORS.HTTP_ERROR, "server groupList return bad json");
		}
	}

	/**
	 *
	 * 用户创建场景
	 * @param groupName 场景名称
	 * @param address 地址
	 * @param province 省份
	 * @param city 城市
	 * @param brand 品牌
	 * @return
	 * @throws BFException
	 */
	public BFGroup createGroup(String groupName, String address, String province, String city, String brand)
            throws BFException, ParseException {
	    if(  StringUtils.isNullOrEmpty( groupName  )||
             StringUtils.isNullOrEmpty( address  )  ||
             StringUtils.isNullOrEmpty( province  ) ||
             StringUtils.isNullOrEmpty( city  ) ||
             StringUtils.isNullOrEmpty( brand  ) ){
            throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "create group of param groupName , address ,province ,city,brand can't be blank ");
        }
        try {

            JSONObject  groupEntity = new JSONObject() ;
            groupEntity.put("groupName", groupName);
            groupEntity.put("address", address);
            groupEntity.put("province", province);
            groupEntity.put("city", city);
            groupEntity.put("brand", brand);
            String url = rootUrl + GROUP_CREATE ;

            String req =  sandPost( url , accessKey ,secretKey ,groupEntity.toString() ,POST) ;

            JSONObject reqResult = new JSONObject( req ) ;

            if( reqResult.getString( CODE ).equals( OK) ){
                //  通过 appFactory创建 BBFApp 对象
                return  BFObjFactory. createBFGroup((BFOpenAPI_Implement) createBFOpenAPInstance ( accessKey ,secretKey ),  reqResult.getString(DATA)   )   ;

            }
            else{
                throw new BFException(BFException.ERRORS.GROUP_CREATE_ERROR, reqResult.getString(MESSAGE)  );
            }

        }catch (JSONException jsonex){
            throw new BFException(BFException.ERRORS.HTTP_ERROR, "server creategroup return bad json");
        }
	}

	/**
	 *
	 * 单个删除场景
	 * @param group 删除一个百蝠场景，它所包含的安装位置以及安装位置上的识别结果都会被删除
	 * @return
	 * @throws BFException
	 */
	public boolean removeGroup(BFGroup group) throws BFException {
        if(  !group.isValid() ){
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
        }
        try {
            String resUrl = rootUrl + GROUP_DELETE + group.uuid() + "/" + group.lastUpdateTime() ;
            String  req = sandGet( resUrl , accessKey ,secretKey , DELETE  ) ;
            JSONObject reqResult = new JSONObject( req  ) ;
            if( reqResult.getString(CODE).equals(OK)  ){
                return true ;
            }
            else
             throw new BFException(BFException.ERRORS.DELETE_ERROR,  reqResult.getString( DATA ) );
        }catch (JSONException jsonex){
        	jsonex.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server removeGroupsName return bad json");
        }
	}

	/**
	 *
	 * 用户批量删除场景 每次操作最大条数是20条
	 * @param groupList 删除多个百蝠场景，它们所包含的安装位置以及安装位置上的识别结果都会被删除
	 * @return
	 * @throws BFException
	 */
	public boolean removeGroups(List<? extends BFGroup> groupList) throws BFException {

        if( groupList.size() > MAXLIMIT ){
            throw new BFException(BFException.ERRORS.DELETE_ERROR,  "delete beach of group max limit is " + MAXLIMIT );
        }
        groupList.forEach(group->{
            try {

                if( removeGroup(group) ){
				}
            } catch (BFException e) {
                e.printStackTrace();

			}
        });
		return true;
	}

	/**
	 *
	 * 通过设备 SN 前八位匹配相应设备安装详情
	 * @param deviceSN 百蝠设备序列号，可以输入前8位来模糊匹配
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFItem> findItemByDeviceInfo(String deviceSN, String itemKeywords , int pageNum, int limit)
            throws BFException, ParseException {
		if(limit > MAXLIMIT)
			throw new BFException(BFException.ERRORS.DELETE_ERROR,  " findItemByDeviceInfo max limit is " + MAXLIMIT );
        StringBuilder   urlBuild = new StringBuilder( rootUrl + ITEM_LIST);
                        urlBuild.append("?pageNum=" + pageNum);
                        urlBuild.append("&limit=" + limit ) ;
                        if( !StringUtils.isNullOrEmpty(  deviceSN ) ){
							urlBuild.append("&deviceSN=" + deviceSN ) ;
						}
						if( !StringUtils.isNullOrEmpty( itemKeywords ) ){
							urlBuild.append("&itemKeywords=" + itemKeywords ) ;
						}


        String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;
        try {
            JSONObject  req   = new JSONObject( result ) ;
            if( !req.getString(CODE).equals(OK) ) {
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
            }
            else{
                JSONArray items          =   req.getJSONObject(DATA).getJSONArray(ITEMS);
                List<BFItem>bf_itemlist =   new ArrayList<>();
                for( int i = 0 ; i < items.length() ; i ++   ){
                    bf_itemlist.add(  BFObjFactory.createBFItem((BFOpenAPI_Implement) createBFOpenAPInstance( this.accessKey , this.secretKey  ),items.getString(i)  )  ) ;
                }
                return PageParam.getLocationePageResult( req.getJSONObject(DATA) , bf_itemlist   );
            }
        }catch (JSONException   jsonex){
            throw new BFException(BFException.ERRORS.INVALID_JSON, "server ITEM return bad json");
        }

	}

	/**
	 *
	 * 模糊搜索匹配安装位置
	 * @param itemKeywords 安装说明中的关键字
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFItem> findItemByitemDescrption(String itemKeywords,int pageNum ,int limit) throws BFException, ParseException {

		return findItemByDeviceInfo(null ,null , pageNum , limit );
	}

	/**
	 *
	 * 创建添加 app 程序
	 * @param appName 名称
	 * @param comment 备注
	 * @param bundleID iOS应用
	 * @param packageName Android应用
	 * @param wxAppID 微信小程序
	 * @return
	 * @throws BFException
	 */
	public BFApp createApp( String appName, String comment, String bundleID, String packageName, String wxAppID) throws BFException, ParseException {

            if( StringUtils.isNullOrEmpty( bundleID ) &&
				StringUtils.isNullOrEmpty( packageName ) &&
				StringUtils.isNullOrEmpty( wxAppID.trim() ) ){
                throw new BFException(BFException.ERRORS.APP_CREATE_ERROR, "app bundleID , packageName ,wxAppID not allow all be null ");
			}else{
                JSONObject  creeateApp = new JSONObject() ;
			    if( !StringUtils.isNullOrEmpty( comment  )  ){
					try {
						creeateApp.put( "comment" , comment ) ;
						if( !StringUtils.isNullOrEmpty( bundleID  )  ){
							creeateApp.put( "bundleId" , bundleID ) ;
						}

						if( !StringUtils.isNullOrEmpty( packageName  )  ){
							creeateApp.put( "packageName" , packageName ) ;
						}

						if( !StringUtils.isNullOrEmpty( wxAppID  )  ){
							creeateApp.put( "wxId" , wxAppID ) ;
						}
						creeateApp.put( "appName" , appName ) ;

						//post 创建 app


						String url = rootUrl + APP_CREATE ;

						String req =  sandPost( url , accessKey ,secretKey ,creeateApp.toString(),POST ) ;

						JSONObject reqResult = new JSONObject( req ) ;

						if( reqResult.getString( CODE ).equals( OK) ){
							//  通过 appFactory创建 BBFApp 对象
							return  BFObjFactory. createBFApp((BFOpenAPI_Implement) createBFOpenAPInstance ( accessKey ,secretKey ),  reqResult.getString(DATA)   )   ;

						}
						else{
							throw new BFException(BFException.ERRORS.APP_CREATE_ERROR, reqResult.getString(MESSAGE)  );
						}

					} catch (JSONException e) {
						e.printStackTrace();

					}
				}
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, "request server error");

			}

	}

	@Override
	public BFApp getApp( String appId ) throws ParseException, BFException {
		return BFObjFactory.createBFApp( this,appId    );
	}

	/**
	 *
	 *  用户单个删除 app
	 * @param app 百蝠应用
	 * @return
	 * @throws BFException
	 */
	public boolean removeApp(BFApp app) throws BFException {
      try {

		  if( StringUtils.isNullOrEmpty( app.getAppKey() ) || app.getAppKey().length()!=32)
			  throw new BFException(BFException.ERRORS.INVALID_UUID, "appKey 不存在");
		  String  url =  rootUrl + APP_DELETE + app.getAppKey() + "/" + String.valueOf(app.getLastUpdateTime()) ;
		  String  req = sandGet( url , accessKey ,secretKey , DELETE  ) ;
		  JSONObject reqResult = new JSONObject( req  ) ;
		  if( reqResult.getString(CODE).equals(OK)  ){
			  return true ;
		  }
		   else
		     throw new BFException(BFException.ERRORS.DELETE_ERROR,  reqResult.getString( MESSAGE ) );

	  }catch ( Exception ex  ){
      	throw new BFException(BFException.ERRORS.HTTP_ERROR, "request server error");
	  }
	}

	@Override
	public boolean removeAuthorizedApp(String appKey) throws BFException {
		try {
		if (StringUtils.isNullOrEmpty(appKey) || appKey.length() != 32)
			throw new BFException(BFException.ERRORS.INVALID_UUID, "appKey不存在");
		String url = rootUrl + GROUP_ENPOWER_APP_REMOVE + appKey;
		String req = sandGet(url, accessKey, secretKey, DELETE);
		JSONObject reqResult = null;

			reqResult = new JSONObject(req);
			if (reqResult.getString(CODE).equals(OK)) {
				// deleteCacheObj(   accessKey ,accessKey+secretKey); //删除缓存
				return true;
			} else
				throw new BFException(BFException.ERRORS.DELETE_ERROR, reqResult.getString(MESSAGE));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "request server error");
		}


	}

	@Override
	public BFPage<String> getAuthorizedItemList( int pageNum , int limit , String groupId ) throws BFException, ParseException {
	 try{
		 if(  StringUtils.isNullOrEmpty(  groupId ) ||  groupId.length()!= 32 ){
				throw new BFException(BFException.ERRORS.INVALID_UUID, "请求参数格式错误");
		 }

		 if(limit > MAXLIMIT)
			 throw new BFException(BFException.ERRORS.DELETE_ERROR,  " getAuthorizedItemList max limit is " + MAXLIMIT );
		 StringBuilder   urlBuild = new StringBuilder( rootUrl + GROUP_ENPOWER_item_LIST + groupId);
						 urlBuild.append("?pageNum=" + pageNum);
						 urlBuild.append("&limit=" + limit ) ;
		 String req = sandGet( urlBuild.toString(), accessKey, secretKey, GET);
		 com.alibaba.fastjson.JSONObject  reqResult = JSON.parseObject(  req ) ;
			if (reqResult.getString(CODE).equals(OK)) {
				List<String>itemList = com.alibaba.fastjson.JSONObject.parseArray( reqResult.getJSONObject(DATA).getString(ITEMS) ,String.class  ) ;
				return PageParam.getAuthorizedItemListStr( reqResult.getJSONObject(DATA) , itemList   );
			} else
				throw new BFException(BFException.ERRORS.DELETE_ERROR, reqResult.getString(MESSAGE));
	} catch (JSONException e) {
		e.printStackTrace();
		throw new BFException(BFException.ERRORS.HTTP_ERROR, "request server error" );
	}
	}

	@Override
	public BFItem getBFItem(String bFItemId) throws ParseException, BFException {
		return BFObjFactory.createBFItem( this , bFItemId   );
	}

	@Override
	public BFDynamicDevice getBFDynamicItem(String bFItemId) throws ParseException, BFException {
		return BFObjFactory.createBFDynamicDevice(   this ,bFItemId  );
	}

	@Override
	public String getItemId(String groupId, String deviceSN) throws BFException {
		if(  StringUtils.isNullOrEmpty(  groupId ) ||  groupId.length()!= 32 ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, GROUP_DATA_ERROR.toString());
		}
		checkDeviceSN( deviceSN  ) ;
        StringBuilder urlBuild = new StringBuilder( rootUrl ) ;
					  urlBuild.append(  ITEM_GET_UUID   );
					  urlBuild.append(  groupId ) ;
					  urlBuild.append("/");
					  urlBuild.append( deviceSN ) ;
		String req = sandGet( urlBuild.toString(), accessKey, secretKey, GET);
		try {
			JSONObject reqresult = new JSONObject( req ) ;
			if( reqresult.getString(CODE).equals(OK)  ){
				return reqresult.getString( DATA ) ;
			}else{
				throw new BFException(BFException.ERRORS.DELETE_ERROR, req);
			}
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}


}
