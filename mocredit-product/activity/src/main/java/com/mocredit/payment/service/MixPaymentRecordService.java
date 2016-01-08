package com.mocredit.payment.service;

import org.springframework.stereotype.Service;

import com.mocredit.payment.model.MixPaymentRecord;

@Service
public interface MixPaymentRecordService {

	long saveMixPaymentRecord(MixPaymentRecord record);
}
