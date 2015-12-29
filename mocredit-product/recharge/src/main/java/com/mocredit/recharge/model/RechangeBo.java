package com.mocredit.recharge.model;

public class RechangeBo implements java.io.Serializable {
	
	private int retcode;//返回类型 1为成功 其他为错误
	private String errmsg;//错误内容
	private String orderid;//CP流水号
	private String cardid;//充值类型
	private String cardnum;//充值金额
	private String cardname;//充值名称
	private String ordercash;//现金
	private String sporderid;//订单编号
	private String phone;//game_userid;//手机号
	private int status;//;game_state;//充值状态如果成功将为1，澈消(充值失败)为9，充值中为0
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public String getOrdercash() {
		return ordercash;
	}
	public void setOrdercash(String ordercash) {
		this.ordercash = ordercash;
	}
	public String getSporderid() {
		return sporderid;
	}
	public void setSporderid(String sporderid) {
		this.sporderid = sporderid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "RechangeBo [retcode=" + retcode + ", errmsg=" + errmsg
				+ ", orderid=" + orderid + ", cardid=" + cardid + ", cardnum="
				+ cardnum + ", cardname=" + cardname + ", ordercash="
				+ ordercash + ", sporderid=" + sporderid + ", phone=" + phone
				+ ", status=" + status + "]";
	}
	
}
