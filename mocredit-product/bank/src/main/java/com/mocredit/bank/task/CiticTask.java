package com.mocredit.bank.task;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.bank.constant.ReportStatus;
import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.TiPaymentReport;
import com.mocredit.bank.entity.TiShopMerchant;
import com.mocredit.bank.persistence.TiPaymentReportMapper;
import com.mocredit.bank.service.ShopMerchantService;
import com.mocredit.bank.service.impl.IntegralCiticServiceImpl;
import com.mocredit.bank.util.DateTimeUtils;
import com.mocredit.bank.util.Variable;
import com.mocredit.base.util.Banks;

/**
 * 中信银行定时任务
 * 
 * @author liaoying Created on 2015年8月25日
 *
 */
@Service
public class CiticTask {
	private static Logger log = LoggerFactory.getLogger(CiticTask.class);
	@Autowired
	private ShopMerchantService merchantService;
	@Autowired
	private IntegralCiticServiceImpl citicService;
	@Autowired
	private TiPaymentReportMapper reportMapper;

	/**
	 * 维护连接状态
	 * 
	 * @return
	 */
	public boolean maintainSession() {
		List<TiShopMerchant> allMerchants = merchantService.getMerchantsByBank(Banks.CITIC.getName());
		boolean flag = true;
		for (TiShopMerchant merchant : allMerchants) {
			Payment payment = new Payment();
			payment.setInfoType(Variable.ZX_INFO_TYPE_SESSION);
			payment.setMerchantID(merchant.getMerchantId());
			payment.setMerchantName(merchant.getMerchantName());
			payment.setToken(merchant.getToken());
			for (int i = 0; i < 5; i++) {
				try {
					String sessionResult = citicService.maintainSession(payment);
					if (Variable.OK.equals(sessionResult)) {
						break;
					} else {
						log.error("商户" + merchant.getTerminalId() + "第" + (i + 1) + "次session失败：" + sessionResult);
					}
				} catch (RemoteException | DocumentException e) {
					if (i < 4) {
						log.error("商户" + merchant.getTerminalId() + "第" + (i + 1) + "次session失败，200ms后重试");
						try {
							Thread.sleep(200);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else {
						log.error("商户" + merchant.getTerminalId() + "最终session失败");
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 对账
	 */
	public void checkAccount() {
		List<TiShopMerchant> selectValidMerchants = merchantService.getMerchantsByBank(Banks.CITIC.getName());
		for (TiShopMerchant merchant : selectValidMerchants) {
			Map<String, Object> param = new HashMap<>();
			param.put("merchantId", merchant.getMerchantId());
			param.put("status", ReportStatus.PAYED.getStatus());
			param.put("tranDate", DateTimeUtils.getDate("yyyyMMdd"));
			param.put("limitMin", 0);
			param.put("limitCount", 20);
			while (true) {
				List<Payment> reports = reportMapper.selectByMerchantId(param);
				if (reports.isEmpty()) {
					break;
				}
				Set<Integer> successIds = citicService.checkAccount(reports);
				/* 记录对账结果 */
				for (Payment payment : reports) {
					TiPaymentReport record = new TiPaymentReport();
					record.setUuid(payment.getId());
					record.setStatus(successIds.contains(payment.getId()) ? ReportStatus.CHECK_ACCOUNT_YES.getStatus()
							: ReportStatus.CHECK_ACCOUNT_NO.getStatus());
					reportMapper.updateStatus(record);
				}
				param.put("limitMin", Integer.parseInt(param.get("limitMin").toString()) + reports.size());
			}

		}

	}
}
