package com.mocredit.integral.dto;

public class PaymentDto {
	/**
	 * @param { shopId: 商户ID cardNum:卡号 orderId:订单号 transAmt:交易金额
	 *        productType:银行内部活动代码 device:机具号 }
	 */
	private Integer shopId;
	private String cardNum;
	private String transAmt;
	private String productType;
	private String device;
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}

}
