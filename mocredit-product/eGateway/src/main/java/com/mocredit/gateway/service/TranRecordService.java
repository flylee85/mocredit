package com.mocredit.gateway.service;

import com.mocredit.gateway.entity.TranRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ytq on 2015/12/22.
 */
public interface TranRecordService extends BaseService<TranRecord> {
    /**
     * 将过期记录计数器归零
     *
     * @param expireDate
     * @return
     */
    boolean updateByExpireDate(String expireDate);

    /**
     * 根据卡号查询卡的统计
     *
     * @param cardNum
     * @return
     */
    List<TranRecord> getTranRecordByCardNum(String cardNum);

    /**
     * 根据卡号计数器减一
     *
     * @param cardNum
     * @return
     */
    boolean minusCountByCardNum(String cardNum);
}
