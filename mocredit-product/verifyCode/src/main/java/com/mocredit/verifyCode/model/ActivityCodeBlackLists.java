package com.mocredit.verifyCode.model;

import java.util.Date;

/**
 *
 * 验码黑名单。包括 删除、撤销、活动取消等，特殊处理的码。
 * Created by YHL on 15/7/18 11:24.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class ActivityCodeBlackLists  {

    /** 主键 **/
    private String id;

    /** 券码号 **/
    private String code ;

    /**  活动ID **/
    private String activityId;

    /**
     * 券码表中的ID
     */
    private String activityCodeId;

    /** 创建时间 **/
    private Date createTime;

    /** 加入黑名单的类型：0-删除/取消 **/
    private int blacklistsType;

    /** 描述加黑名单的原因 **/
    private String blacklistsDesc;

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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getBlacklistsType() {
        return blacklistsType;
    }

    public void setBlacklistsType(int blacklistsType) {
        this.blacklistsType = blacklistsType;
    }

    public String getBlacklistsDesc() {
        return blacklistsDesc;
    }

    public void setBlacklistsDesc(String blacklistsDesc) {
        this.blacklistsDesc = blacklistsDesc;
    }

    public String getActivityCodeId() {
        return activityCodeId;
    }

    public void setActivityCodeId(String activityCodeId) {
        this.activityCodeId = activityCodeId;
    }
}
