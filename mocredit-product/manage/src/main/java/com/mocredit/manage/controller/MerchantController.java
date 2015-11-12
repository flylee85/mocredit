package com.mocredit.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.mocredit.manage.model.Merchant;
import com.mocredit.manage.service.MerchantService;
import com.mocredit.manage.vo.MerchantVO;

/**
 * 企业管理
 * 
 * @author liaoy
 * @date 2015年11月2日
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {
	@Autowired
	private MerchantService merchantService;

	@RequestMapping("/list")
	public String list(@RequestParam(value = "search[value]", required = false) String key,String contractId, Integer start,
			Integer length) {
		ResponseData response = new AjaxResponseData();
		try {
			if (null == start) {
				start = 1;
			}
			if (null == length) {
				length = 10;
			}
			PageInfo<Merchant> page = merchantService.getPage(key,contractId, start / length + 1, length);
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("recordsTotal", page.getTotal());// 总数量
			newMap.put("recordsFiltered", page.getTotal());// 过滤后的总数量，暂未用到
			newMap.put("data", page.getList());// 数据列表
			return JSON.toJSONString(newMap,SerializerFeature.WriteMapNullValue);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("all")
	public String all(){
		ResponseData response = new AjaxResponseData();
		try {
			List<Merchant> merchants = merchantService.getAll();
			response.setData(merchants);
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
			Merchant merchant = merchantService.getMerchantById(id);
			response.setData(merchant);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/save")
	public String save(@RequestBody String body) {
		MerchantVO merchant = JSON.parseObject(body, MerchantVO.class);
		ResponseData response = new AjaxResponseData();
		try {
			// 新增
			if (null == merchant.getId()) {
				merchantService.add(merchant);
			} else {
				// 修改
				merchantService.update(merchant);
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
			merchantService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}
}
