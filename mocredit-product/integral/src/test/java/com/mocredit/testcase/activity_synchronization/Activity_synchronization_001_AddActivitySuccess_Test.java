package com.mocredit.testcase.activity_synchronization;

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
 * @Description     积分消费_银行参数类型错误
 * 
 * */

public class Activity_synchronization_001_AddActivitySuccess_Test {
	/**
	 * 活动同步参数列表
	 * 
     *  activityId     活动ID
     *  activityName   活动名
     *  productCode    银行内部代码
     *  operCode       操作代码， 1导入 2 更新 3 取消 4 启用 
     *  startTime      活动开始时间，年月日时分秒yyyy-MM-dd HH:mm:ss
     *  endTime        活动结束时间，年月日时分秒yyyy-MM-dd HH:mm:ss
     *  selectDate     指定选择日期（周几），如果是周一和周二，则是1,2,如果是周五周六周日，则是5,6,7  使用英文字符分割
     *  integral       积分，允许小数
     *  maxType        最大类型，暂定01代表每日，02代表每周，03代表每月，空代表不限制
     *  maxNumber      最大次数
     *  status         01启用，02停止
     *  storeList      门店列表        
     *  [ 
     *   {
     *   storeId       门店Id
     *   shopId        商户Id 
     *   } 
     *  ] 
     *                 其他活动信息待定
     *  
	 * 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void ActivitySynchronization_AddActivitySuccess(String baseUrl)throws Exception {
		Random r = new Random();
		int i = 1 + r.nextInt(9000);
		String bank = PropertyUtil.getProInfo("parmeter", "bank_type");
		String device = PropertyUtil.getProInfo("parmeter", "device");
		String orderId = "hegytest" + i;
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
		Buffer.append("\"bank\"").append(":").append("\"").append(bank)
				.append("\"");
		// 定义device字段
		Buffer.append("\"device\"").append(":").append("\"").append(device)
				.append("\"");
		// 定义orderId字段
		Buffer.append("\"orderId\"").append(":").append("\"").append(orderId)
				.append("\"");
		// 定义activityId字段
		Buffer.append("\"activityId\"").append(":").append("\"").append(activityId)
				.append("\"");
		// 定义shopId字段
		Buffer.append("\"shopId\"").append(":").append("\"").append(shopId)
				.append("\"");
		// 定义shopName字段
		Buffer.append("\"shopName\"").append(":").append("\"").append(shopName)
				.append("\"");
		// 定义storeId字段
		Buffer.append("\"storeId\"").append(":").append("\"").append(storeId)
				.append("\"");
		// 定义storeName字段
		Buffer.append("\"storeName\"").append(":").append("\"").append(storeName)
				.append("\"");
		// 定义cardNum字段
		Buffer.append("\"cardNum\"").append(":").append("\"").append(cardNum)
				.append("\"");
		// 定义integral字段
		Buffer.append("\"integral\"").append(":").append("\"").append(integral)
				.append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr = Buffer.toString();
		// 传参数
		String resp = HttpRequestUtil.doPostJson(baseUrl + "payment", jsonStr);
		//转换为字符串
		Response response = JSON.parseObject(resp, Response.class);
		//返回异常时的结果判定
		Assert.assertEquals(response.getSuccess(), false);
		Assert.assertEquals(response.getErrorCode(), "505");
		Assert.assertEquals(response.getErrorMsg(), "参数错误");
	}
}
