/**
 * 
 */
package com.mocredit.order.service;

import java.util.List;

import com.mocredit.order.entity.Order;
import com.mocredit.order.vo.OrderVo;

/**
 * @author ytq
 * 
 */
public interface OrderService extends BaseService<Order> {
	public List<Order> findOrderList(OrderVo orderVo);
}
