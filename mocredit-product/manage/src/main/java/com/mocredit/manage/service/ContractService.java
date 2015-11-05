package com.mocredit.manage.service;

import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.manage.model.Contract;

/*
 * 合同业务类
 */
public interface ContractService {

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Contract> getPage(String key,String enterpriseId, int pageNum, int pageSize);

	/**
	 * 新增
	 * 
	 * @param enterprise
	 * @return
	 */
	int add(Contract contract);

	/**
	 * 修改
	 * 
	 * @param enterprise
	 * @return
	 */
	int update(Contract contract);

	/**
	 * 删除/批量删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);

	Contract getContractById(String id);
}
