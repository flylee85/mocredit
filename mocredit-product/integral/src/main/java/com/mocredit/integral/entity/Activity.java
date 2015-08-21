package com.mocredit.integral.entity;
/**
 * 活动信息，由活动模块推送
 * @author ytq
 *
 */
public class Activity {
/**
 * uuid                 int,
   activity_id          int comment '活动ID',
   activity_name        varchar(200) comment '活动名',
   activity_status      smallint comment '活动状态',
   product_type         varchar(10) comment '银行活动代码',
   amount               decimal(10,0) comment '金额'
 */
	private Integer uuid;
	private Integer activityId;
	private String activityName;
	private Integer acitvityStatus;
	private String productType;
	private Integer amount;
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
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getAcitvityStatus() {
		return acitvityStatus;
	}
	public void setAcitvityStatus(Integer acitvityStatus) {
		this.acitvityStatus = acitvityStatus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
