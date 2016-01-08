package com.mocredit.manage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.DateUtil;
import com.mocredit.manage.model.Store;
import com.mocredit.manage.service.AreaService;
import com.mocredit.manage.service.MerchantService;
import com.mocredit.manage.service.StoreService;
import com.mocredit.manage.vo.StoreVO;

/**
 * 企业管理
 * 
 * @author liaoy
 * @date 2015年11月2日
 */
@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private StoreService storeService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private AreaService areaService;

	@RequestMapping("/list")
	public String list(@RequestParam Map<String, Object> paramMap, Integer start, Integer length) {
		ResponseData response = new AjaxResponseData();
		try {
			if (null == start) {
				start = 1;
			}
			if (null == length) {
				length = 10;
			}
			if (!StringUtils.isEmpty(paramMap.get("time1"))) {
				paramMap.put("time1", DateUtil.strToDate(paramMap.get("time1").toString(), "yyyy-MM-dd HH:mm:ss"));
			}
			if (!StringUtils.isEmpty(paramMap.get("time2"))) {
				paramMap.put("time2", DateUtil.strToDate(paramMap.get("time2").toString(), "yyyy-MM-dd HH:mm:ss"));
			}
			PageInfo<Store> page = storeService.getPage(paramMap, start / length + 1, length);
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("recordsTotal", page.getTotal());// 总数量
			newMap.put("recordsFiltered", page.getTotal());// 过滤后的总数量，暂未用到
			newMap.put("data", page.getList());// 数据列表
			return JSON.toJSONString(newMap, SerializerFeature.WriteMapNullValue);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id) {
		ResponseData response = new AjaxResponseData();
		try {
			Store store = storeService.getStoreById(id);
			Map<String, Object> map = new HashMap<>();
			map.put("store", store);
			map.put("province", areaService.getSubArea("0"));
			if (null != store.getProvince()) {
				map.put("city", areaService.getSubArea(String.valueOf(store.getProvince())));
			}
			if (null != store.getCity()) {
				map.put("area", areaService.getSubArea(String.valueOf(store.getCity())));
			}
			map.put("merchants", merchantService.getAll());
			response.setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/save")
	public String save(@RequestBody String body) {
		StoreVO store = JSON.parseObject(body, StoreVO.class);
		ResponseData response = new AjaxResponseData();
		try {
			// 新增
			if (null == store.getId()) {
				storeService.add(store);
			} else {
				// 修改
				storeService.update(store);
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/del/{id}")
	public String delete(@PathVariable String id) {
		ResponseData response = new AjaxResponseData();
		try {
			storeService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}
}
