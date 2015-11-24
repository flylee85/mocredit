package com.mocredit.integral.entity;

import java.util.Date;

/**
 * 积分订单
 *
 * @author ytq
 */
public class Order {
    /**
     * uuid                 int not null auto_increment comment 'ID',
     * request_id           int comment '请求记录ID',
     * card_num             varchar(20) comment '帐户',
     * bank                 varchar(10) comment '银行',
     * shop_id              varchar(20) comment '商户ID',
     * shop_name            varchar(200) comment '商户名',
     * store_id             varchar(20) comment '门店ID',
     * store_name           varchar(200) comment '门店名称',
     * activity_id          int comment '活动ID',
     * activity_name        varchar(200) comment '活动名称',
     * device               varchar(50) comment '终端ID',
     * amount               decimal(10,0) comment '金额',
     * order_id             varchar(30) comment '订单号',
     * status               int(1) comment '1交易完成 2交易撤销'
     * ctime                datetime comment '创建时间',
     * primary key (uuid)
     */
    private Integer uuid;
    private Integer requestId;
    private String cardNum;
    private String storeId;
    private String activityId;
    private String enCode;
    private String orderId;
    private String oldOrderId;
    private String status;
    private String cardExpDate;//卡的有效期
    private Date ctime;
    private Integer amt;//积分
    private String batchno; //老机具批次号
    private String searchno;//老机具流水号

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getSearchno() {
        return searchno;
    }

    public void setSearchno(String searchno) {
        this.searchno = searchno;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    public Integer getUuid() {
        return uuid;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOldOrderId() {
        return oldOrderId;
    }

    public void setOldOrderId(String oldOrderId) {
        this.oldOrderId = oldOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid=" + uuid +
                ", requestId=" + requestId +
                ", cardNum='" + cardNum + '\'' +
                ", storeId='" + storeId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", enCode='" + enCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", oldOrderId='" + oldOrderId + '\'' +
                ", status='" + status + '\'' +
                ", cardExpDate='" + cardExpDate + '\'' +
                ", ctime=" + ctime +
                ", amt=" + amt +
                '}';
    }
}
