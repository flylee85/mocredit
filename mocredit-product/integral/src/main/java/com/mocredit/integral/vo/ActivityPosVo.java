package com.mocredit.integral.vo;

/**
 * Created by ytq on 2015/11/12.
 */
public class ActivityPosVo {
    /**
     * activityId 活动ID
     * activityType  活动类型：权益类和积分
     * amt 金额(积分)
     * activityName 活动名称
     * enterpriseName 企业名称
     * sTime 开始时间
     * eTime 结束时间
     * cardBin  卡BIN（逗号隔开的，六位数字） “ALL”表示任何卡
     */
    private String activityId;
    private String activityType;
    private Integer amt;
    private String enterpriseName;
    private String sTime;
    private String eTime;
    private String cardBin;
}
