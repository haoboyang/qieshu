package com.buyfull.openapiv1.implement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.buyfull.openapiv1.BFException;
import com.buyfull.openapiv1.BFInstallSite;
import com.buyfull.openapiv1.BFPage;
import com.buyfull.openapiv1.BFScene;
import com.buyfull.util.PageParam;
import com.buyfull.util.SignAndSend;
import com.buyfull.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.buyfull.util.PageParam.getLocationePageResult;
import static com.buyfull.util.SignAndSend.sandPost;
import static com.buyfull.util.SignAndSend.sandGet;
import static com.buyfull.util.TimeUtile.getDateStr;
import static com.buyfull.util.UriPathUtil.*;
import static com.buyfull.util.UriPathUtil.DATA;
import static com.buyfull.util.UriPathUtil.MESSAGE;

public class BFScene_Implement extends BFObjBaseV1_Implement implements BFScene {
    private String senceName ;

    private String brand ;

    private String province ;

    private String city;

    private String address ;

    private Long boundCode ;

	public BFScene_Implement(BFOpenAPI_Implement context, String uuid) throws BFException {
		super(context, uuid);
		// TODO Auto-generated constructor stub
	}

	public String getSenceName() {
		return this.senceName;
	}

	public void setSenceName(String senceName) {

		this.senceName = senceName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address ;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand ;
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
			String url = getContext().rootUrl() + SENCE_INFO + uuid ;
			String req = SignAndSend.sandGet( url ,getContext().accessKey() ,getContext().secretKey() ,GET   ) ;
			JSONObject result = new JSONObject( req ) ;

			if( result.getString(CODE).equals(OK) ){

				this.senceName                = result.getJSONObject(DATA).getString("senceName") ;
				this.brand                 = result.getJSONObject(DATA).getString("brand") ;
				this.boundCode                 = result.getJSONObject(DATA).getLong("boundId") ;
				this.uuid          			= result.getJSONObject(DATA).getString("o_senceId") ;
				this.address        			= result.getJSONObject(DATA).getString( "address"   );
				this.province     			= result.getJSONObject(DATA).optString("province");
				this.city   			= result.getJSONObject(DATA).optString("city");
				this.createTime         = result.getJSONObject(DATA).optString("createTime");
				this.lastUpdateTimeStamp 	=    result.getJSONObject(DATA).optLong("lastUpdateTime")   ;
			}
			else
				throw new BFException( BFException.ERRORS.FETCH_ERROR ,result.getString(MESSAGE)  ) ;
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");
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
	public boolean update() throws BFException, ParseException {
		isValid();

		if(  StringUtils.isNullOrEmpty( senceName  )||
				StringUtils.isNullOrEmpty( address  )  ||
				StringUtils.isNullOrEmpty( province  ) ||
				StringUtils.isNullOrEmpty( city  ) ||
				StringUtils.isNullOrEmpty( brand  )||
				lastUpdateTimeStamp== 0 ) {
			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "update sence of param senceName , address ,province ,city,brand can't be blank ");
		}
		try {

			JSONObject  senceEntity = new JSONObject() ;
			senceEntity.put("o_senceId", uuid);
			senceEntity.put("senceName", getSenceName());
			senceEntity.put("address", getAddress());
			senceEntity.put("province", getProvince());
			senceEntity.put("city",getCity());
			senceEntity.put("brand", getBrand());
			senceEntity.put("lastupdateTime", String.valueOf(lastUpdateTime() )) ;
            String url = getContext().rootUrl() + SENCE_UPDATE ;

			String req =  sandPost( url , getContext().accessKey() ,getContext().secretKey() ,senceEntity.toString() ,PUT) ;

			JSONObject reqResult = new JSONObject( req ) ;

			if( reqResult.getString( CODE ).equals( OK) ){
				//  通过 appFactory创建 BBFApp 对象
				return  fetch();
			}
			else{
				throw new BFException(BFException.ERRORS.SENCE_UPDATE_ERROR, reqResult.getString(MESSAGE)  );
			}

		}catch (JSONException jsonex){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server createSence return bad json");
		}
	}

