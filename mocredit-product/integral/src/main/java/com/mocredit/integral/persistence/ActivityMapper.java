package com.mocredit.integral.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Store;

/**
 * @author ytq
 * 
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
	Activity getByActivityId(@Param("activityId") Integer activityId);

	/**
	 * 根据shopId和storeId和activityId查询门店信息
	 * 
	 * @param shopId
	 * @param storeId
	 * @param activityId
	 * @return
	 */
	Store getByShopIdStoreIdAcId(@Param("shopId") String shopId,
			@Param("storeId") String storeId,
			@Param("activityId") Integer activityId);

	/**
	 * 保存门店信息
	 * 
	 * @param t
	 * @return
	 */
	int saveStore(Store t);

	/**
	 * 根据活动id更新活动状态
	 * 
	 * @param activityId
	 * @param status
	 * @return
	 */
	int updateActStatusById(@Param(value = "activityId") Integer activityId,
			@Param(value = "status") String status);

	/**
	 * 更具活动id删除活动
	 * 
	 * @param activityId
	 * @return
	 */
	int deleteActAndStoreById(@Param("activityId") Integer activityId);

	/**
	 * 保存ActivityTransRecord
	 * 
	 * @param actRecod
	 * @return
	 */
	int saveActTransRecord(ActivityTransRecord actRecod);

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
}
