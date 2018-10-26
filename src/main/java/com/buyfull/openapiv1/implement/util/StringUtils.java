package com.buyfull.openapiv1.implement.util;


import com.buyfull.openapiv1.BFException;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * ClassName StringUtils
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/21  14:24
 * @VERSION 1.0
 **/


public final class StringUtils {

    public final static int MAX_DEVICE_LENGTH = 16 ;

    public final static int MAX_ITEMNAME_LENGTH = 35 ;

    public final static int MAX_RESULT_LENGTH   = 4094;

    public final static int MAX_THIRD_SN   = 54;

    public final static int MAX_THIRD_DEVICETYPE   = 10;

    private StringUtils() {
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Object[] array, String sep) {
        return join(array, sep, null);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Collection list, String sep) {
        return join(list, sep, null);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Collection list, String sep, String prefix) {
        Object[] array = list == null ? null : list.toArray();
        return join(array, sep, prefix);
    }


    /**
     * 以指定的分隔符来进行字符串元素连接
     * <p>
     * 例如有字符串数组array和连接符为逗号(,)
     * <code>
     * String[] array = new String[] { "hello", "world", "qiniu", "cloud","storage" };
     * </code>
     * 那么得到的结果是:
     * <code>
     * hello,world,qiniu,cloud,storage
     * </code>
     * </p>
     *
     * @param array  需要连接的对象数组
     * @param sep    元素连接之间的分隔符
     * @param prefix 前缀字符串
     * @return 连接好的新字符串
     */
    public static String join(Object[] array, String sep, String prefix) {
        if (array == null) {
            return "";
        }

        int arraySize = array.length;

        if (arraySize == 0) {
            return "";
        }

        if (sep == null) {
            sep = "";
        }

        if (prefix == null) {
            prefix = "";
        }

        StringBuilder buf = new StringBuilder(prefix);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(sep);
            }
            buf.append(array[i] == null ? "" : array[i]);
        }
        return buf.toString();
    }

    /**
     *
     * 校验数字字符串合法性
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean inStringArray(String s, String[] array) {
        for (String x : array) {
            if (x.equals(s)) {
                return true;
            }
        }
        return false;
    }


    public static void checkDeviceSN( String deviceSN  ) throws BFException {

        if( StringUtils.isNullOrEmpty( deviceSN )  ){
            throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR, "deviceSN can't be null " );
        }

        if( deviceSN.length() != MAX_DEVICE_LENGTH  ||!isInteger(deviceSN)){
            throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR, "deviceSN data format error" );
        }


    }




    public static void checkItemName( String itemDec ) throws BFException {
        if( StringUtils.isNullOrEmpty( itemDec  )  ){
            throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR, "itemDec can't be null " );
        }

        if( itemDec.length() > MAX_ITEMNAME_LENGTH   ){
            throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR, "itemDec data format error" );
        }
    }

    public static void ckeckResult( String[] result  ) throws BFException {
        if( Arrays.toString(result  ).length()  > MAX_RESULT_LENGTH    ){
            throw new BFException(BFException.ERRORS.DATA_FORMAT_ERROR, "result data format error ,max length is 4094" );
        }
    }





}
