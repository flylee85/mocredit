package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface StoreRepository extends CrudRepository<Store, String> {

}
