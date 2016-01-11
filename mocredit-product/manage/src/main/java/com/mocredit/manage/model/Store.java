package com.mocredit.manage.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Store {
	public static final Byte STATUS_PAUSE = 0;
	public static final Byte STATUS_ACTIVED = 1;
	public static final Byte STATUS_DELETED = 2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.name
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String name;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.code
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String code;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.merchant_id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String merchantId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.province
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Integer province;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.city
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Integer city;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.area
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Integer area;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.address
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String address;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.longitude
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Double longitude;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.latitude
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Double latitude;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.status
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private Byte status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.mobile
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String mobile;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column t_store.phone
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	private String phone;

	private String merchantName;
	private String provinceName;
	private String cityName;
	private String areaName;
	@JSONField(format = "yyyy-MM-dd")
	private Date createTime;
	private String linkman;
	private int businessStatus;// 业务状态
	private String businessStatusName;
	private String mailAddress;// 邮寄地址
	private int terminalCount;// 机具数

	public String getBusinessStatusName() {
		return businessStatusName;
	}

	public void setBusinessStatusName(String businessStatusName) {
		this.businessStatusName = businessStatusName;
	}

	public int getTerminalCount() {
		return terminalCount;
	}

	public void setTerminalCount(int terminalCount) {
		this.terminalCount = terminalCount;
	}

	public String getLinkman() {
		return linkman;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public int getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(int businessStatus) {
		this.businessStatus = businessStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.id
	 *
	 * @return the value of t_store.id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.id
	 *
	 * @param id
	 *            the value for t_store.id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.name
	 *
	 * @return the value of t_store.name
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.name
	 *
	 * @param name
	 *            the value for t_store.name
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.code
	 *
	 * @return the value of t_store.code
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getCode() {
		return code;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.code
	 *
	 * @param code
	 *            the value for t_store.code
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.merchant_id
	 *
	 * @return the value of t_store.merchant_id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.merchant_id
	 *
	 * @param merchantId
	 *            the value for t_store.merchant_id
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.province
	 *
	 * @return the value of t_store.province
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Integer getProvince() {
		return province;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.province
	 *
	 * @param province
	 *            the value for t_store.province
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setProvince(Integer province) {
		this.province = province;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.city
	 *
	 * @return the value of t_store.city
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.city
	 *
	 * @param city
	 *            the value for t_store.city
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.area
	 *
	 * @return the value of t_store.area
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Integer getArea() {
		return area;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.area
	 *
	 * @param area
	 *            the value for t_store.area
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setArea(Integer area) {
		this.area = area;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.address
	 *
	 * @return the value of t_store.address
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.address
	 *
	 * @param address
	 *            the value for t_store.address
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.longitude
	 *
	 * @return the value of t_store.longitude
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.longitude
	 *
	 * @param longitude
	 *            the value for t_store.longitude
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.latitude
	 *
	 * @return the value of t_store.latitude
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.latitude
	 *
	 * @param latitude
	 *            the value for t_store.latitude
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.status
	 *
	 * @return the value of t_store.status
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.status
	 *
	 * @param status
	 *            the value for t_store.status
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.mobile
	 *
	 * @return the value of t_store.mobile
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.mobile
	 *
	 * @param mobile
	 *            the value for t_store.mobile
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column t_store.phone
	 *
	 * @return the value of t_store.phone
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column t_store.phone
	 *
	 * @param phone
	 *            the value for t_store.phone
	 *
	 * @mbggenerated Mon Nov 02 15:47:55 CST 2015
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}