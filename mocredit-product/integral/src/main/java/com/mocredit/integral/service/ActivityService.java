package com.mocredit.integral.service;

import java.util.Date;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.entity.Store;
import com.mocredit.integral.vo.ActivityVo;

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
     * 根据shopId和storeId和activityId查询门店信息
     *
     * @param shopId
     * @param storeId
     * @param activityId
     * @return
     */
    Store getByShopIdStoreIdAcId(String shopId, String storeId,
                                 String activityId);

    /**
     * 根据订单id查询活动系统
     *
     * @param orderId
     * @return
     */
    Activity getActivityByOrderId(String orderId);
}
