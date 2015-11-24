package com.mocredit.manage.constant;

/**
 * 操作代码
 * 
 * @author liaoy
 * @date 2015年11月23日
 */
public enum OperType {
	ADD(1), UPDATE(2), DELETE(3);
	private int oper;

	private OperType(int oper) {
		this.oper = oper;
	};

	public int getValue() {
		return this.oper;
	}
}
