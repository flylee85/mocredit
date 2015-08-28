package com.mocredit.integral.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.entity.InRequestLog;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.InRequestLogService;
import com.mocredit.integral.service.OrderService;
import com.mocredit.integral.util.DateEditor;
import com.mocredit.integral.vo.ActivityVo;
import com.mocredit.integral.vo.OrderVo;

/**
 * 对网关接口
 * 
 * @author ytq 2015年8月25日
 */

@RestController
@RequestMapping("/interface")
public class IntegralInterfaceController extends IntegralBaseController {
	private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
			.getLogger("interfaceCtrl");
	@Autowired
	private OrderService orderService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private InRequestLogService inRequestLogService;
	static ObjectMapper objectMapper = new ObjectMapper();
	static {
		DeserializationConfig cfg = objectMapper.getDeserializationConfig();
		cfg.withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper.setDeserializationConfig(cfg);
	}

	/**
	 * 积分消费
	 * 
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/payment", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String payment(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String param) {
		LOGGER.info("### request in payment param={} ###", param);
		Response resp = new Response();
		try {
			OrderVo order = objectMapper.readValue(param, OrderVo.class);
			order.setAmount(order.getIntegral());
			saveInRequestLog(request, order.getOrderId(), param);
			if (doPostJsonAndSaveOrder(getBankInterfaceUrl("payment"), param,
					order, resp)) {
				LOGGER.info("### payment success param={} ###", param);
				resp.setSuccess("true");
				return renderJSONString(true, "", "", "");
			} else {
				LOGGER.error("### payment error param={} ###", param);
				return renderJSONString(false, "积分消费失败", resp.getErrorCode(),
						"");
			}
		} catch (Exception e) {
			LOGGER.error("### payment error param={} ###", param);
			resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
			return renderJSONString(false, "积分消费失败", resp.getErrorCode(), "");
		}
		//
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("bank", order.getBank());
		// paramMap.put("device", order.getBank());
		// paramMap.put("activityId", order.getActivityId());
		// paramMap.put("orderId", order.getOrderId());
		// paramMap.put("shopId", order.getShopId());
		// paramMap.put("shopName", order.getShopName());
		// paramMap.put("storeId", order.getStoreId());
		// paramMap.put("storeName", order.getStoreName());
		// paramMap.put("cardNum", order.getCardNum());
		// paramMap.put("integral", integral);
		//
		// order.setAmount(integral);
		// if (doPostAndSaveOrder(getBankInterfaceUrl("payment"), paramMap,
		// order,
		// resp)) {
		// LOGGER.info("### payment success order={} ###", order.toString());
		// resp.setSuccess("true");
		// return renderJSONString(true, "", "", "");
		// } else {
		// LOGGER.error("### payment error order={} ###", order.toString());
		// return renderJSONString(false, "积分消费失败", resp.getErrorCode(), "");
		// }
	}

	/**
	 * 积分消费撤销
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/paymentRevoke", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String paymentRevoke(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String param) {
		LOGGER.info("### request in paymentRevoke param={} ###", param);
		// Map<String, String> paramMap = new HashMap<String, String>();
		// paramMap.put("device", device);
		// paramMap.put("orderId", orderId);
		Response resp = new Response();
		try {
			JsonNode jsonNode = objectMapper.readTree(param);
			String orderId = jsonNode.get("orderId").asText();
			saveInRequestLog(request, orderId, param);
			if (paymentRevokeJson(getBankInterfaceUrl("paymentRevoke"), param,
					orderId, resp)) {
				LOGGER.info("### paymentRevoke success param={} ###", orderId);
				return renderJSONString(true, "", "", "");
			} else {
				LOGGER.error("### paymentRevoke error param={} ###", orderId);
				return renderJSONString(false, "积分消费撤销失败", resp.getErrorCode(),
						"");
			}
		} catch (Exception e) {
			LOGGER.error("### paymentRevoke error param={} ###", param);
			resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
			return renderJSONString(false, "积分消费撤销失败", resp.getErrorCode(), "");
		}
	}

	/**
	 * 积分消费冲正
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/paymentReserval", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String paymentReserval(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String param) {
		LOGGER.info("### request in paymentReserval param={} ###", param);
		// Map<String, String> paramMap = new HashMap<String, String>();
		// paramMap.put("device", device);
		// paramMap.put("orderId", orderId);
		Response resp = new Response();
		try {
			JsonNode jsonNode = objectMapper.readTree(param);
			String orderId = jsonNode.get("orderId").asText();
			saveInRequestLog(request, orderId, param);
			if (paymentReservalJson(getBankInterfaceUrl("paymentReserval"),
					param, resp)) {
				LOGGER.info("### paymentReserval success param={} ###", param);
				return renderJSONString(true, "", "", "");
			} else {
				LOGGER.error("### paymentReserval error param={} ###", param);
				return renderJSONString(false, "积分消费冲正失败", resp.getErrorCode(),
						"");
			}
		} catch (Exception e) {
			LOGGER.error("### paymentReserval error param={} ###", param);
			resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
			return renderJSONString(false, "积分消费冲正失败", resp.getErrorCode(), "");
		}
	}

	/**
	 * 积分消费撤销冲正
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/paymentRevokeReserval", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String paymentRevokeReserval(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String param) {
		LOGGER.info("### request in paymentReserval param={} ###", param);
		// Map<String, String> paramMap = new HashMap<String, String>();
		// paramMap.put("device", device);
		// paramMap.put("orderId", orderId);
		Response resp = new Response();
		try {
			JsonNode jsonNode = objectMapper.readTree(param);
			String orderId = jsonNode.get("orderId").asText();
			saveInRequestLog(request, orderId, param);
			if (paymentRevokeReservalJson(
					getBankInterfaceUrl("paymentRevokeReserval"), param, resp)) {
				LOGGER.info("### paymentRevokeReserval success param={} ###",
						param);
				return renderJSONString(true, "", "", "");
			} else {
				LOGGER.error("### paymentRevokeReserval error param={} ###",
						param);
				return renderJSONString(false, "积分消费撤销冲正失败",
						resp.getErrorCode(), "");
			}
		} catch (Exception e) {
			LOGGER.error("### paymentRevokeReserval error param={} ###", param);
			resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
			return renderJSONString(false, "积分消费撤销冲正失败", resp.getErrorCode(),
					"");
		}
	}

	/**
	 * 积分查询
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/confirmInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String confirmInfo(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String param) {
		LOGGER.info("### request in confirmInfo param={} ###", param);
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("bank", conformInfoVo.getBank());
		// paramMap.put("activityId", conformInfoVo.getActivityId());
		// paramMap.put("cardNum", conformInfoVo.getCardNum());
		Response resp = new Response();
		saveInRequestLog(request, null, param);
		if (confirmInfoJson(getBankInterfaceUrl("confirmInfo"), param, resp)) {
			LOGGER.info("### confirmInfo success param={} ###", param);
			return renderJSONString(true, "", "", "");
		} else {
			LOGGER.error("### confirmInfo error param={} ###", param);
			return renderJSONString(false, "积分查询失败", resp.getErrorCode(), "");
		}
	}

	/**
	 * 活动同步
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/activityImport", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String activityImport(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String param) {
		LOGGER.info("### request in activityImport param={} ###", param);

		Response resp = new Response();
		try {
			// 使用ObjectMapper嵌套json转对象报错采用fastJson
			ActivityVo activity = JSON.parseObject(param, ActivityVo.class);
			activity.setProductType(activity.getProductCode());
			saveInRequestLog(request, null, param);
			if (activityService.operActivityAndStore(activity,
					activity.getOperCode(), activity.getStoreList(), resp)) {
				LOGGER.info(
						"### request in success activityImport param={} ###",
						param);
				return renderJSONString(true, "", "", "");
			} else {
				LOGGER.error(
						"### request in error activityImport param={} ###",
						param);
				return renderJSONString(false, "活动同步失败", resp.getErrorCode(),
						"");
			}
		} catch (Exception e) {
			LOGGER.error("### activityImport error param={} error={} ###",
					param, e);
			resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
			return renderJSONString(false, "活动同步失败", resp.getErrorCode(), "");
		}
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	public void saveInRequestLog(HttpServletRequest request, String orderId,
			String param) {
		InRequestLog inRequestLog = new InRequestLog();
		inRequestLog.setIp(getIpAddr(request));
		inRequestLog.setRequest(param);
		inRequestLog.setOrderId(orderId);
		inRequestLog.setInterfaceUrl(request.getRequestURI());
		inRequestLogService.save(inRequestLog);
		request.setAttribute("request_id", inRequestLog.getUuid());
	}
}
