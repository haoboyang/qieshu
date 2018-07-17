package com.buyfull.openapiv1.implement;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.buyfull.openapiv1.*;


import com.buyfull.util.PageParam;
import com.buyfull.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static com.buyfull.openapiv1.BFOpenAPIManager.ROOT_URL;
import static com.buyfull.openapiv1.BFOpenAPIManager.createBFOpenAPInstance;
import static com.buyfull.util.SignAndSend.sandPost;
import static com.buyfull.util.SignAndSend.sandGet;
import static com.buyfull.util.UriPathUtil.*;

public class BFOpenAPI_Implement implements BFOpenAPI{
	String accessKey;
	String secretKey;

	public BFOpenAPI_Implement(String accessKey, String secretKey){
		this.accessKey = accessKey;
		this.secretKey = secretKey;

	}
	
	public void destory(){
		// TODO Auto-generated method stub
	}
	//////////////////////////////////////////////////////////////////
	
	//public interface methods

	public String accessKey() {
		return accessKey;
	}

	public String secretKey() {
		return secretKey;
	}

	/**
	 *
	 * 获取用户 app 列表default pagNum =1, limit =20
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFApp> getAppList(int pageNum, int limit) throws BFException, ParseException {
		StringBuilder  urlBuild = new StringBuilder( ROOT_URL + APP_LIST);
		if( pageNum != 0 && limit != 0){
			urlBuild.append("?pageNum=" + pageNum);
			urlBuild.append("&limit=" + limit ) ;
		}

//		Request request = new Request.Builder()
//				         .url(url)
//				         .cacheControl(CacheControl.FORCE_NETWORK) //only user network
//						 .build();
		// Call call = getHttpClient().newCall(request);
		//JSONObject data = JsonUtil.getDataObj(url, call);
		//通过网关转发请求数据
		String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;

        try {
            final JSONObject data = new JSONObject(result) ;
		    if(!data.get(CODE).equals(OK)){
                throw new BFException(BFException.ERRORS.NETWORK_ERROR,  data.getString(MESSAGE));
            }
		    JSONArray items =  data.getJSONObject(DATA).getJSONArray(ITEMS) ;
			//List<BFApp_Implement> list = JsonMapper.jsonToObject( item , new TypeReference<List<BFApp_Implement>>() {});
			List<BFApp_Implement> bf_appList = new ArrayList<BFApp_Implement>() ;


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
		StringBuilder  urlBuild = new StringBuilder( ROOT_URL + SENCE_ENPOWER_APP_LISTVO );
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
			throw new BFException(BFException.ERRORS.INVALID_JSON, "server senceList return bad json");
		}
	}

    @Override
    public BFPage<String> getAuthAppSenceList(int pageNum, int limit, String appKey) throws BFException {
		if( limit > MAXLIMIT )
			throw new BFException(BFException.ERRORS.INVALID_WORK, "获取列表最大数为"+MAXLIMIT);
		if( StringUtils.isNullOrEmpty( appKey ) || appKey.trim().length()!=32  )
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request appKey can't be blank");
        StringBuilder   urlBuild = new StringBuilder( ROOT_URL + SENCE_ENPOWER_APP_SENCELIST);
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
            throw new BFException(BFException.ERRORS. HTTP_ERROR, "server senceList return bad json");
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
	public BFPage<? extends BFScene> getSceneList(int pageNum, int limit) throws BFException, ParseException {
		return findScenesByKeyword(null , null ,null  ,pageNum ,limit);
	}

	/**
	 *
	 *
	 * @param name 场景名称
	 * @param brand 品牌
	 * @param address 地址，可包含城市省份
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFScene> findScenesByKeyword(String name, String brand, String address, int pageNum,
                                                         int limit) throws BFException, ParseException {
		StringBuilder  urlBuild = new StringBuilder( ROOT_URL + SENCE_LIST);
			urlBuild.append("?pageNum=" + pageNum);
			urlBuild.append("&limit=" + limit ) ;

			if(  !StringUtils.isNullOrEmpty( name  ) ){
                urlBuild.append("&senceName=" + name ) ;
            }

            if(  !StringUtils.isNullOrEmpty( brand ) ){
                 urlBuild.append("&brand=" +  brand) ;
            }

            if( !StringUtils.isNullOrEmpty(  address ) ){
                 urlBuild.append("&address=" + address ) ;
            }



		String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;
		try {
			JSONObject  req   = new JSONObject( result ) ;
			if( !req.getString(CODE).equals(OK) ) {
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
            }
			else{
                JSONArray items =  req.getJSONObject(DATA).getJSONArray( ITEMS ) ;
                List<BFScene_Implement>bf_secelist =   new ArrayList<>();
                for( int i = 0 ; i < items.length() ; i ++   ){
                    bf_secelist.add(  BFObjFactory.createBFScene((BFOpenAPI_Implement) createBFOpenAPInstance( this.accessKey , this.secretKey  ),items.getJSONObject(i).getString("o_senceId")   )  ) ;
                }
                return PageParam.getSencePageResult( req.getJSONObject(DATA) , bf_secelist   );
            }
		}catch (JSONException   jsonex){
            throw new BFException(BFException.ERRORS.HTTP_ERROR, "server senceList return bad json");
		}
	}

	/**
	 *
	 * 用户创建场景
	 * @param sceneName 场景名称
	 * @param address 地址
	 * @param province 省份
	 * @param city 城市
	 * @param brand 品牌
	 * @return
	 * @throws BFException
	 */
	public BFScene createScene(String sceneName, String address, String province, String city, String brand)
            throws BFException, ParseException {
	    if(  StringUtils.isNullOrEmpty( sceneName  )||
             StringUtils.isNullOrEmpty( address  )  ||
             StringUtils.isNullOrEmpty( province  ) ||
             StringUtils.isNullOrEmpty( city  ) ||
             StringUtils.isNullOrEmpty( brand  ) ){
            throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "create sence of param senceName , address ,province ,city,brand can't be blank ");
        }
        try {

            JSONObject  senceEntity = new JSONObject() ;
            senceEntity.put("senceName", sceneName);
            senceEntity.put("address", address);
            senceEntity.put("province", province);
            senceEntity.put("city", city);
            senceEntity.put("brand", brand);
            String url = ROOT_URL + SENCE_CREATE ;

            String req =  sandPost( url , accessKey ,secretKey ,senceEntity.toString() ,POST) ;

            JSONObject reqResult = new JSONObject( req ) ;

            if( reqResult.getString( CODE ).equals( OK) ){
                //  通过 appFactory创建 BBFApp 对象
                return  BFObjFactory. createBFScene((BFOpenAPI_Implement) createBFOpenAPInstance ( accessKey ,secretKey ),  reqResult.getString(DATA)   )   ;

            }
            else{
                throw new BFException(BFException.ERRORS.SENCE_CREATE_ERROR, reqResult.getString(MESSAGE)  );
            }

        }catch (JSONException jsonex){
            throw new BFException(BFException.ERRORS.HTTP_ERROR, "server createSence return bad json");
        }
	}

	/**
	 *
	 * 单个删除场景
	 * @param scence 删除一个百蝠场景，它所包含的安装位置以及安装位置上的识别结果都会被删除
	 * @return
	 * @throws BFException
	 */
	public boolean removeScene(BFScene scence) throws BFException {
        if(  !scence.isValid() ){
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
        }
        try {
            String resUrl = ROOT_URL + SENCE_DELETE + scence.uuid() + "/" + scence.lastUpdateTime() ;
            String  req = sandGet( resUrl , accessKey ,secretKey , DELETE  ) ;
            JSONObject reqResult = new JSONObject( req  ) ;
            if( reqResult.getString(CODE).equals(OK)  ){
                scence.destory();
                return true ;
            }
            else
             throw new BFException(BFException.ERRORS.DELETE_ERROR,  reqResult.getString( DATA ) );
        }catch (JSONException jsonex){
        	jsonex.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server removeScene return bad json");
        }
	}

	/**
	 *
	 * 用户批量删除场景 每次操作最大条数是20条
	 * @param sceneList 删除多个百蝠场景，它们所包含的安装位置以及安装位置上的识别结果都会被删除
	 * @return
	 * @throws BFException
	 */
	public boolean removeScenes(List<? extends BFScene> sceneList) throws BFException {

        if( sceneList.size() > MAXLIMIT ){
            throw new BFException(BFException.ERRORS.DELETE_ERROR,  "delete beach of sence max limit is " + MAXLIMIT );
        }
        sceneList.forEach(scence->{
            try {

                if( removeScene(scence) ){
					scence.destory();
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
	public BFPage<? extends BFInstallSite> findInstallSiteByDeviceInfo(String deviceSN, String installKeywords , int pageNum, int limit)
            throws BFException, ParseException {
		if(limit > MAXLIMIT)
			throw new BFException(BFException.ERRORS.DELETE_ERROR,  " findInstallSiteByDeviceInfo max limit is " + MAXLIMIT );
        StringBuilder   urlBuild = new StringBuilder( ROOT_URL + INSTALLSITE_LIST);
                        urlBuild.append("?pageNum=" + pageNum);
                        urlBuild.append("&limit=" + limit ) ;
                        if( !StringUtils.isNullOrEmpty(  deviceSN ) ){
							urlBuild.append("&deviceSN=" + deviceSN ) ;
						}
						if( !StringUtils.isNullOrEmpty( installKeywords ) ){
							urlBuild.append("&installKeywords=" + installKeywords ) ;
						}


        String result = sandGet( urlBuild.toString().trim() , this.accessKey , this.secretKey , GET   ) ;
        try {
            JSONObject  req   = new JSONObject( result ) ;
            if( !req.getString(CODE).equals(OK) ) {
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
            }
            else{
                JSONArray items          =   req.getJSONObject(DATA).getJSONArray(ITEMS);
                List<BFInstallSite_Implement>bf_locationlist =   new ArrayList<>();
                for( int i = 0 ; i < items.length() ; i ++   ){
                    bf_locationlist.add(  BFObjFactory.createBFInstallSite((BFOpenAPI_Implement) createBFOpenAPInstance( this.accessKey , this.secretKey  ),items.getString(i)  )  ) ;
                }
                return PageParam.getLocationePageResult( req.getJSONObject(DATA) , bf_locationlist   );
            }
        }catch (JSONException   jsonex){
            throw new BFException(BFException.ERRORS.INVALID_JSON, "server installSite return bad json");
        }

	}

	/**
	 *
	 * 模糊搜索匹配安装位置
	 * @param installKeywords 安装说明中的关键字
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFInstallSite> findInstallSiteByInstallDescrption(String installKeywords,int pageNum ,int limit) throws BFException, ParseException {

		return findInstallSiteByDeviceInfo(null ,null , pageNum , limit );
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


						String url = ROOT_URL + APP_CREATE ;

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

	/**
	 *
	 *  用户单个删除 app
	 * @param app 百蝠应用
	 * @return
	 * @throws BFException
	 */
	public boolean removeApp(BFApp app) throws BFException {
      try {

		  if( StringUtils.isNullOrEmpty( app.getAppKey().trim() ) || app.getAppKey().trim().length()!=32)
			  throw new BFException(BFException.ERRORS.INVALID_UUID, "appKey 不存在");
		  String  url =  ROOT_URL + APP_DELETE + app.getAppKey() + "/" + String.valueOf(app.getLastUpdateTime()) ;
		  String  req = sandGet( url , accessKey ,secretKey , DELETE  ) ;
		  JSONObject reqResult = new JSONObject( req  ) ;
		  if( reqResult.getString(CODE).equals(OK)  ){
              app.destory() ;
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
		if (StringUtils.isNullOrEmpty(appKey.trim()) || appKey.trim().length() != 32)
			throw new BFException(BFException.ERRORS.INVALID_UUID, "appKey不存在");
		String url = ROOT_URL + SENCE_ENPOWER_APP_REMOVE + appKey;
		String req = sandGet(url, accessKey, secretKey, DELETE);
		System.out.println("删除炒作结果 == " + req);
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
	public String getAuthorizedInstallSiteList( String senceId ) throws BFException {
	 try{
			if(  StringUtils.isNullOrEmpty(  senceId.trim() ) ||  senceId.trim().length()!= 32 ){
				throw new BFException(BFException.ERRORS.INVALID_UUID, "请求参数格式错误");
			}
			String url = ROOT_URL + SENCE_ENPOWER_INSTALL_LIST + senceId;
			String req = sandGet(url, accessKey, secretKey, GET);
			JSONObject reqResult = null;

			reqResult = new JSONObject(req);
			if (reqResult.getString(CODE).equals(OK)) {
				return reqResult.get(  DATA ).toString();
			} else
				throw new BFException(BFException.ERRORS.DELETE_ERROR, reqResult.getString(MESSAGE));
	} catch (JSONException e) {
		e.printStackTrace();
		throw new BFException(BFException.ERRORS.HTTP_ERROR, "request server error" );
	}
	}


}
