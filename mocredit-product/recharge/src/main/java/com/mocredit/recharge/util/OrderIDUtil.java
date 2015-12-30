package com.mocredit.recharge.util;

import java.util.Date;

import com.mocredit.base.util.DateTimeUtils;
import com.mocredit.base.util.RandomUtil;

/*
 * 订单号工具类
 */
public class OrderIDUtil {
	/**
	 * 生成订单号
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public static String genOrderId(Date time, RechargeTypeCode type) {
		// 生成订单号
		String currenttime = DateTimeUtils.dateToStr(time, "yyyyMMddHHmmss");
		return "CZ" + currenttime + RandomUtil.getRandom();
	}
}
