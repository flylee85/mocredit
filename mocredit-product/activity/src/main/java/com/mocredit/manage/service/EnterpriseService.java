package com.mocredit.manage.service;

import java.util.List;

import com.mocredit.manage.model.Enterprise;

public interface EnterpriseService {
	/**
	 * 获得所有企业
	 * @return
	 */
     List<Enterprise> getAll();
     /**
      * 根据ID获得企业
      * @param id
      * @return
      */
     Enterprise getEnterpriseById(String id);
}
