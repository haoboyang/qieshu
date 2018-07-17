package com.buyfull.openapiv1.implement;

import com.buyfull.openapiv1.BFPage;
import org.json.JSONException;

import java.util.List;

/**
 * ClassName BFPage_Implement
 * Descricption TOOD
 *
 * @Authorhaobaoyang
 * @Dage 2018/6/21  15:02
 * @VERSION 1.0
 **/


public class BFPage_Implement<E> implements BFPage{

    private int currentPage;
    private int pageSize;
    private int totalNum;
    private boolean hasMore;
    private int totalPage;
    private List<E> data;

    public BFPage_Implement(int currentPage, int pageSize, int totalNum, boolean hasMore, int totalPage, List<E> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.hasMore = hasMore;
        this.totalPage = totalPage;
        this.data = data;
    }

    @Override
    public int getCurrentPage(  ) throws JSONException {
        return this.currentPage;
    }

    @Override
    public int getPageSize() throws JSONException {
        return this.pageSize;
    }

    @Override
    public int getTotalNum() throws JSONException {
        return this.totalNum;
    }

    @Override
    public boolean getHasMore() throws JSONException {
        return this.hasMore;
    }

    @Override
    public int getTotalPage() throws JSONException {
        return this.totalPage;
    }

    @Override
    public List<E> getResultList() {
        return this.data;
    }
}
