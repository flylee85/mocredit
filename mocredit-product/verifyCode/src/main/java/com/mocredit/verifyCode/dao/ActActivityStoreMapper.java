package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.ActActivityStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by YHL on 15/7/16 10:45.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface ActActivityStoreMapper {

	/**
	 * 插入一条记录
	 * 
	 * @param actActivityStore
	 * @return
	 */
	public int insert(ActActivityStore actActivityStore);

	/**
	 * 根据活动ID查询活动对应的 活动-门店 列表
	 * 
	 * @param activity_id
	 * @return
	 */
	public ActActivityStore findByActivityIdAndStoreId(Map<String, Object> param);

	/**
	 * 批量保存 活动-门店 信息。
	 * 
	 * @param actActivityStores
	 * @return
	 */
	public int batchSave(@Param("actActivityStores") List<ActActivityStore> actActivityStores);

	/**
	 * 根据活动ID，删除活动对应的门店
	 * 
	 * @param activity_id
	 * @return
	 */
	public int deleteByActivityId(@Param("activity_id") String activity_id);

}
