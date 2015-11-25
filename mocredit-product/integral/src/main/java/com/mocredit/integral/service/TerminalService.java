package com.mocredit.integral.service;

import com.mocredit.integral.entity.Terminal;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ytq on 2015/11/17.
 */
public interface TerminalService {
    /**
     * 根据机具号和活动id查询门店id
     *
     * @param enCode
     * @param activityId
     * @return
     */
    Terminal getTerminalByEnCodeAndActivityId(String enCode, String activityId);
}
