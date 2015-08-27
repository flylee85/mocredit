package com.mocredit.bank.entity;

public class Payment {

	// Fields

	private int id;
	private int requestId;
	private String infoType = "";// 信息类型
	private String terminalID = "";// 终端号
	private String posID = "";// POS流水号
	private String posTime = "";// POS交易时间
	private String transDate = "";// 交易日期
	private String transTime = "";// 交易时间
	private String merchantID = "";// 销售商户号(银行)
	private String merchantName = "";// 销售商户名称(银行)
	private String inMerchantID = ""; // 内部商户号
	private String inMerchantName = "";// 内部商户名
	private String merchantType = "";// 商户类型，1 :现金 2:积分
	private String confirmType = "";// 查询类型 POINT 积分查询 COUNT 计数查询
	private String terminalFlag = "";// 对账终止符 Y/N
	private String password = "";// 商户密码
	private String newPassword = "";// 新商户密码
	private String batchNo = "";// 批次号
	private String serialNo = "";// 序号
	private String orderID = "";// 商户订单号
	private String pan = "";// 卡号
	private String bankOrderID = "";// 银行订单号
	private String expiredDate = "";// 卡有效期
	private String cvv2 = "";// CVV2
	private String chIdNum = "";// 持卡人证件号码
	private String chName = "";// 持卡人姓名
	private String chMobile = "";// 持卡人手机号码
	private String productType = "";// 产品代码
	private String productNum = "";// 商品数量
	private String payWay = "";// 支付方式
	private String transAmt = "";// 交易金额
	private String currCode = "";// 货币代码
	private String dividedNum = "01";// 分期期数
	private String inputType = "031";// POS输入方式 031刷卡 ,042手工

	private String posConditionCode = "";// 服务点条件码
	private String systemRefCode = "";// 系统参考号
	private String authorizeCode = "";// 授权码
	private String authDate = "";// 授权日期
	private String authTime = "";// 授权时间
	private String retCode = "";// 返回码
	private String commentRes = "";// 附加响应
	private String timeStamp = "";// 时间戳
	private String signature = "";// 签名信息
	private String secondTrack = "";// 第二磁道
	private String thirdTrack = "";// 第三磁道
	private String csc4 = "";// 卡片后四位
	private String dynamicPwd = "";// 动态密码
	private String reserved = "";// 保留域
	private String payURL = "";// 网上交易地址 下装参数
	private String token = "";// 用于验证的键值 下装参数
	private short status = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getPosID() {
		return posID;
	}

	public void setPosID(String posID) {
		this.posID = posID;
	}

	public String getPosTime() {
		return posTime;
	}

	public void setPosTime(String posTime) {
		this.posTime = posTime;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getBankOrderID() {
		return bankOrderID;
	}

	public void setBankOrderID(String bankOrderID) {
		this.bankOrderID = bankOrderID;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getDividedNum() {
		return dividedNum;
	}

	public void setDividedNum(String dividedNum) {
		this.dividedNum = dividedNum;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getPosConditionCode() {
		return posConditionCode;
	}

	public void setPosConditionCode(String posConditionCode) {
		this.posConditionCode = posConditionCode;
	}

	public String getSystemRefCode() {
		return systemRefCode;
	}

	public void setSystemRefCode(String systemRefCode) {
		this.systemRefCode = systemRefCode;
	}

	public String getAuthorizeCode() {
		return authorizeCode;
	}

	public void setAuthorizeCode(String authorizeCode) {
		this.authorizeCode = authorizeCode;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getAuthTime() {
		return authTime;
	}

	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getCommentRes() {
		return commentRes;
	}

	public void setCommentRes(String commentRes) {
		this.commentRes = commentRes;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getPayURL() {
		return payURL;
	}

	public void setPayURL(String payURL) {
		this.payURL = payURL;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInMerchantID() {
		return inMerchantID;
	}

	public void setInMerchantID(String inMerchantID) {
		this.inMerchantID = inMerchantID;
	}

	public String getInMerchantName() {
		return inMerchantName;
	}

	public void setInMerchantName(String inMerchantName) {
		this.inMerchantName = inMerchantName;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getConfirmType() {
		return confirmType;
	}

	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}

	public String getTerminalFlag() {
		return terminalFlag;
	}

	public void setTerminalFlag(String terminalFlag) {
		this.terminalFlag = terminalFlag;
	}

}