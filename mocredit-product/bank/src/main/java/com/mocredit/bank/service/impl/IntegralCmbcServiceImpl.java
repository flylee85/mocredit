package com.mocredit.bank.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.bank.constant.ReportStatus;
import com.mocredit.bank.constant.RespError;
import com.mocredit.bank.datastructure.ResponseData;
import com.mocredit.bank.entity.MessageObject;
import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.RequestData;
import com.mocredit.bank.entity.TiPaymentReport;
import com.mocredit.bank.entity.TiReportDataZx;
import com.mocredit.bank.entity.TiShopMerchant;
import com.mocredit.bank.persistence.TiPaymentReportMapper;
import com.mocredit.bank.persistence.TiReportDataZxMapper;
import com.mocredit.bank.persistence.TiShopMerchantMapper;
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
@Service("cmbcService")
public class IntegralCmbcServiceImpl implements IntegralService {
	private static Logger logger = Logger.getLogger(IntegralCmbcServiceImpl.class);
	private static Map<Integer, TiShopMerchant> merchantsCache = new HashMap<>();
	@Autowired
	private PosAction posAction;
	@Autowired
	private TiShopMerchantMapper merchantMapper;
	@Autowired
	private TiPaymentReportMapper reportMapper;
	@Autowired
	private TiReportDataZxMapper reportDataMapper;

