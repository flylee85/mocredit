package com.mocredit.activity.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by ytq on 2015/10/26.
 */
public class BatchCodeBvo extends BatchCode {
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String remark = "";

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
