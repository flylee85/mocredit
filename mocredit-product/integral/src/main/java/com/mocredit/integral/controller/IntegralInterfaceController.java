package com.mocredit.integral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mocredit.integral.constant.Bank;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.entity.*;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.InRequestLogService;
import com.mocredit.integral.service.OrderService;
import com.mocredit.integral.service.TerminalService;
import com.mocredit.integral.util.DateEditor;
import com.mocredit.integral.util.DateTimeUtils;
import com.mocredit.integral.util.RandomUtil;
import com.mocredit.integral.util.ToolUtils;
import com.mocredit.integral.vo.ActivityVo;
import com.mocredit.integral.vo.ConfirmInfoVo;
import com.mocredit.integral.vo.OrderVo;
import com.mocredit.integral.vo.TerminalVo;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private TerminalService terminalService;
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
            setOrderStoreId(order);
            if (doPostJsonAndSaveOrder(param, order, resp)) {
                LOGGER.info("### payment success param={} ###", param);
                resp.setSuccess(true);
                Map<String, String> mapData = new HashMap<>();
                mapData.put("activityId", order.getActivityId());
                mapData.put("orderId", order.getOrderId());
                return renderJSONString(true, "", "", JSON.toJSONString(mapData));
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
     * 老机具积分消费
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/paymentOld", produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    public String paymentOld(HttpServletRequest request,
                             HttpServletResponse response, Order order) {
        String param = order.toString();
        LOGGER.info("### request in paymentOld param={} ###", param);
        Response resp = new Response();
        String orderId = ToolUtils.getPosno();
        try {
//            order.setOrderId(ToolUtils.getPosno());
            order.setOrderId(orderId);
            saveInRequestLog(request, order.getOrderId(), param);
            setOrderStoreId(order);
            if (doPostJsonAndSaveOrderForOld(param, order, resp)) {
                LOGGER.info("### paymentOld success param={} ###", param);
                resp.setSuccess(true);
                saveReponseLog(getRequestId(), resp.getData() + "");
                return resp.getData() + "";
            } else {
                LOGGER.error("### paymentOld error param={} ###", param);
                saveReponseLog(getRequestId(), resp.getData() + "");
                return resp.getData() + "";
            }
        } catch (Exception e) {
            LOGGER.error("### paymentOld error param={} error={}###", param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            saveReponseLog(getRequestId(), resp.getData() + "");
            String xml = getPaymentRevokeOldForXml(false, null, null, resp.getErrorCode(), resp.getErrorMsg());
            saveReponseLog(getRequestId(), xml);
            return xml;
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
            setOrderInfo(orderVo);
            setOrderStoreId(orderVo);
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
     * 老机具积分消费撤销
     * <p>
     * 撤销接口参数：
     * imei                     机具号
     * account               银行账户
     * batchno               批次号
     * searchno              流水号
     *
     * @return
     */
    @RequestMapping(value = "/paymentRevokeOld", produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    public String paymentRevokeOld(HttpServletRequest request,
                                   HttpServletResponse response, String imei, String account, String batchno, String searchno) {
        String param = "imei=" + imei + "&account=" + account + "&batchno=" + batchno + "&searchno=" + searchno;
        LOGGER.info("### request in paymentRevokeOld param={} ###", param);
        Response resp = new Response();
        String orderId = ToolUtils.getPosno();
        try {
            OrderVo orderVo = new OrderVo();
//            String orderId = ToolUtils.getPosno();
            orderVo.setOrderId(orderId);
            saveInRequestLog(request, orderId, param);
            setOrderInfoForOld(orderVo, searchno, batchno);
            setOrderStoreId(orderVo);
            Activity activity = activityService.getByActivityId(orderVo.getActivityId());
            if (paymentRevokeJson(param, orderVo, resp)) {
                LOGGER.info("### paymentRevokeOld success param={} ###", orderId);
                String xml = getPaymentRevokeOldForXml(true, activity, orderVo, null, null);
                saveReponseLog(getRequestId(), xml);
                return xml;
            } else {
                LOGGER.error("### paymentRevokeOld error param={} ###", orderId);
                String xml = getPaymentRevokeOldForXml(false, activity, orderVo, resp.getErrorCode(), resp.getErrorMsg());
                saveReponseLog(getRequestId(), xml);
                return xml;
            }
        } catch (Exception e) {
            LOGGER.error("### paymentRevokeOld error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, orderId, param);
            String xml = getPaymentRevokeOldForXml(false, null, null, resp.getErrorCode(), resp.getErrorMsg());
            saveReponseLog(getRequestId(), xml);
            return xml;
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
            setOrderInfo(orderVo);
            setOrderStoreId(orderVo);
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
            setOrderInfo(orderVo);
            setOrderStoreId(orderVo);
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
            setOrderInfo(orderVo);
            setOrderStoreId(orderVo);
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
                              HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in activitySyn param={} ###", param);
        Response resp = new Response();
        try {
            String enCode = JSON.parseObject(param).getString("enCode");
            saveInRequestLog(request, null, param);
            if (activitySyn(enCode, resp)) {
                LOGGER.info(
                        "### request in success activitySyn param={} ###",
                        param);
                return renderJSONString(true, resp.getErrorMsg(), resp.getErrorCode(), resp.getData());
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

    /**
     * 更新终端信息
     *
     * @param request
     * @param response
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateTerminal", produces = {"application/json;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateTerminal(HttpServletRequest request,
                                 HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in updateTerminal param={} ###", param);
        Response resp = new Response();
        try {
            TerminalVo terminalVo = JSON.parseObject(param, TerminalVo.class);
            saveInRequestLog(request, null, param);
            if (updateTerminal(terminalVo, resp)) {
                LOGGER.info(
                        "### request in success updateTerminal param={} ###",
                        param);
                return renderJSONString(true, resp.getErrorMsg(), resp.getErrorCode(), resp.getData());
            } else {
                LOGGER.error(
                        "### request in error updateTerminal param={} ###",
                        param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### updateTerminal error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 更新门店信息
     *
     * @param request
     * @param response
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateStore", produces = {"application/json;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateStore(HttpServletRequest request,
                              HttpServletResponse response, @RequestBody String param) {
        LOGGER.info("### request in updateStore param={} ###", param);
        Response resp = new Response();
        try {
            JSONObject jsonObject = JSON.parseObject(param);
            String oper = jsonObject.getString("oper");
            String storeId = jsonObject.getString("storeId");
            saveInRequestLog(request, null, param);
            if (updateStore(oper, storeId, resp)) {
                LOGGER.info(
                        "### request in success updateStore param={} ###",
                        param);
                return renderJSONString(true, resp.getErrorMsg(), resp.getErrorCode(), resp.getData());
            } else {
                LOGGER.error(
                        "### request in error updateStore param={} ###",
                        param);
                return renderJSONString(false, resp.getErrorMsg(),
                        resp.getErrorCode(), resp.getData());
            }
        } catch (Exception e) {
            LOGGER.error("### updateStore error param={} error={} ###",
                    param, e);
            resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            saveInRequestLog(request, null, param);
            return renderJSONString(false, resp.getErrorMsg(),
                    resp.getErrorCode(), resp.getData());
        }
    }

    /**
     * 积分核销与老pos网关活动同步
     *
     * @param request
     * @param response
     * @param enCode
     * @return
     */
    @RequestMapping(value = "/activityOldSyn", produces = {"application/xml;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String activityOldSyn(HttpServletRequest request,
                                 HttpServletResponse response, String enCode) {
        String param = enCode;
        LOGGER.info("### request in activityOldSyn param={} ###", param);
        Response resp = new Response();
        try {
            saveInRequestLog(request, null, param);
            if (activityOldSyn(enCode, resp)) {
                JsonNode jsonNodeArray = objectMapper.readTree(resp.getData() + "");
                StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
                for (JsonNode jsonNode : jsonNodeArray) {
                    String activityId = jsonNode.get("activityId").asText();
                    String activityName = jsonNode.get("activityName").asText();
                    String enterpriseName = jsonNode.get("enterpriseName").asText();
                    sb.append("<Table>");
                    sb.append("<isSuccess>true</isSuccess>");
                    sb.append("<eitemname>").append(activityName).append("</eitemname>");
                    sb.append("<eitemid>").append(activityId).append("</eitemid>");
                    sb.append("<ispayment>").append(3).append("</ispayment>");
                    sb.append("<iszxcheck>").append(0).append("</iszxcheck>");
                    sb.append("<codetimes>").append(1).append("</codetimes>");
                    sb.append("<entname>").append(enterpriseName).append("</entname>");
                    sb.append("</Table>");
                }
                JsonNode jsonNodeArrayOff = objectMapper.readTree(resp.getExtraData() + "");
                for (JsonNode jsonNode : jsonNodeArrayOff) {
                    String activityId = jsonNode.get("activityId").asText();
                    String activityName = jsonNode.get("activityName").asText();
                    String enterpriseName = jsonNode.get("enterpriseName").asText();
                    sb.append("<Table>");
                    sb.append("<isSuccess>true</isSuccess>");
                    sb.append("<eitemname>").append(activityName).append("</eitemname>");
                    sb.append("<eitemid>").append(activityId).append("</eitemid>");
                    sb.append("<ispayment>").append(3).append("</ispayment>");
                    sb.append("<iszxcheck>").append(0).append("</iszxcheck>");
                    sb.append("<codetimes>").append(1).append("</codetimes>");
                    sb.append("<entname>").append(enterpriseName).append("</entname>");
                    sb.append("</Table>");
                }
                LOGGER.info("### request in success activityOldSyn param={} ###", param);
                sb.append("</NewDataSet>");
                return sb.toString();
            } else {
                LOGGER.error(
                        "### request in error activityOldSyn param={} ###",
                        param);
                StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
                sb.append("<Table>");
                sb.append("<isSuccess>false</isSuccess>");
                sb.append("<resultInfo>").append(ErrorCodeType.ACTIVITY_SYN_ERROR.getText()).append("</resultInfo>");
                sb.append("</Table>");
                sb.append("</NewDataSet>");
                return sb.toString();
            }
        } catch (Exception e) {
            LOGGER.error("### activityOldSyn error param={} error={} ###",
                    param, e);
            StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
            sb.append("<Table>");
            sb.append("<isSuccess>false</isSuccess>");
            sb.append("<resultInfo>").append(ErrorCodeType.ACTIVITY_SYN_ERROR.getText()).append("</resultInfo>");
            sb.append("</Table>");
            sb.append("</NewDataSet>");
            return sb.toString();
        }
    }

    /**
     * 查询银行列表
     *
     * @param request
     * @param response
     * @param enCode
     * @return
     */
    @RequestMapping(value = "/bankList", produces = {"application/xml;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String bankList(HttpServletRequest request,
                           HttpServletResponse response, String enCode) {
        String param = enCode;
        LOGGER.info("### request in bankList param={} ###", param);
        try {
            saveInRequestLog(request, null, param);
            StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
            Map<String, String> mapBank = new HashMap<>();
            for (Activity activity : activityService.getActivityByEnCode(enCode)) {
                String bankId = activity.getChannel();
                String bankName = Bank.getBankNameByBankId(bankId);
                if (!mapBank.containsKey(bankId)) {
                    sb.append("<Table>");
                    sb.append("<bankid>").append(bankId).append("</bankid>");
                    sb.append("<bankname>").append(bankName).append("</bankname>");
                    sb.append("</Table>");
                    mapBank.put(bankId, bankName);
                }
            }
            sb.append("</NewDataSet>");
            return sb.toString();

        } catch (Exception e) {
            LOGGER.error("### activityOldSyn error param={} error={} ###",
                    param, e);
            StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
            sb.append("<Table>");
            sb.append("<resultInfo>").append("获取银行列表失败").append("</resultInfo>");
            sb.append("</Table>");
            sb.append("</NewDataSet>");
            return sb.toString();
        }
    }

    /**
     * 根据银行查询活动列表
     *
     * @param request
     * @param response
     * @param enCode
     * @param bankId
     * @return
     */
    @RequestMapping(value = "/activityByBankId", produces = {"application/xml;charset=UTF-8"}, method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String activityByBankId(HttpServletRequest request,
                                   HttpServletResponse response, String enCode, String bankId) {
        String param = enCode;
        LOGGER.info("### request in bankList param={} ###", param);
        try {
            saveInRequestLog(request, null, param);
            StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
            for (Activity activity : activityService.getActivityByEnCode(enCode)) {
                if (bankId == null || "null".equals(bankId) || "".equals(bankId)) {
                    String bId = activity.getChannel();
                    sb.append("<Table>");
                    sb.append("<bankid>").append(bId).append("</bankid>");
                    sb.append("<outerid>").append(activity.getActivityId()).append("</outerid>");
                    sb.append("<eitemname>").append(activity.getActivityName()).append("</eitemname>");
                    sb.append("<expointtype>").append(activity.getExchangeType()).append("</expointtype >");
                    sb.append("</Table>");
                } else {
                    if (bankId.equals(activity.getChannel())) {
                        sb.append("<Table>");
                        sb.append("<bankid>").append(bankId).append("</bankid>");
                        sb.append("<outerid>").append(activity.getActivityId()).append("</outerid>");
                        sb.append("<eitemname>").append(activity.getActivityName()).append("</eitemname>");
                        sb.append("<expointtype>").append(activity.getExchangeType()).append("</expointtype >");
                        sb.append("</Table>");
                    }
                }
            }
            sb.append("</NewDataSet>");
            return sb.toString();

        } catch (Exception e) {
            LOGGER.error("### activityOldSyn error param={} error={} ###",
                    param, e);
            StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet>");
            sb.append("<Table>");
            sb.append("<resultInfo>").append("获取活动列表失败").append("</resultInfo>");
            sb.append("</Table>");
            sb.append("</NewDataSet>");
            return sb.toString();
        }
    }

    /**
     * 设置订单的门店id
     *
     * @param order
     */
    public void setOrderStoreId(Order order) {
        Terminal terminal = terminalService.getTerminalByEnCodeAndActivityId(order.getEnCode(), order.getActivityId());
        order.setStoreId(terminal.getStoreId());
//        if (terminal != null) {
//            order.setStoreId(terminal.getStoreId());
//        }
    }

    /**
     * 设置订单信息
     *
     * @param order
     */
    public void setOrderInfo(Order order) {
        Order oldOrder = orderService.getOrderByOrderId(order.getOldOrderId());
        order.setStoreId(oldOrder.getStoreId());
        order.setEnCode(oldOrder.getEnCode());
        order.setCardExpDate(oldOrder.getCardExpDate());
        order.setActivityId(oldOrder.getActivityId());
        order.setAmt(oldOrder.getAmt());
        order.setCardNum(oldOrder.getCardNum());
        order.setEnCode(oldOrder.getEnCode());
    }

    /**
     * 设置订单信息
     *
     * @param order
     */
    public void setOrderInfoForOld(Order order, String searchno, String batchno) {
        Order oldOrder = orderService.getOrderBySearchNoAndBatchNo(searchno, batchno);
        order.setBatchno(batchno);
        order.setSearchno(searchno);
        order.setOldOrderId(oldOrder.getOrderId());
        order.setStoreId(oldOrder.getStoreId());
        order.setEnCode(oldOrder.getEnCode());
        order.setCardExpDate(oldOrder.getCardExpDate());
        order.setActivityId(oldOrder.getActivityId());
        order.setAmt(oldOrder.getAmt());
        order.setCardNum(oldOrder.getCardNum());
        order.setEnCode(oldOrder.getEnCode());
    }

    private String getPaymentRevokeOldForXml(boolean flag, Activity activity, Order order, String errorCode, String msg) {
        StringBuilder stringBuilder = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table>");
        if (flag) {
            Store store = activityService.getByShopIdStoreIdAcId(order.getStoreId(),
                    order.getActivityId());
//            String orderid = Calendar.getInstance().getTimeInMillis() + "";
//            orderid = orderid + RandomUtil.getString(19 - orderid.length());
            stringBuilder.append("<isSuccess>true</isSuccess>");
            stringBuilder.append("<shopname>").append(store.getShopName()).append("</shopname>");
            stringBuilder.append("<shopno>").append(store.getShopId()).append("</shopno>");
            stringBuilder.append("<storename>").append(store.getStoreName()).append("</storename>");
            stringBuilder.append("<imei>").append(order.getEnCode()).append("</imei>");
            stringBuilder.append("<eitemtime>").append("null").append("</eitemtime>");
            stringBuilder.append("<trantype>").append("积分兑换").append("</trantype>");
            stringBuilder.append("<batchno>").append("").append("</batchno>");
            stringBuilder.append("<orderid>").append(order.getOrderId()).append("</orderid>");
            stringBuilder.append("<point>").append(activity.getIntegral()).append("</point>");
            stringBuilder.append("<trantime>").append(DateTimeUtils.getDate()).append("</trantime>");
            stringBuilder.append("<admin>").append("001").append("</admin>");
            stringBuilder.append("<remark>").append("").append("</remark>");
            stringBuilder.append("<eitemname>").append(activity.getActivityName()).append("</eitemname>");
            stringBuilder.append("<bankname>").append(Bank.getBankNameByBankId(activity.getChannel())).append("</bankname>");
            stringBuilder.append("<cardno>").append(order.getCardNum()).append("</cardno>");
            stringBuilder.append("<posno>").append(order.getOrderId()).append("</posno>");
            stringBuilder.append("<eitemid>").append(activity.getActivityId()).append("</eitemid>");
            //支付方式
            stringBuilder.append("<payway>").append(0 + activity.getExchangeType()).append("</payway>");
        } else {
            stringBuilder.append("<isSuccess>false</isSuccess>");
            stringBuilder.append("<errorCode>").append(errorCode).append("</errorCode>");
            stringBuilder.append("<errorMsg>").append(msg).append("</errorMsg>");
        }
        stringBuilder.append("</Table>").append("</NewDataSet>");
        return stringBuilder.toString();
    }
}
