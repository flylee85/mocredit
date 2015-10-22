package com.mocredit.base.exception;


/**
 * 业务操作异常，必须初始化异常消息。
 * @author lishoukun
 * @date 2015/07/24
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098068479494978884L;

	/**
	 * 构造函数
	 * @param message
	 */
	public BusinessException(String message){
		super(message);
	}
}
