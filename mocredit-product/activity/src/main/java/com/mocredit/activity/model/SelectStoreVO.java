package com.mocredit.activity.model;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 活动管理选择门店对象-视图类
 * @author lishoukun
 * @date 2015/07/13
 */
public class SelectStoreVO implements Serializable{
	//序列化
	private static final long serialVersionUID = 6905308258132311722L;
	//门店Id
	private String storeId ;
	//门店名称
	private String storeName ;
	//门店编码
	private String storeCode;
	//商户Id
	private String shopId;
	//商户名称
	private String shopName;
	//机具数量
	private Integer deviceNum;
	//省
	private String province ;
	//市
	private String city ;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(Integer deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
}
