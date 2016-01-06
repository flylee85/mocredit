/**
 *
 */
package com.mocredit.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.order.constant.OrderStatusType;
import com.mocredit.order.dto.OrderDto;
import com.mocredit.order.entity.OrderData;
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
    public ResponseData findOrderList(OrderDto orderDto) {
        String param = JSON.toJSONString(orderDto);
        String orderSearchUrl = null;
        if ("02".equals(orderDto.getType())) {
            orderSearchUrl = PropertiesUtil.getValue("verifyCode.orderSearch");
        }
        if ("01".equals(orderDto.getType())) {
            orderSearchUrl = PropertiesUtil.getValue("integral.orderSearch");
        }
        String resp = HttpUtil.doRestfulByHttpConnection(orderSearchUrl, param);
        return JSON.parseObject(resp, ResponseData.class);
    }

    @Override
    public ResponseData findCodeOrderList(OrderDto orderDto) {
        String param = JSON.toJSONString(orderDto);
        String resp = HttpUtil.doRestfulByHttpConnection(PropertiesUtil.getValue("verifyCode.codeOrderSearch"), param);
        return JSON.parseObject(resp, ResponseData.class);
    }

    @Override
    public boolean revokeOrderByOrderId(String orderId, String enCode, ResponseData responseData) {
        orderRevoke(orderId, enCode, responseData);
        return responseData.getSuccess();
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

    @Override
    public void abolishCodeById(ResponseData responseData, String id) {
        String url = PropertiesUtil.getValue("verifyCode.codeOrderOp");
        // 调用Http工具，执行送码操作，并解析返回值
        Map<String, Object> param = new HashMap<>();
        param.put("oper", 3);
        param.put("id", id);
        String respJson = HttpUtil.doRestfulByHttpConnection(url, JSON.toJSONString(param));// 送码
        ResponseData resp = JSON.parseObject(respJson, ResponseData.class);
        responseData.setSuccess(resp.getSuccess());
        responseData.setErrorMsg(resp.getErrorMsg());
    }

    @Override
    public void delayOrderById(ResponseData responseData, String id, String delayTime) {
        String url = PropertiesUtil.getValue("verifyCode.codeOrderOp");
        // 调用Http工具，执行送码操作，并解析返回值
        Map<String, Object> param = new HashMap<>();
        param.put("oper", 2);
        param.put("id", id);
        param.put("endTime", delayTime);
        String respJson = HttpUtil.doRestfulByHttpConnection(url, JSON.toJSONString(param));// 送码
        ResponseData resp = JSON.parseObject(respJson, ResponseData.class);
        responseData.setSuccess(resp.getSuccess());
        responseData.setErrorMsg(resp.getErrorMsg());
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

    public void orderRevoke(String orderId, String enCode, ResponseData responseData) {
        String orderRevokeUrl = PropertiesUtil.getValue("verifyCode.orderRevoke");
        // 调用Http工具，执行送码操作，并解析返回值
        Map<String, Object> param = new HashMap<>();
        param.put("requestSerialNumber", orderId);
        param.put("device", enCode);
        String orderRevokeJson = HttpUtil.doRestfulByHttpConnection(orderRevokeUrl, JSON.toJSONString(param));// 送码
        ResponseData resp = JSON.parseObject(orderRevokeJson, ResponseData.class);
        responseData.setSuccess(resp.getSuccess());
        responseData.setErrorMsg(resp.getErrorMsg());
    }
}
