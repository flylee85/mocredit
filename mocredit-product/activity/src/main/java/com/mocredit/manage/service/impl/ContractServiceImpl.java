package com.mocredit.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mocredit.manage.model.Contract;
import com.mocredit.manage.persitence.ContractMapper;
import com.mocredit.manage.service.ContractService;

public class ContractServiceImpl implements ContractService {
	@Autowired
	private ContractMapper contractMapper;
	
	@Override
	public List<Contract> getAllContract() {
		
		return null;
	}

	@Override
	public List<Contract> getEnterpriseContract(String enterpriseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
