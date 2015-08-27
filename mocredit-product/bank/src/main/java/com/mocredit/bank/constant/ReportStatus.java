package com.mocredit.bank.constant;

/**
 * 交易记录状态列表
 * 
 * @author liaoying Created on 2015年8月24日
 *
 */
public enum ReportStatus {
	PAYED((short) 1, "扣减"), REVOKE((short) 2, "撤销"), REVERSAL((short) 3, "冲正"), CHECK_ACCOUNT_YES((short) 4,
			"对账平"), CHECK_ACCOUNT_NO((short) 5, "对账不平");
	private short status;
	private String name;

	private ReportStatus(short status, String name) {
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
