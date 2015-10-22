package com.mocredit.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocredit.order.entity.UserDisplay;
import com.mocredit.order.persistence.UserDisplayMapper;
import com.mocredit.order.service.UserDisplayService;
import com.mocredit.order.vo.UserDisplayVo;

public class UserDisplayServiceImpl implements UserDisplayService {

	@Autowired
	private UserDisplayMapper userDisplayMapper;

	@Override
	public boolean save(UserDisplay t) {
		return userDisplayMapper.save(t) > 0;
	}

	@Override
	public boolean update(UserDisplayVo userDisplayVo) {
		return userDisplayMapper.update(userDisplayVo) > 0;
	}

}
