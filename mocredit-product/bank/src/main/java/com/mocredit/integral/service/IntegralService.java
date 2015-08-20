package com.mocredit.integral.service;

import java.util.Map;

import com.mocredit.integral.entity.Payment;
import com.mocredit.integral.entity.Response;

/**
 * 积分操作业务接口
 * @author liaoying
 * Created on 2015年8月11日
 *
 */
public interface IntegralService {
	/**
	 * 积分交易扣减
	 * @param reqMap 参数Map
	 * @throws Exception 
	 */
	Response payment(Map<String, Object>reqMap);
	/**
	 * 积分交易撤销（冲正）
	 * @param reqMap
	 */
	Response paymentReversal(Map<String, Object>reqMap);
	
	/**
	 * 积分查询
	 * @param reqMap
	 * @return
	 */
	Response confirmInfo(Map<String, Object>reqMap);
}
