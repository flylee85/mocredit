package com.mocredit.order.entity;

import java.util.List;

/**
 * Created by ytq on 2015/12/7.
 */
public class OrderRespData {
    private Integer pageNum;
    private Integer pageSize;
    private Integer pageCount;
    private List<OrderData> data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<OrderData> getData() {
        return data;
    }

    public void setData(List<OrderData> data) {
        this.data = data;
    }
}
