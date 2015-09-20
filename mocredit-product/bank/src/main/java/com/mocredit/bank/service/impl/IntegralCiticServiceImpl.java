package com.mocredit.bank.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.bank.constant.ReportStatus;
import com.mocredit.bank.constant.RespError;
import com.mocredit.bank.datastructure.ResponseData;
import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.RequestData;
import com.mocredit.bank.entity.TiPaymentReport;
import com.mocredit.bank.entity.TiReportDataZx;
import com.mocredit.bank.entity.TiShopMerchant;
import com.mocredit.bank.persistence.TiPaymentReportMapper;
import com.mocredit.bank.persistence.TiReportDataZxMapper;
import com.mocredit.bank.persistence.TiShopMerchantMapper;
import com.mocredit.bank.service.IntegralService;
import com.mocredit.bank.service.PaymentServices;
import com.mocredit.bank.util.SSLTools;
import com.mocredit.bank.util.ServiceUtil;
import com.mocredit.bank.util.Utils;
import com.mocredit.bank.util.Variable;
import com.mocredit.base.util.Banks;

/**
 * 中信银行积分业务实现
 * 
 * @author liaoying Created on 2015年8月11日
 *
 */
@Service("citicService")
public class IntegralCiticServiceImpl implements IntegralService {
	private static Logger log = LoggerFactory.getLogger(IntegralCiticServiceImpl.class);
	private PaymentServices service = SSLTools.getSSLService(Variable.ZXKEYSTORE, Variable.ZXKEYSTOREPWD,
			Variable.ZXKEYSTORETYPE, Variable.ZXTRUSTSTORE, Variable.ZXTRUSTSTOREPWD, Variable.ZXTRUSTSTORETYPE,
			Variable.ZXWSURL);
	@Autowired
	private TiShopMerchantMapper merchantMapper;
	@Autowired
	private TiPaymentReportMapper reportMapper;
	@Autowired
	private TiReportDataZxMapper reportDataMapper;

	@Override
	public ResponseData payment(RequestData requestData) {
		TiShopMerchant merchant = merchantMapper.selectByShopId(requestData.getShopId());
		if (null == merchant) {
			return genResponseData(false, RespError.INVALID_SHOP, null, null);
		}
		try {
			requestData.setMerchantId(merchant.getMerchantId());
			requestData.setMerchantName(merchant.getMerchantName());
			requestData.setTerminalId(merchant.getTerminalId());
			Payment payment = ServiceUtil.getPayment(requestData);
			String payResult = this.payment(payment);
			/* 对交易结果验签 */
//			if (!ServiceUtil.verify(payment)) {
//				log.error(requestData.getCardNum() + "验签失败,签名：" + payment.getSignature());
//				return genResponseData(false, RespError.PAY_ERROR, "验签失败", null);
//			}
			if (!Variable.OK.equals(payResult)) {
				return genResponseData(false, RespError.PAY_ERROR, payment.getCommentRes(), null);
			}

			/* 成功后记录交易 */
			savePaymentReport(requestData, payment);
		} catch (RemoteException | DocumentException e) {
			return genResponseData(false, RespError.SYETEM_ERROR, null, null);
		} catch (Exception e) {
			log.error("支付时参数不正确：" + e.getMessage());
			return genResponseData(false, RespError.SYETEM_ERROR, e.getMessage(), null);
		}
		return genResponseData(true, null, null, null);
	}

	private void savePaymentReport(RequestData requestData, Payment payment) {
		// 记录交易记录
		TiPaymentReport report = new TiPaymentReport();
		report.setBank(Banks.CITIC.getName());
		report.setTerminalId(payment.getTerminalID());
		report.setOrderId(payment.getOrderID());
		report.setCardNum(payment.getPan());
		report.setPosId(payment.getPosID());
		report.setPosTime(payment.getPosTime());
		report.setProductType(payment.getProductType());
		report.setAmount(Long.valueOf(payment.getTransAmt()));
		report.setDevice(requestData.getDevice());
		report.setStatus((short) 1);
		report.setRequestId(requestData.getRequestId());
		reportMapper.save(report);

		// 记录交易记录中信银行数据
		TiReportDataZx reportData = new TiReportDataZx();
		reportData.setMerchantId(payment.getMerchantID());
		reportData.setMerchantName(payment.getMerchantName());
		reportData.setReportId(report.getUuid());
		reportData.setBatchNo(payment.getBatchNo());
		reportData.setSerialNo(payment.getSerialNo());
		reportData.setAuthorizeCode(payment.getAuthorizeCode());
		reportData.setAuthDate(payment.getAuthDate());
		reportData.setAuthTime(payment.getAuthTime());
		reportData.setBankOrderId(payment.getBankOrderID());
		reportDataMapper.save(reportData);
	}

