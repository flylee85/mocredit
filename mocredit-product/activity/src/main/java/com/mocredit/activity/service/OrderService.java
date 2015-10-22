package com.mocredit.activity.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Batch;
import com.mocredit.base.pagehelper.PageInfo;

/**
 * 
 * 发码批次-Service接口
 * @author lishoukun
 * @date 2015/07/13
 */
@Service
public interface OrderService {
	/**
	 * 获取一条发码批次，根据主键
	 * @param id
	 * @return
	 */
	Batch getOrderById(String id);
	/**
	 * 获取一条发码批次，随机获取
	 * @return
	 */
	Batch getOrderByRand();
	/**
	 * 获取一条发码批次，根据条件
	 * @param orderMap
	 * @return
	 */
	Batch getOrder(Map<String,Object> orderMap);
	/**
	 * 查询发码批次列表，根据条件
	 * @param orderMap
	 * @return
	 */
	List<Batch> queryOrderList(Map<String,Object> orderMap);
	/**
	 * 获取发码批次总数量，根据条件
	 * @param orderMap
	 * @return
	 */
	Integer getOrderTotal(Map<String,Object> orderMap);
	/**
	 * 查询发码批次分页信息，根据条件
	 * 
	 * @param orderMap 请求参数
	 * @param currentPage 当前页
	 * @param pageSize 每页条数
	 * @return
	 */
	public PageInfo<Batch> queryOrderPage(Map<String, Object> orderMap,Integer pageNum, Integer pageSize);
	/**
	 * 添加发码批次
	 * @param order
	 * @return
	 */
	public Integer addOrder(Batch order);
	/**
	 * 更新发码批次
	 * @param order
	 * @return
	 */
	public Integer updateOrder(Batch order);
	/**
	 * 删除发码批次,根据主键
	 * @param id
	 * @return
	 */
	public Integer deleteOrderById(String id);
	/**
	 * 批量删除发码批次,根据主键集合
	 * @param ids
	 * @return
	 */
	public Integer deleteOrdersByIds(String ids);
	
	/**
	 * 删除发码批次,根据条件
	 * @param orderMap
	 * @return
	 */
	public Integer deleteOrder(Map<String,Object> orderMap);
}
