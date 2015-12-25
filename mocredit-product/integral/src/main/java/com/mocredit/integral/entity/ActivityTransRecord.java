package com.mocredit.integral.entity;

import java.util.Date;

/**
 * @author ytq 2015年8月26日
 */
public class ActivityTransRecord {
    /**
     * uuid                 int not null auto_increment,
     * activity_id          int comment '活动ID',
     * trans_date           date comment '交易日期',
     * trans_count          int comment '交易数量',
     */
    private Integer uuid;
    private String activityId;
    private String transType;
    private Integer transCount;
    private Date expireDate;
    private Date cTime;
    private Date uTime;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getTransCount() {
        return transCount;
    }

    public void setTransCount(Integer transCount) {
        this.transCount = transCount;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }
}
