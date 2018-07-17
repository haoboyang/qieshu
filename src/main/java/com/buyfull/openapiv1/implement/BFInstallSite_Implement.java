package com.buyfull.openapiv1.implement;

import com.buyfull.openapiv1.BFException;
import com.buyfull.openapiv1.BFInstallSite;
import com.buyfull.openapiv1.BFScene;
import com.buyfull.util.SignAndSend;
import com.buyfull.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import static com.buyfull.openapiv1.BFOpenAPIManager.ROOT_URL;
import static com.buyfull.util.StringUtils.isInteger;
import static com.buyfull.util.UriPathUtil.*;
import static com.buyfull.util.UriPathUtil.DATA;
import static com.buyfull.util.UriPathUtil.MESSAGE;

public class BFInstallSite_Implement extends BFObjBaseV1_Implement implements BFInstallSite {

	private String sence_uuid ;

	private String deviceSN ;

	private Long boundSubCode ;

	private String installDescrption ;

	public BFInstallSite_Implement(BFOpenAPI_Implement context, String uuid) throws BFException {
		super(context, uuid);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * 通过 uuid  获取绑定场景；
	 * @return
	 * @throws BFException
	 */
	public BFScene getScene() throws BFException, ParseException {

		if(StringUtils.isNullOrEmpty( sence_uuid ))
			return null ;
		BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();
		BFScene_Implement scence = BFObjFactory.createBFScene(  api, sence_uuid  );
		return scence;
	}
	public String getSenceId(){
		return  this.sence_uuid;
	}

	public Long getBoundSubCode() {

		return this.boundSubCode;
	}

	public String getInstallDescrption() {

		return this.installDescrption;
	}

	public void setInstallDescrption(String installDescrption) {
		 this.installDescrption = installDescrption ;

	}

	public String getDeviceSN() {

		return this.deviceSN;
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

		BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();

		try {
			String url = ROOT_URL + INSTALLSITE_INFO + uuid ;
			String req = SignAndSend.sandGet( url ,api.accessKey ,api.secretKey ,GET   ) ;
			JSONObject result = new JSONObject( req ) ;

			if( result.getString(CODE).equals(OK) ){
				this.deviceSN                     = result.getJSONObject(DATA).getString("seriaNo") ;
				this.boundSubCode                 = result.getJSONObject(DATA).getLong("installNum") ;
				this.uuid          			= result.getJSONObject(DATA).getString("uuid") ;
				this.sence_uuid        			= result.getJSONObject(DATA).getString( "sence_uuid"   );
				this.installDescrption     			= result.getJSONObject(DATA).optString("location");
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


	@Override
	public void destory(){

		this.deviceSN                  = null ;
		this.uuid                      = null ;
		this.boundSubCode              = null ;
		this.installDescrption         = null ;
		this.sence_uuid                = null ;
		super.destory();

	}

	@Override
	public boolean isValid() {
		if (!super.isValid())
			return false;
		if ( uuid == null || uuid == "" )
			return false;
		return true;
	}



	/**
	 *
	 * 用户自由安装位置在安装位置上面添加设备
	 * @param deviceSN 绑定百蝠设备序列号，在二维码贴纸下的16位数字。一个设备只能被绑定在一个安装位置上，更换绑定前必须解绑
	 * @return
	 * @throws BFException
	 */
	public boolean bindDeviceSN(String deviceSN) throws BFException, JSONException, ParseException {

	   if( deviceSN.length() !=16 || !isInteger(deviceSN)  ){
		   throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR, "设备序列号错误,请输入专业设备所属序列号");
	   }
	   //绑定设备
		if( !StringUtils.isNullOrEmpty( getDeviceSN() ) ){
			throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR, "安装位置上面已经设备已绑定,请先解除绑定再操作");
		}
       	//String url , String secretId, String secretKey , String  data
			JSONObject  data = new JSONObject() ;
			data.put("uuid" , uuid ) ;
			data.put("seriaNo",deviceSN);
			data.put("lastUpdateTiem",this.lastUpdateTimeStamp);
			BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();
			String url = ROOT_URL + INSTALLSITE_BOUND;
			String req = SignAndSend.sandPost(url , api.accessKey , api.secretKey ,data.toString() ,PUT) ;
		try {
			JSONObject reqResult = new JSONObject( req ) ;
			if( reqResult.getString(CODE).equals(  OK)) {
				fetch();
				return true;
			}
			else
				throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR,  reqResult.getString(MESSAGE));
		}catch (JSONException  jsonEx){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");
		}

	}


	/**
	 *
	 * 用户自有安装位置接触设备绑定
	 * @return
	 * @throws BFException
	 */
	public boolean unbindDeviceSN() throws BFException, ParseException {
		BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();
		String url = ROOT_URL + INSTALLSITE_UNBOUND + uuid + "/" + String.valueOf(lastUpdateTime() );
		String req = SignAndSend.sandGet(url , api.accessKey , api.secretKey , PUT) ;
		try {
			JSONObject reqResult = new JSONObject( req ) ;
			if( reqResult.getString(CODE).equals(  OK)) {
				fetch();
				return true;
			}
			else
				throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR,  reqResult.getString(MESSAGE));
		}catch (JSONException  jsonEx){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, "server return bad json");
		}
	}

}
