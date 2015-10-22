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
	Batch getOrderById(String id);
	//获取一条发码批次，随机获取
	Batch getOrderByRand();
	//查询发码批次列表，根据条件
	List<Batch> queryOrderList(Map<String,Object> orderMap);
	//获取发码批次总数量，根据条件
	int getOrderTotal(Map<String,Object> orderMap);
	//添加一条发码批次记录
	int addOrder(Batch order);
	//修改一条发码批次记录
	int updateOrder(Batch order);
	//删除一条发码批次记录 ，根据主键
	int deleteOrderById(String id);
	//删除发码批次记录 ，根据条件
	int deleteOrder(Map<String,Object> orderMap);
}
