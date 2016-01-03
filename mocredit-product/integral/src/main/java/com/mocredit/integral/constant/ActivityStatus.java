package com.mocredit.integral.constant;

public enum ActivityStatus {
	START("01", "启用"), STOP("02", "停止");
	private String value;
	private String text;

	ActivityStatus(String value, String text) {
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
