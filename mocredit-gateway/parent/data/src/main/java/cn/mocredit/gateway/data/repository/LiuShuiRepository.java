package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.Device;
import cn.mocredit.gateway.data.entities.LiuShui;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LiuShuiRepository extends CrudRepository<LiuShui, String> {

    @Query(value = "SELECT * FROM `liushui` WHERE devcode =?1 ORDER BY batchno desc,searchno desc LIMIT 0,1", nativeQuery = true)
    LiuShui getLastLiuShui(String devcodemd5);

    @Query(value = "SELECT * FROM liushui WHERE wangposorderid =?1", nativeQuery = true)
    LiuShui getLiuShuiByWangposorderid(String wangposorderid);

}
