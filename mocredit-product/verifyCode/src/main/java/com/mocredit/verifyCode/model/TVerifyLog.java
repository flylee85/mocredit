package com.mocredit.verifyCode.model;

import java.util.Date;

/**
 *
 * 验码记录
 * Created by YHL on 15/7/21 22:09.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TVerifyLog {

    private String id;

    /** 券码 **/
    private String code ;

    /** 码序列号 **/
    private String codeSerialNumber;

    /** 活动ID **/
    private String activityId;

    /** 活动名称 **/
    private String activityName;
    /** 机具ID **/
    private String device;

    /** 门店ID **/
    private String storeId;

    /** 门店名称 **/
    private String storeName;

    /** 验证时间 **/
    private Date verifyTime;

    /** 验证结果 **/
    private boolean success;

    /** 错误码 **/
    private String errorCode;

    /** 错误信息 **/
    private String message;

    /** POS请求序列号 **/
    private String requestSerialNumber;


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

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestSerialNumber() {
        return requestSerialNumber;
    }

    public void setRequestSerialNumber(String requestSerialNumber) {
        this.requestSerialNumber = requestSerialNumber;
    }
}
