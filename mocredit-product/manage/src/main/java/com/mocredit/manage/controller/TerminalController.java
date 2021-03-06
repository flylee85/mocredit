package com.mocredit.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.mocredit.base.util.HttpUtil;
import com.mocredit.manage.model.Terminal;
import com.mocredit.manage.service.OperLogService;
import com.mocredit.manage.service.SupplierService;
import com.mocredit.manage.service.TerminalService;
import com.mocredit.manage.service.TerminalTypeService;
import com.mocredit.manage.vo.TerminalVO;

/**
 * 企业管理
 * 
 * @author liaoy
 * @date 2015年11月2日
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private TerminalTypeService typeService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private OperLogService logService;
	@Value("${newgateway.resetPwd}")
	private String resetUrl;// 重置秘钥URL

	@RequestMapping("/list")
	public String list(@RequestParam(value = "search[value]", required = false) String key, Integer start,
			Integer length, String storeId) {
		ResponseData response = new AjaxResponseData();
		try {
			if (null == start) {
				start = 1;
			}
			if (null == length) {
				length = 10;
			}
			PageInfo<Terminal> page = terminalService.getPage(key, storeId, start / length + 1, length);
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
			Terminal terminal = terminalService.getTerminalById(id);
			Map<String, Object> map = new HashMap<>();
			map.put("terminal", terminal);
			map.put("supplier", supplierService.getAll());
			map.put("type", typeService.getAll());
			response.setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/save")
	public String save(HttpSession session, @RequestBody String body) {
		TerminalVO terminal = JSON.parseObject(body, TerminalVO.class);
		ResponseData response = new AjaxResponseData();
		if (StringUtils.isEmpty(terminal.getInfo())) {
			response.setSuccess(false);
			response.setErrorMsg("描述不能为空");
			return JSON.toJSONString(response);
		}
		try {
			Object username = session.getAttribute("username");
			// 新增
			if (null == terminal.getId()) {
				terminalService.add(terminal);
				logService.add(null == username ? "" : username.toString(), OperLogService.ADD,
						JSON.toJSONString(terminal), terminal.getInfo(), terminal.getId());
			} else {
				// 修改
				terminalService.update(terminal);
				logService.add(null == username ? "" : username.toString(), OperLogService.UPDATE,
						JSON.toJSONString(terminal), terminal.getInfo(), terminal.getId());
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/del/{id}")
	public String delete(HttpSession session, @PathVariable String id) {
		ResponseData response = new AjaxResponseData();
		try {
			terminalService.delete(id);
			Object username = session.getAttribute("username");
			logService.add(null == username ? "" : username.toString(), OperLogService.DELETE, id, "", id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/getStoreInfo/{storeId}")
	public String getStoreInfo(@PathVariable String storeId) {
		ResponseData response = new AjaxResponseData();
		try {
			response.setData(terminalService.getStoreInfo(storeId));
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	@RequestMapping("/getComb")
	public String getComb() {
		ResponseData response = new AjaxResponseData();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("supplier", supplierService.getAll());
			map.put("type", typeService.getAll());
			response.setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	/**
	 * 根据机具号获取门店ID
	 * 
	 * @return
	 */
	@RequestMapping("/getStoreIdByCode/{code}")
	public String getStoreIdByCode(@PathVariable String code) {
		ResponseData response = new AjaxResponseData();
		try {
			String storeId = terminalService.getStoreIdByCode(code);
			response.setData(storeId);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}

	/**
	 * 重置秘钥
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resetPwd/{id}")
	public String resetPwd(@PathVariable String id) {
		ResponseData response = new AjaxResponseData();
		try {
			Terminal terminal = terminalService.getTerminalById(id);
			Map<String, Object> param = new HashMap<>();
			param.put("enCode", terminal.getSnCode());
			String rest = HttpUtil.doRestfulByHttpConnection(resetUrl, JSON.toJSONString(param));
			response.setSuccess("0".equals(rest));
			response.setErrorMsg(rest);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return JSON.toJSONString(response);
	}
}
