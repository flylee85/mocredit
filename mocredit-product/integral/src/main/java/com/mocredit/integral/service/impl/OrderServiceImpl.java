package com.mocredit.integral.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mocredit.base.util.DateUtil;
import com.mocredit.integral.constant.ActivityRule;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.constant.TranRecordType;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.util.DateTimeUtils;
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
    public synchronized boolean saveAndCount(Order t) {
//        ActivityTransRecord actRecod = new ActivityTransRecord();
//        actRecod.setActivityId(t.getActivityId());
//        actRecod.setTransCount(1);
//        actRecod.setTransDate(new Date());
//        return orderMapper.save(t) > 0
//                && activityMapper.saveActTransRecord(actRecod) > 0;
        saveOrderAndTranRecord(t);
        return true;
    }

    @Override
    public boolean save(Order t) {
        return orderMapper.save(t) > 0;
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

    @Override
    public Order getOrderBySearchNoAndBatchNo(String searchno, String batchno) {
        return orderMapper.getOrderBySearchNoAndBatchNo(searchno, batchno);
    }

    @Override
    public List<Map<String, Object>> findOrderByList(OrderDto orderDto) {
        return orderMapper.findOrderByList(orderDto);
    }

    @Override
    public int findOrderByListCount(OrderDto orderDto) {
        return orderMapper.findOrderByListCount(orderDto);
    }

    public void saveOrderAndTranRecord(Order order) {
        orderMapper.save(order);
        for (String tranType : TranRecordType.tranTypes) {
            ActivityTransRecord tranRecord = new ActivityTransRecord();
            tranRecord.setActivityId(order.getActivityId());
            tranRecord.setTransType(tranType);
            tranRecord.setTransCount(1);
            tranRecord.setExpireDate(getExpireDateByTranType(tranType));
            activityMapper.saveActTransRecord(tranRecord);
        }
    }

    public Date getExpireDateByTranType(String tranType) {
        String expireDate = null;
        switch (tranType) {
            case ActivityRule.DayMax:
                expireDate = DateUtil.getCurDate("yyyy-MM-dd");
                break;
            case ActivityRule.WeekMax:
                expireDate = DateTimeUtils.getMaxDayOfCurrentWeek("yyyy-MM-dd");
                break;
            case ActivityRule.MonthMax:
                expireDate = DateTimeUtils.getMaxDayOfCurrentMonth("yyyy-MM-dd");
                break;
            case ActivityRule.YearMax:
                expireDate = DateTimeUtils.getMaxDayOfCurrentYear("yyyy-MM-dd");
                break;
            case ActivityRule.TotalMax:
                expireDate = "2099-12-31";
                break;
        }
        return DateUtil.strToDate(expireDate, "yyyy-MM-dd");
    }
}
