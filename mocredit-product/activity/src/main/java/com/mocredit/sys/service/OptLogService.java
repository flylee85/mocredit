package com.mocredit.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.sys.model.OptLog;

/**
 * 
 * 操作日志-Service接口
 * @author lishoukun
 * @date 2015/07/18
 */
@Service
public interface OptLogService {
	/**
	 * 获取一条操作日志，根据主键
	 * @param id
	 * @return
	 */
	OptLog getOptLogById(String id);
	/**
	 * 获取一条操作日志，随机获取
	 * @return
	 */
	OptLog getOptLogByRand();
	/**
	 * 获取一条操作日志，根据条件
	 * @param optLogMap
	 * @return
	 */
	OptLog getOptLog(Map<String,Object> optLogMap);
	/**
	 * 查询操作日志列表，根据条件
	 * @param optLogMap
	 * @return
	 */
	List<OptLog> queryOptLogList(Map<String,Object> optLogMap);
	/**
	 * 获取操作日志总数量，根据条件
	 * @param optLogMap
	 * @return
	 */
	Integer getOptLogTotal(Map<String,Object> optLogMap);
	/**
	 * 查询操作日志分页信息，根据条件
	 * 
	 * @param optLogMap 请求参数
	 * @param currentPage 当前页
	 * @param pageSize 每页条数
	 * @return
	 */
	public PageInfo<OptLog> queryOptLogPage(Map<String, Object> optLogMap,Integer pageNum, Integer pageSize);
	/**
	 * 添加操作日志
	 * @param optLog
	 * @return
	 */
	public Integer addOptLog(OptLog optLog);
	/**
	 * 添加操作日志
	 * @param optCode,操作编码
	 * @param operator,操作人
	 * @param operInfo,说明
	 * @return
	 */
	public Integer addOptLog(String optCode,String operator,String optInfo);
	/**
	 * 添加操作日志
	 * @param optCode,操作编码
	 * @param operator,操作人
	 * @return
	 */
	public Integer addOptLog(String optCode,String operator);
	/**
	 * 更新操作日志
	 * @param optLog
	 * @return
	 */
	public Integer updateOptLog(OptLog optLog);
	/**
	 * 删除操作日志,根据主键
	 * @param id
	 * @return
	 */
	public Integer deleteOptLogById(String id);
	/**
	 * 批量删除操作日志,根据主键集合
	 * @param ids
	 * @return
	 */
	public Integer deleteOptLogsByIds(String ids);
	
	/**
	 * 删除操作日志,根据条件
	 * @param optLogMap
	 * @return
	 */
	public Integer deleteOptLog(Map<String,Object> optLogMap);
}
