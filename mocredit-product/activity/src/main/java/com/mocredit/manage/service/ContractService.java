package com.mocredit.manage.service;

import java.util.List;

import com.mocredit.manage.model.Contract;

public interface ContractService {
	/**
	 * 获得所有合同列表
	 * @return
	 */
     List<Contract> getAllContract();
     /**
      * 获得企业的合同列表
      * @param enterpriseId
      * @return
      */
     List<Contract> getEnterpriseContract(String enterpriseId);
}
