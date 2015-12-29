package com.mocredit.recharge.service.impl;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocredit.recharge.model.Request;
import com.mocredit.recharge.persitence.RequestMapper;

@Aspect
public class RequestLog {
	@Autowired
	private RequestMapper requestMapper;

	@Before("execution(public * com.mocredit.recharge.controller.RechargeController.*(..))")
	public void doAccessCheck(JoinPoint point) {
		System.out.println("@Before：模拟权限检查...");
		System.out.println(
				"@Before：目标方法为：" + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
		System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
		System.out.println("@Before：被织入的目标对象为：" + point.getTarget());
		Request request = new Request();
		request.setCtime(new Date());
		request.setMethod(point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
//		request.setData(JSON.toJSONString(point.getArgs()));
		request.setIp("");
	}
}
