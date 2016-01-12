package com.mocredit.recharge.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.recharge.model.Request;
import com.mocredit.recharge.persitence.RequestMapper;
import com.mocredit.recharge.service.RequestLogService;

@Service
public class RequestLogServiceImpl implements RequestLogService {
	@Autowired
	private RequestMapper requestMapper;

	@Override
	public void addLog(String ip, String method, String data) {
		Request request = new Request();
		request.setCtime(new Date());
		request.setIp(ip);
		request.setData(data);
		request.setMethod(method);
		requestMapper.save(request);
	}
}
