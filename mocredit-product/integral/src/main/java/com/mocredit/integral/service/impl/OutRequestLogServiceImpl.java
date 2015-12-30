package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.OutRequestLog;
import com.mocredit.integral.persistence.OutRequestLogMapper;
import com.mocredit.integral.service.OutRequestLogService;

@Service
public class OutRequestLogServiceImpl implements OutRequestLogService {
	@Autowired
	private OutRequestLogMapper outRequestLogMapper;

	@Override
	public boolean save(OutRequestLog t) {
		return outRequestLogMapper.save(t) > 0;
	}

}
