package com.mocredit.integral.service;

/**
 * 业务公共模板
 * 
 * @author ytq
 * 
 * @param <T>
 */
public interface BaseService<T> {
	boolean save(T t);
}
