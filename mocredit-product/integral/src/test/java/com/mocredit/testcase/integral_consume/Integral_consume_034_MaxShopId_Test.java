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
 * @Description     积分消费_销售商户号参数为最大长度30
 * 
 * */

public class Integral_consume_034_MaxShopId_Test {

	/**
	 * bank 银行代码 中信：citic，民生：cmbc device 终端号 activityId 活动ID orderId 订单号
	 * 当天、当前机具上不重复 shopId 销售商户号 shopName 销售商户名 storeId 门店ID storeName 门店名称
	 * cardNum 卡号 integral 消费积分
	 * 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void IntegralConsume_MaxShopId(String baseUrl)throws Exception {
		String activityId = PropertyUtil.getProInfo("parmeter", "activityId");
		String activityName = PropertyUtil.getProInfo("parmeter", "activityName");
		String productCode = PropertyUtil.getProInfo("parmeter", "productCode");
		String operCode = PropertyUtil.getProInfo("parmeter", "operCode");
		String startTime = PropertyUtil.getProInfo("parmeter", "startTime");
		String endTime = PropertyUtil.getProInfo("parmeter", "endTime");
		String selectDate = PropertyUtil.getProInfo("parmeter", "selectDate");
		String integral = PropertyUtil.getProInfo("parmeter", "integral");
		String maxType_everyday = PropertyUtil.getProInfo("parmeter", "maxType_everyday");
		String maxNumber = PropertyUtil.getProInfo("parmeter", "maxNumber");
		String status_enable = PropertyUtil.getProInfo("parmeter", "status_enable");
		String storeId = PropertyUtil.getProInfo("parmeter", "storeId");
		String shopId_max = PropertyUtil.getProInfo("parmeter", "shopId_max");

		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义activityId字段
		Buffer.append("\"activityId\"").append(":").append("\"").append(activityId).append("\"");
		// 定义activityName字段
		Buffer.append("\"activityName\"").append(":").append("\"").append(activityName).append("\"");
		// 定义productCode字段
		Buffer.append("\"productCode\"").append(":").append("\"").append(productCode).append("\"");
		// 定义operCode字段
		Buffer.append("\"operCode\"").append(":").append("\"").append(operCode).append("\"");
		// 定义startTime字段
		Buffer.append("\"startTime\"").append(":").append("\"").append(startTime).append("\"");
		// 定义endTime字段
		Buffer.append("\"endTime\"").append(":").append("\"").append(endTime).append("\"");
		// 定义selectDate字段
		Buffer.append("\"selectDate\"").append(":").append("\"").append(selectDate).append("\"");
		// 定义storeNintegralame字段
		Buffer.append("\"integral\"").append(":").append("\"").append(integral).append("\"");
		// 定义maxType字段
		Buffer.append("\"maxType\"").append(":").append("\"").append(maxType_everyday).append("\"");
		// 定义maxNumber字段
		Buffer.append("\"maxNumber\"").append(":").append("\"").append(maxNumber).append("\"");
		// 定义status字段
		Buffer.append("\"status\"").append(":").append("\"").append(status_enable).append("\"");
		// 定义storeList开始
		Buffer.append("\"storeList\"").append(":").append("[").append("{");
		// 定义storeId字段
		Buffer.append("\"storeId\"").append(":").append("\"").append(storeId).append("\"");
		// 定义shopId字段
		Buffer.append("\"shopId\"").append(":").append("\"").append(shopId_max).append("\"");
		// 定义storeList结束
		Buffer.append("}").append("]");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr = Buffer.toString();
		// 传参数
		String resp = HttpRequestUtil.doPostJson(baseUrl + "activityImport", jsonStr);
		//转换为字符串
		Response response = JSON.parseObject(resp, Response.class);
		//返回结果判定
		Assert.assertEquals(response.getSuccess(), true);

	}
}
