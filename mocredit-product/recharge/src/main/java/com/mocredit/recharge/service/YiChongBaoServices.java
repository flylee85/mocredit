package com.mocredit.recharge.service;

import java.util.Date;

import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.model.RechangeBo;

public interface YiChongBaoServices {
	/**
	 * 充值
	  * rechange 
	  * @param dealerid 用户编码
	  * @param dealerkey 密码
	  * @param photonum 充值手机号
	  * @param amount 充值金额 (单位元)
	  * @param orderid 订单编号
	  * @return 
	  * @return RechangeBo 
	 */
	public RechangeBo rechange(String photonum,
			String amount, String orderid, Date time,Code code);

}
