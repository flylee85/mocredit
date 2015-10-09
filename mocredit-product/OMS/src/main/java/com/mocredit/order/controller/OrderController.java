package com.mocredit.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocredit.base.web.BaseController;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    @RequestMapping("/showOrder")
    @ResponseBody
    public String showOrder() {
        return renderJSONString(0, "test");
    }
}
