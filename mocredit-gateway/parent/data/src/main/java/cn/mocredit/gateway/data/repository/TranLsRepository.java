package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.TranLs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface TranLsRepository extends CrudRepository<TranLs, String> {

}
