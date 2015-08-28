package com.mocredit.integral.controller;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.web.BaseController;
import com.mocredit.integral.entity.InResponseLog;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.HttpRequestService;
import com.mocredit.integral.service.InResponseLogService;
import com.mocredit.integral.util.Utils;

public class IntegralBaseController extends BaseController {
	@Autowired
	private InResponseLogService inResponseLogService;
	@Autowired
	private HttpRequestService httpRequstService;
	@Autowired
	private Properties config;

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!Utils.isNullOrBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!Utils.isNullOrBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	protected String renderJSONString(boolean success, String errorMsg,
			String errorCode, Object data) {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("success", success);
		map.put("errorMsg", errorMsg);
		map.put("errorCode", errorCode);
		map.put("data", data);
		String jsonStr = JSON.toJSONString(map);
		saveReponseLog(getRequestId(), jsonStr);
		return jsonStr;
	}

	private void saveReponseLog(Integer requestId, String jsonStr) {
		inResponseLogService.save(new InResponseLog(requestId, jsonStr));
	}

	/**
	 * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
	 * 
	 * @param url
	 * @param order
	 * @return
	 */
	protected boolean doGetAndSaveOrder(String url, Order order) {
		return httpRequstService.doGetAndSaveOrder(getRequestId(), url, order);
	}

	/**
	 * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
	 * 
	 * @param url
	 * @param paramMap
	 * @param order
	 * @return
	 */
	protected boolean doPostAndSaveOrder(String url, Map<?, ?> paramMap,
			Order order, Response resp) {
		return httpRequstService.doPostAndSaveOrder(getRequestId(), url,
				paramMap, order, resp);
	}

	/**
	 * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
	 * 
	 * @param url
	 * @param paramMap
	 * @param order
	 * @return
	 */
	protected boolean doPostJsonAndSaveOrder(String url, String param,
			Order order, Response resp) {
		return httpRequstService.doPostJsonAndSaveOrder(getRequestId(), url,
				param, order, resp);
	}

	/**
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	protected boolean paymentRevoke(String url, Map<?, ?> paramMap,
			Response resp) {
		return httpRequstService.paymentRevoke(getRequestId(), url, paramMap,
				resp);
	}

	/**
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	protected boolean paymentRevokeJson(String url, String param,
			String device, String orderId, Response resp) {
		return httpRequstService.paymentRevokeJson(getRequestId(), url, param,
				device, orderId, resp);
	}

	/**
	 * 积分冲正
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	protected boolean paymentReserval(String url, Map<?, ?> paramMap,
			Response resp) {
		return httpRequstService.paymentReserval(getRequestId(), url, paramMap,
				resp);
	}

	/**
	 * 积分冲正
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	protected boolean paymentReservalJson(String url, String param,
			Response resp) {
		return httpRequstService.paymentReservalJson(getRequestId(), url,
				param, resp);
	}

	/**
	 * 积分撤销冲正
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	protected boolean paymentRevokeReserval(String url, Map<?, ?> paramMap,
			Response resp) {
		return httpRequstService.paymentRevokeReserval(getRequestId(), url,
				paramMap, resp);
	}

	/**
	 * 积分撤销冲正
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	protected boolean paymentRevokeReservalJson(String url, String param,
			Response resp) {
		return httpRequstService.paymentRevokeReservalJson(getRequestId(), url,
				param, resp);
	}

	/**
	 * 积分查询
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public boolean confirmInfo(String url, Map<?, ?> paramMap, Response resp) {
		return httpRequstService.confirmInfo(getRequestId(), url, paramMap,
				resp);
	}

	/**
	 * 积分查询
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public boolean confirmInfoJson(String url, String param, Response resp) {
		return httpRequstService.confirmInfoJson(getRequestId(), url, param,
				resp);
	}

	/**
	 * 活动同步
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public boolean activityImport(String url, Map<?, ?> paramMap, Response resp) {
		return httpRequstService.activityImport(getRequestId(), url, paramMap,
				resp);
	}

	/**
	 * 获取request_id
	 * 
	 * @return
	 */
	public Integer getRequestId() {
		String requestId = (null == request.getAttribute("request_id") ? "0"
				: request.getAttribute("request_id").toString());
		return Integer.valueOf(requestId);
	}

	/**
	 * 獲得請求銀行接口的地址
	 * 
	 * @param url
	 * @return
	 */
	protected String getBankInterfaceUrl(String url) {
		return config.getProperty("base_url") + "/" + url;
	}

}
