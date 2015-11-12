/**
 *
 */
package com.mocredit.order.entity;

import java.util.Date;

/**
 * 订单表
 *
 * @author ytq
 */
public class Order {
    private Integer id;
    private String orderId;
    private String type;
    private String activityId;
    private String activityName;
    private String pubEnterpriseId;
    private String pubEnterpriseName;
    private String supEnterpriseId;
    private String supEnterpriseName;
    private String storeId;
    private String storeName;
    private String status;
    private String bank;
    private String cardNum;
    private String code;
    private String integral;
    private String startTime;
    private String endTime;
    private String createTime;
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSupEnterpriseId() {
        return supEnterpriseId;
    }

    public void setSupEnterpriseId(String supEnterpriseId) {
        this.supEnterpriseId = supEnterpriseId;
    }

    public String getSupEnterpriseName() {
        return supEnterpriseName;
    }

    public void setSupEnterpriseName(String supEnterpriseName) {
        this.supEnterpriseName = supEnterpriseName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if (!"".equals(startTime)) {
            this.startTime = startTime;
        }
        if (startTime != null && startTime.contains(".0")) {
            startTime = startTime.substring(0, startTime.length() - 2);
            this.startTime = startTime;
        }
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if (!"".equals(endTime)) {
            this.endTime = endTime;
        }
        if (endTime != null && endTime.contains(".0")) {
            endTime = endTime.substring(0, endTime.length() - 2);
            this.endTime = endTime;
        }
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
