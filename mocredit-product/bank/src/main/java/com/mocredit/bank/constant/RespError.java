package com.mocredit.bank.constant;

public enum RespError {
	PARAM_ERROR("001", "参数错误"), 
	SYETEM_ERROR("002", "系统错误"), 
	LOGIN_ERROR("003", "登录失败"), 
	PAY_ERROR("004", "支付失败"),
	HAS_REVERSED("005", "已撤销成功"),
	NO_REPORT("006","交易记录不存在"),
	INVALID_SHOP("007","该商户没有权限");
	private String errorCode;
	private String errorMsg;

	private RespError(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
