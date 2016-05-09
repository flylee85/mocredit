package cn.m.mt.util;

/**
 * Description: 验证过程出现错误
 * Copyright (c) Department of Research and Development/Beijing/emay.
 * All Rights Reserved.
 * @version 1.0  2011-6-3 上午11:31:14  by 凡红恩（fanhongen@emay.cn）创建
 */
public class VerifyErrorException extends Exception {

	private static final long serialVersionUID = 8245204700367589640L;
	private String errorcode;
	private String mes;
	
	public VerifyErrorException(String mes) {
		super(mes);
		this.mes = mes;
	}
	public VerifyErrorException(String errorcode, String mes) {
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
