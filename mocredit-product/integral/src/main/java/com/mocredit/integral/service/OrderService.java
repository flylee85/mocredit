package com.mocredit.integral.service;

import com.mocredit.integral.entity.Order;

/**
 * 积分订单业务
 * 
 * @author ytq
 * 
 */
public interface OrderService extends BaseService<Order> {
	boolean isExistOrderAndUpdate(String device, String orderId);

	/**
	 * 通过device,orderId,transDate判断订单是否已经存在
	 * 
	 * @param device
	 * @param orderId
	 * @param transDate
	 * @return
	 */
	boolean isExistOrder(String device, String orderId, String transDate);
}
