package com.buyfull.openapiv1.implement;

import java.text.ParseException;
import java.util.*;
import com.buyfull.openapiv1.*;
import com.buyfull.util.PageParam;
import com.buyfull.util.SignAndSend;
import com.buyfull.util.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.buyfull.openapiv1.BFOpenAPIManager.createBFOpenAPInstance;
import static com.buyfull.util.SignAndSend.sandGet;
import static com.buyfull.util.TimeUtile.simpleDateFormat;
import static com.buyfull.util.UriPathUtil.*;


public class BFApp_Implement extends BFObjBaseV1_Implement  implements BFApp {

	String appKey;
	@JsonProperty("secretKey")
	String secKey;
	String appName;
	String appDescrption;
	@JsonProperty("bundleId")
	String bundleID;
	String packageName;
	@JsonProperty("wxId")
	String wxAppID;


	public BFApp_Implement(BFOpenAPI_Implement context, String uuid) throws BFException {
		super(context, uuid);
	}

	public BFApp_Implement( ) throws BFException {
		super();

	}



	@Override
	public void destory() {
		appKey = null;
		secKey = null;
		appName = null;
		appDescrption = null;
		bundleID = null;
		packageName = null;
		wxAppID = null;
		super.destory();
	}
	
	public String getAppName() {
		return appName;
	}


	public String getAppKey() {
		return appKey;
	}

	public String getAppDescrption() {
		return appDescrption;
	}


	public String getiOSBundleID() {
		return bundleID;
	}


	public String getAndroidPackageName() {
		return packageName;
	}


	public String getWXAppID() {
		return wxAppID;
	}


	public String getSecKey() {
		return secKey;
	}

	@Override
	public long getLastUpdateTime() {
		return    lastUpdateTimeStamp  ;
	}


	/**
	 *
	 * 更新 BFAppObject
	 * @return
	 * @throws BFException
	 */
	@Override
	public boolean fetch() throws BFException, ParseException {
		if (uuid() == null || uuid() == ""){
			return false;
		}
		try {
			String url = getContext().rootUrl() + APP_INFO + uuid ;
			String req = SignAndSend.sandGet( url ,getContext().accessKey() , getContext().secretKey(),GET   ) ;
			JSONObject result = new JSONObject( req ) ;

			if( result.getString(CODE).equals(OK) ){
				this.appName                = result.getJSONObject(DATA).getString("appName") ;
				this.appKey                 = result.getJSONObject(DATA).getString("appKey") ;
				this.uuid          			= result.getJSONObject(DATA).getString("appKey") ;
				this.secKey        			= result.getJSONObject(DATA).getString( "secretKey"   );
				this.bundleID     			= result.getJSONObject(DATA).optString("bundleId");
				this.packageName   			= result.getJSONObject(DATA).optString("packageName");
				this.wxAppID       			= result.getJSONObject(DATA).optString("wxId");
				this.appDescrption 			= result.getJSONObject(DATA).optString("comment");
                this.createTime         = result.getJSONObject(DATA).optString("createTime");
                this.lastUpdateTimeStamp 	=  simpleDateFormat.parse( result.getJSONObject(DATA).optString("lastUpdateTime")).getTime()  ;
			}
			else
				throw new BFException( BFException.ERRORS.FETCH_ERROR ,result.getString(MESSAGE)  ) ;
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");

		}

		return true;
	}

	@Override
	public boolean isValid() {
		if (!super.isValid())
			return false;
		if (appKey == null || appKey == "" || secKey == null || secKey == "")
			return false;
		return true;
	}

	/**
	 *
	 * 更新 app密钥
	 * @return
	 * @throws BFException
	 */
	public boolean generateSecKey() throws BFException, ParseException {
		try {

			String url = getContext().rootUrl() + APP_UPDATE_SECKEY + uuid + "/" + this.getLastUpdateTime();
			String req = SignAndSend.sandGet( url ,getContext().accessKey() , getContext().secretKey() ,PUT   ) ;
			JSONObject result = new JSONObject( req ) ;
			if( result.getString(CODE).equals(OK) ){
				return fetch();

			}else
				throw new BFException( BFException.ERRORS.NETWORK_ERROR , req  ) ;

		}catch (JSONException ex){
			ex.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");
		}
	}



