package com.mocredit.bank.constant;

/**
 * 商户（终端）状态
 * 
 * @author liaoying Created on 2015年8月24日
 *
 */
public enum MerchantStatus {
	VALID((short) 1, "正常"), INVALID((short) 2, "异常"), SIGNIN((short) 3, "签到"), SIGNOUT((short) 4, "签退");
	private short status;
	private String name;

	private MerchantStatus(short status, String name) {
		this.status = status;
		this.name = name;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
