/**
 *
 */
package com.mocredit.order.service;

import java.util.List;

import com.mocredit.order.entity.Order;
import com.mocredit.order.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;

/**
 * @author ytq
 */
public interface OrderService extends BaseService<Order> {
    Logger logger = Logger.getLogger("order_log");

    List<Order> findOrderList(OrderVo orderVo);

    int updateOrderStatusByOrderId(String orderId, String status);

    int updateOrderByCode(Order order);

    /**
     * 更加订单id查询订单信息
     *
     * @param orderId
     * @return
     */
    Order findOrderByOrderId(String orderId);

}
