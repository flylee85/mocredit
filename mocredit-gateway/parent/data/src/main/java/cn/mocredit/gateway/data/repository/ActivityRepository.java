package cn.mocredit.gateway.data.repository;

import cn.mocredit.gateway.data.entities.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityRepository extends CrudRepository<Activity, String> {

    @Query(value = "SELECT * FROM activity WHERE activitId in ?1", nativeQuery = true)
    List<Activity> getByActivitId(List<String> ids);
    @Query(value = "SELECT * FROM activity WHERE eitemid in ?1", nativeQuery = true)
    List<Activity> getByEitemId(List<String> ids);
    @Query(value = "SELECT * FROM activity WHERE activitId = ?1", nativeQuery = true)
    Activity getOneByActivitId(String activitId);

}
