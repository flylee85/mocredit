package com.mocredit.order.persitence;

import org.apache.ibatis.annotations.Param;

import com.mocredit.order.entity.UserDisplay;
import com.mocredit.order.vo.UserDisplayVo;

public interface UserDisplayMapper {
	/**
	 * 保存用户显示信息
	 * 
	 * @param t
	 * @return
	 */
	int save(@Param(value = "t") UserDisplay t);

	/**
	 * 更新用户显示信息
	 * 
	 * @param tvo
	 * @return
	 */
	int update(@Param(value = "tvo") UserDisplayVo tvo);
}
