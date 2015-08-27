package com.mocredit.bank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.bank.entity.InRequest;
import com.mocredit.bank.persistence.InRequestMapper;
import com.mocredit.bank.service.InteractRequestService;
@Service
public class InteractRequestServiceImpl implements InteractRequestService {
	@Autowired
	private InRequestMapper requestMapper;
	@Override
	public int save(InRequest interact) {
		return requestMapper.save(interact);
	}

}
