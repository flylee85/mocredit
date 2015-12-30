/**
 * 
 */
package com.mocredit.order.entity;

import java.util.Date;

/**
 * 订单表
 * 
 * @author ytq
 * 
 */
public class Order {
	private Integer id;
	private String orderId;
	private String type;
	private Integer activityId;
	private String activityName;
	private Integer pubEnterpriseId;
	private String pubEnterpriseName;
	private Integer supEnterpriseId;
	private String supEnterpriseName;
	private Integer storeId;
	private String storeName;
	private String status;
	private String bank;
	private String cardNum;
	private String code;
	private Integer integral;
	private String startTime;
	private String endTime;
	private String createTime;

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

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getPubEnterpriseId() {
		return pubEnterpriseId;
	}

	public void setPubEnterpriseId(Integer pubEnterpriseId) {
		this.pubEnterpriseId = pubEnterpriseId;
	}

	public String getPubEnterpriseName() {
		return pubEnterpriseName;
	}

	public void setPubEnterpriseName(String pubEnterpriseName) {
		this.pubEnterpriseName = pubEnterpriseName;
	}

	public Integer getSupEnterpriseId() {
		return supEnterpriseId;
	}

	public void setSupEnterpriseId(Integer supEnterpriseId) {
		this.supEnterpriseId = supEnterpriseId;
	}

	public String getSupEnterpriseName() {
		return supEnterpriseName;
	}

	public void setSupEnterpriseName(String supEnterpriseName) {
		this.supEnterpriseName = supEnterpriseName;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
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

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
