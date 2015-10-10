package com.mocredit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.web.BaseController;
import com.mocredit.order.service.OrderService;
import com.mocredit.order.vo.OrderVo;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/show")
	@ResponseBody
	public ModelAndView showOrder(OrderVo orderVo,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
}
