package com.mocredit.manage.controller;

import java.util.HashMap;
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
import com.mocredit.manage.model.Contract;
import com.mocredit.manage.service.ContractService;
import com.mocredit.manage.service.MerchantService;
import com.mocredit.manage.vo.ContractVO;

/**
 * 企业管理
 * 
 * @author liaoy
 * @date 2015年11月2日
 */
@RestController
@RequestMapping("/contract")
public class ContractController {
	@Autowired
	private ContractService contractService;
	@Autowired
	private MerchantService merchantService;

	@RequestMapping("/list")
	public String list(@RequestParam(value = "search[value]", required = false) String key, String enterpriseId,
			Integer start, Integer length) {
		ResponseData response = new AjaxResponseData();
		try {
			if (null == start) {
				start = 1;
			}
			if (null == length) {
				length = 10;
			}
			PageInfo<Contract> page = contractService.getPage(key,enterpriseId, start / length + 1, length);
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
			Contract contract = contractService.getContractById(id);
			Map<String, Object> map = new HashMap<>();
			map.put("contract", contract);
			if (null != contract) {
				map.put("merchants", merchantService.getAll());
			}
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
		ContractVO enterprise = JSON.parseObject(body, ContractVO.class);
		ResponseData response = new AjaxResponseData();
		try {
			// 新增
			if (null == enterprise.getId()) {
				contractService.add(enterprise);
			} else {
				// 修改
				contractService.update(enterprise);
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
			contractService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}
}
