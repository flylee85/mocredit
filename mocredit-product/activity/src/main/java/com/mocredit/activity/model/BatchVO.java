package com.mocredit.activity.model;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 发码批次码-视图类
 * @author lishoukun
 * @date 2015/07/13
 */
public class BatchVO implements Serializable{
	//序列化
	private static final long serialVersionUID = 6905308258132311722L;
	
	private String activityId ;//活动Id
	
	private String activityName ;//活动名称
	
	private String operType ;//操作编码
	
	private String ticketTitle;//活动小票标题
	
	private String ticketContent;//小票内容
	
	private String posSuccessMsg;//验证成功
	
	private String successSmsMsg;//验证成功提示
	private String status;//活动状态
	
	private List<ActivityStore> actActivityStores ;//活动门店关联列表
	
	private List<BatchCodeVO> activityCodeList ;//活动发码批次和码的关联列表

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

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public List<ActivityStore> getActActivityStores() {
		return actActivityStores;
	}

	public void setActActivityStores(List<ActivityStore> actActivityStores) {
		this.actActivityStores = actActivityStores;
	}

	public List<BatchCodeVO> getActivityCodeList() {
		return activityCodeList;
	}

	public void setActivityCodeList(List<BatchCodeVO> activityCodeList) {
		this.activityCodeList = activityCodeList;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
