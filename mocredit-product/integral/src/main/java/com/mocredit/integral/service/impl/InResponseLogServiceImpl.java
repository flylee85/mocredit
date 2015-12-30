package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.InResponseLog;
import com.mocredit.integral.persistence.InResponseLogMapper;
import com.mocredit.integral.service.InResponseLogService;

@Service
public class InResponseLogServiceImpl implements InResponseLogService {
	@Autowired
	private InResponseLogMapper inResponseLogMapper;

	@Override
	public boolean save(InResponseLog t) {
		return inResponseLogMapper.save(t) > 0;

	}

}
