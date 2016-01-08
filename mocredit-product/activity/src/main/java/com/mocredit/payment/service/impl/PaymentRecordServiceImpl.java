package com.mocredit.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.payment.model.PaymentRecord;
import com.mocredit.payment.persitence.PaymentRecordMapper;
import com.mocredit.payment.service.PaymentRecordService;

@Service
public class PaymentRecordServiceImpl implements PaymentRecordService{
	@Autowired
	private PaymentRecordMapper paymentRecordMapper;

	public long savePaymentRecord(PaymentRecord record) {
		return paymentRecordMapper.savePaymentRecord(record);
	}
}
