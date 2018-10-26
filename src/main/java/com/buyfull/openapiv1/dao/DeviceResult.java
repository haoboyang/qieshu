package com.buyfull.openapiv1.dao;

import com.buyfull.openapiv1.BFException;
import static com.buyfull.openapiv1.implement.util.StringUtils.*;

/**
 * ClassName DeviceResult
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/9/14  16:19
 * @VERSION 1.0
 **/


public class DeviceResult {

    private String deviceSN ;

    private String itemDec ;

    private String[] tag;

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getItemDec() {
        return itemDec;
    }

    public void setItemDec(String itemDec) {
        this.itemDec = itemDec;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    private   DeviceResult( String deviceSN , String itemDec  , String[] tag) throws BFException {
        checkDeviceSN( deviceSN ) ;
        checkItemName( itemDec );
        ckeckResult(tag) ;
        this.deviceSN  = deviceSN ;
        this.itemDec   = itemDec  ;
        this.tag = tag;
    }

    public static  DeviceResult getDeviceResult( String deviceSN , String itemDec  , String[] result   ) throws BFException {
        return  new DeviceResult( deviceSN , itemDec ,  result ) ;
    }

}