	@Override
	public void destory(){
		this.senceName     = null ;
		this.uuid          = null ;
		this.province      = null ;
		this.city          = null ;
		this.brand         = null ;
		this.address       = null ;
		this.boundCode     = null ;
		super.destory();

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

			String url  = getContext().rootUrl() + SENCE_GENERATE_BOUNDCODE + uuid + "/" + lastUpdateTimeStamp ;
			String req = SignAndSend.sandGet( url ,getContext().accessKey() ,getContext().secretKey() ,PUT  ) ;
			             reqResult = new JSONObject( req  ) ;
			if( reqResult.getString(CODE).equals(OK) ){
				fetch();
				return true ;
			}else {
				throw new BFException(BFException.ERRORS.NETWORK_ERROR ,reqResult.getString( MESSAGE )  );

			}
		}catch (JSONException jsonex){
			jsonex.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR ,"server getInstallSiteList return bad json"  );
		}
	}

	/**
	 *
	 * 获取用户场景下面所有安装位置
	 * @return
	 * @throws BFException
	 */
	public BFPage<? extends BFInstallSite> getInstallSiteList( int pageNum , int limit   ) throws BFException, ParseException {
		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
		}
		StringBuilder   urlBuild  =  new StringBuilder( getContext().rootUrl() + SENCE_DEVICE_LIST + this.uuid )  ;
						urlBuild.append("?pageNum=" + pageNum);
						urlBuild.append("&limit=" + limit ) ;
		String req  = SignAndSend.sandGet( urlBuild.toString() , getContext().accessKey() ,getContext().secretKey() ,GET  ) ;
		try {

			JSONObject reqResult = new JSONObject( req );
			if(  reqResult.getString(CODE).equals( OK  )  ){
				//create BFInstallSite
				List<BFInstallSite_Implement> resultList = new ArrayList<>() ;
				JSONArray    reqList =  reqResult.getJSONArray( DATA  );

				for ( int i = 0 ; i < reqList.length() ; i ++ ) {
					resultList.add( BFObjFactory.createBFInstallSite((BFOpenAPI_Implement) getContext(), reqList.getString( i )   ) ) ;
				}
				return getLocationePageResult( reqResult.getJSONObject(DATA)  ,  resultList  ) ;
			}
		} catch ( JSONException e ) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server getInstallSiteList return bad json");

		}

		return null;
	}

	/**
	 *
	 * 场景下面添加安装位置
 	 * @param installDescrption 具体的安装位置的说明文字
	 * @param deviceSN 百蝠设备序列号，可为空
	 * @return
	 * @throws BFException
	 */
	public BFInstallSite createInstallSite(String installDescrption, String deviceSN) throws BFException, ParseException {
		HashMap<String, String> installDescrptionList = new HashMap<>() ;
							    installDescrptionList.put(  installDescrption , deviceSN  ) ;

		List<BFInstallSite_Implement> InstallSite = (List<BFInstallSite_Implement>) createInstallSiteList(installDescrptionList);
		if( !InstallSite.isEmpty() ){
			return InstallSite.get( 0 ) ;
		}
		return null;
	}

	/**
	 *
	 * 场景下批量添加安装位置
	 * @param installDescrptionList 多个安装位置列表，key为百蝠设备序列号，value为具体的安装位置的说明文字，可为空
	 * @return
	 * @throws BFException
	 */
	public List<? extends BFInstallSite> createInstallSiteList(HashMap<String, String> installDescrptionList)
			throws BFException {

		if( !isValid() ){
			throw new BFException(BFException.ERRORS.INVALID_UUID, " request uuid can't be blank");
		}
		if( installDescrptionList.isEmpty()|| installDescrptionList.size() > MAXLIMIT ){
			throw new BFException(BFException.ERRORS.INVALID_WORK, " 设备安装位置不能为空且最大批量数为"+MAXLIMIT);
		}

        String url  = getContext().rootUrl()  + INSTALLSITE_CREATEBEACH ;
		try {
			installDescrptionList.forEach((k,v)->{
				if(  v == null )
					installDescrptionList.put(k , "" );
			});
            String 	installDescrptio = 	JSON.toJSONString(  installDescrptionList  );
			JSONObject  data = new JSONObject() ;
			data.put( "sence_uuid" ,uuid ) ;
			data.put( "installDescrptio" , 	JSON.toJSONString(  installDescrptionList  ) ) ;
			String req = SignAndSend.sandPost(  url ,getContext().accessKey() ,getContext().secretKey() , data.toString() ,POST );
			JSONObject reqResult = new JSONObject( req ) ;

			if( reqResult.getString(CODE ).equals(  OK )  ){

				List<BFInstallSite_Implement> installSite_implements = new ArrayList<>() ;
				JSONArray  array = reqResult.getJSONArray(DATA) ;
				for( int i  = 0 ; i < array.length() ; i ++ ){

					installSite_implements.add(  BFObjFactory.createBFInstallSite((BFOpenAPI_Implement) getContext(), array.getString( i )   )  ) ;
				}
				return installSite_implements ;

			}else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR ,reqResult.getString( MESSAGE )  );
			}
		}catch ( JSONException jsonEx ){
			throw new BFException(BFException.ERRORS.INVALID_JSON, "server getInstallSiteList return bad json");

		} catch (ParseException e) {
			e.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server createInstallSiteList return bad json");

		}
	}

	/**
	 *
	 * 移除场景下面一个安装位置
	 * @param is 安装位置
	 * @return
	 * @throws BFException
	 */
	public boolean removeInstallSite(BFInstallSite is) throws BFException {
    List<BFInstallSite>list = new ArrayList<>() ;
        list.add( is ) ;
		return removeInstallSites(  list );
	}

	/**
	 *
	 * 移除场景下面多个安装位置
	 * @param islist
	 * @return
	 * @throws BFException
	 */
	public boolean removeInstallSites(List<? extends BFInstallSite> islist) throws BFException {
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
            data.put( "senceId",uuid ) ;
            data.put("locationUuid",deviceIds);
        } catch (JSONException e) {
            throw new BFException(BFException.ERRORS.INVALID_JSON, e.getMessage() );
        }
        String url = getContext().rootUrl() + SENCE_REMOVE_INSRALL ;
        String req = SignAndSend.sandPost( url , getContext().accessKey() ,getContext().secretKey(), data.toString(), POST   ) ;
        try {
            JSONObject reqResult = new JSONObject( req ) ;
            //销毁对象
            if( reqResult.getString(CODE).equals( OK) ){
            	destory();
				islist.forEach(item->{
					item.destory();
				});

                return true ;
            }else{
                throw new BFException(BFException.ERRORS.NETWORK_ERROR, reqResult.getString( MESSAGE ) );

            }
        }catch (JSONException jsonex){
            throw new BFException(BFException.ERRORS.HTTP_ERROR, "server removeInstallSites return bad json");

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
		StringBuilder   urlBuild  =  new StringBuilder( getContext().rootUrl() + SENCE_ENPOWER_APP_LIST)  ;
						urlBuild.append("?pageNum=" + pageNum);
						urlBuild.append("&limit=" + limit ) ;
						urlBuild.append("&senceUuid=" + uuid ) ;
		String req  = SignAndSend.sandGet( urlBuild.toString() , getContext().accessKey() ,getContext().secretKey() ,GET  ) ;
		try {
			JSONObject reqResult = new JSONObject( req );
			if(  reqResult.getString(CODE).equals( OK  )  ) {
				JSONArray  jsonArray = reqResult.getJSONObject(DATA).getJSONArray(ITEMS);
				List<String>list =new ArrayList<>() ;
				for( int i = 0 ; i < jsonArray.length() ; i ++  ){
					list.add(jsonArray.getString(i));
				}
				return PageParam.getSenceauthAppkeys( reqResult.getJSONObject(DATA) , list );
			}
		} catch ( JSONException e ) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server getInstallSiteList return bad json");

		}
		return null ;
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

			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "addAuthorizedApp   of param senceName , address ,province ,city,brand can't be blank ");
		}
		try {

			JSONObject  senceEntity = new JSONObject() ;
			JSONArray senceId = new JSONArray(  ) ;
            senceId.put(uuid);
			senceEntity.put("appId", appKey);
			senceEntity.put("startData", getDateStr(startDate));
			senceEntity.put("endData", getDateStr(endDate));
			senceEntity.put("senceId", senceId);
			senceEntity.put("appBackupName", backupName);

			String url = getContext().rootUrl() + SENCE_ENPOWER_CREATE;

			String req =  sandPost( url , getContext().accessKey() ,getContext().secretKey() ,senceEntity.toString() ,POST) ;

			JSONObject reqResult = new JSONObject( req ) ;

			if( reqResult.getString( CODE ).equals( OK) ){
				//  通过 appFactory创建 BBFApp 对象
				return  true ;

			}
			else{
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, reqResult.getString(MESSAGE)  );
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
		String url = getContext().rootUrl() + SENCE_ENPOWER_APP_DELETE + uuid + "/" + appKey ;

		String req =  sandGet( url , getContext().accessKey() ,getContext().secretKey()  ,DELETE ) ;

		try {
			JSONObject reqResult = new JSONObject( req );
			if( reqResult.getString( CODE ).equals( OK) ){
				//  通过 appFactory创建 BBFApp 对象
				return  true ;
			}
			else{
				System.out.println(  reqResult.toString()  );
				throw new BFException(BFException.ERRORS.NETWORK_ERROR, reqResult.getString(MESSAGE)  );
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server removeAuthorizedApp return bad json");

		}

	}

}
