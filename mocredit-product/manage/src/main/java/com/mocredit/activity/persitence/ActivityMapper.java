package com.mocredit.activity.persitence;

import java.util.List;
import java.util.Map;

import com.mocredit.activity.model.Activity;

/**
 * 
 * 活动-Dao 接口
 * @author lishoukun
 * @date 2015/07/08
 */
public interface ActivityMapper {
	//查询活动列表，根据条件
	List<Activity> queryActivityList(Map<String,Object> activityMap);
}
