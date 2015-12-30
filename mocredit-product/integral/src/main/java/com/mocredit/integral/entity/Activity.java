package com.mocredit.integral.entity;

import java.util.Date;

/**
 * 活动信息，由活动模块推送
 *
 * @author ytq
 */
public class Activity {
    /**
     * activity_id int comment '活动ID', activity_name varchar(200) comment '活动名',
     * product_type varchar(10) comment '银行活动代码', start_time datetime comment
     * '活动开始时间', end_time datetime comment '活动结束时间', select_date varchar(16)
     * comment '指定选择日期（周几），如果是周一和周二，则是1,2,如果是周五周六周日，则是5,6,7使用英文字符分割 ', integral
     * decimal(10,0) comment '积分', max_type varchar(5) comment
     * '最大类型，暂定01代表每日，02代表每周，03代表每月，空代表不限制', max_number int comment '最大次数',
     * status varchar(2) comment '01启用，02停止 decimal(10,0) comment '金额'
     * exchangeType  兑换类型 （固定积分兑换 1，外部商品码兑换 2）
     * bins 银行卡号BIN码，以逗号分隔开
     * channel 积分渠道（中信银行 citic、民生银行cmbc、交通银行bocom等）
     */
    private String activityId;
    private String activityName;
    private String pubEnterpriseId;
    private String pubEnterpriseName;
    private String productType;
    private Date startTime;
    private Date endTime;
    private String selectDate;
    private Integer integral;
    private String maxType;
    private Integer maxNumber;
    private String status;
    //新增的三个字段
    private String exchangeType;
    private String bins;
    private String channel;
    private String rule;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getBins() {
        return bins;
    }

    public void setBins(String bins) {
        this.bins = bins;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getPubEnterpriseId() {
        return pubEnterpriseId;
    }

    public void setPubEnterpriseId(String pubEnterpriseId) {
        this.pubEnterpriseId = pubEnterpriseId;
    }

    public String getPubEnterpriseName() {
        return pubEnterpriseName;
    }

    public void setPubEnterpriseName(String pubEnterpriseName) {
        this.pubEnterpriseName = pubEnterpriseName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getMaxType() {
        return maxType;
    }

    public void setMaxType(String maxType) {
        this.maxType = maxType;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Activity [activityId=" + activityId + ", activityName="
                + activityName + ", pubEnterpriseId=" + pubEnterpriseId
                + ", pubEnterpriseName=" + pubEnterpriseName + ", productType="
                + productType + ", startTime=" + startTime + ", endTime="
                + endTime + ", selectDate=" + selectDate + ", integral="
                + integral + ", maxType=" + maxType + ", maxNumber="
                + maxNumber + ", status=" + status + "]";
    }

}
