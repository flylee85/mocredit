package cn.m.mt.barcodeservice.zx;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m.mt.barcodeservice.util.Variable;
import cn.m.mt.po.Payment;
import cn.m.mt.util.DateTimeUtils;

import com.citiccard.payment.service.PaymentServices;
import com.citiccard.payment.service.SSLTools;

public class TestZXWsClient {
	private static Logger log = LoggerFactory.getLogger(ZXWsClient.class);
	private static PaymentServices service = SSLTools.getSSLService(
			Variable.TEST_ZXKEYSTORE, Variable.TEST_ZXKEYSTOREPWD,
			Variable.TEST_ZXKEYSTORETYPE, Variable.TEST_ZXTRUSTSTORE,
			Variable.TEST_ZXTRUSTSTOREPWD, Variable.TEST_ZXTRUSTSTORETYPE,
			Variable.TEST_ZXWSURL);
	private static String nowtoken = "";

	/**
	 * 登录
	 * @return
	 */
	public static String login(Payment payment) {
		String result = "OK";
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "login");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0800");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transTime = root.addElement("transTime");
		transTime.addText("");
		Element transDate = root.addElement("transDate");
		transDate.addText("");
		Element merchantType = root.addElement("merchantType");
		merchantType.addText(Variable.TEST_ZXMERCHANTTYPE);
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element password = root.addElement("password");
		password.addText(payment.getMerchantpassword());
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
		Element resParam = root.addElement("resParam");
		resParam.addText("");
		Element payURL = root.addElement("payURL");
		payURL.addText("");
		Element token = root.addElement("token");
		token.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			System.out.println("loginRequest=="+document.asXML());
			String resultxml = service.login(document.asXML());
			System.out.println("loginReqponse=="+resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			if ("0000000".equals(retcode)) {
				resParam = root.element("resParam");
				token = resParam.element("token");
				nowtoken = token.getText();
			}else if( "6666663".equals(retcode)){
				return "OK";
			}else{
				result = root.element("commentRes").getText();
			}
//			System.out.println("retCode===="+retCode.getText());
		} catch (RemoteException e) {
			e.printStackTrace();
			return "error:系统错误";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "error:系统错误";
		}
		return result;
	}

	/**
	 * 1.3.10	维护连接状态
	 
	public static void keepSession() {
		if ("".equals(nowtoken)) {
			login();
		}
		if (!"".equals(nowtoken)) {
			Document sessionDocument = DocumentHelper.createDocument();
			Element root = sessionDocument.addElement("message");
			root.addAttribute("method", "maintainSession");
			root.addAttribute("type", "request");
			Element infoType = root.addElement("infoType");
			infoType.addText("0200");
			Element merchantID = root.addElement("merchantID");
			merchantID.addText(Variable.TEST_ZXMERCHANTID);
			Element merchantName = root.addElement("merchantName");
			merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
			Element token = root.addElement("token");
			token.addText(nowtoken);
			Element retCode = root.addElement("retCode");
			retCode.addText("");
			Element commentRes = root.addElement("commentRes");
			commentRes.addText("");
			try {
//				log.debug("keepSessionRequest=="+sessionDocument.asXML());
				String sessionResultxml = 
						service.maintainSession(sessionDocument
						.asXML());
//				log.debug("keepSessionResponse=="+sessionResultxml);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * 查询持卡人信息
	 * @param pan
	 * @return
	 */
	public static String confirmInfo(String pan,String mid){
		String pointAmt = "";
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPayment");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0200");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(mid);
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element confirmType = root.addElement("confirmType");
		confirmType.addText("POINT");
		Element expiredDate = root.addElement("expiredDate");
		expiredDate.addText("");
		Element cvv2 = root.addElement("cvv2");
		cvv2.addText("");
		Element secondTrack = root.addElement("secondTrack");
		secondTrack.addText("");
		Element thirdTrack = root.addElement("thirdTrack");
		thirdTrack.addText("");
		Element dynamicPwd = root.addElement("dynamicPwd");
		dynamicPwd.addText("");
		Element csc4 = root.addElement("csc4");
		csc4.addText("");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		String resultxml;
		try {
			log.debug("confirmInfoRequest=="+document.asXML());
			resultxml = service.confirmInfo(document.asXML());
			log.debug("confirmInfoResponse==" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			log.debug("retcode==" + retcode);
			Element info = root.element("info");
			if(info!=null){
				Element point =info.element("pointAmt");
				if(point!=null){
					pointAmt = point.getText();
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return pointAmt;
	}
	/**
	 * 分期支付
	 * @param payment
	 * @return
	 */
	public static Payment cardDividedPayment(Payment payment) {
		String rest = login(payment);
		if(!rest.equals("OK")){
			payment.setRetcode("6666661");
			payment.setCommentres(rest);
			return payment;
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPayment");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0200");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element batchNo = root.addElement("batchNo");// M必须6位 批次号
		batchNo.addText(payment.getBatchno());
		Element serialNo = root.addElement("serialNo"); // M必须6位 序号(批次号+序号唯一)
		serialNo.addText(payment.getSerialno());
		Element orderID = root.addElement("orderID");// M定单号 19位
		orderID.addText(payment.getOrderid());
		Element pan = root.addElement("pan");// M卡号 19位
		String panstr = payment.getPanstr();
		Element bankOrderID = root.addElement("bankOrderID");
		bankOrderID.addText("");
		Element expiredDate = root.addElement("expiredDate");
		expiredDate.addText("");
		Element cvv2 = root.addElement("cvv2");
		cvv2.addText("");
		Element chIdNum = root.addElement("chIdNum");// C姓名
		chIdNum.addText("");
		Element chName = root.addElement("chName");// C身份证号
		chName.addText("");
		Element chMobile = root.addElement("chMobile");// C手机号
		String moblie = payment.getMobile();
		if(moblie == null)
			moblie = "";
		chMobile.addText(moblie);
		Element productType = root.addElement("productType"); // M--产品代码（即支付编码）
		productType.addText(payment.getProducttype());
//		productType.addText("YMSP");
		Element productNum = root.addElement("productNum"); // M产品数量2位不足左补0
		productNum.addText(payment.getProductnum());
		Element payWay = root.addElement("payWay");
		payWay.addText(payment.getPayway());
		Element transAmt = root.addElement("transAmt"); // M交易金额13位不足左补0
		transAmt.addText(payment.getTransamt());
		Element currCode = root.addElement("currCode");
		currCode.addText("156");
		Element dividedNum = root.addElement("dividedNum"); // M分期支付的期数
		dividedNum.addText("01");
		Element inputType = root.addElement("inputType"); // M输入方式：031刷卡,042手工
		inputType.addText("031");
		Element posConditionCode = root.addElement("posConditionCode");// M服务点条件码
		//1:"输入积分兑换",2:"输入金额兑换",3:"固定积分
		if(payment.getPayway().equals("02") || payment.getPayway().equals("03")){
			posConditionCode.addText("03");
		}else{
			posConditionCode.addText("99");
		}
		Element systemRefCode = root.addElement("systemRefCode");
		systemRefCode.addText("");
		Element authorizeCode = root.addElement("authorizeCode");
		authorizeCode.addText("");
		Element authDate = root.addElement("authDate");
		authDate.addText("");
		Element authTime = root.addElement("authTime");
		authTime.addText("");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
		Element timeStamp = root.addElement("timeStamp");
		timeStamp.addText(payment.getTimestamp());
		Element signature = root.addElement("signature");
		String signaturestr = "";
		String keyStorePath = Variable.TEST_ZXKEYSTORE;
		String alias = Variable.TEST_ZXKEYSTOREALIAS;
		String password = Variable.TEST_ZXKEYSTOREPWD;
		pan.addText(panstr);
		System.out.println(panstr+"===panstr2=====");
		int len = panstr.length();
		if(len!=14){
			for(int i=0;i<19-len;i++){
				panstr = panstr+" ";
			}
		}
		System.out.println(payment.getOrderid()+"===payment.getOrderid()=====");
		System.out.println(payment.getSerialno()+"===payment.getSerialno()=====");
		System.out.println(panstr+"===panstr=====");
		System.out.println(payment.getTimestamp()+"===payment.getTimestamp()=====");
		System.out.println(payment.getTransamt()+"===payment.getTransamt()=====");
		System.out.println(payment.getBatchno()+"===payment.getBatchno()=====");
		String sign = payment.getOrderid() + payment.getBatchno() + payment.getSerialno() +panstr+ payment.getTransamt()
				+ payment.getTimestamp();
		try {
			signaturestr = CertificateCoder.sign(sign.getBytes(), keyStorePath,
					alias, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		signaturestr = signaturestr.replace("\r","").replace("\\s","").replace("\n","");
		System.out.println("==========signaturestr========"+signaturestr);
		signature.addText(signaturestr);
		// PwdDataId BatchNo serialNo ExtField1 0000000000000 timestamp
		// 对定单号＋批次号＋序号＋卡号＋金额＋时间戳
		Element secondTrack = root.addElement("secondTrack");
		String secondTrackStr = payment.getSecondTrack();
		if(secondTrackStr==null)
			secondTrackStr="";
		secondTrack.addText(secondTrackStr);
		Element thirdTrack = root.addElement("thirdTrack");
		thirdTrack.addText("");
		Element dynamicPwd = root.addElement("dynamicPwd");
		dynamicPwd.addText("");
		Element csc4 = root.addElement("csc4");
		csc4.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			System.out.println("requestxml===="+document.asXML());
			String resultxml = service.dividedPayment(document.asXML());
			System.out.println("responsexml===="+resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			payment.setRetcode(retcode);
			commentRes = root.element("commentRes");
			payment.setCommentres(commentRes.getText());
			if ("0000000".equals(retcode)) {
				authDate = root.element("authDate");
				payment.setAuthdate(authDate.getText());
				authorizeCode = root.element("authorizeCode");
				payment.setAuthorizecode(authorizeCode.getText());
				authTime = root.element("authTime");
				payment.setAuthtime(authTime.getText());
				payment.setStatus(new Short("1"));
			}
			
			/*else if("6666661".equals(retcode)){//未登录
				String rest = login();
				if(rest.equals("OK")){
					return cardDividedPayment(payment);
				}else{
					payment.setRetcode("6666661");
					payment.setCommentres(rest);
					return payment;
				}
			}*/
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return payment;
	}

	public static void main(String[] args) {
		// <?xml version="1.0" encoding="GBK"?>
		// <message method="login" type="response">
		// <infoType>0810</infoType>
		// <terminalID>00000000</terminalID>
		// <posID>000000</posID>
		// <posTime>20120328170912</posTime>
		// <transDate>20120328</transDate>
		// <transTime>170656</transTime>
		// <merchantID>EWCSSH</merchantID>
		// <merchantName>二维码测试商户</merchantName>
		// <retCode>0000000</retCode>
		// <commentRes>交易成功</commentRes>
		// <merchantType>2</merchantType>
		// <password>074324bg</password>
		// <resParam count="2">
		// <payURL>https://creditcard.ecitic.com/Payment/payerauth.do</payURL>
		// <token>34D826BE9F6C88017616376794A9BACC</token>
		// </resParam>
		// <reserved/>
		// </message>
//		System.out.println("login====" + login());
		// int youNumber = 1;
		// // 0 代表前面补充0
		// // 4 代表长度为4
		// // d 代表参数为正数型
		// String str = String.format("%04d", youNumber);
		// System.out.println(str); // 0001
		// System.out.println(String.format("%1$19s", "aaaa"));
		Payment payment = new Payment();
		String orderid = "0020120419175705742";
		String batchno = String.format("%06d", 120419);
		String serialno = String.format("%06d", 175705);
//		String panstr = String.format("%1$-19s", "35317440651334");
		String panstr = "5201080000040103";
		String transamt = String.format("%013d", 0);
		String timestamp = DateTimeUtils.dateToStr(new Date(),
				"yyyyMMddHHmmssSSS");
		String productnum = String.format("%02d", 1);
		String producttype = "YMMB";
		payment.setBatchno(batchno);
		payment.setMobile("13811753398");
		payment.setOrderid(orderid);
		payment.setPanstr(panstr);
		payment.setProductnum(productnum);
		payment.setProducttype(producttype);
		payment.setSerialno(serialno);
		payment.setTimestamp(timestamp);
		payment.setTransamt(transamt);
		payment.setPayway("02");
		System.out.println(cardDividedPayment(payment));
		
//		PayMentBean transData = new PayMentBean(); 
//		transData.setAuthdate("20120401");
//		transData.setAuthtime("12121221");
//		transData.setAuthorizecode("123456");
//		transData.setTransamt("0000000000000");
//		transData.setBatchno("123456");
//		transData.setSerialno("123456");
//		List<PayMentBean> list = new ArrayList<PayMentBean>();
//		list.add(transData);
//		list.add(transData);
//		checkAccount(list);
	}
	/**
	 * 分期支付交易撤销(冲正)
	 * @param payment
	 * @return
	 */
	public static String dividedPaymentReversal(Payment payment) {
		String rest = login(payment);
		if(!rest.equals("OK")){
			return "error:"+rest;
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPaymentReversal");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0420");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element batchNo = root.addElement("batchNo");// M必须6位 批次号
		batchNo.addText(payment.getBatchno());
		Element serialNo = root.addElement("serialNo"); // M必须6位 序号(批次号+序号唯一)
		serialNo.addText(payment.getSerialno());
		Element orderID = root.addElement("orderID");// M定单号 19位
		orderID.addText(payment.getOrderid());
		Element pan = root.addElement("pan");// M卡号 19位
		pan.addText(payment.getPanstr());
		Element bankOrderID = root.addElement("bankOrderID");
		bankOrderID.addText("");
		Element expiredDate = root.addElement("expiredDate");
		expiredDate.addText("");
		Element cvv2 = root.addElement("cvv2");
		cvv2.addText("");
		Element chIdNum = root.addElement("chIdNum");// C姓名
		chIdNum.addText("");
		Element chName = root.addElement("chName");// C身份证号
		chName.addText("");
		Element chMobile = root.addElement("chMobile");// C手机号
		String mobile = payment.getMobile();
		if(mobile == null)
			mobile = "";
		chMobile.addText(mobile);
		Element productType = root.addElement("productType"); // M--产品代码（即支付编码）
		productType.addText(payment.getProducttype());
		Element productNum = root.addElement("productNum"); // M产品数量2位不足左补0
		productNum.addText(payment.getProductnum());
		Element payWay = root.addElement("payWay");
		payWay.addText(payment.getPayway());
		Element transAmt = root.addElement("transAmt"); // M交易金额13位不足左补0
		transAmt.addText(payment.getTransamt());
		Element currCode = root.addElement("currCode");
		currCode.addText("");
		Element dividedNum = root.addElement("dividedNum"); // M分期支付的期数
		dividedNum.addText("01");
//		Element inputType = root.addElement("inputType"); // M输入方式：031刷卡,042手工
//		inputType.addText("031");
//		Element posConditionCode = root.addElement("posConditionCode");// M服务点条件码
//		posConditionCode.addText("99");
//		Element systemRefCode = root.addElement("systemRefCode");
//		systemRefCode.addText("");
		Element authorizeCode = root.addElement("authorizeCode");
		authorizeCode.addText("");
		Element authDate = root.addElement("authDate");
		authDate.addText("");
		Element authTime = root.addElement("authTime");
		authTime.addText("");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
//		Element timeStamp = root.addElement("timeStamp");
//		timeStamp.addText(payment.getTimestamp());
//		String signaturestr = "";
//		String keyStorePath = Variable.TEST_ZXKEYSTORE;
//		String alias = Variable.TEST_ZXKEYSTOREALIAS;
//		String password = Variable.TEST_ZXKEYSTOREPWD;
//		String sign = payment.getOrderid() + payment.getBatchno() + payment.getSerialno() +payment.getPanstr()+ payment.getTransamt()
//				+ payment.getTimestamp();
//		try {
//			signaturestr = CertificateCoder.sign(sign.getBytes(), keyStorePath,
//					alias, password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Element signature = root.addElement("signature");
//		signature.addText(signaturestr);
//		// PwdDataId BatchNo serialNo ExtField1 0000000000000 timestamp
//		// 对定单号＋批次号＋序号＋卡号＋金额＋时间戳
//		Element secondTrack = root.addElement("secondTrack");
//		secondTrack.addText("");
//		Element thirdTrack = root.addElement("thirdTrack");
//		thirdTrack.addText("");
//		Element dynamicPwd = root.addElement("dynamicPwd");
//		dynamicPwd.addText("");
//		Element csc4 = root.addElement("csc4");
//		csc4.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			System.out.println("dividedPaymentReversalRequest=="+document.asXML());
			String resultxml = service.dividedPaymentReversal(document.asXML());
			System.out.println("dividedPaymentReversalResponse==" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			if ("0000000".equals(retcode)) {
				return "OK";
			}else{
				rest = root.element("commentRes").getText();
				return "error:"+rest;
			}
			
			/*else if("6666661".equals(retcode)){//未登录
				if(login().equals("OK")){
					return dividedPaymentReversal(payment);
				}
			}*/
		} catch (RemoteException e) {
			e.printStackTrace();
			return "error:系统错误";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "error:系统错误";
		}
	}
	/**
	 * 分期支付退款交易
	 * @param payment
	 * @return
	 */
	public static String hirePurchaseReturn(Payment payment){
		String rest = login(payment);
		if(!rest.equals("OK")){
			return "error:"+rest;
		}
		String result = "OK";
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "hirePurchaseReturn");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0420");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element batchNo = root.addElement("batchNo");// M必须6位 批次号
		batchNo.addText(payment.getBatchno());
		Element serialNo = root.addElement("serialNo"); // M必须6位 序号(批次号+序号唯一)
		serialNo.addText(payment.getSerialno());
		Element orderID = root.addElement("orderID");// M定单号 19位
		orderID.addText(payment.getOrderid());
//		Element pan = root.addElement("pan");// M卡号 19位
//		pan.addText(payment.getPanstr());
//		Element bankOrderID = root.addElement("bankOrderID");
//		bankOrderID.addText("");
//		Element expiredDate = root.addElement("expiredDate");
//		expiredDate.addText("");
//		Element cvv2 = root.addElement("cvv2");
//		cvv2.addText("");
//		Element chIdNum = root.addElement("chIdNum");// C姓名
//		chIdNum.addText("");
//		Element chName = root.addElement("chName");// C身份证号
//		chName.addText("");
//		Element chMobile = root.addElement("chMobile");// C手机号
//		chMobile.addText(payment.getMobile());
		Element productType = root.addElement("productType"); // M--产品代码（即支付编码）
		productType.addText(payment.getProducttype());
		Element productNum = root.addElement("productNum"); // M产品数量2位不足左补0
		productNum.addText(payment.getProductnum());
		Element payWay = root.addElement("payWay");
		payWay.addText(payment.getPayway());
		Element orgTransAmt = root.addElement("orgTransAmt");
		orgTransAmt.addText("0000000000000");
		Element transAmt = root.addElement("transAmt"); // M交易金额13位不足左补0
		transAmt.addText("0000000000000");
		Element currCode = root.addElement("currCode");
		currCode.addText("156");
		Element dividedNum = root.addElement("dividedNum"); // M分期支付的期数
		dividedNum.addText("01");
		Element inputType = root.addElement("inputType"); // M输入方式：031刷卡,042手工
		inputType.addText("031");
//		Element posConditionCode = root.addElement("posConditionCode");// M服务点条件码
//		posConditionCode.addText("99");
//		Element systemRefCode = root.addElement("systemRefCode");
//		systemRefCode.addText("");
		Element authorizeCode = root.addElement("authorizeCode");
		authorizeCode.addText("");
		Element authDate = root.addElement("authDate");
		authDate.addText("");
		Element authTime = root.addElement("authTime");
		authTime.addText("");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
//		Element timeStamp = root.addElement("timeStamp");
//		timeStamp.addText(payment.getTimestamp());
//		String signaturestr = "";
//		String keyStorePath = Variable.TEST_ZXKEYSTORE;
//		String alias = Variable.TEST_ZXKEYSTOREALIAS;
//		String password = Variable.TEST_ZXKEYSTOREPWD;
//		String sign = payment.getOrderid() + payment.getBatchno() + payment.getSerialno() +payment.getPanstr()+ payment.getTransamt()
//				+ payment.getTimestamp();
//		try {
//			signaturestr = CertificateCoder.sign(sign.getBytes(), keyStorePath,
//					alias, password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Element signature = root.addElement("signature");
//		signature.addText(signaturestr);
//		// PwdDataId BatchNo serialNo ExtField1 0000000000000 timestamp
//		// 对定单号＋批次号＋序号＋卡号＋金额＋时间戳
//		Element secondTrack = root.addElement("secondTrack");
//		secondTrack.addText("");
//		Element thirdTrack = root.addElement("thirdTrack");
//		thirdTrack.addText("");
//		Element dynamicPwd = root.addElement("dynamicPwd");
//		dynamicPwd.addText("");
//		Element csc4 = root.addElement("csc4");
//		csc4.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			log.debug("dividedPaymentReversalRequest=="+document.asXML());
			String resultxml = service.dividedPaymentReversal(document.asXML());
			log.debug("dividedPaymentReversalResponse==" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			/*if("6666661".equals(retcode)){//未登录
				String rest = login();
				if(rest.equals("OK")){
					return hirePurchaseReturn(payment);
				}else{
					return "error:"+rest;
				}
			
			}*/
		} catch (RemoteException e) {
			e.printStackTrace();
			result = "error:系统错误";
		} catch (DocumentException e) {
			e.printStackTrace();
			result = "error:系统错误";
		}
		return result;
		
	}
	/**
	 * 对帐
	 * @param transDatas
	 * @return
	 */
	public static String checkAccount(List<Payment> transDatas){
		Payment payment = transDatas.get(0);
		String rest = login(payment);
		if(!rest.equals("OK")){
			return "error:"+rest;
		}
		String result = "OK";
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "checkAccount");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0200");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element dataSet = root.addElement("dataSet");
		dataSet.addAttribute("count", String.valueOf(transDatas.size()));
		for(Payment transData:transDatas){
			Element record = dataSet.addElement("record");
			String recordstr = transData.getBatchno()+transData.getSerialno()+transData.getAuthorizecode()+transData.getAuthdate()+transData.getAuthtime()+transData.getTransamt()+String.format("%1$-103s", "");
			record.addText(recordstr);
		}
		Element terminalFlag = root.addElement("terminalFlag");
		terminalFlag.addText("Y");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			log.debug("checkAccountRequest=="+document.asXML());
			String resultxml = service.checkAccount(document.asXML());
			log.debug("checkAccountResponse==" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			/*if("6666661".equals(retcode)){//未登录
				String rest = login();
				if(rest.equals("OK")){
					return checkAccount(transDatas);
				}else{
					return "error:"+rest;
				}
			}*/
		} catch (RemoteException e) {
			e.printStackTrace();
			result = "error:系统错误";
		} catch (DocumentException e) {
			e.printStackTrace();
			result = "error:系统错误";
		}
		return result;
	}
	/*
	 * 查询
	 */
	public static String confirmInfo(Payment payment) {
		String rest = login(payment);
		if(!rest.equals("OK")){
			return "error:"+rest;
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "confirmInfo");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0200");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element confirmType = root.addElement("confirmType");// M必须6位 批次号
		confirmType.addText("POINT");
		Element pan = root.addElement("pan");// M卡号 19位
		String penstr = payment.getPanstr();
		String secondTrackStr = "";
		if(penstr.indexOf("@")!=-1){
			String str[]= penstr.split("@");
			penstr = str[0];
			secondTrackStr =  str[1];
		}
		for(int i=0;i<19-penstr.length();i++){
			penstr = penstr+" ";
		}
		pan.addText(penstr);
//		System.out.println("penstr===="+penstr);
		Element expiredDate = root.addElement("expiredDate");
//		System.out.println("===secondTrackStr===="+secondTrackStr);
//		if(!secondTrackStr.equals("")){
//			expiredDate.addText(secondTrackStr.substring(0,4));
//		}else{
			expiredDate.addText("");
//		}
		
		Element cvv2 = root.addElement("cvv2");
		cvv2.addText("");
		Element secondTrack = root.addElement("secondTrack");
		secondTrack.addText(secondTrackStr);
		Element thirdTrack = root.addElement("thirdTrack");
		thirdTrack.addText("");
		Element csc4 = root.addElement("csc4");
		csc4.addText("");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			System.out.println(document.asXML().toString());
			String resultxml = service.dividedPayment(document.asXML());
			System.out.println("==resultxml=========="+resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			payment.setRetcode(retcode);
			commentRes = root.element("commentRes");
			payment.setCommentres(commentRes.getText());
			if ("0000000".equals(retcode)) {
				Element info = root.element("info");
				Element pointAmt = info.element("pointAmt");
				return pointAmt.getText();
			}else{
				return "error:"+commentRes.getText();
			}
			
			/*else if("6666661".equals(retcode)){//未登录
				String rest = login();
				if(rest.equals("OK")){
					return confirmInfo(payment);
				}else{
					return "error:"+rest;
				}
			}*/
		} catch (RemoteException e) {
			e.printStackTrace();
			return "error:系统错误";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "error:系统错误";
		}
	}
	public static Payment barcodeDividedPayment(Payment payment) {
		String rest = login(payment);
		System.out.println("====rest=="+rest);
		if(!rest.equals("OK")){
			payment.setRetcode("6666661");
			payment.setCommentres(rest);
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("message");// 创建根节点
		root.addAttribute("method", "dividedPayment");
		root.addAttribute("type", "request");
		Element infoType = root.addElement("infoType");
		infoType.addText("0200");
		Element terminalID = root.addElement("terminalID");
		terminalID.addText("00000000");
		Element posID = root.addElement("posID");
		posID.addText("000000");
		Element posTime = root.addElement("posTime");
		posTime.addText(DateTimeUtils.getDate("yyyyMMddHHmmss"));
		Element transDate = root.addElement("transDate");
		transDate.addText(DateTimeUtils.getDate("yyyyMMdd"));
		Element transTime = root.addElement("transTime");
		transTime.addText(DateTimeUtils.getDate("HHmmss"));
		Element merchantID = root.addElement("merchantID");
		merchantID.addText(payment.getMerchantid());
		Element merchantName = root.addElement("merchantName");
		merchantName.addText(Variable.TEST_ZXMERCHANTNAME);
		Element batchNo = root.addElement("batchNo");// M必须6位 批次号
		batchNo.addText(payment.getBatchno());
		Element serialNo = root.addElement("serialNo"); // M必须6位 序号(批次号+序号唯一)
		serialNo.addText(payment.getSerialno());
		Element orderID = root.addElement("orderID");// M定单号 19位
		orderID.addText(payment.getOrderid());
		Element pan = root.addElement("pan");// M卡号 19位
		String panstr = payment.getPanstr();
		pan.addText(panstr);
		Element bankOrderID = root.addElement("bankOrderID");
		bankOrderID.addText("");
		Element expiredDate = root.addElement("expiredDate");
		expiredDate.addText("");
		Element cvv2 = root.addElement("cvv2");
		cvv2.addText("");
		Element chIdNum = root.addElement("chIdNum");// C姓名
		chIdNum.addText("");
		Element chName = root.addElement("chName");// C身份证号
		chName.addText("");
		Element chMobile = root.addElement("chMobile");// C手机号
		String moblie = payment.getMobile();
		if(moblie == null)
			moblie = "";
		chMobile.addText(moblie);
		Element productType = root.addElement("productType"); // M--产品代码（即支付编码）
		productType.addText(payment.getProducttype());
//		productType.addText("YMMP");
		Element productNum = root.addElement("productNum"); // M产品数量2位不足左补0
		productNum.addText(payment.getProductnum());
		Element payWay = root.addElement("payWay");
		payWay.addText(payment.getPayway());
		Element transAmt = root.addElement("transAmt"); // M交易金额13位不足左补0
		transAmt.addText(payment.getTransamt());
		Element currCode = root.addElement("currCode");
		currCode.addText("156");
		Element dividedNum = root.addElement("dividedNum"); // M分期支付的期数
		dividedNum.addText("01");
		Element inputType = root.addElement("inputType"); // M输入方式：031刷卡,042手工
		inputType.addText("031");
		Element posConditionCode = root.addElement("posConditionCode");// M服务点条件码
		if(panstr.length()==16 || panstr.length()==19)
			posConditionCode.addText("05");
		else
			posConditionCode.addText("99");
		Element systemRefCode = root.addElement("systemRefCode");
		systemRefCode.addText("");
		Element authorizeCode = root.addElement("authorizeCode");
		authorizeCode.addText("");
		Element authDate = root.addElement("authDate");
		authDate.addText("");
		Element authTime = root.addElement("authTime");
		authTime.addText("");
		Element retCode = root.addElement("retCode");
		retCode.addText("");
		Element commentRes = root.addElement("commentRes");
		commentRes.addText("");
		Element timeStamp = root.addElement("timeStamp");
		timeStamp.addText(payment.getTimestamp());
		Element signature = root.addElement("signature");
		String signaturestr = "";
		String keyStorePath = Variable.TEST_ZXKEYSTORE;
		String alias = Variable.TEST_ZXKEYSTOREALIAS;
		String password = Variable.TEST_ZXKEYSTOREPWD;
		String penstr = payment.getPanstr();
		int len = penstr.length();
		for(int i=0;i<19-len;i++){
			penstr = penstr+" ";
		}
		String sign = payment.getOrderid() + payment.getBatchno() + payment.getSerialno() +penstr+ payment.getTransamt()
				+ payment.getTimestamp();
		try {
			System.out.println(sign.getBytes()+"==="+keyStorePath+"===="+alias+"===="+password);
			signaturestr = CertificateCoder.sign(sign.getBytes(), keyStorePath,
					alias, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		signaturestr = signaturestr.replace("\r","").replace("\\s","").replace("\n","");
		signature.addText(signaturestr);
		// PwdDataId BatchNo serialNo ExtField1 0000000000000 timestamp
		// 对定单号＋批次号＋序号＋卡号＋金额＋时间戳
		Element secondTrack = root.addElement("secondTrack");
		secondTrack.addText("");
		Element thirdTrack = root.addElement("thirdTrack");
		thirdTrack.addText("");
		Element dynamicPwd = root.addElement("dynamicPwd");
		dynamicPwd.addText("");
		Element csc4 = root.addElement("csc4");
		csc4.addText("");
		Element reserved = root.addElement("reserved");
		reserved.addText("");
		try {
			System.out.println("======barcode==="+document.asXML());
			String resultxml = service.dividedPayment(document.asXML());
			Document resultDoc = DocumentHelper.parseText(resultxml);
			root = resultDoc.getRootElement();
			retCode = root.element("retCode");
			String retcode = retCode.getText();
			payment.setRetcode(retcode);
			commentRes = root.element("commentRes");
			payment.setCommentres(commentRes.getText());
			if ("0000000".equals(retcode)) {
				authDate = root.element("authDate");
				payment.setAuthdate(authDate.getText());
				authorizeCode = root.element("authorizeCode");
				payment.setAuthorizecode(authorizeCode.getText());
				authTime = root.element("authTime");
				payment.setAuthtime(authTime.getText());
				payment.setStatus(new Short("1"));
			}
			
			/*else if("6666661".equals(retcode)){//未登录
				String rest = login();
				if(rest.equals("OK")){
					return barcodeDividedPayment(payment);
				}else{
					payment.setRetcode("6666661");
					payment.setCommentres(rest);
				}
			}*/
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return payment;
	}
}
