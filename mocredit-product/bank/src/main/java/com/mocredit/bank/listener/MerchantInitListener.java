package com.mocredit.bank.listener;

import java.rmi.RemoteException;
import java.util.List;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.TiShopMerchant;
import com.mocredit.bank.service.ShopMerchantService;
import com.mocredit.bank.service.impl.IntegralCiticServiceImpl;
import com.mocredit.bank.util.DateTimeUtils;
import com.mocredit.bank.util.Variable;
import com.mocredit.base.util.Banks;

/**
 * 初始化商户
 * 
 * @author liaoying Created on 2015年8月25日
 *
 */
 @Service
public class MerchantInitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger log = LoggerFactory.getLogger(MerchantInitListener.class);
	@Autowired
	private ShopMerchantService shopMerchantService;
	@Autowired
	private IntegralCiticServiceImpl  citicService;


	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		List<TiShopMerchant> allMerchants = shopMerchantService.getMerchantsByBank(Banks.CITIC);
		for (TiShopMerchant merchant : allMerchants) {
			Payment payment = new Payment();
			payment.setInfoType(Variable.ZX_INFO_TYPE_LOGIN);
			payment.setTerminalID(merchant.getTerminalId());
			payment.setPosID("000001");
			payment.setPosTime(DateTimeUtils.getDate("yyyyMMddHHmmss"));
			payment.setMerchantType(Variable.ZXMERCHANTTYPE);
			payment.setMerchantID(merchant.getMerchantId());
			payment.setMerchantName(merchant.getMerchantName());
			payment.setPassword(merchant.getMerchantPassword());
			for (int i = 0; i < 5; i++) {
				try {
					String login = citicService.login(payment);
					if (Variable.OK == login) {
						merchant.setToken(payment.getToken());
						shopMerchantService.updateToken(merchant);
						break;
					}
				} catch (RemoteException | DocumentException e) {
					if (i < 4) {
						log.error("商户" + merchant.getTerminalId() + "第" + (i + 1) + "次登录失败，稍后重试");
						try {
							Thread.sleep(200);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else {
						log.error("商户" + merchant.getTerminalId() + "最终登录失败");
					}
				}
			}
		}
	}
}
