package com.mocredit.integral.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.integral.constant.OrderStatus;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.persistence.ActivityMapper;
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
	@Autowired
	private ActivityMapper activityMapper;

	@Override
	@Transactional
	public boolean save(Order t) {
		ActivityTransRecord actRecod = new ActivityTransRecord();
		actRecod.setActivityId(t.getActivityId());
		actRecod.setTransCount(1);
		actRecod.setTransDate(new Date());
		return orderMapper.save(t) > 0
				&& activityMapper.saveActTransRecord(actRecod) > 0;
	}

	@Override
	@Transactional
	public boolean isExistOrderAndUpdate(String device, String orderId) {
		boolean isExist = orderMapper.getOrderByOrderIdAndDevice(device,
				orderId) > 0;
		if (isExist) {
			return orderMapper.updateStatusByOrderIdAndDevice(device, orderId,
					OrderStatus.REVOKE.getValue()) > 0;
		} else {
			return isExist;
		}
	}

	@Override
	public boolean isExistOrder(String device, String orderId, String transDate) {
		return orderMapper.isExistOrder(device, orderId, transDate) > 0;
	}

	@Override
	public boolean isExistOrder(String orderId) {
		return orderMapper.getOrderByOrderId(orderId) > 0;
	}

}
