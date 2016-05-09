package cn.m.mt.util;
/**
 * Description: 必须记录日志异常
 * Copyright (c) Department of Research and Development/Beijing/emay.
 * All Rights Reserved.
 * @version 1.0  2011-6-3 上午11:30:53  by 凡红恩（fanhongen@emay.cn）创建
 */
public class MustlogException extends Exception {
	private static final long serialVersionUID = -372465758268791902L;
	String logtype;
	String mes;
	public MustlogException(String logtype, String mes) {
		super(mes);
		this.logtype = logtype;
		this.mes = mes;
	}
	public MustlogException(String mes) {
		super(mes);
		this.mes = mes;
	}
	public String getLogtype() {
		return logtype;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	
	
	
	
}
