package com.mocredit.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.gateway.constant.ErrorCodeType;
import com.mocredit.gateway.constant.ReqType;
import com.mocredit.gateway.entity.Record;
import com.mocredit.gateway.entity.Response;
import com.mocredit.gateway.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 对网关接口
 *
 * @author ytq 2015年8月25日
 */

@RestController
@RequestMapping("/")
public class EGatewayInterfaceController extends EGatewayBaseController {
    private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("EGatewayInterfaceController");
    @Autowired
    private RecordService recordService;

    /**
     * 积分消费
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/payment", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String payment(HttpServletRequest request, @RequestBody String param) {
        LOGGER.info("### request in payment param={} ###", param);
        Response resp = new Response();
        try {
            Record record = JSON.parseObject(param, Record.class);
            record.setReqType(ReqType.PAYMENT.getValue());
            saveInRequestLog(request, record.getOrderId(), param);
            setRecordRequestId(record);
            if (recordService.save(record, resp)) {
                LOGGER.info("### payment success param={} ###", param);
                resp.setSuccess(true);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### payment error param={} ###", param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### payment error param={} error={}###", param, e);
            saveInRequestLog(request, null, param);
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 积分消费撤销
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/paymentRevoke", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String paymentRevoke(HttpServletRequest request, @RequestBody String param) {
        LOGGER.info("### request in paymentRevoke param={} ###", param);
        Response resp = new Response();
        try {
            Record record = JSON.parseObject(param, Record.class);
            record.setReqType(ReqType.PAYMENT_REVOKE.getValue());
            saveInRequestLog(request, record.getOrderId(), param);
            setRecordRequestId(record);
            if (recordService.savePaymentRevoke(record, resp)) {
                LOGGER.info("### paymentRevoke success param={} ###", param);
                resp.setSuccess(true);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### paymentRevoke error param={} ###", param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### paymentRevoke error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }


    /**
     * 积分消费冲正
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/paymentReversal", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String paymentReversal(HttpServletRequest request, @RequestBody String param) {
        LOGGER.info("### request in paymentReversal param={} ###", param);
        Response resp = new Response();
        try {
            Record record = JSON.parseObject(param, Record.class);
            record.setReqType(ReqType.PAYMENT_REVERSAL.getValue());
            saveInRequestLog(request, record.getOrderId(), param);
            setRecordRequestId(record);
            if (recordService.savePaymentRevoke(record, resp)) {
                LOGGER.info("### paymentReversal success param={} ###", param);
                resp.setSuccess(true);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### paymentReversal error param={} ###", param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### paymentReversal error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }
}
