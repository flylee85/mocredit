package com.mocredit.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.IDUtil;
import com.mocredit.sys.model.OptLog;
import com.mocredit.sys.persitence.OptLogMapper;
import com.mocredit.sys.service.OptLogService;

/**
 * 
 * 操作日志-Service映射层
 * @author lishoukun
 * @date 2015/07/18
 */
@Service
public class OptLogServiceImpl implements OptLogService{
	
	@Autowired
	private OptLogMapper optLogDao;
	/**
	 * 获取一条操作日志，根据主键
	 * @param id
	 * @return
	 */
	public OptLog getOptLogById(String id) {
		OptLog optLogObj = optLogDao.getOptLogById(id);
		return optLogObj;
	}
	/**
	 * 获取一条操作日志，随机获取
	 * @return
	 */
	public OptLog getOptLogByRand() {
		OptLog optLogObj = optLogDao.getOptLogByRand();
		return optLogObj;
	}
	/**
	 * 获取一条操作日志，根据条件
	 * @param optLogMap
	 * @return
	 */
	public OptLog getOptLog(Map<String,Object> optLogMap) {
		List<OptLog> optLogList = optLogDao.queryOptLogList(optLogMap);
		if(optLogList.size()>0){
			return optLogList.get(0);
		}
		return null;
	}
	/**
	 * 查询操作日志列表，根据条件
	 * @param optLogMap
	 * @return
	 */
	public List<OptLog> queryOptLogList(Map<String,Object> optLogMap) {
		List<OptLog> optLogList = optLogDao.queryOptLogList(optLogMap);
		return optLogList;
	}
	/**
	 * 获取操作日志总数量，根据条件
	 * @param optLogMap
	 * @return
	 */
	public Integer getOptLogTotal(Map<String,Object> optLogMap) {
		Integer optLogTotal = optLogDao.getOptLogTotal(optLogMap);
		return optLogTotal;
	}
	/**
	 * 查询操作日志分页信息，根据条件
	 * @param optLogMap 请求参数
	 * @param currentPage  当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	public PageInfo<OptLog> queryOptLogPage(Map<String,Object> optLogMap,Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<OptLog> logs = optLogDao.queryOptLogList(optLogMap);
		return new PageInfo<>(logs);
	}
	/**
	 * 添加操作日志
	 * @param optLog
	 * @return
	 */
	public Integer addOptLog(OptLog optLog) {
		Integer affectCount = optLogDao.addOptLog(optLog);
		return affectCount;
	}
	/**
	 * 添加操作日志
	 * @param optCode,操作编码
	 * @param operator,操作人
	 * @param operInfo,说明
	 * @return
	 */
	public Integer addOptLog(String optCode,String operator,String optInfo) {
		OptLog optLog = new OptLog();
		optLog.setId(IDUtil.getID());
		optLog.setOptCode(optCode);
		optLog.setOperator(operator);
		optLog.setOptTime(new Date());
		optLog.setOptInfo(optInfo);
		Integer affectCount = optLogDao.addOptLog(optLog);
		return affectCount;
	}
	/**
	 * 添加操作日志
	 * @param optCode,操作编码
	 * @param operator,操作人
	 * @return
	 */
	public Integer addOptLog(String optCode,String operator) {
		OptLog optLog = new OptLog();
		optLog.setId(IDUtil.getID());
		optLog.setOptCode(optCode);
		optLog.setOperator(operator);
		optLog.setOptTime(new Date());
		Integer affectCount = optLogDao.addOptLog(optLog);
		return affectCount;
	}
	/**
	 * 更新操作日志
	 * @param optLog
	 * @return
	 */
	public Integer updateOptLog(OptLog optLog) {
		Integer affectCount = optLogDao.updateOptLog(optLog);
		return affectCount;
	}
	/**
	 * 删除操作日志,根据主键
	 * @param id
	 * @return
	 */
	public Integer deleteOptLogById(String id) {
		Integer affectCount = optLogDao.deleteOptLogById(id);
		return affectCount;
	}
	/**
	 * 批量删除操作日志,根据主键集合
	 * @param ids
	 * @return
	 */
	public Integer deleteOptLogsByIds(String ids) {
		Integer affectCount = 0;
		String[] idArray = ids.split(",");
		for(String id : idArray){
			affectCount += optLogDao.deleteOptLogById(id);
		}
		return affectCount;
	}
	
	/**
	 * 删除操作日志,根据条件
	 * @param optLogMap
	 * @return
	 */
	public Integer deleteOptLog(Map<String,Object> optLogMap) {
		Integer affectCount = optLogDao.deleteOptLog(optLogMap);
		return affectCount;
	}
}
