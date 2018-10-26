package com.buyfull.openapiv1.implement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.buyfull.openapiv1.*;
import com.buyfull.openapiv1.dao.CodeType;
import com.buyfull.openapiv1.dao.DeviceResult;
import com.buyfull.openapiv1.implement.util.PageParam;
import com.buyfull.openapiv1.implement.util.ResultCode;
import com.buyfull.openapiv1.implement.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.buyfull.openapiv1.dao.CodeType.A;
import static com.buyfull.openapiv1.dao.CodeType.B;
import static com.buyfull.openapiv1.implement.util.PageParam.getDynamicItem;
import static com.buyfull.openapiv1.implement.util.PageParam.getLocationePageResult;
import static com.buyfull.openapiv1.implement.util.ResultCode.*;
import static com.buyfull.openapiv1.implement.util.SignAndSend.sandGet;
import static com.buyfull.openapiv1.implement.util.SignAndSend.sandPost;
import static com.buyfull.openapiv1.implement.util.StringUtils.MAX_THIRD_DEVICETYPE;
import static com.buyfull.openapiv1.implement.util.StringUtils.MAX_THIRD_SN;
import static com.buyfull.openapiv1.implement.util.TimeUtile.getDateStr;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.*;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.OK;


class BFGroup_Implement extends BFObjBaseV1_Implement implements BFGroup {

    private String groupName ;

    private String  backup ;

    private Long boundCode ;

	public BFGroup_Implement(BFOpenAPI_Implement context, String uuid) throws BFException {
		super(context, uuid);
		// TODO Auto-generated constructor stub
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public String getBackup(){
		return this.backup;
	}

	public Long getBoundCode() {
		return this.boundCode;
	}

	/**
	 *
	 * 更新 BFAppObject
	 * @return
	 * @throws BFException
	 */
	@Override
	public boolean fetch() throws BFException, ParseException {
		isValid();

		try {
			String url = getContext().rootUrl() + GROUP_INFO + uuid ;
			String req = sandGet( url ,getContext().accessKey() ,getContext().secretKey() ,GET   ) ;
			JSONObject result = new JSONObject( req ) ;

			if( result.getString(CODE).equals(OK) ){

				this.groupName                = result.getJSONObject(DATA).getString("groupName") ;
				this.boundCode                 = result.getJSONObject(DATA).getLong("boundId") ;
				this.uuid          			= result.getJSONObject(DATA).getString("o_groupId") ;
				this.backup     			= result.getJSONObject(DATA).optString("backup");
				this.createTime         = result.getJSONObject(DATA).optString("createTime");
				this.lastUpdateTimeStamp 	=    result.getJSONObject(DATA).optLong("lastUpdateTime")   ;
			}
			else
				throw new BFException( BFException.ERRORS.FETCH_ERROR ,req ) ;
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString());
		}

		return true;
	}

