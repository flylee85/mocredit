package com.mocredit.testcase.integral_search;


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
 * @Description     积分查询_银行参数为NULL
 * 
 * */

public class Integral_search_006_BankNumNull_Test {

	/**
	 * 积分查询参数列表
	 * 
	 * bank 银行代码  中信：citic，民生：cmbc  
	 * activityId  活动ID 
	 * cardNum     卡号
	 * shopId      商铺id
	 * 
	 * @throws Exception
	 */

	@Parameters({ "baseUrl", "paymentUrl" })
	@Test
	public void IntegralSearch_BankNumNull(String baseUrl)throws Exception {
		String bank_null = PropertyUtil.getProInfo("parmeter", "bank_null");
		String activityId = PropertyUtil.getProInfo("parmeter", "activityId");
		String cardNum = PropertyUtil.getProInfo("parmeter", "cardNum");
		String shopId = PropertyUtil.getProInfo("parmeter", "shopId");

//***************************************积分查询**********************************************//
////////定义积分查询缓冲区		
		// 定义一个字符串缓冲区
		StringBuffer Buffer = new StringBuffer();
		// 定义字符串引号
		Buffer.append("{");
		// 定义bank字段
		Buffer.append("\"bank\"").append(":").append("\"").append(bank_null).append("\"");
		// 定义device字段
		Buffer.append("\"device\"").append(":").append("\"").append(activityId).append("\"");
		// 定义orderId字段
		Buffer.append("\"orderId\"").append(":").append("\"").append(cardNum).append("\"");
		// 定义activityId字段
		Buffer.append("\"activityId\"").append(":").append("\"").append(shopId).append("\"");
		// 定义缓冲区结束
		Buffer.append("}");
		// 字符串转换成jsonStr
		String jsonStr = Buffer.toString();
		
///////进行积分查询		
		// 传入参数
		String resp = HttpRequestUtil.doPostJson(baseUrl + "confirmInfo", jsonStr);
		//转换为字符串
		Response response = JSON.parseObject(resp, Response.class);
		//返回异常时的结果判定
		Assert.assertEquals(response.getSuccess(), false);
		Assert.assertEquals(response.getErrorCode(), "505");
		Assert.assertEquals(response.getErrorMsg(), "参数错误");
	}
}
