package com.buyfull.openapiv1.implement;

import com.buyfull.openapiv1.BFDynamicDevice;
import com.buyfull.openapiv1.BFException;
import com.buyfull.openapiv1.implement.util.ResultCode;
import com.buyfull.openapiv1.implement.util.SignAndSend;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import static com.buyfull.openapiv1.implement.util.UriPathUtil.*;

/**
 * ClassName BFDynamicDevice_Implement
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/10/25  16:49
 * @VERSION 1.0
 **/


public class BFDynamicDevice_Implement extends BFObjBaseV1_Implement implements BFDynamicDevice {

    private String group_uuid ;

    private String deviceSN ;

    private Long boundSubCode ;

    private String itemDescrption ;

    public BFDynamicDevice_Implement() throws BFException {
    }

    public BFDynamicDevice_Implement(BFOpenAPI_Implement context, String uuid) throws BFException {
        super(context, uuid);
    }

    @Override
    public String getGroupId() {
        return this.group_uuid;
    }

    @Override
    public Long getBoundSubCode() {
        return this.boundSubCode;
    }

    @Override
    public String getItemDescrption() {
        return this.itemDescrption;
    }

    @Override
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

}
