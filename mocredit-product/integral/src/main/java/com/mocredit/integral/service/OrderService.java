package com.mocredit.integral.service;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.Order;

/**
 * 积分订单业务
 * 
 * @author ytq
 * 
 */
public interface OrderService extends BaseService<Order> {
	/**
	 * 根据设备和订单id判断是否存在，存在及更新
	 * 
	 * @param device
	 * @param orderId
	 * @return
	 */
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

	/**
	 * 根据订单id判断订单是否已存在
	 * 
	 * @param orderId
	 * @return
	 */
	boolean isExistOrder(String orderId);
}
