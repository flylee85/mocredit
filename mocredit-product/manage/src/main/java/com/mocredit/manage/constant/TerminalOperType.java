package com.mocredit.manage.constant;

public enum TerminalOperType {
	ADD(1), UPDATE(2), DELETE(3);
	private int oper;

	private TerminalOperType(int oper) {
		this.oper = oper;
	};

	public int getValue() {
		return this.oper;
	}
}
