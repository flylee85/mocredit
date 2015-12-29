package com.mocredit.recharge.model;

import java.util.Date;

public class Record {
	private long id;
	private String orderid; // 订单号
	private Date createtime;// 添加时间
	private int status;// 0:正在发送;1发送成功;2充值成功;3充值失败
	private String errmessage;// 错误描述
	private double amount;// 充值金额
	private String mctype;// 充值类型 欧飞充值/易充宝
	private String phone;// 充值手机号
	private String code; // 充值码
	private int codeid;// 码ID
	private Date noticetime;// 通知支付成功时间
	private String server;// 服务器域名

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCodeid() {
		return codeid;
	}

	public void setCodeid(int codeid) {
		this.codeid = codeid;
	}

	public String getOrderid() {
		return orderid;
	}

	public Date getNoticetime() {
		return noticetime;
	}

	public void setNoticetime(Date noticetime) {
		this.noticetime = noticetime;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrmessage() {
		return errmessage;
	}

	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMctype() {
		return mctype;
	}

	public void setMctype(String mctype) {
		this.mctype = mctype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
