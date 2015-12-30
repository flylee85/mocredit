package com.mocredit.gateway.persistence;

import com.mocredit.gateway.entity.TranRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ytq 2015年8月21日
 */
public interface TranRecordMapper {
    /**
     * 保存兑换记录统计
     *
     * @param t
     * @return
     */
    int save(@Param(value = "t") TranRecord t);

    /**
     * 将过期记录计数器归零
     *
     * @param expireDate
     * @return
     */
    int updateByExpireDate(@Param(value = "expireDate") String expireDate);

    /**
     * 根据卡号查询卡的统计
     *
     * @param cardNum
     * @return
     */
    List<TranRecord> getTranRecordByCardNum(@Param(value = "cardNum") String cardNum);

    /**
     * 根据卡号计数器减一
     *
     * @param cardNum
     * @return
     */
    int minusCountByCardNum(@Param(value = "cardNum") String cardNum);
}
