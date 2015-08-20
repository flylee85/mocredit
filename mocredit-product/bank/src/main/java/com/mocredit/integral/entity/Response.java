package com.mocredit.integral.entity;

/**
 * 响应对象
 * 
 * @author liaoying Created on 2015年8月17日
 *
 */
public class Response {
	private String code;
	private String errMsg;

	public Response(String code, String errMsg) {
		super();
		this.code = code;
		this.errMsg = errMsg;
	}

	public Response() {
		super();
	}

	public Response(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
