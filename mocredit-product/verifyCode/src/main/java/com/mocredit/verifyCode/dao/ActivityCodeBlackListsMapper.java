package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.ActivityCodeBlackLists;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 验码黑名单的mapper
 * Created by YHL on 15/7/19 08:20.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface ActivityCodeBlackListsMapper {

    /**
     *
     * 根据活动ID，将活动券码加入到黑名单。
     * <pre>
     *     从 券码表，根据活动ID获取到券码，然后插入到黑名单表
     * </pre>
     * @param activity_id 活动主键
     * @param type        加入黑名单的类型
     * @param desc        类型的文本描述
     * @return
     */
    public int addBlackListsByActivityId(@Param("activity_id") String activity_id,@Param("type")int type,@Param("desc")String desc);

    /**
     * 根据券码号，获取券码在黑名单中的记录。
     * @param code
     * @return
     */
    public List<ActivityCodeBlackLists> findBlackListsByCode(@Param("code")String code);


    /**
     * 根据活动ID，删除黑名单中的 券码。
     * @param activity_id
     * @return
     */
    public int deleteBlackListsByActivityId(@Param("activity_id") String activity_id);
}
