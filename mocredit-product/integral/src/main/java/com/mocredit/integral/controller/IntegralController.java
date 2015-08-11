package com.mocredit.integral.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class IntegralController {

	@RequestMapping("")
	public ModelAndView index() {
		return new ModelAndView("login");
	}

}
