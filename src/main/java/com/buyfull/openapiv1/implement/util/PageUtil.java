package com.buyfull.openapiv1.implement.util;

import com.buyfull.openapiv1.BFApp;
import com.buyfull.openapiv1.BFPage;
import org.json.JSONException;

import java.util.List;

/**
 * ClassName PageUtil
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/21  12:05
 * @VERSION 1.0
 **/


public class PageUtil {

    public static BFPage<? extends BFApp>getPageBean(  String result  ){

        BFPage<BFApp>pageData= new BFPage<BFApp>() {
            @Override
            public int getCurrentPage() throws JSONException {
                return 0;
            }

            @Override
            public int getPageSize() throws JSONException {
                return 0;
            }

            @Override
            public int getTotalNum() throws JSONException {
                return 0;
            }

            @Override
            public boolean getHasMore() throws JSONException {
                return false;
            }

            @Override
            public int getTotalPage() throws JSONException {
                return 0;
            }

            @Override
            public List<BFApp> getResultList() {
                return null;
            }
        } ;
        return pageData ;

    }

}
