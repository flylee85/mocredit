package com.mocredit.integral.constant;

public enum ErrorCodeType {
	POST_BANK_ERROR("400", "请求Bank出错"), 
	ANA_RESPONSE_ERROR("401", "解析Bank响应出错"), 
	SYSTEM_ERROR("500", "系统出错"), 
	SAVE_DATEBASE_ERROR("501", "保存数据库出错"),
    NOT_EXIST_ACTIVITY_ERROR("502", "不存在该活动"),
    EXIST_ACTIVITY_ERROR("502", "已存在该活动"), 
    EXIST_STORE_ERROR("503", "已存在该门店"), 
    PARAM_ERROR("504", "参数错误"),
	ACTIVITY_OUT_DATE("505","不在活动时间内"),
	ACTIVITY_OUT_COUNT("506","超过活动限制次数");
	private String value;
	private String text;

	ErrorCodeType(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
