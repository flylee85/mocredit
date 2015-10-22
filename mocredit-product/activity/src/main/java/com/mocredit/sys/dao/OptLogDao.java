package com.mocredit.sys.dao;

import java.util.List;
import java.util.Map;

import com.mocredit.sys.model.OptLog;

/**
 * 
 * 操作日志-Dao 接口
 * @author lishoukun
 * @date 2015/07/18
 */
public interface OptLogDao {
	public final static String PREFIX = OptLogDao.class.getName();
	/*
	 * 操作日志
	 */
	//获取一条操作日志，根据主键
	OptLog getOptLogById(String id);
	//获取一条操作日志，随机获取
	OptLog getOptLogByRand();
	//查询操作日志列表，根据条件
	List<OptLog> queryOptLogList(Map<String,Object> optLogMap);
	//获取操作日志总数量，根据条件
	int getOptLogTotal(Map<String,Object> optLogMap);
	//添加一条操作日志记录
	int addOptLog(OptLog optLog);
	//修改一条操作日志记录
	int updateOptLog(OptLog optLog);
	//删除一条操作日志记录 ，根据主键
	int deleteOptLogById(String id);
	//删除操作日志记录 ，根据条件
	int deleteOptLog(Map<String,Object> optLogMap);
}
