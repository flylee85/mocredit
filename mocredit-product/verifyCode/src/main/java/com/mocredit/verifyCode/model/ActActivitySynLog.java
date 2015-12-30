package com.mocredit.verifyCode.model;

import com.mocredit.base.enums.ActActivityCodeOperType;

import java.util.Date;

/**
 *
 * 存放活动同步日志。 也就是码的 导入、删除日志
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class ActActivitySynLog {

    private String id;

    /** 活动ID**/
    private String activityId;

    /** 活动名称 **/
    private String activityName;

    /** 操作类型 **/
    private ActActivityCodeOperType operType;

    /** 操作时间**/
    private Date operTime;

    /** 操作是否成功 **/
    private boolean success;

    /** 错误信息 **/
    private  String message;

    /** 错误代码 **/
    private  String errorCode;

    /** 码导入的时候，同步的券码数量 **/
    private  int synNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ActActivityCodeOperType getOperType() {
        return operType;
    }

    public void setOperType(ActActivityCodeOperType operType) {
        this.operType = operType;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getSynNum() {
        return synNum;
    }

    public void setSynNum(int synNum) {
        this.synNum = synNum;
    }
}
