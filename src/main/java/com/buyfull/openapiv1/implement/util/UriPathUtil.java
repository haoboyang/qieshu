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
    public static  final String APP_CREATE               = "/app/create"; //账户下创建 app  --- api-jmnv3c08   -- test ok
    public static  final String APP_LIST                 = "/app/applist"; //账户下 app 列表                   -- test ok
    public static  final String APP_INFO                 = "/app/info/"; //获取用户详情  ---api-1wtrtjfq       -- test ok
    public static  final String APP_DELETE               = "/app/delete/"; //删除用户下面某个 app  ---api-aze04oco --test ok
    public static  final String APP_UPDATE_SECKEY        = "/app/update/seckey/"; //修改 app sk  --api-pdc3idpi  --test ok
    public static  final String APP_TAG                  = "/app/tag/"; //用户获取 app 场景下面所有安装位置和识别结果  --api-ea0vvs10
    public static  final String APP_RESULTS              = "/app/setrecgonize/results"; //用户在 app 下某个场景给多给安装设备位置添加识别信息 api-m9firix0


    // group module
    public static final String  GROUP_CREATE             = "/group/create"; //user create secen --api-5ti6xvqi --test -ok
    public static final String  GROUP_UPDATE             = "/group/update"; //user update secen -- api-lhjmgj1o --test --ok
    public static final String  GROUP_INFO               = "/group/info/"; //user get group info by group_uuid --api-p4ntqajy --ok
    public static final String  GROUP_DELETE             = "/group/delete/"; // user delete group by group_uuid --api-1noqg82u --ok
    public static final String  GROUP_LIST               = "/group/list";  // get user group list and query by keywords and bran an --ok
    public static final String  GROUP_DEVICE_LIST        = "/group/devicelist/"; // get all deviceList with group_uuid  //api-qizriuy6 --ok
    public static final String  GROUP_GENERATE_BOUNDCODE = "/group/generate/boundcode/"; // update group of boundId //api-f92nimme --ok
    public static final String  GROUP_REMOVE_INSRALL     = "/group/remove/items"; // remove group of inatll //api-2r8r148y -- no test
    public static final String  GROUP_SETRESULT_SN       = "/group/setresult/sn" ;
    //下面三个接口带调试
    public static final String  GROUP_DYNAMIC_CREATE     = "/sound/make";  //--第三方动态业务创建
    public static final String  GROUP_DYNAMIC_LIST       = "/group/dynamic/list/";  //--第三方动态业务创建
    public static final String  GROUP_DYNAMIC_REMOVE       = "/group/remove/dynamic/items";  //--第三方动态业务创建




    //device and item module
    public static final String  ITEM_LIST        = "/item/list";  // get user device and item of data list --//api-fkokvgfg --ok
    public static final String  ITEM_INFO        = "/item/info/";//get device and item by uuid --//api-ivzlgphg //--ok
    public static final String  ITEM_BOUND       = "/item/update"; //bound device with item    --- //api-4i2n1ig4--ok
    public static final String  ITEM_UNBOUND     = "/item/relieve/"; //unbound device with item -- //api-3mslwg3u--ok
    public static final String  ITEM_CREATEBEACH = "/item/create/beach"; //unbound device with item -- //api-qsp7aspw -- ok
    public static final String  ITEM_GET_UUID    = "/item/result/uuid/" ; //get result of item uuid

    //enpower group
    public static final String GROUP_ENPOWER_LIST        = "/groupenpower/enpowerlist"; //get groupEnpowerList by an app --api-3iln7b34 -- ok
    public static final String GROUP_ENPOWER_APP_LIST    = "/groupenpower/app/list";  // get auth group all of appKey api-3bypmv8s --ok
    public static final String GROUP_ENPOWER_CREATE      = "/groupenpower/create"; // 场景授权给 app  ---api-ki9gmbms --ok
    public static final String GROUP_ENPOWER_APP_DELETE  = "/groupenpower/delete/";// 移除场景授权 app -- api-ddhadobo --ok
    public static final String GROUP_ENPOWER_APP_LISTVO  = "/groupenpower/enpowerapp/listinfo";//用户获取授权 app 列表详情  --api-cr0bx5ac --ok
    public static final String GROUP_ENPOWER_APP_groupList  = "/groupenpower/app/group/list";//用户获取授权 app授权列表  --api-fke1jve6
    public static final String GROUP_ENPOWER_APP_REMOVE  = "/groupenpower/app/remove/";//用户移除第三方被授权 app --api-71umagn6
    public static final String GROUP_ENPOWER_item_LIST  = "/groupenpower/item/list/";//三方授权场景下面设备安装位置信息  api-q7yyphcc



}
