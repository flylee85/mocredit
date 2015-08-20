package com.mocredit.integral.service.impl;

import java.rmi.RemoteException;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocredit.integral.entity.Payment;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.IntegralService;
import com.mocredit.integral.service.PaymentServices;
import com.mocredit.integral.util.SSLTools;
import com.mocredit.integral.util.ServiceUtil;
import com.mocredit.integral.util.Variable;

/**
 * 中信银行积分业务实现
 * 
 * @author liaoying Created on 2015年8月11日
 *
 */
public class IntegralCiticServiceImpl implements IntegralService {
	private static Logger log = LoggerFactory.getLogger(IntegralCiticServiceImpl.class);
	private static PaymentServices service = SSLTools.getSSLService(Variable.ZXKEYSTORE, Variable.ZXKEYSTOREPWD,
			Variable.ZXKEYSTORETYPE, Variable.ZXTRUSTSTORE, Variable.ZXTRUSTSTOREPWD, Variable.ZXTRUSTSTORETYPE,
			Variable.ZXWSURL);
	@Override
	public Response payment(Map<String, Object> reqMap) {
		Payment payment;
		try {
			payment = ServiceUtil.getLoginPayment(reqMap);
		} catch (Exception e) {
			log.error("登录时参数不正确：" + e.getMessage());
			return new Response(Variable.ERROR_PARAM, e.getMessage());
		}
		try {
			log.info(payment.getPan() + "开始登录");
			String rest = this.login(payment);
			if (!Variable.OK.equals(rest)) {
				log.error(payment.getPan() + "登录失败:" + rest);
				return new Response(Variable.ERROR_LOGIN, rest);
			} else {
				payment = ServiceUtil.getPayment(reqMap);
				String payResult = this.cardDividedPayment(payment);
				/* 对交易结果验签 */
				if (!ServiceUtil.verify(payment)) {
					return new Response(Variable.ERROR_PAY, "验签失败");
				}
				if (!Variable.OK.equals(payResult)) {
					return new Response(Variable.ERROR_PAY, payment.getCommentRes());
				}
			}
		} catch (RemoteException | DocumentException e) {
			return new Response(Variable.ERROR_SYSTEM, "系统错误");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("支付时参数不正确：" + e.getMessage());
			return new Response(Variable.ERROR_PARAM, e.getMessage());
		}
		return new Response(Variable.OK);
	}

	@Override
	public Response paymentReversal(Map<String, Object> reqMap) {
		return null;
		// TODO Auto-generated method stub

	}

	/**
	 * 登录
	 * 
	 * @param payment
	 *            payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	private String login(Payment payment) throws RemoteException, DocumentException {
		String result = Variable.OK;
		Document document = ServiceUtil.createLoginDocument(payment);
		String reqXml = document.asXML();
		log.info(payment.getPan() + "登录请求:" + reqXml);
		String resultxml = service.login(reqXml);
		log.info(payment.getPan() + "登录响应:" + resultxml);
		Document resultDoc = DocumentHelper.parseText(resultxml);
		Element root = resultDoc.getRootElement();
		Element retCode = root.element("retCode");
		String retcode = retCode.getText();
		if ("0000000".equals(retcode)) {
			return Variable.OK;
		} else {
			result = root.element("commentRes").getText();
		}
		return result;
	}

	/**
	 * 分期支付
	 * 
	 * @param payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	private String cardDividedPayment(Payment payment) throws RemoteException, DocumentException {
		Document document = ServiceUtil.createPaymentDocument(payment);
		try {
			String reqXml = document.asXML();
			log.info(payment.getPan() + "支付请求:" + reqXml);
			String resultxml = service.dividedPayment(reqXml);
			log.info(payment.getPan() + "支付响应:" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);

			/* 提取响应信息数据 */
			Element root = resultDoc.getRootElement();
			Element retCode = root.element("retCode");
			String retcode = retCode.getText();
			payment.setRetCode(retcode);
			Element commentRes = root.element("commentRes");
			payment.setCommentRes(commentRes.getText());
			Element authDate = root.element("authDate");
			payment.setAuthDate(authDate.getText());
			Element authorizeCode = root.element("authorizeCode");
			payment.setAuthorizeCode(authorizeCode.getText());
			Element authTime = root.element("authTime");
			payment.setAuthTime(authTime.getText());
			payment.setStatus(new Short("1"));
			payment.setTransDate(root.element("transDate").getText());
			payment.setTransTime(root.element("transTime").getText());
			payment.setSystemRefCode(root.element("systemRefCode").getText());
			payment.setTimeStamp(root.element("timeStamp").getText());
			if ("0000000".equals(retcode)) {
				return Variable.OK;
			}
		} catch (RemoteException e) {
			throw e;
		} catch (DocumentException e) {
			throw e;
		}
		return payment.getCommentRes();
	}

	@Override
	public Response confirmInfo(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
