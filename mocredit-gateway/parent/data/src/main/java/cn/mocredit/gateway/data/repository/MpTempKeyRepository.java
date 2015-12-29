package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.MpTempKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface MpTempKeyRepository extends CrudRepository<MpTempKey, String> {
	
	

}
