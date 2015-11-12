/**
 * 
 */
package com.mocredit.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.order.entity.Order;
import com.mocredit.order.persitence.OrderMapper;
import com.mocredit.order.service.OrderService;
import com.mocredit.order.vo.OrderVo;

/**
 * @author ytq
 * 
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public boolean save(Order t) {
		return orderMapper.save(t) > 0;
	}

	@Override
	public List<Order> findOrderList(OrderVo orderVo) {
		return orderMapper.findOrderList(orderVo);
	}

}
