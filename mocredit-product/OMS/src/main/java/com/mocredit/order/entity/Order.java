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
	private Integer activityId;
	private String activityName;
	private Integer pubEnterpriseId;
	private String pubEnterpriseName;
	private Integer subEnterpriseId;
	private String supEnterpriseName;
	private Integer storeId;
	private Integer storeName;
	private String status;
	private String cardNum;
	private String code;
	private Integer integral;
	private Date startTime;
	private Date endTime;
	private Date createTime;
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
	public Integer getSubEnterpriseId() {
		return subEnterpriseId;
	}
	public void setSubEnterpriseId(Integer subEnterpriseId) {
		this.subEnterpriseId = subEnterpriseId;
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
	public Integer getStoreName() {
		return storeName;
	}
	public void setStoreName(Integer storeName) {
		this.storeName = storeName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
