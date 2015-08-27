package com.mocredit.integral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.integral.entity.Order;
import com.mocredit.integral.persistence.OrderMapper;
import com.mocredit.integral.service.OrderService;

/**
 * 
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

}
