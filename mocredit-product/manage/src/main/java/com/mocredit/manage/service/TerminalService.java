package com.mocredit.manage.service;

import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.manage.constant.OperType;
import com.mocredit.manage.model.Terminal;

/**
 * 企业业务类
 * 
 * @author liaoy
 * @date 2015年11月3日
 */
public interface TerminalService {

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Terminal> getPage(String key, String storeId, int pageNum, int pageSize);

	/**
	 * 新增
	 * 
	 * @param enterprise
	 * @return
	 */
	int add(Terminal terminal);

	/**
	 * 修改
	 * 
	 * @param enterprise
	 * @return
	 */
	int update(Terminal terminal);

	/**
	 * 删除/批量删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(String id);

	Terminal getTerminalById(String id);

	/**
	 * 获取门店商户信息
	 * 
	 * @param storeId
	 * @return
	 */
	Terminal getStoreInfo(String storeId);

	void synGateway(Object newTerminal, OperType oper);

	void synIntegral(Terminal newTerminal, Terminal oldTerminal, OperType oper);
}
