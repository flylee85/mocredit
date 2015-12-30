package com.mocredit.order.constant;

/**
 * Created by ytq on 2014/11/19.
 */
public enum BaseExportTitle {
	ORDER_ID("订单编号"), PUB_ENTERPRISE("发行商"), SUP_ENTERPRISE("兑换商"), STORE("门店"), ACTIVITY(
			"活动名称"), START_TIME("启用时间"), END_TIME("结束时间"), STATUS("状态"), BANK(
			"银行"), CODE("码"), CARD_NUM("银行卡号"), INTEGRAL("积分");

	private String text;

	BaseExportTitle(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
