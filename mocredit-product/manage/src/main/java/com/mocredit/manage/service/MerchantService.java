package com.mocredit.manage.service;

import java.util.List;

import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.manage.model.Merchant;

/**
 * 商户业务类
 * 
 * @author liaoy
 * @date 2015年11月3日
 */
public interface MerchantService {

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Merchant> getPage(String key,String contractId, int pageNum, int pageSize);

	/**
	 * 获得所有的商户
	 * 
	 * @return
	 */
	List<Merchant> getAll();

	/**
	 * 新增
	 * 
	 * @param enterprise
	 * @return
	 */
	int add(Merchant enterprise);

	/**
	 * 修改
	 * 
	 * @param enterprise
	 * @return
	 */
	int update(Merchant enterprise);

	/**
	 * 删除/批量删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);

	Merchant getMerchantById(String id);
}
