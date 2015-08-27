package com.mocredit.bank.service;

import java.util.List;
import java.util.Set;

import com.mocredit.bank.datastructure.ResponseData;
import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.RequestData;

/**
 * 积分操作业务接口
 * 
 * @author liaoying Created on 2015年8月11日
 *
 */
public interface IntegralService {
	/**
	 * 积分交易扣减
	 * 
	 * @param reqMap
	 *            参数Map
	 * @throws Exception
	 */
	ResponseData payment(RequestData requestData);

	/**
	 * 积分交易冲正
	 * 
	 * @param requestData
	 * @return
	 */
	ResponseData paymentReversal(RequestData requestData);

	/**
	 * 积分交易撤销
	 * 
	 * @param reqMap
	 */
	ResponseData paymentRevoke(RequestData requestData);


	/**
	 * 积分交易撤销冲正
	 * 
	 * @param requestData
	 * @return
	 */
	ResponseData paymentRevokeReversal(RequestData requestData);

	/**
	 * 积分查询
	 * 
	 * @param reqMap
	 * @return
	 */
	ResponseData confirmInfo(RequestData requestData);
	
	/**
	 * 对账
	 */
	Set<Integer> checkAccount(List<Payment> records);
}
