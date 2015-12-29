package com.mocredit.integral.task;

import com.mocredit.base.util.DateUtil;
import com.mocredit.integral.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ytq on 2015/12/23.
 */
public class TranRecordTimerTask {
    public final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("TranRecordTimerTask");
    @Autowired
    private ActivityService activityService;

    public void exec() {
        LOGGER.info("### TranRecordTimerTask run ###");
        activityService.updateByExpireDate(DateUtil.getCurDate());
        LOGGER.info("### TranRecordTimerTask stop ###");
    }
}
