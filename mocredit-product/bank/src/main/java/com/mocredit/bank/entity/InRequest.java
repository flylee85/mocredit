package com.mocredit.bank.entity;

import java.util.Date;

public class InRequest {
	private int uuid;
	private String ip;
	private String request;
	private String reqInterface;
	private String orderId;
	private Date timestamp;
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int id) {
		this.uuid = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInterface() {
		return reqInterface;
	}
	public void setInterface(String reqInterface) {
		this.reqInterface = reqInterface;
	}
	
	
}
