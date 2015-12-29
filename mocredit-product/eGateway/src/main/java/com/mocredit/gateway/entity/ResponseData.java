package com.mocredit.gateway.entity;

import java.io.Serializable;

/**
 * Response数据对象实现类
 * 
 * @author lishoukun
 * @date 2015/07/02
 */
public class ResponseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5385196012551758394L;
	/**
	 * 错误码
	 */
	private String errorCode;
	/**
	 * 错误友好提示,例如"服务器出现异常,请稍候再试"
	 */
	private String errorMsg = "";
	/**
	 * 是否访问成功,如果后台出现异常则返回false
	 */
	private boolean success = true;
	/**
	 * 返回的内容
	 */
	private Object data = null;

	
	public ResponseData() {
		super();
	}

	public ResponseData(String errorCode, String errorMsg, boolean success) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.success = success;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
