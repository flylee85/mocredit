package com.mocredit.gateway.service;

/**
 * 业务公共模板
 *
 * @param <T>
 * @author ytq
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
