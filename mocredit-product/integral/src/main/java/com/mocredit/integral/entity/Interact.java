package com.mocredit.integral.entity;

public class Interact {
	private String id;
	private String ip;
	private String request;
	private String response;
	private String reqInterface;
	private String orderId;
	private String timestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReqInterface() {
		return reqInterface;
	}
	public void setReqInterface(String reqInterface) {
		this.reqInterface = reqInterface;
	}
	
	
}
