package com.mocredit.recharge.service;

/**
 * 访问日志
 * 
 * @author lenovo
 *
 */
public interface RequestLogService {
	/**
	 * 插入日志
	 * 
	 * @param ip
	 * @param method
	 * @param data
	 */
	void addLog(String ip, String method, String data);
}
