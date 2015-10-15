package com.mocredit.order.dto;

/**
 * 与订单管理系统数据传输
 * 
 * @author ytq
 * 
 */
public class OrderDto {
	private String Id;
	private String orderId;
	private Integer activityId;
	private String activityName;
	private Integer enterpriseId;
	private String enterpriseName;
	private Integer supEnterpriseId;
	private String supEnterpriseName;
	private Integer storeId;
	private String storeName;
	private String status;
	private String startTime;
	private String endTime;
	private String code;
	private String bank;
	private String cardNum;
	private Integer integral;
	private String createTime;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
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

	public String getStartTime() {
		if (startTime != null && startTime.endsWith(".0")) {
			startTime = startTime.substring(0, startTime.length() - 2);
		}
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		if (endTime != null && endTime.endsWith(".0")) {
			endTime = endTime.substring(0, endTime.length() - 2);
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getCreateTime() {
		if (createTime != null && createTime.endsWith(".0")) {
			createTime = createTime.substring(0, createTime.length() - 2);
		}
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
