package cn.mocredit.gateway.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cn.mocredit.gateway.data.entities.CheckCodeRecord;

@Service
public interface CheckCodeRecordRepository extends CrudRepository<CheckCodeRecord, String> {

	@Query(value = "SELECT * FROM check_code_record WHERE batchno = ?1", nativeQuery = true)
	CheckCodeRecord getCheckCodeRecordByBatchno(String batchno);
}
