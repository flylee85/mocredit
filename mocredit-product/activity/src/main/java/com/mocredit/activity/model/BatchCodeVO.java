package com.mocredit.activity.model;

import java.io.Serializable;


/**
 * 
 * 发码批次码-视图类
 * @author lishoukun
 * @date 2015/07/13
 */
public class BatchCodeVO implements Serializable{
	//序列化
	private static final long serialVersionUID = 6905308258132311722L;
	//发码批次Id
	private String orderId ;
	//联系人名称
	private String customName ;
	//码
	private String code ;
	//活动Id
	private String activityId ;
	//活动名称
	private String activityName ;
	//码流水号
	private String codeSerialNumber ;
	//价格
	private String amount ;
	//创建时间
	private String createTime ;
	//联系人电话
	private String customMobile ;
	//活动发行商Id
	private String issueEnterpriseId ;
	//活动发行商名称
	private String issueEnterpriseName ;
	//合同Id
	private String contractId ;
	//最大使用次数
	private String maxNum ;
	//发布时间
	private String releaseTime ;
	//开始时间
	private String startTime ;
	//结束时间
	private String endTime ;
	//指定时间
	private String selectDate ;

	private String activityCode;
	private String outCode;//活动外部编码
    private String enterpriseCode;
    private String orderCode;//发码批次

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getCodeSerialNumber() {
		return codeSerialNumber;
	}

	public void setCodeSerialNumber(String codeSerialNumber) {
		this.codeSerialNumber = codeSerialNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCustomMobile() {
		return customMobile;
	}

	public void setCustomMobile(String customMobile) {
		this.customMobile = customMobile;
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

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
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

	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
}
