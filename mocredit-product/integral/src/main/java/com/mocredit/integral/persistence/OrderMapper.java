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
	 * 订单id和设备号查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	int getOrderByOrderIdAndDevice(@Param(value = "device") String device,
			@Param(value = "orderId") String orderId);

	/**
	 * 订单id查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	int getOrderByOrderId(@Param(value = "orderId") String orderId);

	/**
	 * 根据订单Id更新订单状态
	 * 
	 * @param orderId
	 * @param status
	 * @return
	 */
	int updateStatusByOrderIdAndDevice(@Param(value = "device") String device,
			@Param(value = "orderId") String orderId,
			@Param(value = "status") Integer status);

	/**
	 * 判断订单是否存在
	 * 
	 * @param device
	 * @param orderId
	 * @param transDate
	 * @return
	 */
	int isExistOrder(@Param(value = "device") String device,
			@Param(value = "orderId") String orderId,
			@Param(value = "transDate") String transDate);

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
