/**
 *
 */
package com.mocredit.order.service;

import java.util.List;

import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.order.dto.OrderDto;
import com.mocredit.order.entity.Order;
import com.mocredit.order.entity.OrderData;
import com.mocredit.order.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;

/**
 * @author ytq
 */
public interface OrderService extends BaseService<Order> {
    Logger logger = Logger.getLogger("order_log");

    List<Order> findOrderList(OrderVo orderVo);

    ResponseData findOrderList(OrderDto orderDto);

    boolean revokeOrderByOrderId(String orderId, String enCode,ResponseData responseData);

    int updateOrderByCode(Order order);

    /**
     * 更加订单id查询订单信息
     *
     * @param orderId
     * @return
     */
    Order findOrderByOrderId(String orderId);

    /**
     * 根据流水id监测订单
     *
     * @return
     */
    int checkOrderById(String id);
}
