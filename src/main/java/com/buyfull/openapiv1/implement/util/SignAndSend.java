package com.buyfull.openapiv1.implement.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.buyfull.openapiv1.implement.util.TimeUtile.getGMTTime;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.DELETE;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.POST;
import static com.buyfull.openapiv1.implement.util.UriPathUtil.PUT;


public class SignAndSend {
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final String HMAC_ALGORITHM = "HmacSHA1"; 
    public static String sign(String secret, String timeStr) 
    		throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException 
    {

        String signStr = "date: "+timeStr+"\n"+"source: "+"source";
        String sig = null;
        Mac mac1 = Mac.getInstance(HMAC_ALGORITHM);
        byte[] hash;
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac1.getAlgorithm());
        mac1.init(secretKey);
        hash = mac1.doFinal(signStr.getBytes(CONTENT_CHARSET));
        sig = new String(Base64.encode(hash));
        return sig;
    }


    public static String sandGet(String url, String secretId, String secretKey , String resMethod) {
        String result = "";
        BufferedReader in = null;

        try {
            String timeStr =getGMTTime();
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            HttpURLConnection httpUrlCon = (HttpURLConnection)connection;
            // 设置通用的请求属性
            httpUrlCon.setRequestProperty("Host", urlNameString);
          //  httpUrlCon.setRequestProperty("Accept", "text/html, */*; q=0.01");
            httpUrlCon.setRequestProperty("Source","source");
            httpUrlCon.setRequestProperty("Date",timeStr);
            if( resMethod.equals(  DELETE ) )
                httpUrlCon.setRequestMethod( DELETE );
            if( resMethod.equals( PUT  ) )
                httpUrlCon.setRequestMethod( PUT );

            String sig = sign(secretKey,timeStr);
            String authen = "hmac id=\""+secretId+"\", algorithm=\"hmac-sha1\", headers=\"date source\", signature=\""+sig+"\"";
            httpUrlCon.setRequestProperty("Authorization",authen);
            // 建立实际的连接
            httpUrlCon.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = httpUrlCon.getHeaderFields();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
            		httpUrlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     *
     * 发送 post 请求
     * @param url
     * @param secretId
     * @param secretKey
     * @param data
     * @return
     */

   public static String sandPost( String url , String secretId, String secretKey , String  data ,String resMethod){

       String result = "";
       BufferedReader in = null;
       try {
           String timeStr =getGMTTime();
           String urlNameString = url;
           URL realUrl = new URL(urlNameString);
           // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
           HttpURLConnection httpUrlCon = (HttpURLConnection)connection;
           // 设置通用的请求属性
           httpUrlCon.setDoInput(  true );
           httpUrlCon.setDoOutput( true );
           httpUrlCon.setUseCaches(false);
           if( resMethod.equals( POST )  ){
               ((HttpURLConnection) connection).setRequestMethod(POST);
           }
           if( resMethod.equals( PUT )  ){
               ((HttpURLConnection) connection).setRequestMethod(PUT);
           }

           httpUrlCon.setRequestProperty("Connection", "Keep-Alive");
           httpUrlCon.setRequestProperty("Host", url);
           httpUrlCon.setRequestProperty("Accept", "application/json");
           httpUrlCon.setRequestProperty("Content-Type","application/json; charset=UTF-8");
           httpUrlCon.setRequestProperty("Source","source");
           httpUrlCon.setRequestProperty("Date",timeStr);
           String sig = sign(secretKey,timeStr);
           String authen = "hmac id=\""+secretId+"\", algorithm=\"hmac-sha1\", headers=\"date source\", signature=\""+sig+"\"";
           httpUrlCon.setRequestProperty("Authorization",authen);
            if( StringUtils.isNullOrEmpty( data ) ){
                httpUrlCon.setRequestProperty("Content-Lentth",String.valueOf( 0  )   );
                OutputStream    out = connection.getOutputStream();
               // out.write( data.getBytes());
                out.flush();
                out.close();
            }else{
                httpUrlCon.setRequestProperty("Content-Lentth",String.valueOf( data.length()  )   );
                OutputStream    out = connection.getOutputStream();
                out.write( data.getBytes());
                out.flush();
                out.close();
            }
           // 建立实际的连接
           // 定义 BufferedReader输入流来读取URL的响应
           in = new BufferedReader(new InputStreamReader(
                   httpUrlCon.getInputStream()));
           String line;
           while ((line = in.readLine()) != null) {
               result += line;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       // 使用finally块来关闭输入流
       finally {
           try {
               if (in != null) {
                   in.close();
               }
           } catch (Exception e2) {
               e2.printStackTrace();
           }
       }
       return result;
   }

}