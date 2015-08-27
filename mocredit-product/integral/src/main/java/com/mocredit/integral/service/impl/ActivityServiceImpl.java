package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.persistence.ActivityMapper;
import com.mocredit.integral.service.ActivityService;

/**
 * 
 * @author ytq
 * 
 */
@Service
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityMapper activityMapper;

	@Override
	public boolean save(Activity t) {
		return activityMapper.save(t) > 0;
	}

}
