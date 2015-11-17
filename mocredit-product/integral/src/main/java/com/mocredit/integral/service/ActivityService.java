package com.mocredit.integral.service;

import java.util.Date;
import java.util.List;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.entity.Store;
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
     * 根据机具号查询活动列表
     *
     * @param enCode
     * @return
     */
    String getActIdsByEnCode(String enCode);
}
