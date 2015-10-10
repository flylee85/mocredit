package com.mocredit.order.service;

/**
 * 业务公共模板
 * 
 * @author ytq
 * 
 * @param <T>
 */
public interface BaseService<T> {
	/**
	 * 保存信息模板方法
	 * 
	 * @param t
	 * @return
	 */
	boolean save(T t);
}
