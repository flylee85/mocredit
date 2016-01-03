/**
 * 
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 * 
 */
public enum OrderType {
	CHECK_CODE_ORDER("01", "验码订单"), INTEGRAL_ORDER("02", "积分订单");
	private String value;
	private String text;

	OrderType(String value, String text) {
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
