package com.mocredit.activity.persitence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mocredit.activity.model.Activity;
import com.mocredit.activity.model.SelectStoreVO;

/**
 * 
 * 活动-Dao 接口
 * @author lishoukun
 * @date 2015/07/08
 */
public interface ActivityMapper {
	/*
	 * 活动
	 */
	//获取一条活动，根据主键
	Activity getActivityById(String id);
	//获取一条活动，随机获取
	Activity getActivityByRand();
	//查询活动列表，根据条件
	List<Activity> queryActivityList(Map<String,Object> activityMap);
	//获取活动总数量，根据条件
	int getActivityTotal(Map<String,Object> activityMap);
	//查询活动管理相关选择门店分页信息，根据条件
	List<SelectStoreVO> querySelectStore(Map<String,Object> activityMap);
	//添加一条活动记录
	int addActivity(Activity activity);
	//修改一条活动记录
	int updateActivity(Activity activity);
	//删除一条活动记录 ，根据主键
	int deleteActivityById(String id);
	//删除活动记录 ，根据条件
	int deleteActivity(Map<String,Object> activityMap);
	//为机具查询活动信息
	List<Map<String, Object>>selectForDevice(@Param("activityId") List<String> activityId);
	//为机具查询活动信息
	Map<String, Object>selectStoreInfoForDevice(@Param("snCode") String snCode);
}
