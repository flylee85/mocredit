package com.mocredit.recharge.model;

public class Code {
	private String orderId;// 订单号
	private String code;// 码
	private int codeId;// 码ID
	private String phone;// 手机
	private String activityOutCode;// 活动外部编码
	private int amount;// 金额
	private boolean isSuccess;
	private String errorMsg;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCodeId() {
		return codeId;
	}

	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getActivityOutCode() {
		return activityOutCode;
	}

	public void setActivityOutCode(String activityOutCode) {
		this.activityOutCode = activityOutCode;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
