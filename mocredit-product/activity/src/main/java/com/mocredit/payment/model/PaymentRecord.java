package com.mocredit.payment.model;

import java.io.Serializable;
import java.util.Date;

public class PaymentRecord implements Serializable{
	private long id;
	private String cardBank;
	private String machOrderId;
	private String referId;
	private String transactionTime;
	private String amount;
	private String signatureString;
	private String cardValidateData;
	private String authorizationNo;
	private String status;
	private String cardId;
	private String pinpadId;
	private String merchantID;
	private String goodsDetail;
	private String goodsName;
	private String goodsType;
	private String ownerName;
	private String incomeBankId;
	private String transSeq;
	private String merchantNumber;
	private Date createOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getMachOrderId() {
		return machOrderId;
	}

	public void setMachOrderId(String machOrderId) {
		this.machOrderId = machOrderId;
	}

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSignatureString() {
		return signatureString;
	}

	public void setSignatureString(String signatureString) {
		this.signatureString = signatureString;
	}

	public String getCardValidateData() {
		return cardValidateData;
	}

	public void setCardValidateData(String cardValidateData) {
		this.cardValidateData = cardValidateData;
	}

	public String getAuthorizationNo() {
		return authorizationNo;
	}

	public void setAuthorizationNo(String authorizationNo) {
		this.authorizationNo = authorizationNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPinpadId() {
		return pinpadId;
	}

	public void setPinpadId(String pinpadId) {
		this.pinpadId = pinpadId;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getIncomeBankId() {
		return incomeBankId;
	}

	public void setIncomeBankId(String incomeBankId) {
		this.incomeBankId = incomeBankId;
	}

	public String getTransSeq() {
		return transSeq;
	}

	public void setTransSeq(String transSeq) {
		this.transSeq = transSeq;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

}
