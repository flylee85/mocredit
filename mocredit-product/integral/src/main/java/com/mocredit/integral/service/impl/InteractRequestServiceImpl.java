package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.Interact;
import com.mocredit.integral.persistence.InteractRequestMapper;
import com.mocredit.integral.service.InteractRequestService;
@Service
public class InteractRequestServiceImpl implements InteractRequestService {
	@Autowired
	private InteractRequestMapper requestMapper;
	@Override
	public int save(Interact interact) {
		return requestMapper.save(interact);
	}

}
