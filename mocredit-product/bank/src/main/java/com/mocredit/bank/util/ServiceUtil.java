package com.mocredit.bank.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocredit.bank.constant.ZXConst;
import com.mocredit.bank.entity.MessageObject;
import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.RequestData;
import com.mocredit.bank.entity.TiPaymentReport;
import com.mocredit.bank.entity.TiReportDataZx;
import com.mocredit.bank.entity.TiShopMerchant;

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
	public static String createLoginDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "login");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText(payment.getInfoType()); // 登录
		Element terminalID = root.addElement("terminalID");
		terminalID.addText(payment.getTerminalID());
		Element posID = root.addElement("posID");
		posID.addText(payment.getPosID());
		Element posTime = root.addElement("posTime");
		posTime.addText(payment.getPosTime());
		Element merchantType = root.addElement("merchantType");
		merchantType.addText(payment.getMerchantType());
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantID());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(payment.getMerchantName());
		Element password = root.addElement("password");
		password.addText(payment.getPassword());
		Element reserved = root.addElement("reserved");
		reserved.addText(payment.getReserved());
		return document.asXML();
	}

	/**
	 * 构造心跳WS参数
	 * 
	 * @param payment
	 * @return Document
	 */
	public static String createMaintainSessionDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "maintainSession");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText(payment.getInfoType()); // 登录
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantID());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(payment.getMerchantName());
		Element token = root.addElement("token");
		token.addText(payment.getToken());
		return document.asXML();
	}

	/**
	 * 构造交易WS参数
	 * 
	 * @param payment
	 * @return Document
	 */
	public static String createPaymentDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPayment");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText(payment.getInfoType());
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
		return document.asXML();
	}

	/**
	 * 构造交易撤销WS参数
	 * 
	 * @param payment
	 * @return Document
	 */
	public static String createPaymentRevokeDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPaymentReversal");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText(payment.getInfoType());
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
		Element productType = root.addElement("productType"); // M--产品代码（即支付编码）
		productType.addText(payment.getProductType());
		Element productNum = root.addElement("productNum"); // M产品数量2位不足左补0
		productNum.addText(payment.getProductNum());
		Element dividedNum = root.addElement("dividedNum"); // M分期支付的期数
		dividedNum.addText(payment.getDividedNum());
		Element transAmt = root.addElement("transAmt"); // M交易金额13位不足左补0
		transAmt.addText(payment.getTransAmt());
		Element currCode = root.addElement("currCode");
		currCode.addText(payment.getCurrCode());
		Element authorizeCode = root.addElement("authorizeCode");
		authorizeCode.addText(payment.getAuthorizeCode());
		Element authDate = root.addElement("authDate");
		authDate.addText(payment.getAuthDate());
		Element authTime = root.addElement("authTime");
		authTime.addText(payment.getAuthTime());
		return document.asXML();
	}

	/**
	 * 构造交易撤销WS参数
	 * 
	 * @param payment
	 * @return Document
	 */
	public static String createConfirmInfoDocument(Payment payment) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "confirmInfo");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText(payment.getInfoType());
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
		Element confirmType = root.addElement("confirmType");
		confirmType.addText(payment.getConfirmType());
		Element batchNo = root.addElement("batchNo");// M必须6位 批次号
		batchNo.addText(payment.getBatchNo());
		Element serialNo = root.addElement("serialNo"); // M必须6位 序号(批次号+序号唯一)
		serialNo.addText(payment.getSerialNo());
		Element pan = root.addElement("pan");// M卡号 19位
		pan.addText(payment.getPan());
		return document.asXML();
	}

	/**
	 * 组装请求参数，生成登录Payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getLoginPayment(RequestData request) throws Exception {
		if (null == request.getMerchantId()) {
			throw new Exception("缺少参数:merchantID");
		} else if (null == request.getMerchantName()) {
			throw new Exception("缺少参数:merchantName");
		} else if (null == request.getMerchantPassword()) {
			throw new Exception("缺少参数:password");
		}
		Payment payment = new Payment();
		payment.setInfoType(Variable.ZX_INFO_TYPE_LOGIN);
		payment.setTerminalID(request.getTerminalId());
		payment.setPosID("000001");
		payment.setPosTime(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		payment.setMerchantType(Variable.ZXMERCHANTTYPE);
		payment.setMerchantID(request.getMerchantId());
		payment.setMerchantName(request.getMerchantName());
		payment.setPassword(request.getMerchantPassword());

		payment.setRequestId(request.getRequestId());
		return payment;
	}

	/**
	 * 组装请求参数，生成支付payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getPayment(RequestData request) throws Exception {
		// 必须参数
		Payment payment = new Payment();
		payment.setInfoType(Variable.ZX_INFO_TYPE_PAYMENT);
		payment.setTerminalID(request.getTerminalId());
		payment.setPosID("000000");
		payment.setPosTime(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		payment.setMerchantID(request.getMerchantId());
		payment.setMerchantName(request.getMerchantName());
		String batchNo = genBatchAndSerialNo();
		payment.setBatchNo(batchNo.substring(0, 6));
		payment.setSerialNo(batchNo.substring(6));
		payment.setOrderID(request.getOrderId());
		payment.setPan(request.getCardNum());
		payment.setProductType(request.getProductType());
		payment.setProductNum("01");
		payment.setPayWay(Variable.ZX_PAY_WAY_INTEGRAL);
		payment.setTransAmt(Utils.fillZeroToLen(request.getTransAmt(), 13));
		payment.setCurrCode(Variable.ZX_CURR_CODE);
		payment.setDividedNum("01");
		payment.setInputType(Variable.ZX_INPUT_TYPE_BRUSH);
		payment.setPosConditionCode(Variable.ZX_POS_CONDITIONCODE_EPOS);
		payment.setTimeStamp(DateTimeUtils.getDate("yyyyMMddHHmmssSSS"));
		payment.setSignature(sign(payment));

		payment.setRequestId(request.getRequestId());
		return payment;
	}

	/**
	 * 组装请求参数，生成查询payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getConfirmPayment(RequestData request) throws Exception {
		// 必须参数
		Payment payment = new Payment();
		payment.setInfoType(ZXConst.CONFIRMINFO_INFOTYPE_REQUEST);
		payment.setTerminalID(request.getTerminalId());
		payment.setPosID("000000");
		payment.setPosTime(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		payment.setMerchantID(request.getMerchantId());
		payment.setMerchantName(request.getMerchantName());
		payment.setConfirmType(Variable.ZX_CONFIRM_TYPE_POINT);
		String batchNo = genBatchAndSerialNo();
		payment.setBatchNo(batchNo.substring(0, 6));
		payment.setSerialNo(batchNo.substring(6));
		payment.setPan(request.getCardNum());

		payment.setRequestId(request.getRequestId());
		return payment;
	}

	/**
	 * 组装请求参数，生成 payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getCheckAccount(RequestData request) throws Exception {
		// 必须参数
		Payment payment = new Payment();
		payment.setInfoType(ZXConst.CONFIRMINFO_INFOTYPE_REQUEST);
		payment.setTerminalID(request.getTerminalId());
		payment.setPosID("000000");
		payment.setPosTime(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		payment.setMerchantID(request.getMerchantId());
		payment.setMerchantName(request.getMerchantName());
		payment.setConfirmType(Variable.ZX_CONFIRM_TYPE_POINT);
		String batchNo = genBatchAndSerialNo();
		payment.setBatchNo(batchNo.substring(0, 6));
		payment.setSerialNo(batchNo.substring(6));
		payment.setPan(request.getCardNum());

		payment.setRequestId(request.getRequestId());
		return payment;
	}

	/**
	 * 生成批次号+序列号
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String genBatchAndSerialNo() {
		String batchNo = String.valueOf(System.currentTimeMillis() / 1000)
				+ Utils.fillZeroToLen(new Random().nextInt(20), 2);
		return batchNo;
	}

	/**
	 * 组装请求参数，生成支付撤销payment对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public static Payment getPaymentRevoke(TiPaymentReport report, TiReportDataZx reportData) {
		// 必须参数
		Payment payment = new Payment();
		payment.setInfoType(Variable.ZX_INFO_TYPE_REVERSAL);
		payment.setTerminalID(report.getTerminalId());
		payment.setPosID("000000");
		payment.setPosTime(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		payment.setMerchantID(reportData.getMerchantId());
		payment.setMerchantName(reportData.getMerchantName());
		payment.setBatchNo(reportData.getBatchNo());
		payment.setSerialNo(reportData.getSerialNo());
		payment.setOrderID(report.getOrderId());
		payment.setPan(report.getCardNum());
		payment.setProductType(report.getProductType());
		payment.setProductNum("01");
		payment.setPayWay(Variable.ZX_PAY_WAY_INTEGRAL);
		payment.setTransAmt(Utils.fillZeroToLen(report.getAmount(), 13));
		payment.setCurrCode(Variable.ZX_CURR_CODE);
		payment.setDividedNum("01");

		payment.setAuthorizeCode(reportData.getAuthorizecode());
		payment.setAuthDate(reportData.getAuthDate());
		payment.setAuthTime(reportData.getAuthTime());

		payment.setRequestId(report.getRequestId());
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

	public static void main(String[] args) {
		System.out.println(DateTimeUtils.getDate("yyMMddHHmmssSSS"));
	}
/**
 * 生成对账XML
 * @param records
 * @return
 */
	public static String createCheckAccountDocument(List<Payment> records) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "checkAccount");
		root.addAttribute("type", "request");
		Payment payment = records.get(0);
		Element infoType = root.addElement("infoType");
		infoType.addText(ZXConst.CHECKACCOUNT_INFOTYPE_REQUEST); // 登录
		Element terminalID = root.addElement("terminalID");
		terminalID.addText(payment.getTerminalID());
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantID());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(payment.getMerchantName());
		Element terminalFlag = root.addElement("terminalFlag");
		terminalFlag.addText("Y");
		Element dataSet = root.addElement("dataSet");
		dataSet.addAttribute("count", records.size() + "");
		for (Payment record : records) {
			Element recordElement = dataSet.addElement("record");
			String recordstr = record.getBatchNo() + record.getSerialNo() + record.getAuthorizeCode()
					+ record.getAuthDate() + record.getAuthTime()+ Utils.fillZeroToLen(record.getTransAmt(), 13)
					+ String.format("%1$-103s", "");
			recordElement.addText(recordstr);
		}
		return document.asXML();
	}
	
	/**
	 * 民生银行 消费MessageObject
	 * @param requestData
	 * @return
	 */
	public static MessageObject getPayMessage(RequestData requestData,TiShopMerchant merchant){
		MessageObject message = new MessageObject();
    	message.setMesstype("0200");
    	message.setField02_Primary_Account_Number(requestData.getCardNum());//账号
    	message.setField03_Processing_Code("000000");//交易处理码
    	message.setField04_Amount_Of_Transactions(Utils.fillZeroToLen(requestData.getTransAmt(),12));// 交易金额
    	message.setField11_System_Trace_Audit_Number(String.valueOf(Integer.valueOf(merchant.getPosNo())+1)); //POS终端交易流水 +1
//		request.setField14_Date_Of_Expired(subByLenFromKey(secondTrackStr, "=", 4));
		message.setField22_Point_Of_Service_Entry_Mode("010");//服务点输入方式码
		message.setField25_Point_Of_Service_Condition_Mode("04");//服务点条件码
    	message.setField37_Retrieval_Reference_Number(getPosno());//POS中心系统流水号
    	message.setField41_Card_Acceptor_Terminal_ID(merchant.getTerminalId());//终端号
    	message.setField42_Card_Acceptor_ID(merchant.getMerchantId()); //商户ID
    	message.setField49_Currency_Code_Of_Transaction("156");//货币代码
    	message.setField60_Reserved_Private(22+merchant.getBatchNo());//交易类型+批次号
    	message.setField62_Reserved_Private("ACTN"+requestData.getProductType());//15位活动代码
		message.setField64_MAC("3131313131313131");
		return message;
	}
	/**
	 * 民生银行 消费冲正MessageObject
	 * @param requestData
	 * @return
	 */
	public static MessageObject getPayReservalMessage(TiPaymentReport report,TiReportDataZx reportData){
		MessageObject message = new MessageObject();
    	message.setMesstype("0400");
    	message.setField02_Primary_Account_Number(report.getCardNum());//账号
    	message.setField03_Processing_Code("000000");//交易处理码
    	message.setField04_Amount_Of_Transactions(Utils.fillZeroToLen(report.getAmount(),12));// 交易金额
    	message.setField11_System_Trace_Audit_Number(report.getPosId()); //POS终端交易流水 
//		request.setField14_Date_Of_Expired(subByLenFromKey(secondTrackStr, "=", 4));
		message.setField22_Point_Of_Service_Entry_Mode("010");//服务点输入方式码
		message.setField25_Point_Of_Service_Condition_Mode("04");//服务点条件码
    	message.setField37_Retrieval_Reference_Number(getPosno());//POS中心系统流水号
    	/*1．POS终端在时限内未能收到POS中心的应答消息而引发，冲正原因码填“98”。
			2．POS终端在时限内收到POS中心的批准应答消息，但由于POS机故障无法完成交易而引发，冲正原因码填“96”。
			3．POS终端对收到POS中心的应答消息，验证MAC出错，冲正原因码填“A0”。
			4．其他情况，冲正原因码填“06”
			*/
    	message.setField39_Response_Code("96");//冲正原因 
    	message.setField41_Card_Acceptor_Terminal_ID(report.getTerminalId());//终端号
    	message.setField42_Card_Acceptor_ID(reportData.getMerchantId()); //商户ID
    	message.setField49_Currency_Code_Of_Transaction("156");//货币代码
    	message.setField60_Reserved_Private(22+reportData.getBatchNo());//交易类型+批次号
    	message.setField62_Reserved_Private("ACTN"+report.getProductType());//15位活动代码
		message.setField64_MAC("3131313131313131");
		return message;
	}
	/**
	 * 民生银行 消费撤销MessageObject
	 * @param requestData
	 * @return
	 */
	public static MessageObject getPayRevokeMessage(TiPaymentReport report,TiReportDataZx reportData){
		MessageObject message = new MessageObject();
    	message.setMesstype("0200");
    	message.setField02_Primary_Account_Number(report.getCardNum());//账号
    	message.setField03_Processing_Code("200000");//交易处理码
    	message.setField04_Amount_Of_Transactions(Utils.fillZeroToLen(report.getAmount(),12));// 交易金额
    	message.setField11_System_Trace_Audit_Number(report.getPosId()); //POS终端交易流水 
//		request.setField14_Date_Of_Expired(subByLenFromKey(secondTrackStr, "=", 4));
		message.setField22_Point_Of_Service_Entry_Mode("010");//服务点输入方式码
		message.setField25_Point_Of_Service_Condition_Mode("04");//服务点条件码
    	message.setField37_Retrieval_Reference_Number(getPosno());//POS中心系统流水号
    	/*1．POS终端在时限内未能收到POS中心的应答消息而引发，冲正原因码填“98”。
			2．POS终端在时限内收到POS中心的批准应答消息，但由于POS机故障无法完成交易而引发，冲正原因码填“96”。
			3．POS终端对收到POS中心的应答消息，验证MAC出错，冲正原因码填“A0”。
			4．其他情况，冲正原因码填“06”
			*/
    	message.setField39_Response_Code("96");//冲正原因 
    	message.setField41_Card_Acceptor_Terminal_ID(report.getTerminalId());//终端号
    	message.setField42_Card_Acceptor_ID(reportData.getMerchantId()); //商户ID
    	message.setField49_Currency_Code_Of_Transaction("156");//货币代码
    	message.setField60_Reserved_Private(22+reportData.getBatchNo());//交易类型+批次号
    	message.setField62_Reserved_Private("ACTN"+report.getProductType());//15位活动代码
		message.setField64_MAC("3131313131313131");
		return message;
	}
	public static String getPosno(){
    	Random rd = new Random();
    	DecimalFormat df1 = new DecimalFormat("000");
		String currDate1 = new SimpleDateFormat("hhmmssSSS").format(new Date());// 生成日期格式为：年年月月日日、如：110608(11年06月08日)
		String increase1 = df1.format(rd.nextInt(999));// 4位自增长数
		return  increase1 + currDate1;
    }
}
