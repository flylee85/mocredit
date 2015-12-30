package com.mocredit.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.IpUtil;
import com.mocredit.base.web.BaseController;
import com.mocredit.gateway.entity.InRequestLog;
import com.mocredit.gateway.entity.InResponseLog;
import com.mocredit.gateway.entity.Record;
import com.mocredit.gateway.service.InRequestLogService;
import com.mocredit.gateway.service.InResponseLogService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeMap;

public class EGatewayBaseController extends BaseController {
    @Autowired
    private InResponseLogService inResponseLogService;
    @Autowired
    private InRequestLogService inRequestLogService;

    public static String getIpAddr(HttpServletRequest request) {
        return IpUtil.getIp(request);
    }

    /**
     * 保存请求报文日志
     *
     * @param request
     * @param orderId
     * @param param
     */
    public void saveInRequestLog(HttpServletRequest request, String orderId,
                                 String param) {
        InRequestLog inRequestLog = new InRequestLog();
        inRequestLog.setIp(getIpAddr(request));
        inRequestLog.setRequest(param);
        inRequestLog.setOrderId(orderId);
        inRequestLog.setUrl(request.getRequestURI());
        inRequestLogService.save(inRequestLog);
        request.setAttribute("request_id", inRequestLog.getId());
    }

    /**
     * 保存响应信息
     *
     * @param requestId
     * @param jsonStr
     */
    public void saveResponseLog(Integer requestId, String jsonStr) {
        inResponseLogService.save(new InResponseLog(requestId, jsonStr));
    }

    protected String renderJSONString(boolean success, String errorMsg,
                                      String errorCode, Object data) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("success", success);
        map.put("errorMsg", errorMsg);
        map.put("errorCode", errorCode);
        if (null == data) {
            data = "";
        }
        map.put("data", data);
        String jsonStr = JSON.toJSONString(map);
        saveResponseLog(getRequestId(), jsonStr);
        return jsonStr;
    }


    /**
     * 获取request_id
     *
     * @return
     */
    public Integer getRequestId() {
        String requestId = (null == request.getAttribute("request_id") ? "0"
                : request.getAttribute("request_id").toString());
        return Integer.valueOf(requestId);
    }

    protected void setRecordRequestId(Record record) {
        record.setRequestId(getRequestId());
    }
}
