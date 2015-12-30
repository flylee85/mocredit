package com.mocredit.recharge.persitence;

import com.mocredit.recharge.model.Record;

public interface RecordMapper {
	int save(Record record);
	int update(Record record);
}
