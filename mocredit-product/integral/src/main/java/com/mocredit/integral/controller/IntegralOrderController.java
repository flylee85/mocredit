package com.mocredit.integral.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocredit.base.web.BaseController;

@RestController
@RequestMapping("/order")
public class IntegralOrderController extends BaseController {
	@RequestMapping("/integral")
	@ResponseBody
	public String updateAccountstate() {
		return renderJSONString(0, "test");
	}
}
