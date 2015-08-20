package com.mocredit.integral.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mocredit.integral.entity.Payment;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.IntegralService;
import com.mocredit.integral.service.impl.IntegralServiceLocator;
import com.mocredit.integral.util.DateTimeUtils;
import com.mocredit.integral.util.Variable;

@RestController
@RequestMapping("/")
public class IntegralController {
	@Autowired
	private IntegralServiceLocator locator;

	/**
	 * 积分扣减
	 * 
	 * @param reqMap
	 *            param
	 *            {
	 *             bank   M //银行代码
	 *            	terminalId:"",  M //终端号
	 * 				activityId:"" ,  M//活动ID
	 * 				activityName:"" ,  M//活动名称
	 * 				posId:''" , M//POS流水号
	 * 				posTime:"", M     //POS交易时间
	 * 				merchantId:""， M//销售商户号
	 * 				merchantName:"",M//销售商户名
	 * 				out_merchantId:""， M//商户号（银行商户名）
	 * 				out_merchantName:"",M//商户名（银行商户名）
	 * 				out_merchantPassword:"",M//销售商户密码
	 * 				batchNo:"", M//6位批次号
	 * 				serialNo:"",M //6位序号，批号+序号唯一
	 * 				account:""  M//卡号
	 *				productType:"" O //产品代码????????
	 * 				productNum:""  O//产品数量 默认1
	 * 				expiredDate O卡有效期
	 *              cvv2 cvv2 O
	 *              chIdNum C 持卡人证件号码
	 *              chName  C 持卡人姓名
	 *              chMobile C持卡人手机号码
	 *              transAmt M交易金额
	 *              secondTrack  C第二磁道
	 *              thirdTrack  C第三磁道
	 *              csc4 C卡片后四位
	 *              dynamicPwd C动态密码
	 *            }
	 * @return  {
	 * 						retCode   返回码
	 * 						errMsg  错误信息
	 * 						orderId  积分交易订单号（用于撤销/冲正）
	 * 				}
	 */
	@RequestMapping("/payment")
	@ResponseBody
	public String payment(@RequestParam Map<String, Object> reqMap, String bank) {
		reqMap.put("terminalID", "1");
		reqMap.put("posID", "1");
		reqMap.put("posTime", DateTimeUtils.getDate("yyyyMMddHHmmss"));
		reqMap.put("merchantID", Variable.TEST_ZXMERCHANTID);
		reqMap.put("merchantName", Variable.TEST_ZXMERCHANTNAME);
		reqMap.put("password", Variable.TEST_ZXMERCHANTPASSWORD);
		reqMap.put("batchNo", "1");
		reqMap.put("serialNo",100000+new Random().nextInt(899999));
		reqMap.put("account", "5182128000042869");
		reqMap.put("productType", "00000000");
		reqMap.put("transAmt", "10");
		IntegralService service = locator.getService(bank);
		System.out.println("============map");
		return "response data";
	}

	/**
	 * 积分消费撤销（冲正）
	 * 
	 * @param reqMap
	 * param{
	 *      posId  //POS流水号
	 *      posTime POS交易时间
	 *      orderId   原订单号
	 * }
	 * @return   {
	 * 						retCode   返回码
	 * 						errMsg  错误信息
	 * }
	 */
	@RequestMapping("/paymentReversal")
	public ModelAndView paymentReversal(@RequestParam Map<String, Object> reqMap, String bank) {
		IntegralService service = locator.getService(bank);
		service.paymentReversal(reqMap);
		return new ModelAndView("login");
	}
	
	/**
	 * 积分查询
	 * 
	 * @param reqMap
	 * param{
	 *      posId
	 *      posTime
	 *      out_merchantId:""， M//商户号（银行商户名）
	 * 		 out_merchantName:"",M//商户名（银行商户名）
	 * 		 out_merchantPassword:"",M//销售商户密码
	 * }
	 * @return   {
	 * 						retCode   返回码
	 * 						errMsg  错误信息
	 * }
	 */
	@RequestMapping("/confirmInfo")
	public ModelAndView confirmInfo(@RequestParam Map<String, Object> reqMap, String bank) {
		IntegralService service = locator.getService(bank);
		service.paymentReversal(reqMap);
		return new ModelAndView("login");
	}
}
