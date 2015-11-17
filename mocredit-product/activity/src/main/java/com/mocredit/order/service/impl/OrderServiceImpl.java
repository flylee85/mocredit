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
    public int updateOrderStatusByOrderId(String orderId, String status) {
        orderRevoke(orderId);
        return orderMapper.updateOrderStatusByOrderId(orderId, status);
    }

    @Override
    public int updateOrderByCode(Order order) {
        return orderMapper.updateOrderByCode(order);
    }

    @Override
    public Order findOrderByOrderId(String orderId) {
        return orderMapper.findOrderByOrderId(orderId);
    }

    public Map<String, Object> orderRevoke(String orderId) {
        String orderRevokeUrl = PropertiesUtil.getValue("verifyCode.orderRevoke");
        // 调用Http工具，执行送码操作，并解析返回值
        String orderRevokeJson = HttpUtil.doRestfulByHttpConnection(orderRevokeUrl, orderId);// 送码
        Map<String, Object> orderRevokeMap = JSON.parseObject(orderRevokeJson, Map.class);
        logger.debug("送码，返回结果：" + orderRevokeJson);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 将返回对象的success设置为true,并返回数据对象
        resultMap.put("success", true);
        return resultMap;
    }
}
