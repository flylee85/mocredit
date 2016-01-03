package com.mocredit.base.util;

/**
 * 银行列表
 * 
 * @author liaoying Created on 2015年8月11日
 *
 */
public enum Banks {
	/**
	 * 中信银行
	 */
	CITIC("citic"), /**
					 * 民生银行
					 */
	CMBC("cmbc");
	private String name;

	private Banks(String bank) {
		this.name = bank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
