package com.mocredit.testcase.integral_consume_revoke;


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
 * @Description     积分消费撤销_订单号不存在
 * 
 * */

public class Integral_consume_revoke_014_OrderIdNotExist_Test {

	/**
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
	public void IntegralConsumeRevok_MaxOrderId(String baseUrl)throws Exception {
		String device = PropertyUtil.getProInfo("parmeter", "device");
		String orderId_notexist = "999999999";		

		
////////定义积分消费撤销缓冲区
		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义cardNum字段
		Buffer.append("\"device\"").append(":").append("\"").append(device).append("\"");
		// 定义integral字段
		Buffer.append("\"orderId\"").append(":").append("\"").append(orderId_notexist).append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr2 = Buffer.toString();
		
///////进行积分消费撤销
		// 传入消费撤销参数
		String resp2 = HttpRequestUtil.doPostJson(baseUrl + "paymentRevoke", jsonStr2);
		//转换为字符串
		Response response2 = JSON.parseObject(resp2, Response.class);
		//返回异常时的结果判定
		Assert.assertEquals(response2.getSuccess(), false);
		Assert.assertEquals(response2.getErrorCode(), "006");
		Assert.assertEquals(response2.getErrorMsg(), "交易记录不存在");

	}
}
