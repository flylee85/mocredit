package com.mocredit.activity.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.Activity;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageInfo;

/**
 *
 *
 * 活动-控制类 活动管理的入口类，所有活动管理相关的后台入口方法，都是在此类中
 * 
 * @author lishoukun
 * @date 2015/07/08
 */
@RestController
@RequestMapping("/activity/")
public class ActivityController {
	// 引入活动Service类
	@Autowired
	private ActivityService activityService;

	/**
	 * 查询活动分页数据，根据条件和分页参数
	 * 
	 * @param reqMap
	 *            请求参数
	 * @param draw
	 *            查询标示，返回数据时将该值原封不动返回
	 * @param start
	 *            开始数
	 * @param length
	 *            查询数
	 * @return json object string
	 */
	@RequestMapping("/queryActivityPage")
	@ResponseBody
	public String queryActivityPage(@RequestParam Map<String, Object> reqMap, Integer draw, Integer start,
			Integer length) {
		// 定义返回页面的对象
		ResponseData responseData = new AjaxResponseData();
		// 简单计算页数，当前页数=开始条数/搜索条数+1
		if (start == null) {
			start = 0;
		}
		if (length == null) {
			length = 10;
		}
		int currentPage = start / length + 1;
		try {
			// 根据参数查询分页信息对象
			PageInfo<Activity> pageMap = activityService.queryActivityPage(reqMap, draw, currentPage, length);
			// 重构新的分页对象，为适应前端分页插件
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("draw", draw);// 查询标示,原值返回
			newMap.put("recordsTotal", pageMap.getTotal());// 总数量
			newMap.put("recordsFiltered", pageMap.getTotal());// 过滤后的总数量，暂未用到
			newMap.put("data", pageMap.getList());// 数据列表
			String resultStr = JSON.toJSONString(newMap);// 将新的分页对象返回页面
			// 返回页面数据
			return resultStr;
		} catch (Exception e) {
			// 如果抛出异常，则将返回页面的对象设置为false
			e.printStackTrace();
			responseData.setSuccess(false);
			responseData.setErrorMsg(e.getMessage(), e);
		}
		// 返回页面数据
		return JSON.toJSONString(responseData);
	}
}
