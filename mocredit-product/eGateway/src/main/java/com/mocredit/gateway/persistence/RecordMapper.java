package com.mocredit.gateway.persistence;

import com.mocredit.gateway.entity.Record;
import org.apache.ibatis.annotations.Param;

/**
 * @author ytq 2015年8月21日
 */
public interface RecordMapper {
    /**
     * 保存兑换记录
     *
     * @param t
     * @return
     */
    int save(@Param(value = "t") Record t);

    /**
     * 查询该订单的数量
     *
     * @param orderId
     * @return
     */
    int selectByOldOrderIdCount(@Param(value = "orderId") String orderId);

    /**
     * 根据订单id查询订单
     *
     * @param orderId
     * @return
     */
    Record selectByOrderId(@Param(value = "orderId") String orderId);
}
