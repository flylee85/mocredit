package com.mocredit.integral.service.impl;

import org.springframework.stereotype.Service;

import com.mocredit.base.util.Banks;
import com.mocredit.integral.service.IntegralService;

@Service
public class IntegralServiceLocator {

	/**
	 * 获得积分业务模型
	 * @param bank
	 * @return
	 */
	public IntegralService getService(String bank) {
		if (Banks.CITIC.equals(bank)) {
			return new IntegralCiticServiceImpl();
		} else if (Banks.CMBC.equals(bank)) {
			return new IntegralCmbcServiceImpl();
		}
		return new IntegralCiticServiceImpl();
	}
}
