package com.mocredit.payment.service;

import org.springframework.stereotype.Service;

import com.mocredit.payment.model.PaymentRecord;

@Service
public interface PaymentRecordService {
	long savePaymentRecord(PaymentRecord record);
}
