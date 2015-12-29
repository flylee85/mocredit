package com.mocredit.recharge.util;

/**
 * 充值类型编码
 * 
 * @author lenovo
 *
 */
public enum RechargeTypeCode {
	/**
	 * 欧飞充值
	 */
	OFCARD(1),
	/**
	 * 易充宝充值
	 */
	YICHONGBAO(2);
	private int value;

	private RechargeTypeCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
