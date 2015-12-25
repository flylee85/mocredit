package com.mocredit.verifyCode.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 券码表
 * Created by YHL on 2015/7/7.
 */
public class TActivityCode {
    private String id;
    private String code;
    private String codeSerialNumber;
    private String activityId;
    private String activityName;
    private Integer maxNum;
    private BigDecimal amount;
    private Date startTime;
    private Date endTime;
    private String orderId;
    private Date createTime;
    private String issueEnterpriseId;
    private String issueEnterpriseName;
    private String customMobile;
    private Date releaseTime;
    
    private String outCode;//活动外部编码
    private String enterpriseCode;
    private String customName;
    private String orderCode;//发码批次
    private String activityCode;//活动编码
    private String exchangeChannel;//兑换渠道

    /** 冗余字段。合同ID **/
    private String contractId ;
    private String selectDate;
    
    private String status;//状态 01 未使用 02 已使用 03 已禁用

    public String getSelectDate() {
        return selectDate;
    }

    public String getExchangeChannel() {
		return exchangeChannel;
	}

	public void setExchangeChannel(String exchangeChannel) {
		this.exchangeChannel = exchangeChannel;
	}

	public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }
    //------------封装部分方法---------------

    /**
     * 活动有效期校验
     * 0:在有效期内；-1：活动尚未开始；1：活动已结束
     * @return
     */
    @JSONField(serialize = false)
    public int effective(){

        Date now = new Date();
        if( null==endTime ){ //无结束日期，认为是始终有效的
            if( startTime.before(now) ){
                return 0;
            }else{
                return -1;
            }

        }else{ // 开始时间和结束时间都存在的时候
        	if(now.before(startTime)){
        		return -1;
        	}
        	if(now.after(endTime)){
        		return 1;
        	}
            return 0;
        }


    }

    //------------getter/setter------------


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
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

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getCustomMobile() {
        return customMobile;
    }

    public void setCustomMobile(String customMobile) {
        this.customMobile = customMobile;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
