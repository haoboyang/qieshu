package com.buyfull.openapiv1.implement.util;

import com.buyfull.openapiv1.*;

import java.util.List ;

import com.buyfull.openapiv1.implement.BFPage_Implement;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * ClassName PageParam
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/21  15:34
 * @VERSION 1.0
 **/


public  class PageParam {

    public static BFPage<? extends BFApp> getAppPageResult(JSONObject resultStr , List<BFApp> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getInt("isMore") != 0 ){
            hashMore = true ;
        }

        BFPage pageData  = new BFPage_Implement( resultStr.getInt("currentPage") ,
                                                           resultStr.getInt("pageSize") ,
                                                           resultStr.getInt("totalNum") ,
                                                           hashMore ,
                                                           resultStr.getInt("totalPage") ,
                                                           list
                                                        );
        return pageData ;

    }


    public static BFPage<? extends BFGroup> getgroupPageResult(JSONObject resultStr , List<BFGroup> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getInt("isMore") != 0 ){
            hashMore = true ;
        }
        BFPage_Implement pageData  = new BFPage_Implement( resultStr.getInt("currentPage") ,
                                                           resultStr.getInt("pageSize") ,
                                                           resultStr.getInt("totalNum") ,
                                                           hashMore ,
                                                           resultStr.getInt("totalPage") ,
                                                           list
                                                        );
        return pageData ;

    }

    public static BFPage<? extends BFItem> getLocationePageResult(JSONObject resultStr , List<BFItem> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getInt("isMore") != 0 ){
            hashMore = true ;
        }
        BFPage_Implement pageData  = new BFPage_Implement( resultStr.getInt("currentPage") ,
                                                           resultStr.getInt("pageSize") ,
                                                           resultStr.getInt("totalNum") ,
                                                           hashMore ,
                                                           resultStr.getInt("totalPage") ,
                                                           list
                                                        );
        return pageData ;
    }


    public static BFPage<? extends BFDynamicDevice> getDynamicItem(JSONObject resultStr , List<BFDynamicDevice> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getInt("isMore") != 0 ){
            hashMore = true ;
        }
        BFPage_Implement pageData  = new BFPage_Implement( resultStr.getInt("currentPage") ,
                                                           resultStr.getInt("pageSize") ,
                                                           resultStr.getInt("totalNum") ,
                                                           hashMore ,
                                                           resultStr.getInt("totalPage") ,
                                                           list
                                                        );
        return pageData ;
    }



    public static BFPage_Implement<String> getgroupauthAppkeys(JSONObject resultStr , List<String> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getInt("isMore") != 0 ){
            hashMore = true ;
        }
        BFPage_Implement pageData  = new BFPage_Implement( resultStr.getInt("currentPage") ,
                                                           resultStr.getInt("pageSize") ,
                                                           resultStr.getInt("totalNum") ,
                                                           hashMore ,
                                                           resultStr.getInt("totalPage") ,
                                                           list
                                                        );
        return pageData ;
    }

    public static BFPage_Implement<String> getAuthAppListVo(com.alibaba.fastjson.JSONObject resultStr , List<com.alibaba.fastjson.JSONObject> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getIntValue("isMore") != 0 ){
            hashMore = true ;
        }
        BFPage_Implement pageData  = new BFPage_Implement( resultStr.getIntValue("currentPage") ,
                                                           resultStr.getIntValue("pageSize") ,
                                                           resultStr.getIntValue("totalNum") ,
                                                           hashMore ,
                                                           resultStr.getIntValue("totalPage") ,
                                                           list
                                                        );
        return pageData ;
    }

    public static BFPage_Implement<String> getAuthorizedItemListStr(com.alibaba.fastjson.JSONObject resultStr , List<String> list  ) throws JSONException {
        boolean hashMore =  false ;
        if( resultStr.getIntValue("isMore") != 0 ){
            hashMore = true ;
        }
        BFPage_Implement pageData  = new BFPage_Implement( resultStr.getIntValue("currentPage") ,
                resultStr.getIntValue("pageSize") ,
                resultStr.getIntValue("totalNum") ,
                hashMore ,
                resultStr.getIntValue("totalPage") ,
                list
        );
        return pageData ;
    }




}
