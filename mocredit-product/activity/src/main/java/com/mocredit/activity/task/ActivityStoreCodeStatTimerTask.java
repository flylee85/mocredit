package com.mocredit.activity.task;

import com.alibaba.fastjson.JSONObject;
import com.mocredit.activity.model.ActivityStoreStat;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ytq on 2015/12/23.
 */
public class ActivityStoreCodeStatTimerTask {
    public final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("ActivityStoreCodeStatTimerTask");
    @Autowired
    private ActivityService activityService;
    private final static Integer PAGE_SIZE = 1000;

    public void exec() {
        String codeUrl = PropertiesUtil.getValue("verifyCode.statActStoreCode");
        String integralUrl = PropertiesUtil.getValue("integral.statActStoreIntegral");
        LOGGER.info("### ActivityStoreStatTimerTask run ###");
        Integer pageNum = 1;
        while (true) {
            LOGGER.info("### pageNum={} ###", pageNum);
            PageInfo<ActivityStoreStat> pageInfo = activityService.findActStoreAll(pageNum, PAGE_SIZE);
            if (pageInfo.getList().isEmpty()) {
                break;
            } else {
                List<ActivityStoreStat> activityStoreStatList = pageInfo.getList();
                for (ActivityStoreStat activityStoreStat : activityStoreStatList) {
                    try {
                        String url = null;
                        if ("02".equals(activityStoreStat.getActivityType())) {
                            url = codeUrl;
                        }
                        if ("01".equals(activityStoreStat.getActivityType())) {
                            url = integralUrl;
                        }
                        if ("02".equals(activityStoreStat.getActivityType())) {
                            String param = "{\"activityId\":\":actId\",\"storeId\":\":storeId\"}".replace(":actId", activityStoreStat.getActivityId()).replace(":storeId", activityStoreStat.getStoreId());
                            if (null != url) {
                                String returnJson = HttpUtil.doRestfulByHttpConnection(url, param);
                                if (StringUtils.isNotBlank(returnJson)) {
                                    AjaxResponseData ajaxResponseData = JSONObject.parseObject(returnJson, AjaxResponseData.class);
                                    if (ajaxResponseData.getData() != null) {
                                        ActivityStoreStat actStoreStat = JSONObject.parseObject(ajaxResponseData.getData() + "", ActivityStoreStat.class);
                                        actStoreStat.setActivityId(activityStoreStat.getActivityId());
                                        actStoreStat.setStoreId(activityStoreStat.getStoreId());
                                        actStoreStat.setMerchantName(activityStoreStat.getMerchantName());
                                        actStoreStat.setStoreName(activityStoreStat.getStoreName());
                                        actStoreStat.setActivityType(activityStoreStat.getActivityType());
                                        activityService.saveAndUpdActStore(actStoreStat);
                                        LOGGER.info("### ActivityStoreCodeStatTimerTask activityId={} storeId={} ###", actStoreStat.getActivityId(), activityStoreStat.getStoreId());
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pageNum += 1;
            }
        }
        LOGGER.info("### ActivityStoreCodeStatTimerTask stop ###");
    }
}
