package com.mocredit.gateway.service.impl;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.gateway.entity.Activity;
import com.mocredit.gateway.persistence.ActivityMapper;
import com.mocredit.gateway.service.ActivityService;
import com.mocredit.gateway.service.LogService;
import com.mocredit.gateway.vo.ActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ytq
 */
@Service
public class ActivityServiceImpl extends LogService implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public boolean save(Activity t) {
        return activityMapper.save(t) > 0;
    }

    @Override
    public Activity getByActivityCode(String code) {
        return activityMapper.getByActivityCode(code);
    }

    @Override
    public ActivityVo getActivityById(Integer id) {
        return activityMapper.getActivityById(id);
    }

    @Override
    public List<Activity> queryActivityPage(Map<String, Object> activityMap, Integer draw, Integer pageNum, Integer pageSize) {
        if (draw != null && draw != 1) {
            String searchContent = String.valueOf(activityMap.get("search[value]"));
            if (searchContent != null && !"".equals(searchContent)) {
                activityMap.put("searchInfoContent", searchContent);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        return activityMapper.queryActivityPage(activityMap);
    }

    @Override
    public boolean updateActivityById(Activity activity) {
        return activityMapper.updateActivityById(activity) > 0;
    }
}
