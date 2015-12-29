package com.mocredit.gateway.service;

import com.mocredit.gateway.entity.Activity;
import com.mocredit.gateway.vo.ActivityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 活动业务层
 *
 * @author ytq
 */

public interface ActivityService extends BaseService<Activity> {

    /**
     * 根据code查询活动
     *
     * @param code
     * @return
     */
    Activity getByActivityCode(String code);


    ActivityVo getActivityById(Integer id);

    /**
     * 查询活动id的批次列表
     *
     * @param pageNum  页数
     * @param pageSize 页面数量
     * @return
     */
    List<Activity> queryActivityPage(Map<String, Object> activityMap, Integer draw, Integer pageNum, Integer pageSize);

    /**
     * 根据活动id更新活动信息
     *
     * @param activity
     * @return
     */
    boolean updateActivityById(Activity activity);

}
