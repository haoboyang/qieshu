package com.buyfull.util;

import com.buyfull.openapiv1.BFApp;
import com.buyfull.openapiv1.BFItem;
import com.buyfull.openapiv1.BFGroup;
import com.buyfull.openapiv1.implement.BFApp_Implement;
import com.buyfull.openapiv1.implement.BFItem_Implement;
import com.buyfull.openapiv1.implement.BFPage_Implement;
import java.util.List ;
import com.buyfull.openapiv1.implement.BFGroup_Implement;
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

    public static BFPage_Implement<? extends BFApp> getAppPageResult(JSONObject resultStr , List<BFApp_Implement> list  ) throws JSONException {
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


    public static BFPage_Implement<? extends BFGroup> getgroupPageResult(JSONObject resultStr , List<BFGroup_Implement> list  ) throws JSONException {
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

    public static BFPage_Implement<? extends BFItem> getLocationePageResult(JSONObject resultStr , List<BFItem_Implement> list  ) throws JSONException {
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
