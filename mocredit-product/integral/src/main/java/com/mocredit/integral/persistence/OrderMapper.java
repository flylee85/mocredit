package com.mocredit.integral.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.dto.OrderDto;
import com.mocredit.integral.entity.Order;

/**
 * @author ytq 2015年8月21日
 */
public interface OrderMapper {
    /**
     * 保存积分记录
     *
     * @param t
     * @return
     */
    int save(@Param(value = "t") Order t);

    /**
     * 订单id查询订单
     *
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(@Param(value = "orderId") String orderId);

    /**
     * 根据订单Id更新订单状态
     *
     * @param orderId
     * @param status
     * @return
     */
    int updateStatusByOrderId(@Param(value = "orderId") String orderId,
                              @Param(value = "status") String status);

    /**
     * 判断订单是否存在
     *
     * @param orderId
     * @return
     */
    int isExistOrder(@Param(value = "orderId") String orderId);

    /**
     * 给订单管理系统提供同步订单接口
     *
     * @param offset
     * @param pagesize
     * @return
     */
    List<OrderDto> synOrder(@Param(value = "offset") Integer offset,
                            @Param(value = "pagesize") Integer pagesize);
}
