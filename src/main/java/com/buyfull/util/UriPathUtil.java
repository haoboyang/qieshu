package com.buyfull.util;

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

    // sence module
    public static final String  SENCE_CREATE             = "/sence/create"; //user create secen --api-5ti6xvqi --test -ok
    public static final String  SENCE_UPDATE             = "/sence/update"; //user update secen -- api-lhjmgj1o --test --ok
    public static final String  SENCE_INFO               = "/sence/info/"; //user get sence info by sence_uuid --api-p4ntqajy --ok
    public static final String  SENCE_DELETE             = "/sence/delete/"; // user delete sence by sence_uuid --api-1noqg82u --ok
    public static final String  SENCE_LIST               = "/sence/list";  // get user sence list and query by keywords and bran an --ok
    public static final String  SENCE_DEVICE_LIST        = "/sence/devicelist/"; // get all deviceList with sence_uuid  //api-qizriuy6 --ok
    public static final String  SENCE_GENERATE_BOUNDCODE = "/sence/generate/boundcode/"; // update sence of boundId //api-f92nimme --ok
    public static final String  SENCE_REMOVE_INSRALL     = "/sence/remove/locations"; // remove sence of inatll //api-2r8r148y -- no test





    //device and location module
    public static final String  INSTALLSITE_LIST        = "/device/list";  // get user device and location of data list --//api-fkokvgfg --ok
    //public static final String  INSTALLSITE_CREATE    = "/device/create";   //create device and location data
    public static final String  INSTALLSITE_INFO        = "/device/info/";//get device and location by uuid --//api-ivzlgphg //--ok
    public static final String  INSTALLSITE_BOUND       = "/device/update"; //bound device with location    --- //api-4i2n1ig4--ok
    public static final String  INSTALLSITE_UNBOUND     = "/device/relieve/"; //unbound device with location -- //api-3mslwg3u--ok
    public static final String  INSTALLSITE_CREATEBEACH = "/device/create/beach"; //unbound device with location -- //api-qsp7aspw -- ok

    //enpower sence
    public static final String SENCE_ENPOWER_LIST        = "/enpower/enpowerlist"; //get senceEnpowerList by an app --api-3iln7b34 -- ok
    public static final String SENCE_ENPOWER_APP_LIST    = "/enpower/app/list";  // get auth sence all of appKey api-3bypmv8s --ok
    public static final String SENCE_ENPOWER_CREATE      = "/enpower/create"; // 场景授权给 app  ---api-ki9gmbms --ok
    public static final String SENCE_ENPOWER_APP_DELETE  = "/enpower/delete/";// 移除场景授权 app -- api-ddhadobo --ok
    public static final String SENCE_ENPOWER_APP_LISTVO  = "/enpower/enpowerapp/listinfo";//用户获取授权 app 列表详情  --api-cr0bx5ac --ok
    public static final String SENCE_ENPOWER_APP_SENCELIST  = "/enpower/app/sence/list";//用户获取授权 app授权列表  --api-fke1jve6
    public static final String SENCE_ENPOWER_APP_REMOVE  = "/enpower/app/remove/";//用户移除第三方被授权 app --api-71umagn6
    public static final String SENCE_ENPOWER_INSTALL_LIST  = "/enpower/install/list/";//三方授权场景下面设备安装位置信息  api-q7yyphcc



}
