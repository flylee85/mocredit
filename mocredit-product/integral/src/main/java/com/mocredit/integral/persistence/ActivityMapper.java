package com.mocredit.integral.persistence;

import java.util.Date;
import java.util.List;

import com.mocredit.integral.entity.Terminal;
import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Store;

/**
 * @author ytq
 */
public interface ActivityMapper {
    /**
     * 保存活动信息
     *
     * @param t
     * @return
     */
    int save(@Param(value = "t") Activity t);

    /**
     * 根据id查询活动
     *
     * @param activityId
     * @return
     */
    Activity getByActivityId(@Param("activityId") String activityId);

    /**
     * 根据storeId和activityId查询门店信息
     *
     * @param storeId
     * @param activityId
     * @return
     */
    Store getByShopIdStoreIdAcId(@Param("storeId") String storeId,
                                 @Param("activityId") String activityId);

    /**
     * 保存门店信息
     *
     * @param t
     * @return
     */
    int saveStore(Store t);

    /**
     * 批量保存门店
     *
     * @param storeList
     * @return
     */
    int saveStoreList(List<Store> storeList);

    /**
     * 保存机具信息
     *
     * @param t
     * @return
     */
    int saveTerminal(Terminal t);

    /**
     * 批量保存机具
     *
     * @param terminalList
     * @return
     */
    int saveTerminalList(List<Terminal> terminalList);

    /**
     * 根据活动id更新活动状态
     *
     * @param activityId
     * @param status
     * @return
     */
    int updateActStatusById(@Param(value = "activityId") String activityId,
                            @Param(value = "status") String status);

    /**
     * 更具活动id删除活动
     *
     * @param activityId
     * @return
     */
    int deleteActAndStoreById(@Param("activityId") String activityId);

    /**
     * 保存ActivityTransRecord
     *
     * @param actRecod
     * @return
     */
    int saveActTransRecord(@Param(value = "t") ActivityTransRecord actRecod);

    /**
     * 开始时间和结束时间段统计次数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    ActivityTransRecord statCountByTime(
            @Param(value = "startTime") String startTime,
            @Param(value = "endTime") String endTime);

    /**
     * 更具订单id查询活动
     *
     * @param orderId
     * @return
     */
    Activity getActivityByOrderId(@Param(value = "orderId") String orderId);

    /**
     * 根据机具号查询活动列表
     *
     * @param enCode
     * @return
     */
    List<Activity> getActivityByEnCode(@Param(value = "enCode") String enCode);

    /**
     * 根据机具号查询活动列表和离线活动
     *
     * @param enCode
     * @return
     */
    List<Activity> getActivityByEnCodeAndOff(@Param(value = "enCode") String enCode);

    /**
     * 获取离线活动
     *
     * @return
     */
    List<Activity> getActivityOffByEncode(@Param(value = "enCode") String enCode);

    /**
     * 根据机具号和门店查询活动列表
     *
     * @return
     */
    List<Activity> getActivityByStoreId(@Param(value = "storeId") String storeId);

    /**
     * 根据机具号删除机具
     *
     * @param enCode
     * @return
     */
    int deleteTerminalByEnCode(@Param(value = "enCode") String enCode);

    /**
     * 删除门店信息
     *
     * @param storeId
     * @return
     */
    int deleteStoreByStoreId(@Param(value = "storeId") String storeId);

    /**
     * 根据机具号更新机具编码
     *
     * @param enCode
     * @param oldEnCode
     * @return
     */
    int updateTerminalByEnCode(@Param(value = "enCode") String enCode, @Param(value = "oldEnCode") String oldEnCode);

    /**
     * 根据活动id查询活动兑换统计
     *
     * @param actId
     * @return
     */
    List<ActivityTransRecord> getTranRecordByActId(@Param("activityId") String actId);

    /**
     * 将过期记录计数器归零
     *
     * @param expireDate
     * @return
     */
    int updateByExpireDate(@Param(value = "expireDate") String expireDate);

    /**
     * 根据活动id计数器减一
     *
     * @param actId
     * @return
     */
    int minusCountByActId(@Param(value = "actId") String actId);

}
