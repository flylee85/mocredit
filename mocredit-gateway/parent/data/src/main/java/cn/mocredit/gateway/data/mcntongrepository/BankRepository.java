package cn.mocredit.gateway.data.mcntongrepository;

import cn.mocredit.gateway.data.mcntongentities.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface BankRepository extends CrudRepository<Bank, Long> {
    @Query(value = "SELECT 'ÿ��һ��ʱ������һ���������ݿ�' ", nativeQuery = true)
    Object keepJdbcContected();
    @Query(value = "SELECT '��������������һ���������ݿ�' ", nativeQuery = true)
    Object contecteNow();
}
