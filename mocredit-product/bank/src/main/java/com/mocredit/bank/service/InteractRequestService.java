package com.mocredit.bank.service;

import com.mocredit.bank.entity.InRequest;

/**
 * 访问请求日志
 * @author liaoying
 * Created on 2015年8月19日
 *
 */
public interface InteractRequestService {
	
	public int save(InRequest interact);
}
