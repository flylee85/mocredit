/**
 * 
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 * 
 */
public enum ApiUrlCodeType {
	CODE("01", "验码订单"), INTEGRAL("02", "积分订单");
	private String value;
	private String text;

	ApiUrlCodeType(String value, String text) {
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
