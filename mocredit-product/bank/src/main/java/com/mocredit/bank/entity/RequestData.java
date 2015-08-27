package com.mocredit.bank.entity;

public class RequestData {
	private String bank = ""; // M //银行代码
	private String device = ""; // M //终端号
	private String activityId = ""; // M//活动ID
	// private String activityName = "";// 活动名
	private String orderId = "";// 订单号
	private int requestId;
	// private String posId = "";// M//POS流水号
	// private String posTime = "";// M //POS交易时间
	private int shopId;// M//销售商户号
	// private String shopName = "";// M//销售商户名
	// private String storeId = "";// M 门店ID
	// private String storeName = "";// M 门店名称
	private String merchantId = "";// M//商户号（银行
	private String merchantName = "";// M//商户名（银
	private String merchantPassword = "";//// ,M//销
	private String terminalId = "";// 终端ID（虚拟）
	private String cardNum = "";// M//卡号
	private String transAmt = ""; // M交易金额
	private String productType = ""; // O //产品代码?????
	private String expiredDate = "";// O卡有效期
	private String cvv2 = "";// cvv2// O
	private String chIdNum = ""; // C 持卡人证件号码
	private String chName = ""; // C 持卡人姓名
	private String chMobile = "";// C持卡人手机号码
	private String secondTrack = ""; // C第二磁道
	private String thirdTrack = ""; // C第三磁道
	private String csc4 = "";// C卡片后四位
	private String dynamicPwd = "";// C动态密码

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	// public String getActivityName() {
	// return activityName;
	// }
	//
	// public void setActivityName(String activityName) {
	// this.activityName = activityName;
	// }

	// public String getPosId() {
	// return posId;
	// }
	//
	// public void setPosId(String posId) {
	// this.posId = posId;
	// }
	//
	// public String getPosTime() {
	// return posTime;
	// }
	//
	// public void setPosTime(String posTime) {
	// this.posTime = posTime;
	// }

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	// public String getShopName() {
	// return shopName;
	// }
	//
	// public void setShopName(String shopName) {
	// this.shopName = shopName;
	// }
	//
	// public String getStoreId() {
	// return storeId;
	// }
	//
	// public void setStoreId(String storeId) {
	// this.storeId = storeId;
	// }
	//
	// public String getStoreName() {
	// return storeName;
	// }
	//
	// public void setStoreName(String storeName) {
	// this.storeName = storeName;
	// }

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantPassword() {
		return merchantPassword;
	}

	public void setMerchantPassword(String merchantPassword) {
		this.merchantPassword = merchantPassword;
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

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getChIdNum() {
		return chIdNum;
	}

	public void setChIdNum(String chIdNum) {
		this.chIdNum = chIdNum;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getChMobile() {
		return chMobile;
	}

	public void setChMobile(String chMobile) {
		this.chMobile = chMobile;
	}

	public String getSecondTrack() {
		return secondTrack;
	}

	public void setSecondTrack(String secondTrack) {
		this.secondTrack = secondTrack;
	}

	public String getThirdTrack() {
		return thirdTrack;
	}

	public void setThirdTrack(String thirdTrack) {
		this.thirdTrack = thirdTrack;
	}

	public String getCsc4() {
		return csc4;
	}

	public void setCsc4(String csc4) {
		this.csc4 = csc4;
	}

	public String getDynamicPwd() {
		return dynamicPwd;
	}

	public void setDynamicPwd(String dynamicPwd) {
		this.dynamicPwd = dynamicPwd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

}
