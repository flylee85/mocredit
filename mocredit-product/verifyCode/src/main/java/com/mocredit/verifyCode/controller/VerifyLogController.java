package com.mocredit.verifyCode.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.verifyCode.service.VerifyCodeService;
import com.mocredit.verifyCode.vo.VerifyCodeVO;

/**
 * 
 * @author liaoy
 *
 */
@RestController
@RequestMapping("/verifyLog")
public class VerifyLogController {

	private Logger logger = Logger.getLogger(VerifyLogController.class.getName());

	@Autowired
	private VerifyCodeService verifyCodeService;

	/**
	 * 查询验码日志流水
	 * 
	 * @return
	 * {
	 * orderId,
	 * enterpriseName,
	 * shopName,
	 * storeName,
	 * activityName,
	 * status, 验码/撤销
	 * code,
	 * mobile,
	 * orderTime,
	 * enCode,
	 * verifyStatus,
	 * msg
	 * }
	 */
	@RequestMapping(value = "getPage", produces = { "application/json;charset=UTF-8" })
	public String getPage(@RequestBody String body) {
		AjaxResponseData ard = new AjaxResponseData();
		try {
			VerifyCodeVO verifyCode = JSON.parseObject(body, VerifyCodeVO.class);
			if (null == verifyCode.getPageNum() || 0 == verifyCode.getPageNum()) {
				verifyCode.setPageNum(1);
			}
			if (null == verifyCode.getPageSize() || 0 == verifyCode.getPageSize()) {
				verifyCode.setPageSize(15);
			}
			ard.setData(verifyCodeService.getPage(verifyCode, verifyCode.getPageSize(), verifyCode.getPageNum()));
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求时发生异常！");
			logger.error("getPage发生异常：" + e.getMessage());
			e.printStackTrace();
		}
		return JSON.toJSONString(ard);
	}
}
