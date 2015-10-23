package com.mocredit.activity.persitence;

import java.util.List;
import java.util.Map;

import com.mocredit.activity.model.Batch;

/**
 * 
 * 发码批次-Dao 接口
 * @author lishoukun
 * @date 2015/07/13
 */
public interface BatchMapper {
	/*
	 * 发码批次
	 */
	//获取一条发码批次，根据主键
	Batch getBatchById(String id);
	//获取一条发码批次，随机获取
	Batch getBatchByRand();
	//查询发码批次列表，根据条件
	List<Batch> queryBatchList(Map<String,Object> batchMap);
	//获取发码批次总数量，根据条件
	int getBatchTotal(Map<String,Object> batchMap);
	//添加一条发码批次记录
	int addBatch(Batch batch);
	//修改一条发码批次记录
	int updateBatch(Batch batch);
	//删除一条发码批次记录 ，根据主键
	int deleteBatchById(String id);
	//删除发码批次记录 ，根据条件
	int deleteBatch(Map<String,Object> batchMap);
}
