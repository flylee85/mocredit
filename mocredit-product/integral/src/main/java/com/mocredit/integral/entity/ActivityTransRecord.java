package com.mocredit.integral.entity;

import java.util.Date;

/**
 * @author ytq 2015年8月26日
 */
public class ActivityTransRecord {
	/**
   uuid                 int not null auto_increment,
   activity_id          int comment '活动ID',
   trans_date           date comment '交易日期',
   trans_count          int comment '交易数量',
	 */
	private Integer uuid;
	private Integer activityId;
	private Date transDate;
	private int transCount;
	public Integer getUuid() {
		return uuid;
	}
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	
}
