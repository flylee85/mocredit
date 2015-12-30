package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.OptLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface OptLogRepository extends CrudRepository<OptLog, String> {

}
