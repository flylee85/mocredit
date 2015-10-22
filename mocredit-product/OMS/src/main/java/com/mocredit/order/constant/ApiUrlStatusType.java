/**
 * 
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 * 
 */
public enum ApiUrlStatusType {
	FREESE(0, "冻结"), RUNNING(1, "正在运行"), FINISH(2, "结束");
	private Integer value;
	private String text;

	ApiUrlStatusType(Integer value, String text) {
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
