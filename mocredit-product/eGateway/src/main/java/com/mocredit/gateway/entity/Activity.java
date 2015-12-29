package com.mocredit.gateway.entity;

import java.util.Date;

/**
 * 活动信息，由活动模块推送
 *
 * @author ytq
 */
public class Activity {
    private Integer id;
    private String name;
    private String code;
    private String enterpriseName;
    private String merchantId;
    private String merchantName;
    private String rule;
    private String status;
    private Date cTime;
    private Date uTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!"".equals(name)) {
            this.name = name;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (!"".equals(code)) {
            this.code = code;
        }
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        if (!"".equals(enterpriseName)) {
            this.enterpriseName = enterpriseName;
        }
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        if (!"".equals(merchantId)) {
            this.merchantId = merchantId;
        }
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        if (!"".equals(merchantName)) {
            this.merchantName = merchantName;
        }
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        if (!"".equals(rule)) {
            this.rule = rule;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }
}
