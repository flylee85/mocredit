package com.mocredit.activity.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 活动-活动-实体类
 *
 * @author lishoukun
 * @date 2015/07/08
 */
public class Activity implements Serializable {
    // 序列化
    private static final long serialVersionUID = 6905308258132311722L;
    // id,id
    private String id;
    // 活动名称,name
    private String name;
    // 活动类型,type
    private String type;
    // 活动编码,code
    private String code;
    // 外部编码,out_code
    private String outCode;
    // 小票标题,receipt_title
    private String receiptTitle;
    // 小票打印内容,receipt_print
    private String receiptPrint;
    // POS验证成功提示,pos_success_msg
    private String posSuccessMsg;
    // 验证成功通知短信,success_sms_msg
    private String successSmsMsg;
    // 短信发码方式,send_sms_type
    private String sendSmsType;
    // 通知短信内容,notice_sms_msg
    private String noticeSmsMsg;
    // 二维码所在帧
    private Integer codeno;
    // 彩信标题
    private String subject;
    private String mmsJson;
    // 开始时间,start_time
    @JSONField(format = "yyyy-MM-dd")
    private Date startTime;
    // 结束时间,end_time
    @JSONField(format = "yyyy-MM-dd")
    private Date endTime;
    // 选择时间,select_date,选择日期，用英文逗号分隔
    private String selectDate;
    // 价格,amount
    private Double amount;
    // 最大类型,max_type,暂定01代表每日，02代表每周，03代表每月，空代表不限制
    private String maxType;
    // 最大次数,max_number
//	DayMax  一天次数限制
//	WeekMax 一个星期次数限制
//	MonthMax 一个月次数限制
//	YearMax  一年次数限制
//	TotalMax  总次数限制
    private String maxNumber;
    // 积分活动,integral_activity
    private String integralActivity;
    // 积分,integral
    private Integer integral;
    // 活动发行方,enterprise_id
    private String enterpriseId;
    // 合同,contract_id
    private String contractId;
    // 状态,status，01启用，02停止
    private String status;
    // 创建时间,createtime
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    // 更新时间,modifytime
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifytime;
    // 创建人,creator
    private String creator;
    // 更新人,modifier
    private String modifier;

    private String bins;// BIN码
    private String channel;// 积分通道（中信银行、民生银行等）
    private short exchangeType;// 兑换类型 固定积分兑换 1，外部商品码兑换 2

    // 活动关联门店列表，与数据库无关
    private List<ActivityStore> storeList = new ArrayList<ActivityStore>();
    // 活动订单列表，与数据库无关
    private List<Batch> orderList = new ArrayList<Batch>();
    // 门店数量，与数据库无关
    private int storeCount;
    // 订单数量，与数据库无关
    private int orderCount;
    // 活动发行方名称
    private String enterpriseName;
    // 合同名称
    private String contractName;
    // 兑换渠道(验码)
    private String exchangeChannel;
    private int activityStyle;//活动类型
    private String amountLimit;//活动最低额度限制
    private double discount;//折扣额度（金额或者折扣度，两位小数）
    //码验证总数，兑换总数，废码总数
    private int verifyCount;
    private int exchangeCount;
    private int disableCount;

    public int getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(Integer verifyCount) {
        this.verifyCount = verifyCount;
    }

