package com.mocredit.bank.entity;

public class BankLog {
	private String uuid;
	private String cardNum;
	private String bank;
	private String url;
	private String operate;
	private String reqParam;
	private String respResult;
	private int requestId;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String card_num) {
		this.cardNum = card_num;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operator) {
		this.operate = operator;
	}
	public String getReqParam() {
		return reqParam;
	}
	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
	}
	public String getRespResult() {
		return respResult;
	}
	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	
}
