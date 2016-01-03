package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.InRequestLog;
import com.mocredit.integral.persistence.InRequestLogMapper;
import com.mocredit.integral.service.InRequestLogService;

/**
 * 
 * @author ytq
 * 
 */
@Service
public class InRequestLogServiceImpl implements InRequestLogService {
	@Autowired
	private InRequestLogMapper inRequestLogMapper;

	@Override
	public boolean save(InRequestLog t) {
		return inRequestLogMapper.save(t) > 0;
	}
}