    public int getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(Integer exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public int getDisableCount() {
        return disableCount;
    }

    public void setDisableCount(Integer disableCount) {
        this.disableCount = disableCount;
    }

    public int getActivityStyle() {
        return activityStyle;
    }

    public void setActivityStyle(int activityStyle) {
        this.activityStyle = activityStyle;
    }

    public String getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(String amountLimit) {
        this.amountLimit = amountLimit;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getExchangeChannel() {
        return exchangeChannel;
    }

    public void setExchangeChannel(String exchangeChannel) {
        this.exchangeChannel = exchangeChannel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public String getReceiptTitle() {
        return receiptTitle;
    }

    public void setReceiptTitle(String receiptTitle) {
        this.receiptTitle = receiptTitle;
    }

    public String getReceiptPrint() {
        return receiptPrint;
    }

    public void setReceiptPrint(String receiptPrint) {
        this.receiptPrint = receiptPrint;
    }

    public String getPosSuccessMsg() {
        return posSuccessMsg;
    }

    public void setPosSuccessMsg(String posSuccessMsg) {
        this.posSuccessMsg = posSuccessMsg;
    }

    public String getSuccessSmsMsg() {
        return successSmsMsg;
    }

    public void setSuccessSmsMsg(String successSmsMsg) {
        this.successSmsMsg = successSmsMsg;
    }

    public String getSendSmsType() {
        return sendSmsType;
    }

    public void setSendSmsType(String sendSmsType) {
        this.sendSmsType = sendSmsType;
    }

    public String getNoticeSmsMsg() {
        return noticeSmsMsg;
    }

    public void setNoticeSmsMsg(String noticeSmsMsg) {
        this.noticeSmsMsg = noticeSmsMsg;
    }

    public Integer getCodeno() {
        return codeno;
    }

    public void setCodeno(Integer codeno) {
        this.codeno = codeno;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMmsJson() {
        return mmsJson;
    }

    public void setMmsJson(String mmsJson) {
        this.mmsJson = mmsJson;
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

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMaxType() {
        return maxType;
    }

    public void setMaxType(String maxType) {
        this.maxType = maxType;
    }

    public String getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getIntegralActivity() {
        return integralActivity;
    }

    public void setIntegralActivity(String integralActivity) {
        this.integralActivity = integralActivity;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public List<ActivityStore> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<ActivityStore> storeList) {
        this.storeList = storeList;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public List<Batch> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Batch> orderList) {
        this.orderList = orderList;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String toDescribeString() {
        StringBuffer describeString = new StringBuffer();
        if (this.id != null) {
            describeString.append("id:" + this.id + "，");
        }
        if (this.name != null) {
            describeString.append("活动名称:" + this.name + "，");
        }
        if (this.type != null) {
            describeString.append("活动类型:" + this.type + "，");
        }
        if (this.code != null) {
            describeString.append("活动编码:" + this.code + "，");
        }
        if (this.outCode != null) {
            describeString.append("外部编码:" + this.outCode + "，");
        }
        if (this.receiptTitle != null) {
            describeString.append("小票标题:" + this.receiptTitle + "，");
        }
        if (this.receiptPrint != null) {
            describeString.append("小票打印内容:" + this.receiptPrint + "，");
        }
        if (this.posSuccessMsg != null) {
            describeString.append("POS验证成功提示:" + this.posSuccessMsg + "，");
        }
        if (this.successSmsMsg != null) {
            describeString.append("验证成功通知短信:" + this.successSmsMsg + "，");
        }
        if (this.sendSmsType != null) {
            describeString.append("短信发码方式:" + this.sendSmsType + "，");
        }
        if (this.noticeSmsMsg != null) {
            describeString.append("通知短信内容:" + this.noticeSmsMsg + "，");
        }
        if (this.startTime != null) {
            describeString.append("开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.startTime) + "，");
        }
        if (this.endTime != null) {
            describeString.append("结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime) + "，");
        }
        if (this.selectDate != null) {
            describeString.append("选择时间:" + this.selectDate + "，");
        }
        if (this.amount != null) {
            describeString.append("价格:" + this.amount + "，");
        }
        if (this.maxType != null) {
            describeString.append("最大类型:" + this.maxType + "，");
        }
        if (this.maxNumber != null) {
            describeString.append("最大次数:" + this.maxNumber + "，");
        }
        if (this.integralActivity != null) {
            describeString.append("积分活动:" + this.integralActivity + "，");
        }
        if (this.integral != null) {
            describeString.append("积分:" + this.integral + "，");
        }
        if (this.enterpriseId != null) {
            describeString.append("活动发行方:" + this.enterpriseId + "，");
        }
        if (this.contractId != null) {
            describeString.append("合同:" + this.contractId + "，");
        }
        if (this.status != null) {
            describeString.append("状态:" + this.status + "，");
        }
        if (this.createtime != null) {
            describeString.append("创建时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createtime) + "，");
        }
        if (this.modifytime != null) {
            describeString.append("更新时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.modifytime) + "，");
        }
        if (this.creator != null) {
            describeString.append("创建人:" + this.creator + "，");
        }
        if (this.modifier != null) {
            describeString.append("更新人:" + this.modifier + "，");
        }
        return describeString.toString();
    }

    public String getBins() {
        return bins;
    }

    public void setBins(String bins) {
        this.bins = bins;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public short getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(short exchangeType) {
        this.exchangeType = exchangeType;
    }

}
