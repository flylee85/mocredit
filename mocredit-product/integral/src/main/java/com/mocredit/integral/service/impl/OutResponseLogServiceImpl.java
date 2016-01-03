package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.OutResponseLog;
import com.mocredit.integral.persistence.OutResponseLogMapper;
import com.mocredit.integral.service.OutResponseLogService;

/**
 * @author ytq
 * 
 */
@Service
public class OutResponseLogServiceImpl implements OutResponseLogService {
	@Autowired
	private OutResponseLogMapper outResponseLogMapper;

	@Override
	public boolean save(OutResponseLog t) {
		return outResponseLogMapper.save(t) > 0;

	}

}
