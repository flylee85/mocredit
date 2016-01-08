package com.mocredit.payment.persitence;

import com.mocredit.payment.model.PaymentRecord;

public interface PaymentRecordMapper {
	long savePaymentRecord(PaymentRecord record);
}
