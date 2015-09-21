package com.mocredit.testcase.integral_consume;

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
 * @Description     积分消费_订单号参数类型错误
 * 
 * */

public class Integral_consume_024_OrderIdTypeError_Test {

	/**
	 * bank 银行代码 中信：citic，民生：cmbc device 终端号 activityId 活动ID orderId 订单号
	 * 当天、当前机具上不重复 shopId 销售商户号 shopName 销售商户名 storeId 门店ID storeName 门店名称
	 * cardNum 卡号 integral 消费积分
	 * 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void IntegralConsume_OrderIdTypeError(String baseUrl)throws Exception {
		String bank = PropertyUtil.getProInfo("parmeter", "bank_citic");
		String device = PropertyUtil.getProInfo("parmeter", "device");
		String orderId = "!@#$%^&*";
		String activityId = PropertyUtil.getProInfo("parmeter", "activityId");
		String shopId = PropertyUtil.getProInfo("parmeter", "shopId");
		String shopName = PropertyUtil.getProInfo("parmeter", "shopName");
		String storeId = PropertyUtil.getProInfo("parmeter", "storeId");
		String storeName = PropertyUtil.getProInfo("parmeter", "storeName");
		String cardNum = PropertyUtil.getProInfo("parmeter", "cardNum");
		String integral = PropertyUtil.getProInfo("parmeter", "integral");

		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义bank字段
		Buffer.append("\"bank\"").append(":").append("\"").append(bank).append("\"");
		// 定义device字段
		Buffer.append("\"bank\"").append(":").append("\"").append(device).append("\"");
		// 定义orderId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(orderId).append("\"");
		// 定义activityId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(activityId).append("\"");
		// 定义shopId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(shopId).append("\"");
		// 定义shopName字段
		Buffer.append("\"bank\"").append(":").append("\"").append(shopName).append("\"");
		// 定义storeId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(storeId).append("\"");
		// 定义storeName字段
		Buffer.append("\"bank\"").append(":").append("\"").append(storeName).append("\"");
		// 定义cardNum字段
		Buffer.append("\"bank\"").append(":").append("\"").append(cardNum).append("\"");
		// 定义integral字段
		Buffer.append("\"bank\"").append(":").append("\"").append(integral).append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr = Buffer.toString();
		// 传参数
		String resp = HttpRequestUtil.doPostJson(baseUrl + "Buffer", jsonStr);
		//转换为字符串
		Response response = JSON.parseObject(resp, Response.class);
		//返回异常时的结果判定
		Assert.assertEquals(response.getSuccess(), false);
		Assert.assertEquals(response.getErrorCode(), "505");
		Assert.assertEquals(response.getErrorMsg(), "参数错误");
	}
}
