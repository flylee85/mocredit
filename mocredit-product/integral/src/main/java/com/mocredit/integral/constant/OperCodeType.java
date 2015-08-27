package com.mocredit.integral.constant;

public enum OperCodeType {
	// 1导入 2 更新 3 取消 4 启用
	IMPORT(1, "导入"), UPDATE(2, "更新"), CANCEL(3, "取消"), ENABLE(4, "启用");
	private Integer value;
	private String text;

	OperCodeType(Integer value, String text) {
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
