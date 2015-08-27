package com.mocredit.integral.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.OrderService;
import com.mocredit.integral.util.DateEditor;
import com.mocredit.integral.vo.ConformInfoVo;

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
			HttpServletResponse response, Order order, Integer integral) {
		LOGGER.info("### request in payment order={} ###", order.toString());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bank", order.getBank());
		paramMap.put("device", order.getBank());
		paramMap.put("activityId", order.getActivityId());
		paramMap.put("orderId", order.getOrderId());
		paramMap.put("shopId", order.getShopId());
		paramMap.put("shopName", order.getShopName());
		paramMap.put("storeId", order.getStoreId());
		paramMap.put("storeName", order.getStoreName());
		paramMap.put("cardNum", order.getCardNum());
		paramMap.put("integral", integral);
		Response resp = new Response();
		order.setAmount(integral);
		if (doPostAndSaveOrder("", paramMap, order, resp)) {
			LOGGER.info("### payment success order={} ###", order.toString());
			resp.setSuccess("true");
			return renderJSONString("true", "", "", "");
		} else {
			LOGGER.error("### payment error order={} ###", order.toString());
			return renderJSONString("false", "积分消费失败", resp.getErrorCode(), "");
		}
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
			HttpServletResponse response, String device, String orderId) {
		LOGGER.info("### request in paymentRevoke orderId={} ###", orderId);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("device", device);
		paramMap.put("orderId", orderId);
		Response resp = new Response();
		if (paymentRevoke("", paramMap, resp)) {
			LOGGER.info("### paymentRevoke success order={} ###", orderId);
			return renderJSONString("true", "", "", "");
		} else {
			LOGGER.error("### paymentRevoke error order={} ###", orderId);
			return renderJSONString("false", "积分消费撤销失败", resp.getErrorCode(),
					"");
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
			HttpServletResponse response, String device, String orderId) {
		LOGGER.info("### request in paymentReserval orderId={} ###", orderId);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("device", device);
		paramMap.put("orderId", orderId);
		Response resp = new Response();
		if (paymentReserval("", paramMap, resp)) {
			LOGGER.info("### paymentReserval success order={} ###", orderId);
			return renderJSONString("true", "", "", "");
		} else {
			LOGGER.error("### paymentReserval error order={} ###", orderId);
			return renderJSONString("false", "积分消费冲正失败", resp.getErrorCode(),
					"");
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
			HttpServletResponse response, String device, String orderId) {
		LOGGER.info("### request in paymentReserval orderId={} ###", orderId);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("device", device);
		paramMap.put("orderId", orderId);
		Response resp = new Response();
		if (paymentRevokeReserval("", paramMap, resp)) {
			LOGGER.info("### paymentReserval success order={} ###", orderId);
			return renderJSONString("true", "", "", "");
		} else {
			LOGGER.error("### paymentReserval error order={} ###", orderId);
			return renderJSONString("false", "积分消费撤销冲正失败", resp.getErrorCode(),
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
			HttpServletResponse response, ConformInfoVo conformInfoVo) {
		LOGGER.info("### request in confirmInfo confirmInfo={} ###",
				conformInfoVo.toString());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bank", conformInfoVo.getBank());
		paramMap.put("activityId", conformInfoVo.getActivityId());
		paramMap.put("cardNum", conformInfoVo.getCardNum());
		Response resp = new Response();
		if (confirmInfo("", paramMap, resp)) {
			LOGGER.info("### confirmInfo success confirmInfo={} ###",
					conformInfoVo.toString());
			return renderJSONString("true", "", "", "");
		} else {
			LOGGER.error("### confirmInfo error confirmInfo={} ###",
					conformInfoVo.toString());
			return renderJSONString("false", "积分查询失败", resp.getErrorCode(), "");
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
			HttpServletResponse response, Activity activity,
			String productCode, Integer operCode, String storeList) {
		activity.setProductType(productCode);
		LOGGER.info(
				"### request in activityImport activity={} operCode={} storeList={} ###",
				activity.toString(), operCode, storeList);
		Response resp = new Response();
		if (activityService.operActivityAndStore(activity, productCode,
				operCode, storeList, resp)) {
			LOGGER.info(
					"### activityImport success activity={} operCode={} storeList={} ###",
					activity.toString(), operCode, storeList);
			return renderJSONString("true", "", "", "");
		} else {
			LOGGER.error(
					"### activityImport error activity={} operCode={} storeList={} ###",
					activity.toString(), operCode, storeList);
			return renderJSONString("false", "活动同步失败", resp.getErrorCode(), "");
		}
	}
    @InitBinder  
    protected void initBinder(HttpServletRequest request,  
                                  ServletRequestDataBinder binder) throws Exception {  
        //对于需要转换为Date类型的属性，使用DateEditor进行处理  
        binder.registerCustomEditor(Date.class, new DateEditor());  
    }  
}
