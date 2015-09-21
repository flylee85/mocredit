package com.mocredit.integral;

import org.junit.Test;
import org.testng.Assert;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.util.HttpRequestUtil;
import com.mocredit.integral.vo.ActivityVo;
import com.mocredit.integral.vo.ConfirmInfoVo;

/**
 * Created by ytq on 2015/8/28.
 */
public class IntegralInterfaceCtrlTest {
	public final static String BASE_URL = "http://localhost:8080/";

	/**
	 * bank 银行代码 中信：citic，民生：cmbc device 终端号 activityId 活动ID orderId 订单号
	 * 当天、当前机具上不重复 shopId 销售商户号 shopName 销售商户名 storeId 门店ID storeName 门店名称
	 * cardNum 卡号 integral 消费积分
	 */
	@Test
	public void paymentTest() throws Exception {
		// 正常流程
		String jsonStr = "{\"bank\":\"citic\",\"device\":\"10010\",\"activityId\":\"10010\",\"orderId\":\"12342\","
				+ "\"shopId\":\"1\",\"shopName\":\"星巴克\",\"storeId\":\"1\",\"storeName\":\"环球中心\",\"cardNum\":\"62140038786912\",\"integral\":\"100\"}";
		Response resp = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "payment", jsonStr),
				Response.class);
		// 活动不存在流程
		String jsonStr1 = "{\"bank\":\"citic\",\"device\":\"10010\",\"activityId\":\"100110\",\"orderId\":\"12342\","
				+ "\"shopId\":\"1\",\"shopName\":\"星巴克\",\"storeId\":\"1\",\"storeName\":\"环球中心\",\"cardNum\":\"62140038786912\",\"integral\":\"100\"}";
		Response resp1 = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "payment", jsonStr1),
				Response.class);
		// 该活动不包含该商铺的门店
		String jsonStr2 = "{\"bank\":\"citic\",\"device\":\"10010\",\"activityId\":\"10010\",\"orderId\":\"12342\","
				+ "\"shopId\":\"2\",\"shopName\":\"星巴克\",\"storeId\":\"1\",\"storeName\":\"环球中心\",\"cardNum\":\"62140038786912\",\"integral\":\"100\"}";
		Response resp2 = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "payment", jsonStr2),
				Response.class);
		String a = "";
	}

	/**
	 * 积分消费撤销 device 终端号 orderId 原订单号
	 */
	@Test
	public void paymentRevoke() throws Exception {
		// 積分消費撤銷
		String jsonStr = "{\"device\":\"10010\",\"orderId\":\"12342\"}";
		Response resp = JSON
				.parseObject(HttpRequestUtil.doPostJson(BASE_URL
						+ "paymentRevoke", jsonStr), Response.class);
		System.out.println();
		// 消費失敗及數據庫不存在该订单信息
		String jsonStr1 = "{\"device\":\"-1\",\"orderId\":\"12342\"}";
		Response resp1 = JSON.parseObject(HttpRequestUtil.doPostJson(BASE_URL
				+ "paymentRevoke", jsonStr1), Response.class);
		System.out.println();
		// 积分订单不存在
		String jsonStr2 = "{\"device\":\"10010\",\"orderId\":\"12333\"}";
		Response resp2 = JSON.parseObject(HttpRequestUtil.doPostJson(BASE_URL
				+ "paymentRevoke", jsonStr2), Response.class);
		System.out.println();
	}

	/**
	 * 查询积分 bank 银行代码 中信：citic，民生：cmbc activityId 活动ID cardNum 卡号 shopId 商铺id
	 */
	@Test
	public void confirmInfoTest() throws Exception {
		// 不存在该活动导致空指针异常提示参数错误
		ConfirmInfoVo confirmInfoVo = new ConfirmInfoVo();
		confirmInfoVo.setActivityId(100);
		confirmInfoVo.setBank("citic");
		confirmInfoVo.setCardNum("62140038786912");
		confirmInfoVo.setShopId(1246);
		String jsonStr = JSON.toJSONString(confirmInfoVo);
		Response resp = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "confirmInfo", jsonStr),
				Response.class);
		confirmInfoVo.setActivityId(10010);
		confirmInfoVo.setShopId(1);
		String jsonStr1 = JSON.toJSONString(confirmInfoVo);
		Response resp1 = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "confirmInfo", jsonStr1),
				Response.class);
		System.out.println();
	}

	/**
	 * 积分冲正 http请求参数 { device 终端号 orderId 原订单号 }
	 */
	@Test
	public void testPaymentReserval() throws Exception {
		// 積分消費撤銷
		String jsonStr = "{\"device\":\"10010\",\"orderId\":\"12342\"}";
		Response resp = JSON.parseObject(HttpRequestUtil.doPostJson(BASE_URL
				+ "paymentReserval", jsonStr), Response.class);
		System.out.println();
	}

	/**
	 * 积分冲正撤销 http请求参数 { device 终端号 orderId 原订单号 }
	 */
	@Test
	public void paymentRevokeReserval() throws Exception {
		// 積分消費撤銷
		String jsonStr = "{\"device\":\"10010\",\"orderId\":\"12342\"}";
		Response resp = JSON.parseObject(HttpRequestUtil.doPostJson(BASE_URL
				+ "paymentRevokeReserval", jsonStr), Response.class);
		System.out.println();
	}

	/**
	 * http请求参数 { activityId 活动ID activityName 活动名 productCode 银行内部代码 operCode
	 * 操作代码， 1导入 2 更新 3 取消 4 启用 startTime 活动开始时间，年月日时分秒yyyy-MM-dd HH:mm:ss
	 * endTime 活动结束时间，年月日时分秒yyyy-MM-dd HH:mm:ss selectDate
	 * 指定选择日期（周几），如果是周一和周二，则是1,2,如果是周五周六周日，则是5,6,7 使用英文字符分割 integral 积分，允许小数
	 * maxType 最大类型，暂定01代表每日，02代表每周，03代表每月，空代表不限制 maxNumber 最大次数 status
	 * 01启用，02停止 storeList 门店列表 [ { storeId 门店Id shopId 商户Id } ] 其他活动信息待定
	 * <p>
	 * } * operCode为更新时，需要活动的完整信息（包括门店）
	 */
	@Test
	public void activityImport() throws Exception {

		String jsonStr = "{\"activityId\":\"10010\",\"activityName\":\"星巴克\",\"productCode\":\"citic\","
				+ "\"operCode\":\"1\",\"selectDate\":\"1,2,3,4,5\",\"integral\":\"1000\",\"maxType\":\"01\","
				+ "\"maxNumber\":\"1000\",\"status\":\"01\",\"startTime\":\"2015-09-01 10:11:11\",\"endTime\":\"2015-09-31 23:10:11\","
				+ "\"storeList\":[{\"shopId\":\"1\",\"storeId\":\"1\"},{\"shopId\":\"2\",\"storeId\":\"2\"},{\"shopId\":\"3\",\"storeId\":\"3\"}]}";
		ActivityVo activityVo = JSON.parseObject(jsonStr, ActivityVo.class);
		// 新增活動
		Response resp = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "activityImport",
						JSON.toJSONString(activityVo)), Response.class);
		Assert.assertTrue(resp.getSuccess());
		// 更新活動
		activityVo.setOperCode(2);
		activityVo.setActivityName("麦当劳");
		Response resp2 = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "activityImport",
						JSON.toJSONString(activityVo)), Response.class);
		Assert.assertTrue(resp2.getSuccess());
		// 取消活动
		ActivityVo activityVo1 = new ActivityVo();
		activityVo1.setActivityId(10010);
		activityVo1.setOperCode(3);
		Response resp3 = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "activityImport",
						JSON.toJSONString(activityVo1)), Response.class);
		Assert.assertTrue(resp3.getSuccess());
		// 啟用活动
		activityVo1.setOperCode(4);
		Response resp4 = JSON.parseObject(
				HttpRequestUtil.doPostJson(BASE_URL + "activityImport",
						JSON.toJSONString(activityVo1)), Response.class);
		Assert.assertTrue(resp4.getSuccess());

	}

}
