package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.JieSuan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface JieSuanRepository extends CrudRepository<JieSuan, String> {

}
