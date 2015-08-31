package com.mocredit.bank.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mocredit.bank.constant.RespError;
import com.mocredit.bank.datastructure.ResponseData;
import com.mocredit.bank.entity.MessageObject;
import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.RequestData;
import com.mocredit.bank.entity.TiPaymentReport;
import com.mocredit.bank.entity.TiReportDataZx;
import com.mocredit.bank.persistence.TiPaymentReportMapper;
import com.mocredit.bank.persistence.TiReportDataZxMapper;
import com.mocredit.bank.service.IntegralService;
import com.mocredit.bank.service.PosAction;
import com.mocredit.bank.util.FormatException;
import com.mocredit.bank.util.ServiceUtil;
import com.mocredit.base.util.Banks;

/**
 * 民生银行积分业务类
 * 
 * @author liaoying Created on 2015年8月27日
 *
 */
public class IntegralCmbcServiceImpl implements IntegralService {
	private static Logger logger = Logger.getLogger(IntegralCmbcServiceImpl.class);
	@Autowired
	private PosAction posAction;
	@Autowired
	private TiPaymentReportMapper reportMapper;
	@Autowired
	private TiReportDataZxMapper reportDataMapper;

	@Override
	public ResponseData payment(RequestData requestData) {
		MessageObject request = ServiceUtil.getPayMessage(requestData);
		MessageObject response = null;
		try {
			response = posAction.messReceived(request);
			logger.info("msyhResponse[" + response + "]");
		} catch (FormatException e) {
			return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
		} catch (Exception e) {
			return new ResponseData(RespError.SYETEM_ERROR.getErrorCode(), RespError.SYETEM_ERROR.getErrorMsg(), false);
		}
		// 返回码判断成功与否
		if ("00".equals(response.getField39_Response_Code())) {
			savePaymentReport(requestData, response);
			return new ResponseData(null, null, true);
		}
		return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
	}

	@Override
	public ResponseData paymentReversal(RequestData requestData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseData paymentRevoke(RequestData requestData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseData paymentRevokeReversal(RequestData requestData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseData confirmInfo(RequestData requestData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Integer> checkAccount(List<Payment> records) {
		// TODO Auto-generated method stub
		return null;
	}

	private void savePaymentReport(RequestData requestData,MessageObject response) {
		// 记录交易记录
		TiPaymentReport report = new TiPaymentReport();
		report.setBank(Banks.CITIC);
		report.setTerminalId(response.getField41_Card_Acceptor_Terminal_ID());
		report.setOrderId(requestData.getOrderId());
		report.setCardNum(response.getField02_Primary_Account_Number());
		report.setPosId(response.getField11_System_Trace_Audit_Number());
		report.setPosTime(response.getField13_Date_Of_Local_Transaction()+response.getField12_Time_Of_Local_Transaction());
		report.setProductType(response.getField62_Reserved_Private());
		report.setAmount(Long.valueOf(response.getField04_Amount_Of_Transactions()));
		report.setDevice(requestData.getDevice());
		report.setStatus((short) 1);
		report.setRequestId(requestData.getRequestId());
		reportMapper.save(report);

		// 记录交易记录中信银行数据
		TiReportDataZx reportData = new TiReportDataZx();
		reportData.setMerchantId(response.getField42_Card_Acceptor_ID());
		reportData.setReportId(report.getUuid());
		reportData.setBatchNo(response.getField60_Reserved_Private());
		reportDataMapper.save(reportData);
	}
}
