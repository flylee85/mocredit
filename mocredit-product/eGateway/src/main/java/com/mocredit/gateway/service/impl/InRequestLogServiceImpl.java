package com.mocredit.gateway.service.impl;

import com.mocredit.gateway.persistence.InRequestLogMapper;
import com.mocredit.gateway.service.InRequestLogService;
import com.mocredit.gateway.entity.InRequestLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
