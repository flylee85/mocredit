package com.yimiehuijin.codeandbonuslibrary.data;

import java.io.Serializable;

public class BonusConsumeResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean success;
	public String state;
	public String errorCode;
	public String errorMsg;
	public String errorMes;
	public String printInfo;
	public String qr;
	public String data;
}
