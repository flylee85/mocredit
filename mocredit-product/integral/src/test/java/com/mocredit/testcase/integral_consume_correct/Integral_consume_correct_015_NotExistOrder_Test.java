package com.mocredit.testcase.integral_consume_correct;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.PropertyUtil;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.util.HttpRequestUtil;


/**
 * @author sc-candao-hgy
 * 
 * @Description     积分消费冲正_冲正不存在的订单
 * 
 * */

public class Integral_consume_correct_015_NotExistOrder_Test {
	/**
	 *积分消费参数列表
	 * 
	 * bank 银行代码 中信：citic，民生：cmbc device 终端号 activityId 活动ID orderId 订单号
	 * 当天、当前机具上不重复 shopId 销售商户号 shopName 销售商户名 storeId 门店ID storeName 门店名称
	 * cardNum 卡号 integral 消费积分
	 * 
	 * 
	 * 
	 * 积分消费冲正参数列表 
	 * 
	 * device    终端号
	 * orderId  原订单号
	 * 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void IntegralConsumeCorrect_NotExistOrder(String baseUrl)throws Exception {
		Random r = new Random();
		int i = 100 + r.nextInt(9000);
		String device = PropertyUtil.getProInfo("parmeter", "device");
		String orderId = "hegytest" + i;

		
////////定义积分消费冲正缓冲区
		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义cardNum字段
		Buffer.append("\"device\"").append(":").append("\"").append(device).append("\"");
		// 定义integral字段
		Buffer.append("\"orderId\"").append(":").append("\"").append(orderId).append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr2 = Buffer.toString();
		
///////进行积分消费冲正		
			// 传入消费冲正参数
			String resp = HttpRequestUtil.doPostJson(baseUrl + "paymentReserval", jsonStr2);
			//转换为字符串
			Response response = JSON.parseObject(resp, Response.class);
			//返回异常时的结果判定
			Assert.assertEquals(response.getSuccess(), false);
			Assert.assertEquals(response.getErrorCode(), "006");
			Assert.assertEquals(response.getErrorMsg(), "交易记录不存在");

	}
}
