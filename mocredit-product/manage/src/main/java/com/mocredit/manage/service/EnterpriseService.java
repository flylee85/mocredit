package com.mocredit.manage.service;

import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.manage.model.Enterprise;

/**
 * 企业业务类
 * @author liaoy
 * @date 2015年11月3日
 */
public interface EnterpriseService {

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Enterprise> getPage(String key, int pageNum, int pageSize);

	/**
	 * 新增
	 * 
	 * @param enterprise
	 * @return
	 */
	int add(Enterprise enterprise);

	/**
	 * 修改
	 * 
	 * @param enterprise
	 * @return
	 */
	int update(Enterprise enterprise);

	/**
	 * 删除/批量删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	Enterprise getEnterpriseById(String id);
}