	/**
	 *
	 * 更新 BFAppObject
	 * @return
	 * @throws BFException
	 */
	@Override
	public boolean update( String groupName ,String address , String province , String city , String brand   ) throws BFException, ParseException {
		isValid();

		if(  StringUtils.isNullOrEmpty( groupName  )||
				StringUtils.isNullOrEmpty( address  )  ||
				StringUtils.isNullOrEmpty( province  ) ||
				StringUtils.isNullOrEmpty( city  ) ||
				StringUtils.isNullOrEmpty( brand  )||
				lastUpdateTimeStamp== 0 ) {
			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, INVALID_GROUP_CONTEXT.toString());
		}
		try {

			JSONObject  groupEntity = new JSONObject() ;
			groupEntity.put("o_groupId", uuid);
			groupEntity.put("groupName", groupName );
			groupEntity.put("address", address );
			groupEntity.put("province", province );
			groupEntity.put("city", city );
			groupEntity.put("brand", brand );
			groupEntity.put("lastupdateTime", String.valueOf(lastUpdateTime() )) ;
            String url = getContext().rootUrl() + GROUP_UPDATE ;

			String req =  sandPost( url , getContext().accessKey() ,getContext().secretKey() ,groupEntity.toString() ,PUT) ;

			JSONObject reqResult = new JSONObject( req ) ;

			if( reqResult.getString( CODE ).equals( OK) ){
				//  通过 appFactory创建 BBFApp 对象
				return  fetch();
			}
			else{
				throw new BFException(BFException.ERRORS.GROUP_UPDATE_ERROR, req  );
			}
		}catch (JSONException jsonex){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString());
		}
	}


	//[{"deviceSN":"2018072913674157", "itemDec":"A001" ,"result":["playerId=sifghsidfgisdnfigh"] }]
	@Override
	public boolean setResultBySN(List<DeviceResult>deviceResults , BFApp app ) throws BFException {
       if( deviceResults == null || deviceResults.size() == 0  ){
		   throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR, ResultCode.DEVICE_ITEM_TAGS_NOT_NULL.toString());
	   }
	   if( app == null  ){
		   throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR,  ResultCode.BFAPP_NOT_NULL.toString() );
	   }

		com.alibaba.fastjson.JSONObject  results = new com.alibaba.fastjson.JSONObject() ;
		results.put( "group_uuid" , this.uuid  ) ;
		results.put( "appKey" , app.getAppKey() ) ;
		results.put( "results", com.alibaba.fastjson.JSONArray.parseArray( JSON.toJSONString( deviceResults )));
	   //调用 openapi
		String url = getContext().rootUrl() + GROUP_SETRESULT_SN ;

		String req =  sandPost( url , getContext().accessKey() ,getContext().secretKey() ,results.toString() ,POST) ;

		try {
			JSONObject reqResult  = new JSONObject( req );
			if( reqResult.getString( CODE ).equals( OK)  ){
				return true ;
			}else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR,  req );
			}
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}


    @Override
    public String loginDynamicDevice(BFApp app, String deviceSN, String deviceType, CodeType codeType) throws BFException {
        if( !isValid() ){
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
        }
	     if( checkDynamicDeviceParam(  deviceSN , deviceType , codeType ) ){
             com.alibaba.fastjson.JSONObject dynamicDevice = new com.alibaba.fastjson.JSONObject();
             dynamicDevice.put("sn",deviceSN);
             dynamicDevice.put("deviceType",deviceType);
             dynamicDevice.put("codeType",codeType.getType());
             dynamicDevice.put("groupId", uuid );
             dynamicDevice.put("appId", app.getAppKey());
             String url = getContext().rootUrl() + GROUP_DYNAMIC_CREATE ;

             String req =  sandPost( url , getContext().accessKey() ,getContext().secretKey() ,dynamicDevice.toString() ,POST) ;

             try {
                 JSONObject reqResult  = new JSONObject( req );
                 if( reqResult.getString( CODE ).equals( OK)  ){
                     return reqResult.getString(  DATA ) ;
                 }else{
                     throw new BFException(BFException.ERRORS.NETWORK_ERROR,  req );
                 }
             } catch (JSONException e) {
                 throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
             }
         }
        throw new BFException(BFException.ERRORS.UNKNOWN,  ResultCode.GROUP_DATA_ERROR.toString() );
    }


    public boolean checkDynamicDeviceParam(  String deviceSN, String deviceType, CodeType codeType ) throws BFException {
	    if( StringUtils.isNullOrEmpty( deviceSN  ) || StringUtils.isNullOrEmpty( deviceType  ) ||  codeType == null  ){
            throw new BFException(BFException.ERRORS.PARAM_NULL_ERROR,  ResultCode.GROUP_DYNAMIC_PARAM_NULL.toString() );
        }
        if( codeType.getType() != A.getType() ){
	    	if(codeType.getType() != B.getType() ){
				throw new BFException(BFException.ERRORS.DYNAMIC_PARAM_ERROR,  ResultCode.GROUP_DYNAMIC_CODETYPE_ERROR.toString() );
			}
        }
        return checkParamdeviceSN(  deviceSN , deviceType  );
    }

    public boolean checkParamdeviceSN( String deviceSN, String deviceType  ) throws BFException {

	    if( deviceSN.length() > MAX_THIRD_SN  ){
            throw new BFException(BFException.ERRORS.DYNAMIC_PARAM_ERROR,  ResultCode.GROUP_DYNAMIC_DEVICESN_ERROR.toString() );
        }
        if( deviceType.length() > MAX_THIRD_DEVICETYPE ){
            throw new BFException(BFException.ERRORS.DYNAMIC_PARAM_ERROR,  ResultCode.GROUP_DYNAMIC_DEVICESNTYPE_ERROR.toString() );
        }
        return true ;
    }

    @Override
    public BFPage<? extends BFDynamicDevice> getDynamicList(String itemDes, int pageNum, int limit) throws BFException, ParseException {
        if( !isValid() ){
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
        }
	    if(  !StringUtils.isNullOrEmpty( itemDes  )  ){
             if(itemDes.length() > MAX_THIRD_SN   ){
                 throw new BFException(BFException.ERRORS.DYNAMIC_PARAM_ERROR,  ResultCode.GROUP_DYNAMIC_DEVICESN_ERROR.toString() );
             }
        }
        StringBuilder   urlBuild  =  new StringBuilder( getContext().rootUrl() + GROUP_DYNAMIC_LIST + this.uuid )  ;
        urlBuild.append("?pageNum=" + pageNum);
        urlBuild.append("&limit=" + limit ) ;
        if(!StringUtils.isNullOrEmpty( itemDes  )  ){
            urlBuild.append("&itemName=" + itemDes ) ;
        }
        String req  = sandGet( urlBuild.toString() , getContext().accessKey() ,getContext().secretKey() ,GET  ) ;
        try {

            JSONObject reqResult = new JSONObject( req );
            if(  reqResult.getString(CODE).equals( OK  )  ){
                //create BFItem
                List<BFDynamicDevice> resultList = new ArrayList<>() ;
                JSONArray    reqList =  reqResult.getJSONObject(DATA).getJSONArray( ITEMS  );
                for ( int i = 0 ; i < reqList.length() ; i ++ ) {
                    resultList.add( BFObjFactory.createBFDynamicDevice((BFOpenAPI_Implement) getContext(), reqList.getString( i )   ) ) ;
                }
                return getDynamicItem( reqResult.getJSONObject(DATA)  ,  resultList  ) ;
            }else {
                throw new BFException(BFException.ERRORS.NETWORK_ERROR , req );
            }
        } catch ( JSONException e ) {
            throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
        }
    }

    @Override
    public boolean removeDynamicDevices(List<? extends BFDynamicDevice> dynamicDevices) throws BFException {
        if( !isValid() ){
            throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
        }
        if( dynamicDevices.isEmpty()|| dynamicDevices.size() > MAXLIMIT ){
            throw new BFException(BFException.ERRORS.INVALID_WORK, " 设备安装位置不能为空且最大批量数为"+MAXLIMIT);
        }
        List<String>deviceIds = new ArrayList<>() ;
        dynamicDevices.forEach(item->{
            deviceIds.add( item.uuid() );
        });
        JSONObject data = new JSONObject() ;
        try {
            data.put( "groupId",uuid ) ;
            data.put("itemNameUuid",deviceIds);
        } catch (JSONException e) {
            throw new BFException(BFException.ERRORS.INVALID_JSON, e.getMessage() );
        }
        String url = getContext().rootUrl() + GROUP_DYNAMIC_REMOVE ;
        String req = sandPost( url , getContext().accessKey() ,getContext().secretKey(), data.toString(), POST   ) ;
        try {
            JSONObject reqResult = new JSONObject( req ) ;
            //销毁对象
            if( reqResult.getString(CODE).equals( OK) ){
                return true ;
            }else{
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req );
            }
        }catch (JSONException jsonex){
            throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
        }
    }

    @Override
	public boolean isValid() {
		if (!super.isValid())
			return false;
		if (uuid == null || uuid == "" )
			return false;
		return true;
	}

	/**
	 * 更新绑定场景绑定 Id
	 * @return
	 * @throws BFException
	 */
	public boolean generateBoundCode() throws BFException, ParseException {
		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
		}
		JSONObject   reqResult= null ;
		try {

			String url  = getContext().rootUrl() + GROUP_GENERATE_BOUNDCODE + uuid + "/" + lastUpdateTimeStamp ;
			String req = sandGet( url ,getContext().accessKey() ,getContext().secretKey() ,PUT  ) ;
			             reqResult = new JSONObject( req  ) ;
			if( reqResult.getString(CODE).equals(OK) ){
				fetch();
				return true ;
			}else {
				throw new BFException(BFException.ERRORS.NETWORK_ERROR , req );

			}
		}catch (JSONException jsonex){

			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}

	/**
	 *
	 * 获取用户场景下面所有安装位置
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFItem> getItemList( String itemDes , int pageNum , int limit   ) throws BFException, ParseException {
		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
		}
		StringBuilder   urlBuild  =  new StringBuilder( getContext().rootUrl() + GROUP_DEVICE_LIST + this.uuid )  ;
						urlBuild.append("?pageNum=" + pageNum);
						urlBuild.append("&limit=" + limit ) ;
        if(!StringUtils.isNullOrEmpty( itemDes  )  ){
            urlBuild.append("&itemName=" + itemDes ) ;
        }
		String req  = sandGet( urlBuild.toString() , getContext().accessKey() ,getContext().secretKey() ,GET  ) ;
		try {

			JSONObject reqResult = new JSONObject( req );
			if(  reqResult.getString(CODE).equals( OK  )  ){
				//create BFItem
				List<BFItem> resultList = new ArrayList<>() ;
				JSONArray    reqList =  reqResult.getJSONObject(DATA).getJSONArray( ITEMS  );

				for ( int i = 0 ; i < reqList.length() ; i ++ ) {
					resultList.add( BFObjFactory.createBFItem((BFOpenAPI_Implement) getContext(), reqList.getString( i )   ) ) ;
				}
				return getLocationePageResult( reqResult.getJSONObject(DATA)  ,  resultList  ) ;
			}else {
				throw new BFException(BFException.ERRORS.NETWORK_ERROR , req );
			}
		} catch ( JSONException e ) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}

	/**
	 *
	 * 场景下面添加安装位置
 	 * @param itemDescrption 具体的安装位置的说明文字
	 * @param deviceSN 百蝠设备序列号，可为空
	 * @return
	 * @throws BFException
	 */
	public BFItem createItem(String itemDescrption, String deviceSN) throws BFException, ParseException {
		HashMap<String, String> itemDescrptionList = new HashMap<>() ;
							    itemDescrptionList.put(  itemDescrption , deviceSN  ) ;

		List<BFItem_Implement> ITEM = (List<BFItem_Implement>) createItemList(itemDescrptionList);
		if( !ITEM.isEmpty() ){
			return ITEM.get( 0 ) ;
		}
		return null;
	}

	/**
	 *
	 * 场景下批量添加安装位置
	 * @param itemDescrptionList 多个安装位置列表，key为百蝠设备序列号，value为具体的安装位置的说明文字，可为空
	 * @return
	 * @throws BFException
	 */
	public List<? extends BFItem> createItemList(HashMap<String, String> itemDescrptionList)
			throws BFException {

		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, INVALID_UUID_GROUP_UUID.toString());
		}
		if( itemDescrptionList.isEmpty()|| itemDescrptionList.size() > MAXLIMIT ){
			throw new BFException(BFException.ERRORS.INVALID_WORK, INVALID_ERROR_MAXLIMIT.toString()  );
		}

        String url  = getContext().rootUrl()  + ITEM_CREATEBEACH ;
		try {
			itemDescrptionList.forEach((k,v)->{
				if(  v == null )
					itemDescrptionList.put(k , "" );
			});
            String 	itemDescrptio = 	JSON.toJSONString(  itemDescrptionList  );
			JSONObject  data = new JSONObject() ;
			data.put( "group_uuid" ,uuid ) ;
			data.put( "itemDescrptio" , 	JSON.toJSONString(  itemDescrptionList  ) ) ;
			String req = sandPost(  url ,getContext().accessKey() ,getContext().secretKey() , data.toString() ,POST );
			JSONObject reqResult = new JSONObject( req ) ;

			if( reqResult.getString(CODE ).equals(  OK )  ){

				List<BFItem_Implement> ITEM_implements = new ArrayList<>() ;
				JSONArray  array = reqResult.getJSONArray(DATA) ;
				for( int i  = 0 ; i < array.length() ; i ++ ){

					ITEM_implements.add(  BFObjFactory.createBFItem((BFOpenAPI_Implement) getContext(), array.getString( i )   )  ) ;
				}
				return ITEM_implements ;

			}else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR , req  );
			}
		}catch ( JSONException jsonEx ){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}

	/**
	 *
	 * 移除场景下面一个安装位置
	 * @param is 安装位置
	 * @return
	 * @throws BFException
	 */
	public boolean removeItem(BFItem is) throws BFException {
    List<BFItem>list = new ArrayList<>() ;
        list.add( is ) ;
		return removeItems(  list );
	}

	/**
	 *
	 * 移除场景下面多个安装位置
	 * @param islist
	 * @return
	 * @throws BFException
	 */
	public boolean removeItems(List<? extends BFItem> islist) throws BFException {
		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
		}
		if( islist.isEmpty()|| islist.size() > MAXLIMIT ){
			throw new BFException(BFException.ERRORS.INVALID_WORK, " 设备安装位置不能为空且最大批量数为"+MAXLIMIT);
		}
		List<String>deviceIds = new ArrayList<>() ;
        islist.forEach(item->{
            deviceIds.add( item.uuid() );
        });
        JSONObject data = new JSONObject() ;
        try {
            data.put( "groupId",uuid ) ;
            data.put("itemNameUuid",deviceIds);
        } catch (JSONException e) {
            throw new BFException(BFException.ERRORS.INVALID_JSON, e.getMessage() );
        }
        String url = getContext().rootUrl() + GROUP_REMOVE_INSRALL ;
        String req = sandPost( url , getContext().accessKey() ,getContext().secretKey(), data.toString(), POST   ) ;
        try {
            JSONObject reqResult = new JSONObject( req ) ;
            //销毁对象
            if( reqResult.getString(CODE).equals( OK) ){
				islist.forEach(item->{
				});

                return true ;
            }else{
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, req );

            }
        }catch (JSONException jsonex){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );

        }
	}

	/**
	 *
	 * 获取所有被授权 appKey 列表
	 * @return
	 * @throws BFException
	 */
	public BFPage<String> getAuthorizedAppKeys(int pageNum , int limit ) throws BFException {

		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request id can't be blank");
		}
		if( limit > MAXLIMIT){
			throw new BFException(BFException.ERRORS.INVALID_WORK, "获取授权列表最大数为"+MAXLIMIT);
		}
		StringBuilder   urlBuild  =  new StringBuilder( getContext().rootUrl() + GROUP_ENPOWER_APP_LIST)  ;
						urlBuild.append("?pageNum=" + pageNum);
						urlBuild.append("&limit=" + limit ) ;
						urlBuild.append("&groupUuid=" + uuid ) ;
		String req  = sandGet( urlBuild.toString() , getContext().accessKey() ,getContext().secretKey() ,GET  ) ;
		try {
			JSONObject reqResult = new JSONObject( req );
			if(  reqResult.getString(CODE).equals( OK  )  ) {
				JSONArray  jsonArray = reqResult.getJSONObject(DATA).getJSONArray(ITEMS);
				List<String>list =new ArrayList<>() ;
				for( int i = 0 ; i < jsonArray.length() ; i ++  ){
					list.add(jsonArray.getString(i));
				}
				return PageParam.getgroupauthAppkeys( reqResult.getJSONObject(DATA) , list );
			}else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR , req  );
			}
		} catch ( JSONException e ) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}


	/**
	 *
	 * 添加授权,给第三方 appKey  授权场景下面所有安装位置
	 * @param appKey 第三方百蝠应用的APPKEY
	 * @param startDate 授权起始日期时间
	 * @param endDate 授权终止日期时间
	 * @return
	 * @throws BFException
	 */
	public boolean addAuthorizedApp(String appKey, String backupName , Date startDate, Date endDate) throws BFException {

		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
		}

		if(     StringUtils.isNullOrEmpty( appKey  )||
                StringUtils.isNullOrEmpty( backupName  )||
				appKey.length() != 32 ||
				startDate == null ||
				endDate == null ){
			String a = null ;

			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "addAuthorizedApp   of param groupName , address ,province ,city,brand can't be blank ");
		}
		try {

			JSONObject  groupEntity = new JSONObject() ;
			JSONArray groupId = new JSONArray(  ) ;
            groupId.put(uuid);
			groupEntity.put("appId", appKey);
			groupEntity.put("startData", getDateStr(startDate));
			groupEntity.put("endData", getDateStr(endDate));
			groupEntity.put("groupId", groupId);
			groupEntity.put("appBackupName", backupName);

			String url = getContext().rootUrl() + GROUP_ENPOWER_CREATE;

			String req =  sandPost( url , getContext().accessKey() ,getContext().secretKey() ,groupEntity.toString() ,POST) ;

			JSONObject reqResult = new JSONObject( req ) ;

			if( reqResult.getString( CODE ).equals( OK) ){
				//  通过 appFactory创建 BBFApp 对象
				return  true ;

			}
			else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, req  );
			}

		}catch (JSONException jsonex){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server addAuthorizedApp return bad json");
		}

	}

	/**
	 *
	 * 取消对三方 app 授权
	 * @param appKey 第三方百蝠应用的APPKEY
	 * @return
	 * @throws BFException
	 */
	public boolean removeAuthorizedApp( String appKey ) throws BFException {

		if( !isValid()  ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, "removeAuthorizedApp request uuid can't be blank");
		}
		if( StringUtils.isNullOrEmpty( appKey  ) || appKey.length() != 32 ){
			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "removeAuthorizedApp request appKey not exsit");
		}
		String url = getContext().rootUrl() + GROUP_ENPOWER_APP_DELETE + uuid + "/" + appKey ;

		String req =  sandGet( url , getContext().accessKey() ,getContext().secretKey()  ,DELETE ) ;

		try {
			JSONObject reqResult = new JSONObject( req );
			if( reqResult.getString( CODE ).equals( OK) ){
				//  通过 appFactory创建 BBFApp 对象
				return  true ;
			}
			else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, req  );
			}
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString()  );
		}

	}

}
