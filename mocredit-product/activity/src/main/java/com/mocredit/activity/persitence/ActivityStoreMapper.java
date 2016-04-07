package com.mocredit.activity.persitence;

import java.util.List;
import java.util.Map;

import com.mocredit.activity.model.ActivityStore;
import com.mocredit.activity.model.ActivityStoreStat;
import org.apache.ibatis.annotations.Param;

/**
 *
 * 活动商户关联-Dao 接口
 *
 * @author lishoukun
 * @date 2015/07/08
 */
public interface ActivityStoreMapper {
	/*
	 * 活动商户关联
	 */
	// 获取一条活动商户关联，根据主键
	ActivityStore getActivityStoreById(String id);

	// 获取一条活动商户关联，随机获取
	ActivityStore getActivityStoreByRand();

	// 查询活动商户关联列表，根据条件
	List<ActivityStore> queryActivityStoreList(Map<String, Object> activityStoreMap);

	// 获取活动商户关联总数量，根据条件
	int getActivityStoreTotal(Map<String, Object> activityStoreMap);

	// 添加一条活动商户关联记录
	int addActivityStore(ActivityStore activityStore);

	// 批量添加活动商户关联记录
	int batchAddActivityStore(List<ActivityStore> activityStore);

	// 修改一条活动商户关联记录
	int updateActivityStore(ActivityStore activityStore);

	// 删除一条活动商户关联记录 ，根据主键
	int deleteActivityStoreById(String id);

	// 删除活动商户关联记录 ，根据条件
	int deleteActivityStore(Map<String, Object> activityStoreMap);

	int getActivityStoreCount(Map<String, Object> activityStoreMap);

	List<ActivityStore> selectForChoose(Map<String, String> param);
	//根据活动id查询活动的各个门店兑换统计
	List<ActivityStoreStat> getActStoreStat(Map<String, Object> activityStoreMap);
	List<ActivityStoreStat> getActStoreAll();
	//保存更新活动统计信息
	int saveAndUpdActStore(@Param("t") ActivityStoreStat activityStoreStat);
}
