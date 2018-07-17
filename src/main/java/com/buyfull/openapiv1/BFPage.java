package com.buyfull.openapiv1;

import org.json.JSONException;

import java.util.List;

import java.io.Serializable;

/**
 * ClassName BFPage
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/21  11:15
 * @VERSION 1.0
 **/


public interface BFPage <E> extends Serializable {
    /**
     *
     * 查询页码
      * @return
     */
    public int getCurrentPage () throws JSONException;

    /**
     *
     * 查询条数
     * @return
     */
    public int getPageSize    () throws JSONException;

    /**
     *
     * 数据总量
     * @return
     */
    public int getTotalNum    () throws JSONException;

    /**
     *
     * 是否还有页数
     * @return
     */
    public boolean getHasMore    () throws JSONException;

    /**
     *
     * 总页数
     * @return
     */
    public int getTotalPage   () throws JSONException;


    /**
     *
     * 数据结果列表
     * @return
     */
    public List<E> getResultList() ;







}
