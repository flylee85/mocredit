package com.mocredit.integral.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocredit.integral.entity.Order;
import com.mocredit.integral.service.OrderService;

@RestController
@RequestMapping("/interface")
public class IntegralInterfaceController extends IntegralBaseController {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/payment")
	@ResponseBody
	public String payment(HttpServletRequest request,
			HttpServletResponse response, Order order) {
		if (orderService.save(order)) {
			return renderJSONString("true", "", "");
		} else {
			return renderJSONString("false", "积分订单保存失败", "");
		}
	}

	@RequestMapping("/paymentRevoke")
	@ResponseBody
	public String paymentRevoke(HttpServletRequest request,
			HttpServletResponse response, Order order) {
		String requestId = request.getAttribute("request_id").toString();

		if (orderService.save(order)) {

		}

		return renderJSONString(0, "test");
	}
}
