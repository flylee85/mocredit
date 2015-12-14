package com.mocredit.integral.service;

import java.util.Date;
import java.util.List;

import com.mocredit.integral.entity.*;
import com.mocredit.integral.vo.ActivityVo;
import org.apache.ibatis.annotations.Param;

/**
 * 活动业务层
 *
 * @author ytq
 */

public interface ActivityService extends BaseService<Activity> {
    Activity getByActivityId(String activityId);

    boolean operActivityAndStore(ActivityVo activity, Response resp);

    /**
     * 保存ActivityTransRecord
     *
     * @param actRecod
     * @return
     */
    boolean saveActTransRecord(ActivityTransRecord actRecod);

    /**
     * 开始时间和结束时间段统计次数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    ActivityTransRecord statCountByTime(Date startTime, Date endTime);

    /**
     * storeId和activityId查询门店信息
     *
     * @param storeId
     * @param activityId
     * @return
     */
    Store getByShopIdStoreIdAcId(String storeId, String activityId);

    /**
     * 根据订单id查询活动系统
     *
     * @param orderId
     * @return
     */
    Activity getActivityByOrderId(String orderId);

    /**
     * 根据机具号查询活动列表
     *
     * @param enCode
     * @return
     */
    List<Activity> getActivityByEnCode(String enCode);

    /**
     * 根据机具号和门店查询活动列表
     *
     * @return
     */
    List<Activity> getActivityByStoreId(String storeId);

    /**
     * 根据机具号删除机具
     *
     * @param enCode
     * @return
     */
    boolean deleteTerminalByEnCode(String enCode);

    /**
     * 删除门店信息
     *
     * @param storeId
     * @return
     */
    boolean deleteStoreByStoreId(String storeId);

    /**
     * 根据机具号更新机具编码
     *
     * @param enCode
     * @param oldEnCode
     * @return
     */
    boolean updateTerminalByEnCode(String enCode, String oldEnCode);

    /**
     * 根据机具号查询活动列表
     *
     * @param enCode
     * @return
     */
    String getActIdsByEnCode(String enCode);

    /**
     * 保存机具信息
     *
     * @param t
     * @return
     */
    boolean saveTerminal(Terminal t);
}
