package com.mocredit.payment.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.payment.model.MixPaymentRecord;
import com.mocredit.payment.model.PaymentRecord;
import com.mocredit.payment.service.MixPaymentRecordService;
import com.mocredit.payment.service.PaymentRecordService;
import com.mocredit.payment.vo.ShoudanBo;

@RestController
@RequestMapping("/payment/")
public class PaymentController {
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	private MixPaymentRecordService mixPaymentRecordService;

	@RequestMapping("/paymentSync")
	@ResponseBody
	public String paymentSync(@RequestBody String param) {
		System.out.println(param);
		ShoudanBo bo = JSON.parseObject(param, ShoudanBo.class);
		if (bo == null || param == null) {
			return "1";
		}
		String orderId = bo.orderId;
		try {
			PaymentRecord record = bo.orderInfo.toPaymentRecord();
			paymentRecordService.savePaymentRecord(record);
			if (orderId != null && !"".equals(orderId)) {// 混合支付
				MixPaymentRecord mixRecord = new MixPaymentRecord();
				mixRecord.setOrderId(orderId);
				mixRecord.setPaymentId(record.getMachOrderId());
				mixPaymentRecordService.saveMixPaymentRecord(mixRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "0";
	}

	public static void main(String[] args) throws IOException {
		String json = "{cardBank:'1',machOrderId:'2',referId:'3',transactionTime:'4',amount:'5',"
				+ "signatureString:'6',cardValidateData:'7',authorizationNo:'8',status:'9',"
				+ "cardId:'10',pinpadId:'11',merchantId:'12',goodsDetail:'13',goodsName:'14',"
				+ "goodsType:'15',ownerName:'16',incomeBankId:'17',transSeq:'18',merchantNumber:'19'}";
		String url = "http://localhost:8080/activitysys/payment/paymentSync?json=" + json + "&orderId=1";
		System.out.println(HttpUtil.sendGet(url, "utf-8"));
	}
}
