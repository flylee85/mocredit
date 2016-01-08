package com.mocredit.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.payment.model.MixPaymentRecord;
import com.mocredit.payment.persitence.MixPaymentRecordMapper;
import com.mocredit.payment.service.MixPaymentRecordService;

@Service
public class MixPaymentRecordServiceImpl implements MixPaymentRecordService {

	@Autowired
	private MixPaymentRecordMapper mixPaymentRecordMapper;

	public long saveMixPaymentRecord(MixPaymentRecord record) {
		return mixPaymentRecordMapper.saveMixPaymentRecord(record);
	}
}
