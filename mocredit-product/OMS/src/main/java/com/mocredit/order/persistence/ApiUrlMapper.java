/**
 * 
 */
package com.mocredit.order.persistence;

import org.apache.ibatis.annotations.Param;

import com.mocredit.order.entity.ApiUrl;

/**
 * @author ytq
 * 
 */
public interface ApiUrlMapper {
	/**
	 * 保存api信息
	 * 
	 * @param t
	 * @return
	 */
	int save(@Param(value = "t") ApiUrl t);

	/**
	 * 更新api信息
	 * 
	 * @param t
	 * @return
	 */
	int updateByCode(@Param(value = "t") ApiUrl t);

}
