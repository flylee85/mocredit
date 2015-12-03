/**
 *
 */
package com.mocredit.order.persitence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mocredit.order.entity.Order;
import com.mocredit.order.vo.OrderVo;

/**
 * @author ytq
 */
public interface OrderMapper {
    /**
     * 保存订单信息
     *
     * @param t
     * @return
     */
    int save(@Param(value = "t") Order t);

    /**
     * 条件查询订单列表
     *
     * @param orderVo
     * @return
     */
    List<Order> findOrderList(@Param(value = "tvo") OrderVo orderVo);

    int updateOrderStatusByOrderId(@Param(value = "orderId") String orderId, @Param(value = "status") String status);

    /**
     * 根据code更新订单信息
     *
     * @param order
     * @return
     */
    int updateOrderByCode(@Param(value = "t") Order order);

    /**
     * 根据活动id和码更新订单id和状态
     *
     * @param activityId
     * @param code
     * @param orderId
     * @param status
     * @return
     */
    int updateOrderByActIdAndCode(@Param(value = "activityId") String activityId,
                                  @Param(value = "code") String code,
                                  @Param(value = "orderId") String orderId,
                                  @Param(value = "status") String status);

    /**
     * 根据订单id查询订单信息
     *
     * @param orderId
     * @return
     */
    Order findOrderByOrderId(@Param(value = "orderId") String orderId);

    /**
     * 根据流水id查询订单信息
     *
     * @return
     */
    Order findOrderByOId(@Param(value = "id") String id);
}
