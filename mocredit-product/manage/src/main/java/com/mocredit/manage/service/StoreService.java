package com.mocredit.manage.service;

import java.util.Map;

import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.manage.constant.OperType;
import com.mocredit.manage.model.Store;

/**
 * 企业业务类
 * 
 * @author liaoy
 * @date 2015年11月3日
 */
public interface StoreService {

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Store> getPage(Map<String, Object> paramMap, int pageNum, int pageSize);

	/**
	 * 新增
	 * 
	 * @param enterprise
	 * @return
	 */
	int add(Store store);

	/**
	 * 修改
	 * 
	 * @param enterprise
	 * @return
	 */
	int update(Store store);

	/**
	 * 删除/批量删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);

	Store getStoreById(String id);

	void synIntegral(String storeId, OperType oper);
}
