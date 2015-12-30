package com.mocredit.verifyCode.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 已验证码 Created by YHL on 15/7/9 09:33.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TVerifiedCode {
	/** 主键 **/
	String id;
	int oper = 0;// 操作码
	/** 优惠券码 **/
	String code;

	/** 券码序列号,券码ID **/
	String codeSerialNumber;

	/** 验证时间 **/
	Date verifyTime;

	/** 活动ID **/
	String activityId;

	/** 活动名称 **/
	String activityName;

	/** 金额。 撤销冲正等操作填负值 **/
	BigDecimal amount;

	/** 机具ID **/
	String device;

	/** 门店ID **/
	String storeId;

	/** 门店名称 **/
	String storeName;

	/** 启用时间 **/
	Date startTime;

	/** 结束时间 **/
	Date endTime;

	/** 次数。撤销冲正等操作填负值 **/
	Integer number;

	/** pos请求序列号 **/
	String requestSerialNumber;

	/** 发行商ID **/
	String issueEnterpriseId;

	/** 发行商名称 **/
	String issueEnterpriseName;

	// -------------2015-7-15 根据需求，数据库变动，修改字段-----------------
	// /** 执行的企业的ID **/
	// String executiveEnterpriseId;
	//
	// /** 执行的企业的名称 **/
	// String executiveEnterpriseName;
	/** 商户ID（此字段是冗余字段，根据store_id可以确定此字段） **/
	String shopId;

	/** 商户名称（此字段是冗余字段） **/
	String shopName;

	/**
	 * 验码类型：参见 Constant.java中的枚举类 0 - 正常验码核销 1 - 冲正 2 - 撤销
	 */
	Integer verifyType;

	/** 冗余字段，合同ID **/
	private String contractId;

	/**
	 * 冗余字段： 存放门店编码
	 */
	private String storeCode;
	private String batchNo;// 批次号
	private String searchNo;// 流水号
	private int status;// 记录状态（兑换成功、活动未开始等）

	Integer useCount = 1;// 封装用于http请求的时候接收参数。与数据库无关

	// -------------GETTER/SETTER-----------------\\

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSearchNo() {
		return searchNo;
	}

	public void setSearchNo(String searchNo) {
		this.searchNo = searchNo;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeSerialNumber() {
		return codeSerialNumber;
	}

	public void setCodeSerialNumber(String codeSerialNumber) {
		this.codeSerialNumber = codeSerialNumber;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getRequestSerialNumber() {
		return requestSerialNumber;
	}

	public void setRequestSerialNumber(String requestSerialNumber) {
		this.requestSerialNumber = requestSerialNumber;
	}

	public String getIssueEnterpriseId() {
		return issueEnterpriseId;
	}

	public void setIssueEnterpriseId(String issueEnterpriseId) {
		this.issueEnterpriseId = issueEnterpriseId;
	}

	public String getIssueEnterpriseName() {
		return issueEnterpriseName;
	}

	public void setIssueEnterpriseName(String issueEnterpriseName) {
		this.issueEnterpriseName = issueEnterpriseName;
	}

	// public String getExecutiveEnterpriseId() {
	// return executiveEnterpriseId;
	// }
	//
	// public void setExecutiveEnterpriseId(String executiveEnterpriseId) {
	// this.executiveEnterpriseId = executiveEnterpriseId;
	// }
	//
	// public String getExecutiveEnterpriseName() {
	// return executiveEnterpriseName;
	// }
	//
	// public void setExecutiveEnterpriseName(String executiveEnterpriseName) {
	// this.executiveEnterpriseName = executiveEnterpriseName;
	// }

	public Integer getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
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

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public int getOper() {
		return oper;
	}

	public void setOper(int oper) {
		this.oper = oper;
	}
}
