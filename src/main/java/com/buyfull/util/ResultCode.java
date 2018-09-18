package com.buyfull.util;

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
    GROUP_DATA_ERROR(2000,"GROUP 参数格式错误"),

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

    public static void main(String[] args) {
        System.out.println( ResultCode.OK.toString()  );
    }
}
