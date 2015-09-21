package com.mocredit.testcase.integral_consume_revoke;

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
 * @Description     积分消费撤销_消费积分参数为最大长度30
 * 
 * */

public class Integral_consume_revoke_007_MaxDeviceNum_Test {

	/**
	 *积分消费参数列表
	 * 
	 * bank 银行代码 中信：citic，民生：cmbc device 终端号 activityId 活动ID orderId 订单号
	 * 当天、当前机具上不重复 shopId 销售商户号 shopName 销售商户名 storeId 门店ID storeName 门店名称
	 * cardNum 卡号 integral 消费积分
	 * 
	 * 积分消费撤销参数列表 
	 * 
     * device    终端号
     * orderId  原订单号
     * 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void IntegralConsumeRevok_MaxDeviceNum(String baseUrl)throws Exception {
		Random r = new Random();
		int i = 1 + r.nextInt(9000);
		String bank = PropertyUtil.getProInfo("parmeter", "bank_citic");
		String device = PropertyUtil.getProInfo("parmeter", "device");
		String device_max = PropertyUtil.getProInfo("parmeter", "device_max");
		String orderId = "hegytest" + i;
		String activityId = PropertyUtil.getProInfo("parmeter", "activityId");
		String shopId = PropertyUtil.getProInfo("parmeter", "shopId");
		String shopName = PropertyUtil.getProInfo("parmeter", "shopName");
		String storeId = PropertyUtil.getProInfo("parmeter", "storeId");
		String storeName = PropertyUtil.getProInfo("parmeter", "storeName");
		String cardNum = PropertyUtil.getProInfo("parmeter", "cardNum");
		String integral = PropertyUtil.getProInfo("parmeter", "integral_type");

////////定义积分消费缓冲区		
		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义bank字段
		Buffer.append("\"bank\"").append(":").append("\"").append(bank)
				.append("\"");
		// 定义device字段
		Buffer.append("\"bank\"").append(":").append("\"").append(device)
				.append("\"");
		// 定义orderId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(orderId)
				.append("\"");
		// 定义activityId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(activityId)
				.append("\"");
		// 定义shopId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(shopId)
				.append("\"");
		// 定义shopName字段
		Buffer.append("\"bank\"").append(":").append("\"").append(shopName)
				.append("\"");
		// 定义storeId字段
		Buffer.append("\"bank\"").append(":").append("\"").append(storeId)
				.append("\"");
		// 定义storeName字段
		Buffer.append("\"bank\"").append(":").append("\"").append(storeName)
				.append("\"");
		// 定义cardNum字段
		Buffer.append("\"bank\"").append(":").append("\"").append(cardNum)
				.append("\"");
		// 定义integral字段
		Buffer.append("\"bank\"").append(":").append("\"").append(integral)
				.append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr = Buffer.toString();
		
///////进行积分消费		
		// 传入消费参数
		String resp = HttpRequestUtil.doPostJson(baseUrl + "Buffer", jsonStr);
		//转换为字符串
		Response response = JSON.parseObject(resp, Response.class);
		//返回正常时的结果判定
		Assert.assertEquals(response.getSuccess(),true);

		
////////定义积分消费撤销缓冲区
		// 定义一个字符串缓冲区
		StringBuffer Buffer2 = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义cardNum字段
		Buffer.append("\"bank\"").append(":").append("\"").append(device_max).append("\"");
		// 定义integral字段
		Buffer.append("\"bank\"").append(":").append("\"").append(orderId).append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr2 = Buffer2.toString();
		
///////进行积分消费		
		// 传入消费撤销参数
		String resp2 = HttpRequestUtil.doPostJson(baseUrl + "Buffer2", jsonStr2);
		//转换为字符串
		Response response2 = JSON.parseObject(resp2, Response.class);
		//返回时的结果判定
		Assert.assertEquals(response2.getSuccess(), true);

	}
}
