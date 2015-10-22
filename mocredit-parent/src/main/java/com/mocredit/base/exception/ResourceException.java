package com.mocredit.base.exception;

/**
 * 获取资源文件异常
 * 
 * @author lishoukun
 * @date 2015/01/10
 */
public class ResourceException extends RuntimeException {
	/*
	 * 序列化
	 */
	private static final long serialVersionUID = 6626498039107446902L;
	/*
	 * 错误信息
	 */
	private String errorMsg;
	/*
	 * 资源名称
	 */
	private String resourceName;

	public ResourceException(String resourceName, String errorMsg) {
		super("获取资源文件[" + resourceName + "]发生错误,错误原因：[" + errorMsg + "]");
		setErrorMsg(errorMsg);
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
