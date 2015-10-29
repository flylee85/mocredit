package com.mocredit.activity.model;

import java.io.Serializable;


/**
 * 
 * 活动商户关联-活动商户关联-实体类
 * @author lishoukun
 * @date 2015/07/08
 */
public class ActivityStore implements Serializable{
	//序列化
	private static final long serialVersionUID = 6905308258132311722L;
	//活动id,activity_id
	private String activityId ;
	//门店id,store_id
	private String storeId ;
	//企业id,enterprise_id
	private String enterpriseId ;
	//商户id,shop_id
	private String shopId;
	
	//门店名称,与数据库字段无关，需要关联store表查询名称
	private String storeName ;
	//门店编码,与数据库字段无关，需要关联store表查询名称
	private String storeCode ;
	//企业名称,与数据库字段无关，需要关联enterprise表查询名称
	private String enterpriseName ;
	//商户名称,与数据库字段无关，需要关联shop表查询名称
	private String shopName;
	//商户编码,与数据库字段无关，需要关联shop表查询名称
	private String shopCode;
	public String getActivityId(){
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getStoreId(){
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getEnterpriseId(){
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
}
