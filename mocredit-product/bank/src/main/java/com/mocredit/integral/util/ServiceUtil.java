package com.mocredit.integral.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocredit.integral.entity.Payment;

/**
 * 积分交易服务工具类
 * 
 * @author liaoying Created on 2015年8月13日
 *
 */
public class ServiceUtil {

	private static Logger log = LoggerFactory.getLogger(ServiceUtil.class);

	/**
	 * 构造登录WS参数
	 * 
	 * @param payment
	 * @return Document
	 */
	public static Document createLoginDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "login");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0800"); // 登录
		Element terminalID = root.addElement("terminalID");
		terminalID.addText(payment.getTerminalID());
		Element posID = root.addElement("posID");
		posID.addText(payment.getPosID());
		Element posTime = root.addElement("posTime");
		posTime.addText(payment.getPosTime());
		// Element transTime = root.addElement("transTime");
		// transTime.addText("");
		// Element transDate = root.addElement("transDate");
		// transDate.addText("");
		Element merchantType = root.addElement("merchantType");
		merchantType.addText(payment.getMerchantType());
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantID());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(payment.getMerchantName());
		Element password = root.addElement("password");
		password.addText(payment.getPassword());
		// Element retCode = root.addElement("retCode");
		// retCode.addText("");
		// Element commentRes = root.addElement("commentRes");
		// commentRes.addText("");
		// Element resParam = root.addElement("resParam");
		// resParam.addText("");
		// Element payURL = root.addElement("payURL");
		// payURL.addText("");
		// Element token = root.addElement("token");
		// token.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText(payment.getReserved());
		return document;

	}

	/**
	 * 构造交易WS参数
	 * 
	 * @param payment
	 * @return Document
	 */
	public static Document createPaymentDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPayment");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0200");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText(payment.getTerminalID());
		Element posID = root.addElement("posID");
		posID.addText(payment.getPosID());
		Element posTime = root.addElement("posTime");
		posTime.addText(payment.getPosTime());
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantID());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(payment.getMerchantName());
		Element batchNo = root.addElement("batchNo");// M必须6位 批次号
		batchNo.addText(payment.getBatchNo());
		Element serialNo = root.addElement("serialNo"); // M必须6位 序号(批次号+序号唯一)
		serialNo.addText(payment.getSerialNo());
		Element orderID = root.addElement("orderID");// M定单号 19位
		orderID.addText(payment.getOrderID());
		Element pan = root.addElement("pan");// M卡号 19位
		pan.addText(payment.getPan());
		Element bankOrderID = root.addElement("bankOrderID");
		bankOrderID.addText(payment.getBankOrderID());
		Element expiredDate = root.addElement("expiredDate");
		expiredDate.addText(payment.getExpiredDate());
		Element cvv2 = root.addElement("cvv2");
		cvv2.addText(payment.getCvv2().toString());
		Element chIdNum = root.addElement("chIdNum");// C姓名
		chIdNum.addText(payment.getChIdNum());
		Element chName = root.addElement("chName");// C身份证号
		chName.addText(payment.getChName());
		Element chMobile = root.addElement("chMobile");// C手机号
		chMobile.addText(payment.getChMobile());
		Element productType = root.addElement("productType"); // M--产品代码（即支付编码）
		productType.addText(payment.getProductType());
		Element productNum = root.addElement("productNum"); // M产品数量2位不足左补0
		productNum.addText(payment.getProductNum());
		Element payWay = root.addElement("payWay");
		payWay.addText(payment.getPayWay());
		Element transAmt = root.addElement("transAmt"); // M交易金额13位不足左补0
		transAmt.addText(payment.getTransAmt());
		Element currCode = root.addElement("currCode");
		currCode.addText(payment.getCurrCode());
		Element dividedNum = root.addElement("dividedNum"); // M分期支付的期数
		dividedNum.addText(payment.getDividedNum());
		Element inputType = root.addElement("inputType"); // M输入方式：031刷卡,042手工
		inputType.addText(payment.getInputType());
		Element posConditionCode = root.addElement("posConditionCode");// M服务点条件码
		posConditionCode.addText(payment.getPosConditionCode());
		Element systemRefCode = root.addElement("systemRefCode");
		systemRefCode.addText(payment.getSystemRefCode());
		Element timeStamp = root.addElement("timeStamp");
		timeStamp.addText(payment.getTimeStamp());
		Element signature = root.addElement("signature");
		signature.addText(payment.getSignature());
		Element secondTrack = root.addElement("secondTrack");
		secondTrack.addText(payment.getSecondTrack());
		Element thirdTrack = root.addElement("thirdTrack");
		thirdTrack.addText(payment.getThirdTrack());
		Element csc4 = root.addElement("csc4");
		csc4.addText(payment.getCsc4());
		Element dynamicPwd = root.addElement("dynamicPwd");
		dynamicPwd.addText(payment.getDynamicPwd());
		Element reserved = root.addElement("reserved");
		reserved.addText(payment.getReserved());
		return document;
	}

	/**
	 * 组装请求参数，生成登录Payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getLoginPayment(Map<String, Object> reqMap) throws Exception {
		if (null == reqMap.get("terminalID")) {
			throw new Exception("缺少参数:terminalID");
		} else if (null == reqMap.get("posTime")) {
			throw new Exception("缺少参数:posTime");
		} else if (null == reqMap.get("posID")) {
			throw new Exception("缺少参数:posID");
		} else if (null == reqMap.get("merchantID")) {
			throw new Exception("缺少参数:merchantID");
		} else if (null == reqMap.get("merchantName")) {
			throw new Exception("缺少参数:merchantName");
		} else if (null == reqMap.get("password")) {
			throw new Exception("缺少参数:password");
		}
		Payment payment = new Payment();
		payment.setInfoType(Variable.ZX_INFO_TYPE_LOGIN);
		payment.setTerminalID(Utils.fillZeroToLen(reqMap.get("terminalID").toString(),8));
		payment.setPosTime(reqMap.get("posTime").toString());
		payment.setPosID(Utils.fillZeroToLen(reqMap.get("posID").toString(),6));
		payment.setMerchantID(reqMap.get("merchantID").toString());
		payment.setPassword(reqMap.get("password").toString());
		payment.setMerchantName(reqMap.get("merchantName").toString());
		payment.setMerchantType(Variable.ZXMERCHANTTYPE);
		return payment;
	}

	/**
	 * 组装请求参数，生成支付payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getPayment(Map<String, Object> reqMap) throws Exception {
		if (null == reqMap.get("terminalID")) {
			throw new Exception("缺少参数:terminalID");
		} else if (null == reqMap.get("posTime")) {
			throw new Exception("缺少参数:posTime");
		} else if (null == reqMap.get("posID")) {
			throw new Exception("缺少参数:posID");
		} else if (null == reqMap.get("merchantID")) {
			throw new Exception("缺少参数:merchantID");
		} else if (null == reqMap.get("merchantName")) {
			throw new Exception("缺少参数:merchantName");
		} else if (null == reqMap.get("password")) {
			throw new Exception("缺少参数:password");
		} else if (null == reqMap.get("batchNo")) {
			throw new Exception("缺少参数:batchNo");
		} else if (null == reqMap.get("serialNo")) {
			throw new Exception("缺少参数:serialNo");
		} else if (null == reqMap.get("account")) {
			throw new Exception("缺少参数:account");
		} else if (null == reqMap.get("productType")) {
			throw new Exception("缺少参数:productType");
		} else if (null == reqMap.get("transAmt")) {
			throw new Exception("缺少参数:transAmt");
		}
		// 必须参数
		Payment payment = new Payment();
		payment.setInfoType(Variable.ZX_INFO_TYPE_PAYMENT);
		payment.setTerminalID(reqMap.get("terminalID").toString());
		payment.setPosTime(Utils.fillZeroToLen(reqMap.get("posTime").toString(),8));
		payment.setPosID(Utils.fillZeroToLen(reqMap.get("posID").toString(),6));
		payment.setMerchantID(reqMap.get("merchantID").toString());
		payment.setMerchantName(reqMap.get("merchantName").toString());
		payment.setPassword(reqMap.get("password").toString());
		payment.setBatchNo(Utils.fillZeroToLen(reqMap.get("batchNo").toString(),6));
		payment.setSerialNo(Utils.fillZeroToLen(reqMap.get("serialNo").toString(),6));
		payment.setPan(reqMap.get("account").toString());
		payment.setProductType(reqMap.get("productType").toString());
		payment.setTransAmt(Utils.fillZeroToLen(reqMap.get("transAmt").toString(),13));
		payment.setProductNum(null == reqMap.get("productNum")?"01": Utils.fillZeroToLen(reqMap.get("productNum").toString(),2)); // 产品数量
		payment.setPayWay(Variable.ZX_PAY_WAY_INTEGRAL);
		payment.setCurrCode(Variable.ZX_CURR_CODE);
		payment.setDividedNum("01"); // 分期数
		payment.setInputType(Variable.ZX_INPUT_TYPE_BRUSH);
		payment.setPosConditionCode(Variable.ZX_POS_CONDITIONCODE_EPOS);
		payment.setTimeStamp(DateTimeUtils.dateToStr(new Date(), "yyyyMMddHHmmssSSS"));
		payment.setOrderID(genOrderId());

		// PwdDataId BatchNo serialNo ExtField1 0000000000000 timestamp
		// 对定单号＋批次号＋序号＋卡号＋金额＋时间戳
		String signaturestr = sign(payment);
		payment.setSignature(signaturestr);

		// 可选参数
		if (null != reqMap.get("expiredDate")) {
			payment.setExpiredDate(reqMap.get("expiredDate").toString());
		}
		if (null != reqMap.get("cvv2")) {
			payment.setCvv2(reqMap.get("cvv2").toString());
		}
		if (null != reqMap.get("chIdNum")) {
			payment.setChIdNum(reqMap.get("chIdNum").toString());
		}
		if (null != reqMap.get("chName")) {
			payment.setChMobile(reqMap.get("chName").toString());
		}
		if (null != reqMap.get("chMobile")) {
			payment.setChMobile(reqMap.get("chMobile").toString());
		}
		if (null != reqMap.get("systemRefCode")) {
			payment.setSystemRefCode(Utils.fillZeroToLen(reqMap.get("systemRefCode").toString(),12));
		}
		if (null != reqMap.get("secondTrack")) {
			payment.setSecondTrack(reqMap.get("secondTrack").toString());
		}
		if (null != reqMap.get("thirdTrack")) {
			payment.setSecondTrack(reqMap.get("thirdTrack").toString());
		}
		if (null != reqMap.get("csc4")) {
			payment.setCsc4(reqMap.get("csc4").toString());
		}
		if (null != reqMap.get("dynamicPwd")) {
			payment.setDynamicPwd(Utils.fillZeroToLen(reqMap.get("dynamicPwd").toString(),8));
		}
		return payment;
	}

	/**
	 * 用客户端私钥对定单号＋批次号＋序号＋卡号＋金额＋时间戳，进行签名
	 * 
	 * @param payment
	 * @return
	 * @throws Exception
	 */
	private static String sign(Payment payment) throws Exception {
		String panstr = Utils.fillStrToLen(payment.getPan(), 19);
		String orderIdStr = Utils.fillStrToLen(payment.getOrderID(), 19);
		String sign = orderIdStr + payment.getBatchNo() + payment.getSerialNo() + panstr + payment.getTransAmt()
				+ payment.getTimeStamp();
		log.info("签名字符串：" + sign);
		PrivateKey priKey = CertUtils.getPrivateKey(Variable.ZXKEYSTORE, Variable.ZXKEYSTOREPWD,
				Variable.ZXKEYSTORETYPE);
		byte[] testByte = CertUtils.sign(priKey, sign.getBytes("UTF8"));
		String testBase64 = new String(Base64.encode(testByte), "UTF8");
		return testBase64;
	}

	/**
	 * 生成orderID
	 */
	private static String genOrderId() {
		String orderID = DateTimeUtils.getDate("yyDHH") + new Random().nextInt(100000000);
		return orderID;
	}

	/**
	 * 用服务器公钥对定单号＋批次号+序号＋卡号＋金额＋交易结果＋时间戳进行签名
	 * 
	 * @param payment
	 * @throws Exception
	 */
	public static boolean verify(Payment payment) throws Exception {
		PublicKey pubKey = CertUtils.getPublicKey(Variable.ZXSERVERPUBCERT);
		String panstr = Utils.fillStrToLen(payment.getPan(), 19);
		String orderIdStr = Utils.fillStrToLen(payment.getOrderID(), 19);
		String sign = orderIdStr + payment.getBatchNo() + payment.getSerialNo() + panstr + payment.getTransAmt()
				+ payment.getRetCode() + payment.getTimeStamp();
		log.info("验签字符串：" + sign);
		return CertUtils.verify(pubKey, sign.getBytes("UTF8"), Base64.decode(payment.getSignature().getBytes("UTF8")));
	}
}
