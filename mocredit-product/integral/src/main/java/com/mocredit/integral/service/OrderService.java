package com.mocredit.integral.service;

import java.util.List;

import com.mocredit.integral.entity.Activity;
import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.dto.OrderDto;
import com.mocredit.integral.entity.Order;

/**
 * 积分订单业务
 *
 * @author ytq
 */
public interface OrderService extends BaseService<Order> {
    /**
     * 根据设备和订单id判断是否存在，存在及更新
     *
     * @param orderId
     * @return
     */
    boolean isExistOrderAndUpdate(String orderId);
    /**
     * 根据订单id判断订单是否已存在
     *
     * @param orderId
     * @return
     */
    boolean isExistOrder(String orderId);

    /**
     * 给订单管理系统提供同步订单接口
     *
     * @param offset
     * @param pagesize
     * @return
     */
    List<OrderDto> synOrder(Integer offset, Integer pagesize);

}
