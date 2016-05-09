package cn.m.mt.util;
/**
 * Description: 二维码重复异常
 * Copyright (c) Department of Research and Development/Beijing/emay.
 * All Rights Reserved.
 * @version 1.0  2011-6-3 上午11:30:31  by 凡红恩（fanhongen@emay.cn）创建
 */
public class BarcodeRepeatException extends Exception {

	private static final long serialVersionUID = 8245204700367589640L;
	private String errorcode;
	private String mes;
	
	public BarcodeRepeatException(String mes) {
		super(mes);
		this.mes = mes;
	}
	public BarcodeRepeatException(String errorcode, String mes) {
		super(mes);
		this.errorcode = errorcode;
		this.mes = mes;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	
	
	
	
}
