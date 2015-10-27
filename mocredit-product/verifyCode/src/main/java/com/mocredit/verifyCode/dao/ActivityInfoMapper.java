package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.TActivityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动基本信息的 mapper
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface ActivityInfoMapper {

    /**
     * 插入一条活动的基本信息
     * @param activityInfo
     * @return
     */
    public int save(TActivityInfo activityInfo);

    /**
     * 根据 主键删除对象
     * @param id
     * @return
     */
    public int delete(@Param("id")String  id);

    /**
     * 根据活动ID，删除活动信息
     * @param activity_id
     * @return
     */
    public int deleteByActivityId(@Param("activity_id")String activity_id);

    /**
     * 根据活动ID 获取到活动具体信息
     * @param activity_id
     * @return
     */
    public List<TActivityInfo> findByActivityId(@Param("activity_id")String activity_id);

}
