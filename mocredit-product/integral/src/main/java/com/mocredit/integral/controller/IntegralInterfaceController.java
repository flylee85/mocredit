package com.mocredit.integral.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.InRequestLog;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.InRequestLogService;
import com.mocredit.integral.service.OrderService;
import com.mocredit.integral.util.DateEditor;
import com.mocredit.integral.vo.ActivityVo;
import com.mocredit.integral.vo.ConfirmInfoVo;
import com.mocredit.integral.vo.OrderVo;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对网关接口
 *
 * @author ytq 2015年8月25日
 */

@RestController
@RequestMapping("/")
public class IntegralInterfaceController extends IntegralBaseController {
    private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("interfaceCtrl");
    @Autowired
    private OrderService orderService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private InRequestLogService inRequestLogService;
    static ObjectMapper objectMapper = new ObjectMapper();
    static Map<String, String> errorCodeMap = new HashMap<String, String>();

    static {
        for (ErrorCodeType errorCode : ErrorCodeType.values()) {
            errorCodeMap.put(errorCode.getValue(), errorCode.getText());
        }
        DeserializationConfig cfg = objectMapper.getDeserializationConfig();
        cfg.withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setDeserializationConfig(cfg);
    }

    /**
     * 积分消费
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/payment", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String payment(HttpServletRequest request,
                          HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in payment param={} ###", param);
        Response resp = new Response();
        try {
            Order order = JSON.parseObject(param, Order.class);
            saveInRequestLog(request, order.getOrderId(), param);
            if (doPostJsonAndSaveOrder(param, order, resp)) {
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
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 积分消费撤销
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/paymentRevoke", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String paymentRevoke(HttpServletRequest request,
                                HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in paymentRevoke param={} ###", param);
        Response resp = new Response();
        try {
            OrderVo orderVo = JSON.parseObject(param, OrderVo.class);
            String orderId = orderVo.getOrderId();
            saveInRequestLog(request, orderId, param);
            if (paymentRevokeJson(param, orderVo, resp)) {
                LOGGER.info("### paymentRevoke success param={} ###", orderId);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### paymentRevoke error param={} ###", orderId);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### paymentRevoke error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 积分消费冲正
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/paymentReserval", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String paymentReserval(HttpServletRequest request,
                                  HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in paymentReserval param={} ###", param);
        Response resp = new Response();
        try {
            OrderVo orderVo = JSON.parseObject(param, OrderVo.class);
            String orderId = orderVo.getOrderId();
            saveInRequestLog(request, orderId, param);
            if (paymentReservalJson(param, orderVo, resp)) {
                LOGGER.info("### paymentReserval success param={} ###", param);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### paymentReserval error param={} ###", param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### paymentReserval error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 积分消费撤销冲正
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/paymentRevokeReserval", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String paymentRevokeReserval(HttpServletRequest request,
                                        HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in paymentReserval param={} ###", param);
        Response resp = new Response();
        try {
            OrderVo orderVo = JSON.parseObject(param, OrderVo.class);
            String orderId = orderVo.getOrderId();
            saveInRequestLog(request, orderId, param);
            if (paymentRevokeReservalJson(param, orderVo, resp)) {
                LOGGER.info("### paymentRevokeReserval success param={} ###",
                        param);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### paymentRevokeReserval error param={} ###",
                        param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error(
                    "### paymentRevokeReserval error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 积分查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/confirmInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String confirmInfo(HttpServletRequest request,
                              HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in confirmInfo param={} ###", param);
        Response resp = new Response();
        try {
            OrderVo orderVo = JSON.parseObject(param, OrderVo.class);
            String orderId = orderVo.getOrderId();
            saveInRequestLog(request, orderId, param);
            if (confirmInfoJson(param, orderVo, resp)) {
                LOGGER.info("### confirmInfo success param={} ###", param);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error("### confirmInfo error param={} ###", param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### confirmInfo error param={} error={} ###", param,
                    e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 活动同步
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/activityImport", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String activityImport(HttpServletRequest request,
                                 HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in activityImport param={} ###", param);

        Response resp = new Response();
        try {
            // 使用ObjectMapper嵌套json转对象报错采用fastJson
            ActivityVo activity = JSON.parseObject(param, ActivityVo.class);
            activity.setProductType(activity.getProductCode());
            saveInRequestLog(request, null, param);
            if (activityService.operActivityAndStore(activity, resp)) {
                LOGGER.info(
                        "### request in success activityImport param={} ###",
                        param);
                return renderJSONString(true, "", "", "");
            } else {
                LOGGER.error(
                        "### request in error activityImport param={} ###",
                        param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### activityImport error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        // 对于需要转换为Date类型的属性，使用DateEditor进行处理
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    public void saveInRequestLog(HttpServletRequest request, String orderId,
                                 String param) {
        InRequestLog inRequestLog = new InRequestLog();
        inRequestLog.setIp(getIpAddr(request));
        inRequestLog.setRequest(param);
        inRequestLog.setOrderId(orderId);
        inRequestLog.setInterfaceUrl(request.getRequestURI());
        inRequestLogService.save(inRequestLog);
        request.setAttribute("request_id", inRequestLog.getUuid());
    }

    @RequestMapping(value = "/downloadOrder", produces = {"application/json;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String downloadOrder(HttpServletRequest request,
                                HttpServletResponse response, Integer offset, Integer count) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", orderService.synOrder(offset, count));
        return JSON.toJSONString(map);
    }

    /**
     * 积分核销与新pos网关活动同步
     *
     * @param request
     * @param response
     * @param param
     * @return
     */
    @RequestMapping(value = "/activitySyn", produces = {"application/json;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String activitySyn(HttpServletRequest request,
                              HttpServletResponse response, /*@RequestBody*/ String param) {
        LOGGER.info("### request in activitySyn param={} ###", param);
        Response resp = new Response();
        try {
            String enCode = /*JSON.parseObject(param).getString("enCode")*/"12312321r";
            saveInRequestLog(request, null, param);
            if (activitySyn(enCode, resp)) {
                LOGGER.info(
                        "### request in success activitySyn param={} ###",
                        param);
                return renderJSONString(true, resp.getErrorMsg(),resp.getErrorCode(), resp.getData());
            } else {
                LOGGER.error(
                        "### request in error activitySyn param={} ###",
                        param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### activitySyn error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }
}
