package com.mocredit.activitysys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.service.AreaService;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;

/**
 * 地区选择
 * 
 * @author liaoy
 * @date 2015年11月2日
 */
@RestController
@RequestMapping("/area")
public class AreaController {
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/getChildren/{id}", produces = { "application/json;charset=UTF-8" })
	public String getChildren(@PathVariable String id) {
		ResponseData response = new AjaxResponseData();
		try {
			response.setData(areaService.getSubArea(id));
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}
}
