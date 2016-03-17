package com.mocredit.verifyCode.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mocredit.base.enums.VerifyLogCode;
import com.mocredit.verifyCode.dao.VerifiedCodeMapper;
import com.mocredit.verifyCode.service.VerifyCodeService;
import com.mocredit.verifyCode.vo.VerifyCodeVO;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
	@Autowired
	private VerifiedCodeMapper verifyCodeMapper;

	public Map<String, Object> getPage(VerifyCodeVO verifyCode, int pageSize, int pageNum) {
		// 封装查询条件
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startTime", verifyCode.getStartTime());
		param.put("endTime", verifyCode.getEndTime());
		if (!StringUtils.isEmpty(verifyCode.getActivityName())) {
			param.put("activityName", "%" + verifyCode.getActivityName() + "%");
		}
		param.put("enCode", verifyCode.getEnCode());
		param.put("mobile", verifyCode.getMobile());
		if (!StringUtils.isEmpty(verifyCode.getEnterpriseName())) {
			param.put("enterpriseName", "%" + verifyCode.getEnterpriseName() + "%");
		}
		param.put("code", verifyCode.getCode());

		param.put("requestSerialNumber",verifyCode.getOrderNo());
		if (!StringUtils.isEmpty(verifyCode.getStoreName())) {
			param.put("storeName", "%" + verifyCode.getStoreName() + "%");
		}
		// 处理类型
		if (null != verifyCode.getStatusList()) {
			StringBuilder type = new StringBuilder();
			for (String status : verifyCode.getStatusList()) {
				type.append("'").append(status).append("',");
			}
			type.deleteCharAt(type.length() - 1);
			param.put("type", type.toString());
		}
		param.put("pageStart", pageSize * (pageNum - 1));
		param.put("pageSize", pageSize + 1);

		// 获得数据
		List<Map<String, Object>> page = verifyCodeMapper.getPage(param);
		int pageCount = 0;
		if (page.size() == pageSize + 1) {
			pageCount = pageSize * pageNum + 1;
			page.remove(pageSize);
		} else {
			pageCount = pageSize * (pageNum - 1) + page.size();
		}
		for (Map<String, Object> log : page) {
			Object status = log.get("verifyStatus");
			log.put("msg", VerifyLogCode.getName(null == status ? 0 : Integer.parseInt(status.toString())));
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pageSize", pageSize);
		returnMap.put("pageNum", pageNum);
		returnMap.put("pageCount", pageCount);
		returnMap.put("data", page);
		return returnMap;
	}

}
