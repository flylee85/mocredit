package com.mocredit.recharge.model;

/**
 * 活动配置模型
 * 
 * @author lenovo
 *
 */
public class Activity {
	private int id;
	private String activityCode;
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
