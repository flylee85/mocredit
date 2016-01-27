package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.Device;
import cn.mocredit.gateway.data.entities.OptLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceRepository extends CrudRepository<Device, String> {

    @Query(value = "SELECT * FROM device WHERE devcodemd5=?1", nativeQuery = true)
    List<Device> getDeviceByMD5(String devcodemd5);
    @Query(value = "SELECT * FROM device WHERE id=?1", nativeQuery = true)
    List<Device> getDeviceById(String id);
    @Query(value = "SELECT * FROM device WHERE devcode=?1", nativeQuery = true)
    List<Device> getDeviceByEn(String en);
    @Query(value = "SELECT '每隔一段时间连接一下网关数据库' ", nativeQuery = true)
    Object keepJdbcContected();
    @Query(value = "SELECT '启动后立刻连接一下网关数据库' ", nativeQuery = true)
    Object contecteNow();

}
