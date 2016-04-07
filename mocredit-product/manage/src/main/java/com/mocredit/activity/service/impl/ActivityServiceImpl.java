package com.mocredit.activity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.activity.model.Activity;
import com.mocredit.activity.persitence.ActivityMapper;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;

/**
 * 活动-Service映射层 活动管理的Service类，所有活动管理相关的后台处理方法，都是在此类中
 *
 * @author lishoukun
 * @date 2015/07/08
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;// 活动dao对象

    @Override
    public PageInfo<Activity> queryActivityPage(Map<String, Object> activityMap, Integer draw, Integer pageNum,
                                                Integer pageSize) {
        if (draw != null && draw != 1) {
            String searchContent = String.valueOf(activityMap.get("search[value]"));
            if (searchContent != null && !"".equals(searchContent)) {
                activityMap.put("searchInfoContent", searchContent);
            }
//            String orderContent = String.valueOf(
//                    activityMap.get("columns[" + String.valueOf(activityMap.get("order[0][column]")) + "][name]"));
//            String orderDir = String.valueOf(activityMap.get("order[0][dir]"));
            String orderContent = null;
            String orderDir = null;
            if (orderContent != null && !"".equals(orderContent) && orderDir != null && !"".equals(orderDir)) {
                activityMap.put("orderInfoContent", orderContent);
                activityMap.put("orderInfoDir", orderDir);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> activityList = activityMapper.queryActivityList(activityMap);
        PageInfo<Activity> page = new PageInfo<Activity>(activityList);
        // 获取分页对象
        // 拼装存放活动id的list
        List<String> idList = new ArrayList<String>();
        for (Activity act : activityList) {
            idList.add(act.getId());
        }
        return page;
    }

}
