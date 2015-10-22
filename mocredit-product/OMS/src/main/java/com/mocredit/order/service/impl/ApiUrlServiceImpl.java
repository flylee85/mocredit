/**
 * 
 */
package com.mocredit.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.order.entity.ApiUrl;
import com.mocredit.order.persistence.ApiUrlMapper;
import com.mocredit.order.service.ApiUrlService;

/**
 * @author ytq
 * 
 */
@Service
public class ApiUrlServiceImpl implements ApiUrlService {
	@Autowired
	private ApiUrlMapper apiUrlMapper;

	@Override
	public boolean save(ApiUrl t) {
		return apiUrlMapper.save(t) > 0;
	}

	@Override
	public boolean updateByCode(ApiUrl t) {
		return apiUrlMapper.updateByCode(t) > 0;
	}

	@Override
	public ApiUrl selectByCode(String code) {
		return apiUrlMapper.selectByCode(code);
	}

}
