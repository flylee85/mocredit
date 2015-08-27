package com.mocredit.integral.constant;

public enum OrderStatus {
	FINISH(1, "交易完成"), REVOKE(2, "交易撤销");
	private Integer value;
	private String text;

	OrderStatus(Integer value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
