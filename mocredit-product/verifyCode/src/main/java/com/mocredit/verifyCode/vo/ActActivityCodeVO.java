package com.mocredit.verifyCode.vo;

import com.mocredit.base.enums.ActActivityCodeOperType;
import com.mocredit.verifyCode.model.ActActivityStore;
import com.mocredit.verifyCode.model.TActivityCode;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * VO
 * 用于封装 码导入的时候存放的数据
 * Created by YHL on 15/7/17 13:46.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class ActActivityCodeVO {

    private String activityId;

    private String activityName;

    /**
     * 小票标题
     **/
    private String ticketTitle;

    /**
     * 小票内容
     **/
    private String ticketContent;

    /**
     * POS机验证成功提示
     **/
    private String posSuccessMsg;

    /**
     * 验证成功通知短信
     **/
    private String successSmsMsg;

    //---更新活动的时候，会用到如下字段,存放变更信息------

    /**
     * 活动发行方id
     **/
    private String enterpriseId;
    /**
     * 活动发行方名称
     **/
    private String enterpriseName;

    /**
     * 合同ID
     **/
    private String contractId;
    /**
     * 价格
     **/
    private BigDecimal amount;
    /**
     * 开始时间
     **/
    private Date startTime;
    /**
     * 结束时间
     **/
    private Date endTime;
    /**
     * 选择时间，多选的话用英文逗号(,)分隔
     **/
    private String selectDate;

    /**
     * 最大次数
     **/
    private Integer maxNumber;

    private String outCode;//活动外部编码
    private String enterpriseCode;
    private String customName;
    private String orderCode;//发码批次
    private String activityCode;
    private String exchangeChannel;//兑换渠道
    private String status; //活动状态
    //-----------------------------

    /**
     * 操作类型
     **/
    private ActActivityCodeOperType operType;

    /**
     * 存放此次导入的所有的码
     **/
    List<TActivityCode> activityCodeList;

    /**
     * 活动与门店的映射关系
     **/
    List<ActActivityStore> actActivityStores;


    public List<TActivityCode> getActivityCodeList() {
        return activityCodeList;
    }

    public void setActivityCodeList(List<TActivityCode> activityCodeList) {
        this.activityCodeList = activityCodeList;
    }

    public List<ActActivityStore> getActActivityStores() {
        return actActivityStores;
    }

    public void setActActivityStores(List<ActActivityStore> actActivityStores) {
        this.actActivityStores = actActivityStores;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public ActActivityCodeOperType getOperType() {
        return operType;
    }

    public String getExchangeChannel() {
        return exchangeChannel;
    }

    public void setExchangeChannel(String exchangeChannel) {
        this.exchangeChannel = exchangeChannel;
    }

    public void setOperType(ActActivityCodeOperType operType) {
        this.operType = operType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
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

    public boolean codeDataIsEmpty() {
        return StringUtils.isEmpty(activityName) && StringUtils.isEmpty(enterpriseId) && StringUtils.isEmpty(enterpriseName) && StringUtils.isEmpty(contractId) && StringUtils.isEmpty(amount) && StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime) && StringUtils.isEmpty(selectDate) && StringUtils.isEmpty(maxNumber) && StringUtils.isEmpty(outCode) && StringUtils.isEmpty(enterpriseCode) && StringUtils.isEmpty(activityCode) && StringUtils.isEmpty(exchangeChannel);
    }
}
