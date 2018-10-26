package com.buyfull.openapiv1.implement.util;

/**
 * ClassName ResultErrorMessage
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/9/14  10:55
 * @VERSION 1.0
 **/


public enum ResultCode {
    OK(200, "OK"),

    HHTP_SERVER_ERROR(1000,"网络异常，请联系客服"),
    JSON_DATA_FORMAT_ERROR(1001,"数据格式错误"),
    BFAPP_NOT_NULL(1002,"BFApp can not be null"),
    DEVICE_ITEM_TAGS_NOT_NULL( 1003 , "DeviceResult can not be null " ),
    GROUP_DATA_ERROR(2000,"group with wrong param"),
    GROUP_DYNAMIC_DEVICESN_ERROR( 2000 , "max deviceSN of length is 54"),
    GROUP_DYNAMIC_DEVICESNTYPE_ERROR( 2000 , "max deviceType of length is 10"),
    GROUP_DYNAMIC_CODETYPE_ERROR( 2000 , "codeTpye error"),
    INVALID_GROUP_CONTEXT(2001,"pdate group of param groupName , address ,province ,city,brand can't be blank "),
    INVALID_UUID_GROUP_UUID(2002,"request uuid can't be blank"),
    INVALID_ERROR_MAXLIMIT(2003,"设备安装位置不能为空且最大批量数为 200"),
    GROUP_DYNAMIC_PARAM_NULL(2004," Dynamic device param can't be null"),
    INVALID_ITEM(3000,"设备序列号错误,请输入专业设备所属序列号"),
    INVALID_ITEM_BOUND(3001,"安装位置上面已经设备已绑定,请先解除绑定再操作")
    ;

    private final int code;
    private final String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\""    + ":" + code + "," +
                "\"message\"" + ":" + message  +
               "}" ;
    }
}
