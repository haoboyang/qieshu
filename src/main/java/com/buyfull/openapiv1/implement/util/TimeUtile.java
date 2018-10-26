package com.buyfull.openapiv1.implement.util;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * ClassName TimeUtile
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/20  19:59
 * @VERSION 1.0
 **/


public class TimeUtile {

   static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
   public static final SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
   public static final SimpleDateFormat  simdata = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * 获取当前格林时间
     * @return
     */
   public static String getGMTTime(){
       //get current GMT time
       Calendar cd = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
       sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
       return sdf.format(  cd.getTime()  ) ;
   }

   public static String getDateTimeStr( Date date  ){
       return simpleDateFormat.format( date ) ;
   }

   public static String getDateStr( Date date  ){
       return simdata.format( date ) ;
   }

}
