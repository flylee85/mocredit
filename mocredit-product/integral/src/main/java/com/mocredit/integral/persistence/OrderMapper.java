package com.mocredit.integral.persistence;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.Order;

/**
 * @author ytq 2015年8月21日
 */
public interface OrderMapper {
	/**
	 * 保存积分记录
	 * 
	 * @param t
	 * @return
	 */
	int save(@Param(value = "t") Order t);

	/**
	 * 更加订单id查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	int getOrderByOrderId(String orderId);

	/**
	 * 根据订单Id更新订单状态
	 * 
	 * @param orderId
	 * @param status
	 * @return
	 */
	int updateStatusByOrderId(String orderId, Integer status);
}
