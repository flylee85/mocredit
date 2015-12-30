package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.ChongZheng;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface ChongZhengRepository extends CrudRepository<ChongZheng, String> {

}
