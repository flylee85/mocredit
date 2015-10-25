package com.mocredit.activitysys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.activitysys.model.Data;
import com.mocredit.activitysys.service.DataService;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;

/**
 * 数据访问类，从其他子系统或外部系统获取数据都经过该类
 * 
 * @author liaoy
 *
 */
@RestController
@RequestMapping("/third/")
public class DataController {
	@Autowired
	private DataService service;
	@RequestMapping("/getData")
	public String getData(String field) {
		if(null==field){
			return "";
		}
		ResponseData responseData=new AjaxResponseData();
		responseData.setData(service.getData(field));
		return JSON.toJSONString(responseData);
	}
	@RequestMapping("/getStores")
	public String getStores(Map<String, String>param){
		if(null==param){
			return "";
		}
		ResponseData responseData=new AjaxResponseData();
		responseData.setData(service.getStores(param));
		return JSON.toJSONString(responseData);
	}
}

