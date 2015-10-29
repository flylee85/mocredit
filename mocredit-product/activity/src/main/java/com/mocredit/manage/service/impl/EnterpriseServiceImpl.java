package com.mocredit.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.persitence.EnterpriseMapper;
import com.mocredit.manage.service.EnterpriseService;

public class EnterpriseServiceImpl implements EnterpriseService {

	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Override
	public List<Enterprise> getAll() {
		return enterpriseMapper.selectAll(null);
	}

	@Override
	public Enterprise getEnterpriseById(String id) {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(id);
		return enterpriseMapper.selectOne(enterprise);
	}

}
