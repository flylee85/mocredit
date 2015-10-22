package com.mocredit.activity.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Activity;
import com.mocredit.activity.model.SelectStoreVO;
import com.mocredit.base.pagehelper.PageInfo;

/**
 * 
 * 活动-Service接口
 * @author lishoukun
 * @date 2015/07/08
 */
@Service
public interface ActivityService {
	/**
	 * 获取一条活动，根据主键
	 * @param id
	 * @return
	 */
	Activity getActivityById(String id);
	/**
	 * 获取一条活动，随机获取
	 * @return
	 */
	Activity getActivityByRand();
	/**
	 * 获取一条活动，根据条件
	 * @param activityMap
	 * @return
	 */
	Activity getActivity(Map<String,Object> activityMap);
	/**
	 * 查询活动列表，根据条件
	 * @param activityMap
	 * @return
	 */
	List<Activity> queryActivityList(Map<String,Object> activityMap);
	/**
	 * 获取活动总数量，根据条件
	 * @param activityMap
	 * @return
	 */
	Integer getActivityTotal(Map<String,Object> activityMap);
	/**
	 * 查询活动分页信息，根据条件
	 * 
	 * @param activityMap 请求参数
	 * @param currentPage 当前页
	 * @param pageSize 每页条数
	 * @return
	 */
	public PageInfo<Activity> queryActivityPage(Map<String, Object> activityMap,Integer draw,Integer pageNum, Integer pageSize);
	
	/**
	 * 查询活动管理相关选择门店分页信息，根据条件
	 * @param activityMap 请求参数
	 * @param currentPage  当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	public PageInfo<SelectStoreVO> querySelectStorePage(Map<String,Object> activityMap,Integer currentPage,Integer pageSize);
	/**
	 * 添加活动
	 * @param activity
	 * @return
	 */
	public Integer addActivity(Activity activity);
	/**
	 * 更新活动
	 * @param activity
	 * @return
	 */
	public Integer updateActivity(Activity activity);
	/**
	 * 导入联系人，通过Excel数据
	 * InputStream in 导入的流
	 * @return 影响行数
	 */
	public Map<String,Object> importCustomor(String activityId,String orderId,String remark,InputStream in);
	/**
	 * 导出发送短信Excel
	 * @param orderId
	 * @return
	 */
	public Map<String,Object> createSendSmsExcel(String orderId,HttpSession session);
	/**
	 * 删除活动,根据主键
	 * @param id
	 * @return
	 */
	public Integer deleteActivityById(String id);
	/**
	 * 批量删除活动,根据主键集合
	 * @param ids
	 * @return
	 */
	public Integer deleteActivitysByIds(String ids);
	
	/**
	 * 删除活动,根据条件
	 * @param activityMap
	 * @return
	 */
	public Integer deleteActivity(Map<String,Object> activityMap);
}
