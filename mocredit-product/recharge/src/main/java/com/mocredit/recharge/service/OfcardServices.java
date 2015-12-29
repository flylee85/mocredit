package com.mocredit.recharge.service;

import java.util.Date;

import com.mocredit.recharge.model.Code;
import com.mocredit.recharge.model.RechangeBo;

public interface OfcardServices {
	/**
	 * 充值
	  * rechange 
	  * @param phone 充值手机号
	  * @param cardnum 充值金额  可选数量（ 10元，20元,30元，50元，100元，300元）
	  * @param sporderid 订单编号
	  * @param time 订单时间
	  * @param code code
	  * @param server 服务器地址
	  * @return 
	  * @return RechangeBo 
	  * @date 2012-6-15 上午11:46:35
	 */
	public RechangeBo rechange(String phone,int cardnum,String sporderid,Date time,Code code,String server);
	
	/**
	 * 查询账户余额
	  * searchBalance 
	  * @param userid
	  * @param password
	  * @return 
	  * @return double 
	  * @date 2012-6-15 上午11:21:54
	 */
	public double searchBalance(String userid,String password);
	
	/**
	 * 通知充值成功
	 * @param retcode
	 * @param sporderid
	 * @param ordersuccesstime
	 * @param errmsg
	 * @return
	 */
	public boolean notice(String retcode,String sporderid,String ordersuccesstime ,String errmsg);
}