	/**
	 * 查询当前 app 被第三方授权的场景列表
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFGroup> getAuthorizedgroupList(int pageNum ,int limit , String groupName , String backup    ) throws BFException, ParseException {
		//BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();
		StringBuilder   urlBuild = new StringBuilder( getContext().rootUrl() + GROUP_ENPOWER_LIST);
						urlBuild.append("?pageNum=" + pageNum);
						urlBuild.append("&limit=" + limit ) ;

						if(!StringUtils.isNullOrEmpty( groupName)){
							urlBuild.append("&groupName=" + groupName ) ;
						}
						if(!StringUtils.isNullOrEmpty( backup)){
							urlBuild.append("&backup=" + backup ) ;
						}
						urlBuild.append("&appId=" + uuid ) ;

		String result = sandGet( urlBuild.toString().trim() ,  getContext().accessKey() , getContext().secretKey() , GET   ) ;

		try {
			JSONObject  req   = new JSONObject( result ) ;
			if( !req.getString(CODE).equals(OK) ) {
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, req.getString(MESSAGE));
			}
			else{
				JSONArray items          =   req.getJSONObject(DATA).getJSONArray(ITEMS);
				List<BFGroup_Implement>bf_secelist =   new ArrayList<>();
				for( int i = 0 ; i < items.length() ; i ++   ){
					bf_secelist.add(  BFObjFactory.createBFGroup((BFOpenAPI_Implement) createBFOpenAPInstance( getContext().accessKey() , getContext().secretKey() ),items.getJSONObject(i).getString("o_groupId")   )  ) ;
				}
				return PageParam.getgroupPageResult( req.getJSONObject(DATA) , bf_secelist   );
			}
		}catch (JSONException   jsonex) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server groupList return bad json");
		  }
		}

	/**
	 *
	 * app 下面某个场景下面的安装位置和识别结果(包括被授权的场景)
	 * @param  GroupName 自已创建的场景或被授权使用的场景
	 * @return
	 * @throws BFException
	 */
	public HashMap<BFItem, List<String>> getRecgonizeResults( BFGroup GroupName ) throws BFException, ParseException {
		if( !isValid()||StringUtils.isNullOrEmpty(GroupName.uuid()  ) || GroupName.uuid().length()!=32){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid and group_uuid can't be blank");
		}
         String url = getContext().rootUrl() + APP_TAG + uuid + "/" + GroupName.uuid() ;
		 String req = SignAndSend.sandGet( url ,getContext().accessKey() ,getContext().secretKey() , GET    ) ;
		 try {
			 JSONObject  reqResult = new JSONObject( req  ) ;
			 if(  reqResult.getString(CODE).equals(OK) ){
			 	JSONArray array =  reqResult.optJSONArray(  DATA  ) ;
				 HashMap<BFItem,List<String>> results = new HashMap<>() ;
				  for ( int i = 0 ; i < array.length()  ; i ++  ){//(List<String>)
					  results.put( BFObjFactory.createBFItem((BFOpenAPI_Implement) getContext(), array.getJSONObject(i).getString("item_uuid")),com.alibaba.fastjson.JSONArray.parseArray(array.getJSONObject(i).getString("tag") ,String.class ) ); ;
				  }
                return results;
			 }else{
				 throw new BFException(BFException.ERRORS.NETWORK_ERROR, reqResult.getString(MESSAGE) );

			 }
		 }catch (JSONException jsonEx){
			 throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");
		 }
	}

	/**
	 *
	 *
	 * @param  GroupName 自已创建的场景或被授权使用的场景(  )
	 * @param  results 此应用下针对一个场景每个安装位置设定的识别结果的集合，会在百蝠SDK中返回json字符串数组，例如["结果1","result 2"]
	 * @return
	 * @throws BFException
	 */

	public boolean setRecgonizeResults(BFGroup GroupName, HashMap<BFItem, List<String>> results) throws BFException {
		if( !isValid()|| StringUtils.isNullOrEmpty( GroupName.uuid() ) || GroupName.uuid().length()!= 32 ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid and group_uuid can't be blank");
		}
		if(  results.isEmpty() ){
			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "安装位置上面识别结果不能为空");
		}
		HashMap<String ,List<String>> data =new HashMap<>() ;
		for( Map.Entry<BFItem,List<String>> entry : results.entrySet() ){
			data.put(  entry.getKey().uuid(),entry.getValue() ) ;
		}
		JSONObject tags = new JSONObject(  data ) ;

		try {

				JSONObject  dataObj = new JSONObject() ;
				dataObj.put("groupId",GroupName.uuid()  );
				dataObj.put("appId",uuid ) ;
				dataObj.put( "itemName_tag",tags.toString()  );
				String url = getContext().rootUrl() + APP_RESULTS;
				String req = SignAndSend.sandPost(   url , getContext().accessKey() ,getContext().secretKey() ,dataObj.toString() ,POST ) ;
				JSONObject  reqResult = new JSONObject( req ) ;
				if( reqResult.getString(CODE).equals(OK)  ){
					return true ;

				}else{
					throw new BFException(BFException.ERRORS.NETWORK_ERROR,reqResult.getString(MESSAGE)  );
				}
			}catch (JSONException  jsonEx){
				throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");
		}

	}

}
