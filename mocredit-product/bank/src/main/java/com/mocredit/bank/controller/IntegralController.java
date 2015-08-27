package com.mocredit.bank.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.bank.constant.RespError;
import com.mocredit.bank.datastructure.ResponseData;
import com.mocredit.bank.entity.InRequest;
import com.mocredit.bank.entity.InResponse;
import com.mocredit.bank.entity.RequestData;
import com.mocredit.bank.persistence.InRequestMapper;
import com.mocredit.bank.persistence.InResponseMapper;
import com.mocredit.bank.service.IntegralService;
import com.mocredit.bank.service.impl.IntegralServiceLocator;
import com.mocredit.bank.task.CiticTask;
import com.mocredit.bank.util.DateTimeUtils;
import com.mocredit.bank.util.Utils;
import com.mocredit.bank.util.Variable;

@RestController
@RequestMapping("/")
public class IntegralController {
	@Autowired
	private IntegralServiceLocator locator;
	@Autowired
	private InRequestMapper requestMapper;
	@Autowired
	private InResponseMapper responseMapper;
	@Autowired
	private CiticTask task;

	/**
	 * 积分扣减
	 * 
	 * @param 
	 *             {
	 *            shopId: 商户ID
	 *            cardNum:卡号
	 *            orderId:订单号
	 *            transAmt:交易金额
	 *            productType:银行内部活动代码
	 *            device:机具号
	 *            }
	 * @return { retCode 返回码 errMsg 错误信息 ,success 是否成功}
	 */
	@RequestMapping(value = "/payment", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String payment(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) {

		// if(Utils.isNullOrBlank(param)){
		// return "";
		// }
		// RequestData requestData=JSON.parseObject(param, RequestData.class);
//		RequestData requestData = new RequestData();
//		// requestData.setMerchantId(Variable.TEST_ZXMERCHANTID);
//		// requestData.setMerchantName(Variable.TEST_ZXMERCHANTNAME);
//		// requestData.setMerchantPassword(Variable.TEST_ZXMERCHANTPASSWORD);
//		requestData.setShopId(1);
//		requestData.setCardNum("5182128000042869");
//		requestData.setOrderId(DateTimeUtils.getDate("yyDHHmmssSSS"));
//		requestData.setProductType("00000000");
//		requestData.setTransAmt("10");
//		requestData.setDevice("10000000");
//		int requestId = saveRequestLog(request, param, requestData.getOrderId());
//		requestData.setRequestId(requestId);
//
//		ResponseData responseData = null;
//		String checkParam = checkPaymentParam(requestData);
//		if (!Variable.OK.equals(checkParam)) {
//			responseData = new ResponseData(RespError.PARAM_ERROR.getErrorCode(),
//					RespError.PARAM_ERROR.getErrorMsg() + ":" + checkParam, false);
//		} else {
//			IntegralService service = locator.getService(requestData.getBank());
//			responseData = service.payment(requestData);
//		}
//
//		String responseStr = JSON.toJSONString(responseData);
//		saveResponseLog(responseStr, String.valueOf(requestId));
//		return responseStr;
		task.checkAccount();
		return "";
	}

	/**
	 * 支付参数检查
	 * 
	 * @param requestData
	 * @return
	 */
	private String checkPaymentParam(RequestData requestData) {
		String result = Variable.OK;
		if (null == requestData) {
			result = "缺少参数";
		} else if (0 == requestData.getShopId()) {
			result = "缺少参数shopId";
		} else if (Utils.isNullOrBlank(requestData.getCardNum())) {
			result = "缺少参数cardNum";
		} else if (Utils.isNullOrBlank(requestData.getOrderId())) {
			result = "缺少参数orderId";
		} else if (Utils.isNullOrBlank(requestData.getProductType())) {
			result = "缺少参数productType";
		} else if (Utils.isNullOrBlank(requestData.getTransAmt())) {
			result = "缺少参数transAmt";
		} else if (Utils.isNullOrBlank(requestData.getDevice())) {
			result = "缺少参数device";
		}
		return result;
	}

	/**
	 * 积分消费撤销
	 * 
	 * @param reqMap
	 *          {
	 *            orderId 订单号
	 *            device 机具号
	 *          }
	 *          
	 * @return { retCode 返回码 errMsg 错误信息 ,success 是否成功 }
	 */
	@RequestMapping(value = "/paymentRevoke", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String paymentRevoke(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) {
		RequestData requestData = new RequestData();
		// 模拟数据
		requestData.setOrderId("15236150608709");
		requestData.setDevice("10000000");

		int requestId = saveRequestLog(request, param, requestData.getOrderId());
		requestData.setRequestId(requestId);

		ResponseData responseData = null;
		String checkParam = checkReversalParam(requestData);
		if (!Variable.OK.equals(checkParam)) {
			responseData = new ResponseData(RespError.PARAM_ERROR.getErrorCode(),
					RespError.PARAM_ERROR.getErrorMsg() + ":" + checkParam, false);
		} else {
			IntegralService service = locator.getService(requestData.getBank());
			responseData = service.paymentRevoke(requestData);
		}
		String responseStr = JSON.toJSONString(responseData);
		saveResponseLog(responseStr, String.valueOf(requestId));
		return responseStr;
	}

	/**
	 * 积分消费冲正
	 * 
	 * @param reqMap
	 *           {
	 *            orderId 订单号
	 *            device 机具号
	 *          }
	 * @return { retCode 返回码 errMsg 错误信息 ,success 是否成功 }
	 */
	@RequestMapping(value = "/paymentReversal", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String paymentReversal(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) {
		RequestData requestData = new RequestData();
		// 模拟数据
		requestData.setOrderId("15236150608709");
		requestData.setDevice("10000000");

		int requestId = saveRequestLog(request, param, requestData.getOrderId());
		requestData.setRequestId(requestId);

		ResponseData responseData = null;
		String checkParam = checkReversalParam(requestData);
		if (!Variable.OK.equals(checkParam)) {
			responseData = new ResponseData(RespError.PARAM_ERROR.getErrorCode(),
					RespError.PARAM_ERROR.getErrorMsg() + ":" + checkParam, false);
		} else {
			IntegralService service = locator.getService(requestData.getBank());
			responseData = service.paymentRevoke(requestData);
		}
		String responseStr = JSON.toJSONString(responseData);
		saveResponseLog(responseStr, String.valueOf(requestId));
		return responseStr;
	}

	/**
	 * 积分消费撤销冲正
	 * 
	 * @param reqMap
	 *           {
	 *            orderId 订单号
	 *            device 机具号
	 *          }
	 * @return { retCode 返回码 errMsg 错误信息 ,success 是否成功 }
	 */
	@RequestMapping(value = "/paymentRevokeReversal", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String paymentRevokeReversal(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String param) {
		RequestData requestData = new RequestData();
		// 模拟数据
		requestData.setOrderId("15236150608709");
		requestData.setDevice("10000000");

		int requestId = saveRequestLog(request, param, requestData.getOrderId());
		requestData.setRequestId(requestId);

		ResponseData responseData = null;
		String checkParam = checkReversalParam(requestData);
		if (!Variable.OK.equals(checkParam)) {
			responseData = new ResponseData(RespError.PARAM_ERROR.getErrorCode(),
					RespError.PARAM_ERROR.getErrorMsg() + ":" + checkParam, false);
		} else {
			IntegralService service = locator.getService(requestData.getBank());
			responseData = service.paymentRevokeReversal(requestData);
		}
		String responseStr = JSON.toJSONString(responseData);
		saveResponseLog(responseStr, String.valueOf(requestId));
		return responseStr;
	}

	/**
	 * 积分查询
	 * 
	 * @param reqMap
	 *           {
	 *            cardNum 卡号
	 *            shopId 商户ID
	 *            productType  银行内部活动代码
	 *          }
	 * @return { retCode 返回码 errMsg 错误信息 ,success 是否成功  data{integral 积分}}
	 */
	@RequestMapping(value = "/confirmInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String confirmInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String param) {
		RequestData requestData = new RequestData();
		// 模拟数据
//		requestData.setCardNum("5182128000042869");
		requestData.setCardNum("5201080000040103");
		requestData.setProductType("00000000");
		requestData.setShopId(1);

		int requestId = saveRequestLog(request, param, requestData.getOrderId());
		requestData.setRequestId(requestId);

		ResponseData responseData = null;
		String checkParam = checkConfirmParam(requestData);
		if (!Variable.OK.equals(checkParam)) {
			responseData = new ResponseData(RespError.PARAM_ERROR.getErrorCode(),
					RespError.PARAM_ERROR.getErrorMsg() + ":" + checkParam, false);
		} else {
			IntegralService service = locator.getService(requestData.getBank());
			responseData = service.confirmInfo(requestData);
		}
		String responseStr = JSON.toJSONString(responseData);
		saveResponseLog(responseStr, String.valueOf(requestId));
		return responseStr;
	}

	/**
	 * 记录请求日志
	 * 
	 * @param request
	 * @param reqParam
	 * @param order_id
	 * @return
	 */
	private int saveRequestLog(HttpServletRequest request, String reqParam, String order_id) {
		InRequest interact = new InRequest();
		interact.setIp(getIpAddr(request));
		interact.setRequest(reqParam);
		interact.setOrderId(order_id);
		interact.setInterface(request.getRequestURI());
		requestMapper.save(interact);
		return interact.getUuid();
	}

	/**
	 * 记录响应日志
	 * 
	 * @param response
	 * @param requestId
	 * @return
	 */
	private int saveResponseLog(String response, String requestId) {
		InResponse interact = new InResponse();
		interact.setRequestId(requestId);
		interact.setResponse(response);
		return responseMapper.save(interact);
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
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

	/**
	 * 支付冲正参数检查
	 * 
	 * @param requestData
	 * @return
	 */
	private String checkReversalParam(RequestData requestData) {
		String result = Variable.OK;
		if (null == requestData) {
			result = "缺少参数";
		} else if (Utils.isNullOrBlank(requestData.getOrderId())) {
			result = "缺少参数orderId";
		} else if (Utils.isNullOrBlank(requestData.getDevice())) {
			result = "缺少参数device";
		}
		return result;
	}
	/**
	 *积分查询参数检查
	 * 
	 * @param requestData
	 * @return
	 */
	private String checkConfirmParam(RequestData requestData) {
		String result = Variable.OK;
		if (null == requestData) {
			result = "缺少参数";
		} else if (Utils.isNullOrBlank(requestData.getCardNum())) {
			result = "缺少参数cardNum";
		} else if (0==requestData.getShopId()) {
			result = "缺少参数shopId";
		}else if (Utils.isNullOrBlank(requestData.getProductType())) {
			result = "缺少参数productType";
		}
		return result;
	}
}
