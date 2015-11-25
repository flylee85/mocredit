package com.mocredit.integral.controller;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.mocredit.integral.adapter.IntegralBankAdapter;
import com.mocredit.integral.entity.Terminal;
import com.mocredit.integral.vo.OrderVo;
import com.mocredit.integral.vo.TerminalVo;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.IpUtil;
import com.mocredit.base.web.BaseController;
import com.mocredit.integral.entity.InResponseLog;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.HttpRequestService;
import com.mocredit.integral.service.InResponseLogService;
import com.mocredit.integral.util.Utils;
import com.mocredit.integral.vo.ConfirmInfoVo;

public class IntegralBaseController extends BaseController {
    @Autowired
    private InResponseLogService inResponseLogService;
    @Autowired
    private HttpRequestService httpRequstService;

    public static String getIpAddr(HttpServletRequest request) {
        return IpUtil.getIp(request);
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
        saveReponseLog(getRequestId(), jsonStr);
        return jsonStr;
    }

    /**
     * 保存响应信息
     * @param requestId
     * @param jsonStr
     */
    public void saveReponseLog(Integer requestId, String jsonStr) {
        inResponseLogService.save(new InResponseLog(requestId, jsonStr));
    }

    /**
     * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
     *
     * @param param
     * @param order
     * @param resp
     * @return
     */
    protected boolean doPostJsonAndSaveOrder(String param,
                                             Order order, Response resp) {
        return httpRequstService.doPostJsonAndSaveOrder(getRequestId(), param, order, resp);
    }

    /**
     * 请求bank接口如果成功就保存订单并返回true，否则不保存订单返回false
     *
     * @param param
     * @param order
     * @param resp
     * @return
     */
    protected boolean doPostJsonAndSaveOrderForOld(String param,
                                                   Order order, Response resp) {
        return httpRequstService.doPostJsonAndSaveOrderForOld(getRequestId(), param, order, resp);
    }

    /**
     * @param param
     * @param resp
     * @return
     */
    protected boolean paymentRevokeJson(String param, OrderVo orderVo, Response resp) {
        return httpRequstService.paymentRevokeJson(getRequestId(), param, orderVo, resp);
    }


    /**
     * 积分冲正
     *
     * @param param
     * @return
     */
    protected boolean paymentReservalJson(String param, OrderVo orderVo,
                                          Response resp) {
        return httpRequstService.paymentReservalJson(getRequestId(), param, orderVo, resp);
    }

    /**
     * 积分撤销冲正
     *
     * @param param
     * @param resp
     * @return
     */
    protected boolean paymentRevokeReservalJson(String param, OrderVo orderVo,
                                                Response resp) {
        return httpRequstService.paymentRevokeReservalJson(getRequestId(), param, orderVo, resp);
    }

    /**
     * 积分查询
     *
     * @param param
     * @param resp
     * @return
     */
    public boolean confirmInfoJson(String param, OrderVo orderVo, Response resp) {
        return httpRequstService.confirmInfoJson(getRequestId(), param,
                orderVo, resp);
    }

    /**
     * 同步活动信息
     *
     * @param enCode
     * @param resp
     * @return
     */
    public boolean activitySyn(String enCode, Response resp) {
        return httpRequstService.activitySyn(getRequestId(), enCode, resp);
    }

    /**
     * 同步活动信息
     *
     * @param enCode
     * @param resp
     * @return
     */
    public boolean activityOldSyn(String enCode, Response resp) {
        return httpRequstService.activityOldSyn(getRequestId(), enCode, resp);
    }

    /**
     * 更新机具信息
     *
     * @param terminalVo
     * @param resp
     * @return
     */
    public boolean updateTerminal(TerminalVo terminalVo, Response resp) {
        return httpRequstService.updateTerminal(getRequestId(), terminalVo, resp);
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
}
