package com.mocredit.manage.service;

import java.util.List;

import com.mocredit.manage.model.OptLog;

/**
 * 机具管理操作日志
 * 
 * @author lenovo
 *
 */
public interface OperLogService {
	public static final String ADD = "新增机具";
	public static final String UPDATE = "修改机具";
	public static final String DELETE = "删除机具";

	/**
	 * 新增日志
	 * 
	 * @param user
	 *            操作人
	 * @param operate
	 *            操作
	 * @param data
	 *            数据
	 * @param info
	 *            描述
	 * @param relId
	 *            关联ID
	 * @return
	 */
	int add(String user, String operate, String data, String info, String relId);

	/**
	 * 获得机具的操作记录列表
	 * 
	 * @param refId
	 * @return
	 */
	List<OptLog> getLogByRefId(String refId);

}
