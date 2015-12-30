package com.mocredit.gateway.persistence;

import com.mocredit.gateway.entity.Activity;
import com.mocredit.gateway.vo.ActivityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ytq
 */
public interface ActivityMapper {
    /**
     * 保存活动信息
     *
     * @param t
     * @return
     */
    int save(@Param(value = "t") Activity t);

    /**
     * 根据code查询活动
     *
     * @param code
     * @return
     */
    Activity getByActivityCode(@Param("code") String code);

    ActivityVo getActivityById(@Param("id") Integer id);

    /**
     * 查询活动列表
     *
     * @param batchMap
     * @return
     */
    List<Activity> queryActivityPage(Map<String, Object> batchMap);

    /**
     * 根据活动id更新活动信息
     *
     * @param t
     * @return
     */
    int updateActivityById(@Param(value = "t") Activity t);
}
