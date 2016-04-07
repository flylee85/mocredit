package com.mocredit.activity.task;

import com.alibaba.fastjson.JSONObject;
import com.mocredit.activity.model.Activity;
import com.mocredit.activity.service.ActivityService;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by ytq on 2015/12/23.
 */
public class ActivityStatTimerTask {
    public final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("ActivityStatTimerTask");
    @Autowired
    private ActivityService activityService;

    public void exec() {
        String statActCodeUrl = PropertiesUtil.getValue("verifyCode.statActCode");
        String statActIntegralUrl = PropertiesUtil.getValue("integral.statActIntegral");
        LOGGER.info("### ActivityStatTimerTask run ###");
        for (Activity activity : activityService.queryActivityList(new HashMap<String, Object>() {{
//            put("status", "01");
        }})) {
            try {
                String param = "{\"activityId\":\":actId\"}".replace(":actId", activity.getId());
                String url = null;
                if ("02".equals(activity.getType())) {
                    url = statActCodeUrl;
                }
                if ("01".equals(activity.getType())) {
                    url = statActIntegralUrl;
                }
                String returnJson = HttpUtil.doRestfulByHttpConnection(url, param);
                if (StringUtils.isNotBlank(returnJson)) {
                    AjaxResponseData ajaxResponseData = JSONObject.parseObject(returnJson, AjaxResponseData.class);
                    if (ajaxResponseData.getData() != null) {
                        Activity act = JSONObject.parseObject(ajaxResponseData.getData() + "", Activity.class);
                        act.setId(activity.getId());
                        activityService.saveAndUpdAct(act);
                        LOGGER.info("### ActivityStatTimerTask activityName={} ###", activity.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("### ActivityStatTimerTask stop ###");
    }
}
