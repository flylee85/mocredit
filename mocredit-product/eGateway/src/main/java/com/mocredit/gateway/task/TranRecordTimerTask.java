package com.mocredit.gateway.task;

import com.mocredit.base.util.DateUtil;
import com.mocredit.gateway.service.TranRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ytq on 2015/12/23.
 */
public class TranRecordTimerTask {
    public final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("TranRecordTimerTask");
    @Autowired
    private TranRecordService tranRecordService;

    public void exec() {
        LOGGER.info("### TranRecordTimerTask run ###");
        tranRecordService.updateByExpireDate(DateUtil.getCurDate());
        LOGGER.info("### TranRecordTimerTask stop ###");
    }
}
