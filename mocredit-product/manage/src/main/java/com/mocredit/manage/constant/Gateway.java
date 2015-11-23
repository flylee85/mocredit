package com.mocredit.manage.constant;

/**
 * 网关枚举
 * 
 * @author liaoy
 * @date 2015年11月23日
 */
public enum Gateway {
	/**
	 * 新网关
	 */
	NEW("01"),
	/**
	 * 老网关
	 */
	OLD("02"),;
	private String value;

	private Gateway(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
