package com.buyfull.openapiv1.implement;

import com.buyfull.openapiv1.BFException;
import com.buyfull.openapiv1.BFItem;
import com.buyfull.openapiv1.BFGroup;
import com.buyfull.openapiv1.implement.util.ResultCode;
import com.buyfull.openapiv1.implement.util.SignAndSend;
import com.buyfull.openapiv1.implement.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;

import static com.buyfull.openapiv1.implement.util.ResultCode.INVALID_ITEM;
import static com.buyfull.openapiv1.implement.util.ResultCode.INVALID_ITEM_BOUND;
import static com.buyfull.openapiv1.implement.util.StringUtils.isInteger;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.*;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.DATA;

class BFItem_Implement extends BFObjBaseV1_Implement implements BFItem {

	private String group_uuid ;

	private String deviceSN ;

	private Long boundSubCode ;

	private String itemDescrption ;

	public BFItem_Implement(BFOpenAPI_Implement context, String uuid) throws BFException {
		super(context, uuid);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * 通过 uuid  获取绑定场景；
	 * @return
	 * @throws BFException
	 */
	public BFGroup getGroup() throws BFException, ParseException {

		if(StringUtils.isNullOrEmpty( group_uuid ))
			return null ;
		BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();
		BFGroup_Implement group = BFObjFactory.createBFGroup(  api, group_uuid  );
		return group;
	}
	public String getGroupId(){
		return  this.group_uuid;
	}

	public Long getBoundSubCode() {

		return this.boundSubCode;
	}

	public String getItemDescrption() {

		return this.itemDescrption;
	}

	public void setItemDescrption(String itemDescrption) {
		 this.itemDescrption = itemDescrption ;

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
			String url = getContext().rootUrl() + ITEM_INFO + uuid ;
			String req = SignAndSend.sandGet( url ,api.accessKey ,api.secretKey ,GET   ) ;
			JSONObject result = new JSONObject( req ) ;

			if( result.getString(CODE).equals(OK) ){
				this.deviceSN                     = result.getJSONObject(DATA).getString("seriaNo") ;
				this.boundSubCode                 = result.getJSONObject(DATA).getLong("itemNum") ;
				this.uuid          			      = result.getJSONObject(DATA).getString("uuid") ;
				this.group_uuid        			  = result.getJSONObject(DATA).getString( "group_uuid"   );
				this.itemDescrption     		  = result.getJSONObject(DATA).optString("itemName");
				this.createTime                   = result.getJSONObject(DATA).optString("createTime");
				this.lastUpdateTimeStamp 	      = result.getJSONObject(DATA).optLong("lastUpdateTime")   ;
			}
			else
				throw new BFException( BFException.ERRORS.FETCH_ERROR ,req  ) ;
		} catch (JSONException e) {
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}

		return true;
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
		   throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR, INVALID_ITEM.toString());
	   }
	   //绑定设备
		if( !StringUtils.isNullOrEmpty( getDeviceSN() ) ){
			throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR, INVALID_ITEM_BOUND.toString());
		}
       	//String url , String secretId, String secretKey , String  data
			JSONObject  data = new JSONObject() ;
			data.put("uuid" , uuid ) ;
			data.put("seriaNo",deviceSN);
			data.put("lastUpdateTiem",this.lastUpdateTimeStamp);
			BFOpenAPI_Implement api = (BFOpenAPI_Implement) getContext();
			String url = getContext().rootUrl() + ITEM_BOUND;
			String req = SignAndSend.sandPost(url , api.accessKey , api.secretKey ,data.toString() ,PUT) ;
		try {
			JSONObject reqResult = new JSONObject( req ) ;
			if( reqResult.getString(CODE).equals(  OK)) {
				fetch();
				return true;
			}
			else
				throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR,  req );
		}catch (JSONException  jsonEx){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
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
		String url = getContext().rootUrl() + ITEM_UNBOUND + uuid + "/" + String.valueOf(lastUpdateTime() );
		String req = SignAndSend.sandGet(url , api.accessKey , api.secretKey , PUT) ;
		try {
			JSONObject reqResult = new JSONObject( req ) ;
			if( reqResult.getString(CODE).equals(  OK)) {
				fetch();
				return true;
			}
			else
				throw new BFException(BFException.ERRORS.DEVICE_BOUND_ERROR,  req );
		}catch (JSONException  jsonEx){
			throw new BFException(BFException.ERRORS.HTTP_ERROR, ResultCode.HHTP_SERVER_ERROR.toString() );
		}
	}

}