	@Override
	public ResponseData paymentRevoke(RequestData requestData) {
		try {
			// 查询交易记录以及记录银行数据
			TiPaymentReport report = reportMapper.selectByOrderId(requestData.getOrderId());
			TiReportDataZx reportData = reportDataMapper.selectByReportId(report.getUuid());

			Payment payment = ServiceUtil.getPaymentRevoke(report, reportData);
			String payResult = this.paymentRevoke(payment);
			if (!Variable.OK.equals(payResult)) {
				return genResponseData(false, RespError.PAY_ERROR, payment.getCommentRes(), null);
			}
			/* 成功后修改交易状态 */
			report.setStatus(ReportStatus.REVOKE.getStatus());
			reportMapper.updateStatus(report);
		} catch (RemoteException | DocumentException e) {
			return genResponseData(false, RespError.SYETEM_ERROR, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("支付时参数不正确：" + e.getMessage());
			return genResponseData(false, RespError.PARAM_ERROR, e.getMessage(), null);
		}
		return genResponseData(true, null, null, null);
	}

	@Override
	public Set<Integer> checkAccount(List<Payment> reports) {
		String paramXMl = ServiceUtil.createCheckAccountDocument(reports);
		Set<Integer> success = new HashSet<>();
		try {
			System.out.println("对账请求："+paramXMl);
			String resultxml = service.checkAccount(paramXMl);
			try {
				Document resultDoc = DocumentHelper.parseText(resultxml);
				Element root = resultDoc.getRootElement();
				Iterator<Element> ei = root.element("dataSet").elementIterator();
				List<String> list = new ArrayList<String>();
				while (ei.hasNext()) {
					list.add(ei.next().getText());
				}
				for (int i = 0; i < reports.size(); i++) {
					if (i < list.size() && list.get(i).indexOf("请款成功") != -1) {
						success.add(reports.get(i).getId());
					}
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			/* 调用失败200ms后重试一次 */
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return checkAccount(reports);
		}
		return success;
	}

	/**
	 * 分期支付
	 * 
	 * @param payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	private String payment(Payment payment) throws RemoteException, DocumentException {
		try {
			String reqXml = ServiceUtil.createPaymentDocument(payment);
			log.info("支付请求:" + reqXml);
			String resultxml = service.dividedPayment(reqXml, payment.getPan(), payment.getRequestId());
			log.info("支付响应:" + resultxml);
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
			payment.setSignature(root.element("signature").getText());
			if ("0000000".equals(retcode)) {
				payment.setBankOrderID(root.element("bankOrderID").getText());
				return Variable.OK;
			}
		} catch (RemoteException e) {
			throw e;
		} catch (DocumentException e) {
			throw e;
		}
		return payment.getCommentRes();
	}

	/**
	 * 分期支付
	 * 
	 * @param payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	private String confirmInfo(Payment payment) throws RemoteException, DocumentException {
		try {
			String reqXml = ServiceUtil.createConfirmInfoDocument(payment);
			log.info("查询请求:" + reqXml);
			String resultxml = service.confirmInfo(reqXml, payment.getPan(), payment.getRequestId());
			log.info("查询响应:" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);

			/* 提取响应信息数据 */
			Element root = resultDoc.getRootElement();
			Element retCode = root.element("retCode");
			String retcode = retCode.getText();
			payment.setRetCode(retcode);
			Element commentRes = root.element("commentRes");
			payment.setCommentRes(commentRes.getText());
			if ("0000000".equals(retcode)) {
				payment.setTransAmt(root.element("info").element("pointAmt").getText());
				return Variable.OK;
			}
		} catch (RemoteException e) {
			throw e;
		} catch (DocumentException e) {
			throw e;
		}
		return payment.getCommentRes();
	}

	/**
	 * 支付撤销
	 * 
	 * @param payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	private String paymentRevoke(Payment payment) throws RemoteException, DocumentException {
		try {
			String reqXml = ServiceUtil.createPaymentRevokeDocument(payment);
			log.info("支付撤销请求:" + reqXml);
			String resultxml = service.dividedPaymentReversal(reqXml, payment.getPan(), payment.getRequestId());
			log.info("支付撤销响应:" + resultxml);
			Document resultDoc = DocumentHelper.parseText(resultxml);

			/* 提取响应信息数据 */
			Element root = resultDoc.getRootElement();
			Element retCode = root.element("retCode");
			String retcode = retCode.getText();
			payment.setRetCode(retcode);
			Element commentRes = root.element("commentRes");
			payment.setCommentRes(commentRes.getText());
			Element authorizeCode = root.element("authorizeCode");
			payment.setAuthorizeCode(authorizeCode.getText());
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
	public ResponseData confirmInfo(RequestData requestData) {
		TiShopMerchant merchant = merchantMapper.selectByShopId(requestData.getShopId());
		if (null == merchant) {
			return genResponseData(false, RespError.INVALID_SHOP, null, null);
		}
		try {
			requestData.setMerchantId(merchant.getMerchantId());
			requestData.setMerchantName(merchant.getMerchantName());
			requestData.setTerminalId(merchant.getTerminalId());
			Payment payment = ServiceUtil.getConfirmPayment(requestData);
			String payResult = this.confirmInfo(payment);
			Map<String, Object> data = new HashMap<>();
			if (!Variable.OK.equals(payResult)) {
				data.put("integral", 0);
				// 7100008 该卡不能做积分交易
				data.put("changeFlag", !"7100008".equals(payment.getRetCode()));
				return genResponseData(true, RespError.PAY_ERROR, payment.getCommentRes(), data);
			}
			data.put("integral", Long.parseLong(payment.getTransAmt()));
			data.put("changeFlag", true);
			return genResponseData(true, null, null, data);
		} catch (RemoteException | DocumentException e) {
			return genResponseData(false, RespError.SYETEM_ERROR, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return genResponseData(false, RespError.SYETEM_ERROR, null, null);
		}
	}

	private ResponseData genResponseData(boolean success, RespError error, String errMsg, Object data) {
		ResponseData response = new ResponseData();
		response.setSuccess(success);
		if (null != error) {
			response.setErrorCode(error.getErrorCode());
			if (Utils.isNullOrBlank(errMsg)) {
				response.setErrorMsg(error.getErrorMsg());
			} else {
				response.setErrorMsg(error.getErrorMsg() + ":" + errMsg);
			}
		}
		response.setData(data);
		return response;
	}

	@Override
	public ResponseData paymentReversal(RequestData requestData) {
		try {
			// 查询交易记录以及记录银行数据
			TiPaymentReport report = reportMapper.selectByOrderId(requestData.getOrderId());
			TiReportDataZx reportData = reportDataMapper.selectByReportId(report.getUuid());

			Payment payment = ServiceUtil.getPaymentRevoke(report, reportData);
			String payResult = this.paymentRevoke(payment);
			if (!Variable.OK.equals(payResult)) {
				return genResponseData(false, RespError.PAY_ERROR, payment.getCommentRes(), null);
			}
			/* 成功后修改交易状态 */
			report.setStatus(ReportStatus.REVERSAL.getStatus());
			reportMapper.updateStatus(report);
		} catch (RemoteException | DocumentException e) {
			return genResponseData(false, RespError.SYETEM_ERROR, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("支付时参数不正确：" + e.getMessage());
			return genResponseData(false, RespError.PARAM_ERROR, e.getMessage(), null);
		}
		return genResponseData(true, null, null, null);
	}

	@Override
	public ResponseData paymentRevokeReversal(RequestData requestData) {
		// 查询交易记录以及记录银行数据
		TiPaymentReport report = reportMapper.selectByOrderId(requestData.getOrderId());
		/* 交易已撤销，返回失败标志和错误码，中信没有进行撤销冲正的接口，针对这种情况直接，POS端撤销的冲正需要做特殊处理。 */
		if (ReportStatus.REVOKE.getStatus() == report.getStatus()) {
			return genResponseData(false, RespError.HAS_REVERSED, null, null);
		}
		return genResponseData(false, RespError.SYETEM_ERROR, null, null);
	}

	/**
	 * 登录
	 * 
	 * @param payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	public String login(Payment payment) throws RemoteException, DocumentException {
		String result = Variable.OK;
		String paramXML = ServiceUtil.createLoginDocument(payment);
		log.info("登录请求:" + paramXML);
		String resultxml = service.login(paramXML, payment.getPan(), payment.getRequestId());
		log.info("登录响应:" + resultxml);
		Document resultDoc = DocumentHelper.parseText(resultxml);
		Element root = resultDoc.getRootElement();
		Element retCode = root.element("retCode");
		// TODO 避免多次登录报错,这是一个临时方案。会修改为每天定时登录一次，之后每隔25分钟维持一次连接状态。
		// if (null == retCode) {
		// return Variable.OK;
		// }
		String retcode = retCode.getText();
		if ("0000000".equals(retcode)) {
			payment.setToken(root.element("resParam").element("token").getText());
			return Variable.OK;
		} else {
			result = root.element("commentRes").getText();
		}
		return result;
	}

	/**
	 * 维持连接状态
	 * 
	 * @param payment
	 * @return
	 * @throws RemoteException
	 * @throws DocumentException
	 */
	public String maintainSession(Payment payment) throws RemoteException, DocumentException {
		String result = Variable.OK;
		String paramXML = ServiceUtil.createMaintainSessionDocument(payment);
		log.info("维持连接状态请求:" + paramXML);
		String resultxml = service.maintainSession(paramXML);
		log.info("维持连接状态响应:" + resultxml);
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
}
