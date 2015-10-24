package com.mocredit.activity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Batch;
import com.mocredit.activity.persitence.BatchMapper;
import com.mocredit.activity.service.BatchService;
import com.mocredit.base.pagehelper.PageInfo;

/**
 * 
 * 发码批次-Service映射层
 * @author lishoukun
 * @date 2015/07/13
 */
@Service
public class BatchServiceImpl implements BatchService{
	
	@Autowired
	private BatchMapper batchMapper;
	/**
	 * 获取一条发码批次，根据主键
	 * @param id
	 * @return
	 */
	public Batch getOrderById(String id) {
		Batch orderObj = batchMapper.getBatchById(id);
		return orderObj;
	}
	/**
	 * 获取一条发码批次，随机获取
	 * @return
	 */
	public Batch getOrderByRand() {
		Batch orderObj = batchMapper.getBatchByRand();
		return orderObj;
	}
	/**
	 * 获取一条发码批次，根据条件
	 * @param orderMap
	 * @return
	 */
	public Batch getOrder(Map<String,Object> orderMap) {
		List<Batch> orderList = batchMapper.queryBatchList(orderMap);
		if(orderList.size()>0){
			return orderList.get(0);
		}
		return null;
	}
	/**
	 * 查询发码批次列表，根据条件
	 * @param orderMap
	 * @return
	 */
	public List<Batch> queryOrderList(Map<String,Object> orderMap) {
		List<Batch> orderList = batchMapper.queryBatchList(orderMap);
		return orderList;
	}
	/**
	 * 获取发码批次总数量，根据条件
	 * @param orderMap
	 * @return
	 */
	public Integer getOrderTotal(Map<String,Object> orderMap) {
		Integer orderTotal = batchMapper.getBatchTotal(orderMap);
		return orderTotal;
	}
	/**
	 * 查询发码批次分页信息，根据条件
	 * @param orderMap 请求参数
	 * @param currentPage  当前页
	 * @param pageSize	每页条数
	 * @return
	 */
	public PageInfo<Batch> queryOrderPage(Map<String,Object> orderMap,Integer pageNum,Integer pageSize){
		List<Batch> orders = batchMapper.queryBatchList(orderMap);
		return new PageInfo<>(orders);
	}
	/**
	 * 添加发码批次
	 * @param order
	 * @return
	 */
	public Integer addOrder(Batch order) {
		Integer affectCount = batchMapper.addBatch(order);
		return affectCount;
	}
	/**
	 * 更新发码批次
	 * @param order
	 * @return
	 */
	public Integer updateOrder(Batch order) {
		Integer affectCount = batchMapper.updateBatch(order);
		return affectCount;
	}
	/**
	 * 删除发码批次,根据主键
	 * @param id
	 * @return
	 */
	public Integer deleteOrderById(String id) {
		Integer affectCount = batchMapper.deleteBatchById(id);
		return affectCount;
	}
	/**
	 * 批量删除发码批次,根据主键集合
	 * @param ids
	 * @return
	 */
	public Integer deleteOrdersByIds(String ids) {
		Integer affectCount = 0;
		String[] idArray = ids.split(",");
		for(String id : idArray){
			affectCount += batchMapper.deleteBatchById(id);
		}
		return affectCount;
	}
	
	/**
	 * 删除发码批次,根据条件
	 * @param orderMap
	 * @return
	 */
	public Integer deleteOrder(Map<String,Object> orderMap) {
		Integer affectCount = batchMapper.deleteBatch(orderMap);
		return affectCount;
	}
}
