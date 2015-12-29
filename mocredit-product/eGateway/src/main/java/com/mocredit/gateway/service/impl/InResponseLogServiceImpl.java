package com.mocredit.gateway.service.impl;

import com.mocredit.gateway.entity.InResponseLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.gateway.persistence.InResponseLogMapper;
import com.mocredit.gateway.service.InResponseLogService;

@Service
public class InResponseLogServiceImpl implements InResponseLogService {
	@Autowired
	private InResponseLogMapper inResponseLogMapper;

	@Override
	public boolean save(InResponseLog t) {
		return inResponseLogMapper.save(t) > 0;

	}

}
