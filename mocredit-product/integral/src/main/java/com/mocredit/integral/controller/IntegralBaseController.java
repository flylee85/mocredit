package com.mocredit.integral.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.web.BaseController;
import com.mocredit.integral.entity.InResponseLog;
import com.mocredit.integral.service.InResponseLogService;

public class IntegralBaseController extends BaseController {
	@Autowired
	private InResponseLogService inResponseLogService;

	protected String renderJSONString(String success, String errorMsg,
			Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		map.put("errorMsg", errorMsg);
		map.put("success", success);
		String requestId = request.getAttribute("request_id").toString();
		String jsonStr = JSON.toJSONString(map);
		saveReponseLog(requestId, jsonStr);
		return jsonStr;
	}

	private void saveReponseLog(String requestId, String jsonStr) {
		inResponseLogService.save(new InResponseLog(Integer.valueOf(requestId),
				jsonStr));
	}
}
