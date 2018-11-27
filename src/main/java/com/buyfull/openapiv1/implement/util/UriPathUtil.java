package com.buyfull.openapiv1.implement.util;

/**
 * ClassName UriPathUtil
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/20  22:34
 * @VERSION 1.0
 **/


public class UriPathUtil {

    public static final String  GET                      = "GET";
    public static final String  POST                     = "POST";
    public static final String  DELETE                   = "DELETE";
    public static final String  PUT                      = "PUT";
    public static final String  CODE                     = "code";
    public static final String  OK                       = "200";
    public static final String  MESSAGE                  = "message";
    public static final String  DATA                     = "data" ;
    public static final String  ITEMS                    = "items" ;
    public static final int     MAXLIMIT                 = 200;

    //  app module
    public static  final String APP_CREATE               = "/app/create";
    public static  final String APP_LIST                 = "/app/applist";
    public static  final String APP_INFO                 = "/app/info/";
    public static  final String APP_DELETE               = "/app/delete/";
    public static  final String APP_UPDATE_SECKEY        = "/app/update/seckey/";
    public static  final String APP_TAG                  = "/app/tag/";
    public static  final String APP_RESULTS              = "/app/setrecgonize/results";



    // group module
    public static final String  GROUP_CREATE             = "/group/create";
    public static final String  GROUP_UPDATE             = "/group/update";
    public static final String  GROUP_INFO               = "/group/info/";
    public static final String  GROUP_DELETE             = "/group/delete/";
    public static final String  GROUP_LIST               = "/group/list";
    public static final String  GROUP_DEVICE_LIST        = "/group/devicelist/";
    public static final String  GROUP_GENERATE_BOUNDCODE = "/group/generate/boundcode/";
    public static final String  GROUP_REMOVE_INSRALL     = "/group/remove/items";
    public static final String  GROUP_SETRESULT_SN       = "/group/setresult/sn" ;
    //下面三个接口带调试
    public static final String  GROUP_DYNAMIC_CREATE     = "/sound/make";
    public static final String  DYNAMIC_AD_CALLBACK      = "/sound/callback";
    public static final String  GROUP_DYNAMIC_LIST       = "/group/dynamic/list/";
    public static final String  GROUP_DYNAMIC_REMOVE     = "/group/remove/dynamic/items";



    //device and item module
    public static final String  ITEM_LIST        = "/item/list";
    public static final String  ITEM_INFO        = "/item/info/";
    public static final String  ITEM_BOUND       = "/item/update";
    public static final String  ITEM_UNBOUND     = "/item/relieve/";
    public static final String  ITEM_CREATEBEACH = "/item/create/beach";
    public static final String  ITEM_GET_UUID    = "/item/result/uuid/" ;


    //enpower group
    public static final String GROUP_ENPOWER_LIST        = "/groupenpower/enpowerlist";
    public static final String GROUP_ENPOWER_APP_LIST    = "/groupenpower/app/list";
    public static final String GROUP_ENPOWER_CREATE      = "/groupenpower/create";
    public static final String GROUP_ENPOWER_APP_DELETE  = "/groupenpower/delete/";
    public static final String GROUP_ENPOWER_APP_LISTVO  = "/groupenpower/enpowerapp/listinfo";
    public static final String GROUP_ENPOWER_APP_groupList  = "/groupenpower/app/group/list";
    public static final String GROUP_ENPOWER_APP_REMOVE  = "/groupenpower/app/remove/";
    public static final String GROUP_ENPOWER_item_LIST  = "/groupenpower/item/list/";



}
