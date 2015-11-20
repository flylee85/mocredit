package com.mocredit.integral.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.integral.constant.OrderStatus;
import com.mocredit.integral.dto.OrderDto;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.persistence.ActivityMapper;
import com.mocredit.integral.persistence.OrderMapper;
import com.mocredit.integral.service.OrderService;

/**
 * @author ytq
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
    public boolean isExistOrderAndUpdate(String orderId) {
        boolean isExist = orderMapper.isExistOrder(orderId) > 0;
        if (isExist) {
            return orderMapper.updateStatusByOrderId(orderId,
                    OrderStatus.PAYMENT_REVOKE.getValue()) > 0;
        } else {
            return isExist;
        }
    }

    @Override
    public boolean isExistOrder(String orderId) {
        return orderMapper.isExistOrder(orderId) > 0;
    }

    @Override
    public List<OrderDto> synOrder(Integer offset, Integer pagesize) {
        return orderMapper.synOrder(offset, pagesize);
    }

    @Override
    public Order getOrderByOrderId(String orderId) {
        return orderMapper.getOrderByOrderId(orderId);
    }
}