	@Override
	public ResponseData payment(RequestData requestData) {
		TiShopMerchant merchant = getMerchantByShopId(requestData.getShopId());
		if (null == merchant) {
			return new ResponseData(RespError.INVALID_SHOP.getErrorCode(), RespError.INVALID_SHOP.getErrorMsg(), false);
		}
		MessageObject request = ServiceUtil.getPayMessage(requestData, merchant);
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
		// 查询交易记录以及记录银行数据
		TiPaymentReport report = reportMapper.selectByOrderId(requestData.getOrderId());
		TiReportDataZx reportData = reportDataMapper.selectByReportId(report.getUuid());
		TiShopMerchant merchant = getMerchantByMerchantId(reportData.getMerchantId());
		if (null == merchant) {
			return new ResponseData(RespError.INVALID_SHOP.getErrorCode(), RespError.INVALID_SHOP.getErrorMsg(), false);
		}
		MessageObject request = ServiceUtil.getPayReservalMessage(report, reportData,merchant);
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
			/* 成功后修改交易状态 */
			report.setStatus(ReportStatus.REVERSAL.getStatus());
			reportMapper.updateStatus(report);
			return new ResponseData(null, null, true);
		}
		return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
	}

	@Override
	public ResponseData paymentRevoke(RequestData requestData) {
		TiShopMerchant merchant = getMerchantByShopId(requestData.getShopId());
		if (null == merchant) {
			return new ResponseData(RespError.INVALID_SHOP.getErrorCode(), RespError.INVALID_SHOP.getErrorMsg(), false);
		}
		// 查询交易记录以及记录银行数据
		TiPaymentReport report = reportMapper.selectByOrderId(requestData.getOrderId());
		TiReportDataZx reportData = reportDataMapper.selectByReportId(report.getUuid());

		MessageObject request = ServiceUtil.getPayRevokeMessage(report, reportData, merchant);
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
			/* 成功后修改交易状态 */
			report.setStatus(ReportStatus.REVOKE.getStatus());
			reportMapper.updateStatus(report);
			return new ResponseData(null, null, true);
		}
		return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
	}

	@Override
	public ResponseData paymentRevokeReversal(RequestData requestData) {
		// 查询交易记录以及记录银行数据
		TiPaymentReport report = reportMapper.selectByOrderId(requestData.getOrderId());
		TiShopMerchant merchant = getMerchantByShopId(requestData.getShopId());
		if (null == merchant) {
			return new ResponseData(RespError.INVALID_SHOP.getErrorCode(), RespError.INVALID_SHOP.getErrorMsg(), false);
		}
		TiReportDataZx reportData = reportDataMapper.selectByReportId(report.getUuid());
		/* 交易已撤销成功， 调用撤销冲正 */
		if (ReportStatus.REVOKE.getStatus() == report.getStatus()) {
			report.setPosId(merchant.getPosNo());
			MessageObject request = ServiceUtil.getPayRevokeReversalMessage(report, reportData);// TODO
																								// POSNO和授权码需要从银行接口日志中解析获取
			MessageObject response = null;
			try {
				response = posAction.messReceived(request);
				logger.info("msyhResponse[" + response + "]");
			} catch (FormatException e) {
				return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
			} catch (Exception e) {
				return new ResponseData(RespError.SYETEM_ERROR.getErrorCode(), RespError.SYETEM_ERROR.getErrorMsg(),
						false);
			}
			// 返回码判断成功与否
			if ("00".equals(response.getField39_Response_Code())) {
				/* 成功后修改交易状态 */
				report.setStatus(ReportStatus.REVOKE_REVERSAL.getStatus());
				reportMapper.updateStatus(report);
				return new ResponseData(null, null, true);
			}
		}
		return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
	}

	@Override
	public ResponseData confirmInfo(RequestData requestData) {
		TiShopMerchant merchant = merchantMapper.selectByShopId(requestData.getShopId());
		if (null == merchant) {
			return new ResponseData(RespError.INVALID_SHOP.getErrorCode(), RespError.INVALID_SHOP.getErrorMsg(), false);
		}
		MessageObject request = ServiceUtil.getPayMessage(requestData, merchant);// TODO
		MessageObject response = null;
		try {
			response = posAction.messReceived(request);
			logger.info("msyhResponse[" + response + "]");
		} catch (FormatException e) {
			return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
		} catch (Exception e) {
			return new ResponseData(RespError.SYETEM_ERROR.getErrorCode(), RespError.SYETEM_ERROR.getErrorMsg(), false);
		}
		Map<String, Object> data = new HashMap<>();
		// 返回码判断成功与否
		if ("00".equals(response.getField39_Response_Code())) {
			// POS流水号+1
			merchant.setPosNo(String.valueOf(Integer.valueOf(merchant.getPosNo()) + 1));
			data.put("integral", Long.parseLong(response.getField62_Reserved_Private()));// TODO
			data.put("changeFlag", true);
			ResponseData responseData = new ResponseData(null, null, true);
			responseData.setData(data);
			return responseData;
		} else {
			data.put("integral", 0);
			return new ResponseData(RespError.PAY_ERROR.getErrorCode(), RespError.PAY_ERROR.getErrorMsg(), false);
		}
	}

	@Override
	public Set<Integer> checkAccount(List<Payment> records) {
		// TODO Auto-generated method stub
		return null;
	}

	private void savePaymentReport(RequestData requestData, MessageObject response) {
		// 记录交易记录
		TiPaymentReport report = new TiPaymentReport();
		report.setBank(Banks.CITIC.getName());
		report.setTerminalId(response.getField41_Card_Acceptor_Terminal_ID());
		report.setOrderId(requestData.getOrderId());
		report.setCardNum(response.getField02_Primary_Account_Number());
		report.setPosId(response.getField11_System_Trace_Audit_Number());
		report.setPosTime(
				response.getField13_Date_Of_Local_Transaction() + response.getField12_Time_Of_Local_Transaction());
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
		reportData.setAuthorizeCode(response.getField38_Authorization_Identification_Response());
		reportData.setRetrievalNo(response.getField37_Retrieval_Reference_Number());
		reportData.setBatchNo(response.getField60_Reserved_Private().substring(2));
		reportDataMapper.save(reportData);
	}

	/**
	 * 获取指定商户的商户号
	 * 
	 * @param shopId
	 * @return
	 */
	public TiShopMerchant getMerchantByShopId(int shopId) {
		TiShopMerchant tiShopMerchant = merchantsCache.get(shopId);
		if (null == tiShopMerchant) {
			tiShopMerchant = merchantMapper.selectByShopId(shopId);
			if(null!=tiShopMerchant){
				merchantsCache.put(shopId, tiShopMerchant);
			}
		}
		return tiShopMerchant;
	}
	/**
	 * 获取指定商户的商户号
	 * 
	 * @param shopId
	 * @return
	 */
	public TiShopMerchant getMerchantByMerchantId(String merchantId) {
		for(java.util.Map.Entry<Integer,TiShopMerchant> merchant:merchantsCache.entrySet()){
			if(merchant.getValue().getMerchantId().equals(merchantId)){
				return merchant.getValue();
			}
		}
		Map<String, Object> columns=new HashMap<String, Object>();
		columns.put("merchant_id", merchantId);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("params", columns);
		List<TiShopMerchant> merchants = merchantMapper.selectMerchantsByColumn(params);
		if(null==merchants||merchants.size()==0){
			return null;
		}
		TiShopMerchant merchant = merchants.get(0);
		merchantsCache.put(merchant.getShopId(), merchant);
		return merchant;
	}
	public void clearMerchants() {
		merchantsCache.clear();
	}
}
