package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.SysCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface SysCodeRepository extends CrudRepository<SysCode, String> {

}
