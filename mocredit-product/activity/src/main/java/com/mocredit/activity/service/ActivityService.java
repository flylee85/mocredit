package com.mocredit.activity.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mocredit.activity.model.ActivityStoreStat;
import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Activity;
import com.mocredit.activity.model.ActivityStore;
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
	 * 根据活动id统计门店信息
	 * @param activityId
	 * @return
	 */
	public PageInfo<ActivityStoreStat> findActStoreByList(Map<String, Object> activityMap,Integer draw,Integer pageNum, Integer pageSize);

	/**
	 * 查询活动管理相关选择门店分页信息，根据条件
	 * @param activityMap 请求参数
	 * @param currentPage  当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	public PageInfo<SelectStoreVO> querySelectStorePage(Map<String,Object> activityMap,Integer currentPage,Integer pageSize);
	/**
	 * 查询活动管理相关选择门店分页信息，根据条件
	 * @param activityMap 请求参数
	 * @param currentPage  当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	public List<ActivityStore> querySelectStores(Map<String,Object> activityMap);

	public List<ActivityStore> queryStoresForSelect(Map<String,String> activityMap);
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
	/**
	 * 提取一个批次的码
	 * @param activityId
	 * @param count
	 * @return 批次ID
	 */
	public String extractedCode(String activityId,String batchName,String smsType,int count);
	/**
	 * 更新活动状态
	 * @return
	 */
	public Integer updateStatus(Activity activity);

	public Map<String,Object> getComb(String activityId);
	/**
	 * 为积分核销（为机具）查询活动信息
	 * @param activityId
	 * @param snCode
	 * @return
	 */
	public List<Map<String, Object>> getActivitiesForDevice(List<String> activityId,String snCode);

	public void sendMMSPackage(String activityId);

	/**
	 * 保存活动统计信息
	 * @param activity
	 * @return
	 */
    int saveAndUpdAct(Activity activity);
    int saveAndUpdActStore(ActivityStoreStat activityStoreStat);
	/**
	 * 根据活动id统计门店信息
	 * @param activityId
	 * @return
	 */
	public PageInfo<ActivityStoreStat> findActStoreAll(Integer pageNum, Integer pageSize);
}
