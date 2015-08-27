package com.mocredit.integral.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.integral.constant.ActivityStatus;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.entity.Store;
import com.mocredit.integral.persistence.ActivityMapper;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.LogService;
import com.mocredit.integral.util.DateTimeUtils;

/**
 * 
 * @author ytq
 * 
 */
@Service
public class ActivityServiceImpl extends LogService implements ActivityService {
	@Autowired
	private ActivityMapper activityMapper;
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean save(Activity t) {
		return activityMapper.save(t) > 0;
	}

	@Override
	public Activity getByActivityId(Integer activityId) {
		return activityMapper.getByActivityId(activityId);
	}

	@Override
	public boolean operActivityAndStore(Activity activity, String productCode,
			Integer operCode, String storeList, Response resp) {
		boolean flag = true;
		// 1导入 2 更新 3 取消 4 启用
		switch (operCode) {
		case 1:
			Activity ac = getByActivityId(activity.getActivityId());
			if (ac != null) {
				resp.setErrorCode(ErrorCodeType.EXIST_ACTIVITY_ERROR.getValue());
				flag = false;
			} else {
				try {
					JsonNode jsonNode = objectMapper.readTree(storeList);
					List<Store> storeLists = new ArrayList<Store>();
					for (JsonNode jNode : jsonNode) {
						Integer shopId = jNode.get("shopId").asInt();
						Integer storeId = jNode.get("storeId").asInt();
						storeLists.add(getStore(activity, shopId, storeId));
					}
					// 保存活动和对应的店铺
					saveActivityAndStore(activity, storeLists);
				} catch (Exception e) {
					LOGGER.error(
							"###operActivityAndStore storeList={} error={}###",
							storeList, e);
					resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
					flag = false;
				}

			}
			break;
		case 2:
			try {
				JsonNode jsonNode = objectMapper.readTree(storeList);
				List<Store> storeLists = new ArrayList<Store>();
				for (JsonNode jNode : jsonNode) {
					Integer shopId = jNode.get("shopId").asInt();
					Integer storeId = jNode.get("storeId").asInt();
					storeLists.add(getStore(activity, shopId, storeId));
				}
				// 保存活动和对应的店铺
				updateActivityAndStore(activity, storeLists);
			} catch (Exception e) {
				LOGGER.error(
						"###operActivityAndStore storeList={} error={}###",
						storeList, e);
				resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
				flag = false;
			}
			break;
		case 3:
			if (activityMapper.updateActStatusById(activity.getActivityId(),
					ActivityStatus.STOP.getValue()) <= 0) {
				resp.setErrorCode(ErrorCodeType.EXIST_ACTIVITY_ERROR.getValue());
				flag = false;
			}
			break;
		case 4:
			if (activityMapper.updateActStatusById(activity.getActivityId(),
					ActivityStatus.START.getValue()) <= 0) {
				resp.setErrorCode(ErrorCodeType.EXIST_ACTIVITY_ERROR.getValue());
				flag = false;
			}
			break;
		}
		return flag;
	}

	public Store getStore(Activity activity, Integer shopId, Integer storeId) {
		Store store = new Store();
		store.setActivityId(activity.getActivityId());
		store.setShopId(shopId);
		store.setStoreId(storeId);
		return store;
	}

	@Transactional
	public void saveActivityAndStore(Activity activity, List<Store> storeLists) {
		activityMapper.save(activity);
		for (Store store : storeLists) {
			activityMapper.saveStore(store);
		}
	}

	@Transactional
	public void updateActivityAndStore(Activity activity, List<Store> storeLists) {
		// 先删除
		activityMapper.deleteActAndStoreById(activity.getActivityId());
		// 再新增
		saveActivityAndStore(activity, storeLists);
	}

	@Override
	public boolean saveActTransRecord(ActivityTransRecord actRecod) {
		return activityMapper.saveActTransRecord(actRecod) > 0;
	}

	@Override
	public ActivityTransRecord statCountByTime(Date startTime, Date endTime) {
		return activityMapper.statCountByTime(
				DateTimeUtils.parseYYYYMMDD(startTime),
				DateTimeUtils.parseYYYYMMDD(endTime));
	}

}
