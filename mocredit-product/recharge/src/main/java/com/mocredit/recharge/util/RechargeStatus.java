package com.mocredit.recharge.util;

/**
 * 充值状态
 * 
 * @author lenovo
 *
 */
public enum RechargeStatus {
	/**
	 * 充值请求发送中
	 */
	SENDING(0),
	/**
	 * 充值请求已发送
	 */
	SENDED(1),
	/**
	 * 充值成功
	 */
	SUCCEED(2),
	/**
	 * 充值失败（撤销）
	 */
	FAILED(3),
	/**
	 * 充值平台找不到订单
	 */
	NOT_FOUND(4);
	private int value;

	private RechargeStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
