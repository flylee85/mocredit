package com.mocredit.sendcode;

import org.apache.log4j.Logger;

/**
 * Created by ytq on 2015/10/23.
 * 发码组件基本接口
 */
public interface BaseService {
    Logger logger = Logger.getLogger("send_code_log");
    Integer pageSize = 1000;
}
