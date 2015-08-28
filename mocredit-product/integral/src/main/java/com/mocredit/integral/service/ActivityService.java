package com.mocredit.integral.service;

import java.util.Date;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Response;

/**
 * 活动业务层
 * 
 * @author ytq
 * 
 */

public interface ActivityService extends BaseService<Activity> {
	Activity getByActivityId(Integer activityId);

	boolean operActivityAndStore(Activity activity, Integer operCode,
			String storeList, Response resp);

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
}
