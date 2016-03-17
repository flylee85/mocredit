package com.mocredit.integral.dto;

import java.util.List;

/**
 * Created by ytq on 2015/12/7.
 */
public class OrderDto {
    private String startTime; //开始时间
    private String endTime;    //结束时间
    private String activityName; //活动名称
    private String enterpriseName;//企业名称
    private String enCode; //设备号
    private String cardNo; //卡号
    private String mobile; //手机号
    private String code;   //码
    private String type;   //类型（01，积分，02 码）
    private List<String> statusList; //状态(01,已发码，02,已兑换，03，已撤回)
    private Integer offset;
    private Integer pageNum; //页数
    private Integer pageSize; //页面大小
    private boolean download;

    private String orderNo;
    private String storeName;

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if (!"".equals(startTime)) {
            this.startTime = startTime;
        }
    }

    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {
        if (!"".equals(endTime)) {
            this.endTime = endTime;
        }
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        if (!"".equals(activityName)) {
            this.activityName = activityName;
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

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        if (!"".equals(enCode)) {
            this.enCode = enCode;
        }
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        if (!"".equals(cardNo)) {
            this.cardNo = cardNo;
        }
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        if (!"".equals(mobile)) {
            this.mobile = mobile;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        if(!"".equals(orderNo)) {
            this.orderNo = orderNo;
        }
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        if(!"".equals(storeName)) {
            this.storeName = storeName;
        }
    }
}
