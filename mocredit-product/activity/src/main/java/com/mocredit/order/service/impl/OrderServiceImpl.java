/**
 *
 */
package com.mocredit.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.order.constant.OrderStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.order.entity.Order;
import com.mocredit.order.persitence.OrderMapper;
import com.mocredit.order.service.OrderService;
import com.mocredit.order.vo.OrderVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ytq
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

    @Override
    @Transactional
    public int updateOrderStatusById(String id, String status) {
        Order order = orderMapper.findOrderByOId(id);
        orderRevoke(order.getOrderId());
        return orderMapper.updateOrderStatusByOrderId(order.getOrderId(), status);
    }

    @Override
    public int updateOrderByCode(Order order) {
        return orderMapper.updateOrderByCode(order);
    }

    @Override
    public Order findOrderByOrderId(String orderId) {
        return orderMapper.findOrderByOrderId(orderId);
    }

    @Override
    @Transactional
    public int checkOrderById(String id) {
        Order order = orderMapper.findOrderByOId(id);
        Map mapCheck = orderCheck(order.getActivityId(), order.getCode());
        return orderMapper.updateOrderByActIdAndCode(order.getActivityId(), order.getCode(), mapCheck.get("orderId") + "", OrderStatusType.EXCHANGE.getValue());
    }

    public Map<String, Object> orderCheck(String activityId, String code) {
        String orderCheckUrl = PropertiesUtil.getValue("verifyCode.orderCheck");
        // 调用Http工具，执行送码操作，并解析返回值
        Map<String, Object> param = new HashMap<>();
        param.put("activityId", activityId);
        param.put("code", code);
        String orderRevokeJson = HttpUtil.doRestfulByHttpConnection(orderCheckUrl, JSON.toJSONString(param));
        Map<String, Object> orderRevokeMap = JSON.parseObject(orderRevokeJson, Map.class);
        logger.debug("送码，返回结果：" + orderRevokeJson);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 将返回对象的success设置为true,并返回数据对象
        resultMap.put("success", true);
        return resultMap;
    }

    public Map<String, Object> orderRevoke(String orderId) {
        String orderRevokeUrl = PropertiesUtil.getValue("verifyCode.orderRevoke");
        // 调用Http工具，执行送码操作，并解析返回值
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        String orderRevokeJson = HttpUtil.doRestfulByHttpConnection(orderRevokeUrl, JSON.toJSONString(param));// 送码
        Map<String, Object> orderRevokeMap = JSON.parseObject(orderRevokeJson, Map.class);
        logger.debug("送码，返回结果：" + orderRevokeJson);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 将返回对象的success设置为true,并返回数据对象
        resultMap.put("success", true);
        return resultMap;
    }
}
