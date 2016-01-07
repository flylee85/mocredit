package com.mocredit.activity.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Activity;
import com.mocredit.base.pagehelper.PageInfo;

/**
 * 
 * 活动-Service接口
 * 
 * @author lishoukun
 * @date 2015/07/08
 */
@Service
public interface ActivityService {
	/**
	 * 查询活动分页信息，根据条件
	 * 
	 * @param activityMap
	 *            请求参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public PageInfo<Activity> queryActivityPage(Map<String, Object> activityMap, Integer draw, Integer pageNum,
			Integer pageSize);
}
