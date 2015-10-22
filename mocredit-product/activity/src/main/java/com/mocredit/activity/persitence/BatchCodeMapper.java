package com.mocredit.activity.persitence;

import java.util.List;
import java.util.Map;

import com.mocredit.activity.model.BatchCode;

/**
 * 
 * 发码批次码-Dao 接口
 * @author lishoukun
 * @date 2015/07/13
 */
public interface BatchCodeMapper {
	public final static String PREFIX = BatchCodeMapper.class.getName();
	/*
	 * 发码批次码
	 */
	//获取一条发码批次码，根据主键
	BatchCode getOrderCodeById(String id);
	//获取一条发码批次码，随机获取
	BatchCode getOrderCodeByRand();
	//查询发码批次码列表，根据条件
	List<BatchCode> queryOrderCodeList(Map<String,Object> orderCodeMap);
	//获取发码批次码总数量，根据条件
	int getOrderCodeTotal(Map<String,Object> orderCodeMap);
	//添加一条发码批次码记录
	int addOrderCode(BatchCode orderCode);
	//批量添加码批次记录
	int batchAddOrderCode(List<BatchCode> orderCodeList);
	//修改一条发码批次码记录
	int updateOrderCode(BatchCode orderCode);
	//删除一条发码批次码记录 ，根据主键
	int deleteOrderCodeById(String id);
	//删除发码批次码记录 ，根据条件
	int deleteOrderCode(Map<String,Object> orderCodeMap);
}
