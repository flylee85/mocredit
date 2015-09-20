package com.mocredit.bank.service.impl;

import org.springframework.stereotype.Service;

import com.mocredit.bank.service.IntegralService;
import com.mocredit.base.util.Banks;
import com.mocredit.base.util.SpringContextUtils;

@Service
public class IntegralServiceLocator {

	/**
	 * 获得积分业务模型
	 * @param bank
	 * @return
	 */
	public IntegralService getService(String bank) {
		if (Banks.CITIC.equals(bank)) {
			return SpringContextUtils.getBean("citicService");
		} else if (Banks.CMBC.equals(bank)) {
			return SpringContextUtils.getBean("cmbcService");
		}
		return null;
	}
}
