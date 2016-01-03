package cn.mocredit.gateway.data.mcntongrepository;

import cn.mocredit.gateway.data.mcntongentities.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface BankRepository extends CrudRepository<Bank, Long> {
    @Query(value = "SELECT '每隔一段时间连接一下网关数据库' ", nativeQuery = true)
    Object keepJdbcContected();
    @Query(value = "SELECT '启动后立刻连接一下网关数据库' ", nativeQuery = true)
    Object contecteNow();
}
