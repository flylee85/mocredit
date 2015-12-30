package com.mocredit.base.enums;

/**
 * 兑换渠道枚举
 * 
 * @author lenovo
 *
 */
public enum ExchangeChannel {
	POS("1"), RECHARGE("2");
	private String value;

	private ExchangeChannel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
