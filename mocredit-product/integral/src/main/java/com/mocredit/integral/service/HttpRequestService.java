package com.mocredit.integral.service;

import java.io.IOException;
import java.util.*;

import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.integral.adapter.IntegralBankAdapter;
import com.mocredit.integral.constant.*;
import com.mocredit.integral.util.RandomUtil;
import com.mocredit.integral.vo.OrderVo;
import com.mocredit.integral.vo.TerminalVo;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.dto.PaymentDto;
import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.OutRequestLog;
import com.mocredit.integral.entity.OutResponseLog;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.entity.ResponseData;
import com.mocredit.integral.entity.Store;
import com.mocredit.integral.util.DateTimeUtils;
import com.mocredit.integral.util.HttpRequestUtil;
import com.mocredit.integral.vo.ConfirmInfoVo;

@Service
public class HttpRequestService extends LogService {
    @Autowired
    private OutRequestLogService outRequestLogService;
    @Autowired
    private OutResponseLogService outResponseLogService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private IntegralBankAdapter integralBankAdapter;

    public Integer getActTRCount(String maxType) {
        // 暂定01代表每日，02代表每周，03代表每月，空代表不限制',
        Integer count = null;
        switch (maxType) {
            case "01":
                Date now = new Date();
                ActivityTransRecord atr01 = activityService.statCountByTime(now,
                        now);
                if (atr01 != null) {
                    count = atr01.getTransCount();
                } else {
                    count = 0;
                }

                break;
            case "02":
                Calendar cal02S = Calendar.getInstance();
                cal02S.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Calendar cal02E = Calendar.getInstance();
                cal02E.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                cal02E.add(Calendar.DATE, 6);
                ActivityTransRecord atr02 = activityService.statCountByTime(
                        cal02S.getTime(), cal02E.getTime());
                if (atr02 != null) {
                    count = atr02.getTransCount();
                } else {
                    count = 0;
                }
                break;
            case "03":
                Calendar cal03S = Calendar.getInstance();
                cal03S.set(Calendar.DAY_OF_MONTH, 1);

                Calendar cal03E = Calendar.getInstance();
                cal03E.set(Calendar.DAY_OF_MONTH,
                        cal03E.getActualMaximum(Calendar.DAY_OF_MONTH));

                ActivityTransRecord atr03 = activityService.statCountByTime(
                        cal03S.getTime(), cal03E.getTime());
                if (atr03 != null) {
                    count = atr03.getTransCount();
                } else {
                    count = 0;
                }
                break;
        }
        return count;
    }

    /**
     * 积分消费和保存订单
     *
     * @param requestId
     * @param param
     * @param order
     * @param resp
     * @return
     */
    public boolean doPostJsonAndSaveOrder(Integer requestId, String param, Order order, Response resp) {
        String url = "";
        try {
            Activity activity = activityService.getByActivityId(order
                    .getActivityId());
            if (activity != null) {
                if (order.getAmt() == null) {
                    order.setAmt(activity.getIntegral());
                }
                //判断活动是否启用
                if (ActivityStatus.STOP.getValue().equals(activity.getStatus())) {
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_ALREADY_STOP
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_ALREADY_STOP
                            .getText());
                    return false;
                }
                //验证该卡能否参加该活动
                if (activity.getBins() != null && !"".equals(activity.getBins())) {
                    boolean flag = false;
                    for (String bin : activity.getBins().split(",")) {
                        if (order.getCardNum() != null && order.getCardNum().startsWith(bin)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        resp.setErrorCode(ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD
                                .getValue());
                        resp.setErrorMsg(ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD
                                .getText());
                        return false;
                    }
                }
                // 验证该商户，该门店是否能参加该活动
                Store store = activityService.getByShopIdStoreIdAcId(order.getStoreId(),
                        order.getActivityId());
                if (store == null) {
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_NOT_EXIST_SHOP_STORE
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_NOT_EXIST_SHOP_STORE
                            .getText());
                    return false;
                }
                Date now = new Date();
                // 1，先判断当前时间在活动时间范围内
                // 2，再判断当前时间的所属的星期范围内
                if (activity.getStartTime().getTime() <= now.getTime()
                        && now.getTime() <= activity.getEndTime().getTime()
                        && (activity.getSelectDate().contains(DateTimeUtils
                        .getWeekOfDate(now)))) {
                } else {
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_DATE
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_DATE.getText());
                    return false;
                }
                if (activity.getRule() != null && !"".equals(activity.getRule())) {
                    // 3,判断使用次数是否超过最大使用次数限制
                    List<ActivityTransRecord> tranRecords = activityService.getTranRecordByActId(order.getActivityId());
                    if (!tranRecords.isEmpty()) {
                        for (String rule : activity.getRule().split(";")) {
                            String ruleName = rule.split(":")[0];
                            String ruleValue = rule.split(":")[1];
                            for (ActivityTransRecord tranRecord : tranRecords) {
                                if (ruleName.equals(tranRecord.getTransType())) {
                                    compareRuleAndMax(ruleName, Integer.valueOf(ruleValue), tranRecord.getTransCount(), resp);
                                    if (!resp.getSuccess()) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
          /*      // 3,判断使用次数是否超过最大使用次数限制
                if (activity.getMaxType() != null
                        && !"".equals(activity.getMaxType())) {
                    // 4,判断最大类型参数是否正确并返回当前类型目前消费次数
                    Integer count = getActTRCount(activity.getMaxType());
                    if (null == count) {
                        resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
                        resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
                        return false;
                    }
                    if (activity.getMaxNumber() != null
                            && activity.getMaxNumber() < count) {
                        resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_COUNT
                                .getValue());
                        resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_COUNT
                                .getText());
                        return false;
                    }
                }*/
              /*  // 5,条件判断订单是否已经存在
                if (orderService.isExistOrder(order.getOrderId())) {
                    resp.setErrorCode(ErrorCodeType.EXIST_ORDER_ERROR
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.EXIST_ORDER_ERROR.getText());
                    return false;
                }*/
            } else {
                resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getValue());
                resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getText());
                return false;
            }
            url = integralBankAdapter.getPayment(activity.getChannel());
            if (ExchangeType.OFFLINE_QUANYI.equals(activity.getExchangeType())) {
                url = PropertiesUtil.getValue("offline_payment");
            }
            String response = doPostJson(requestId, url, getPaymentDto(activity, order));
            boolean anaFlag = analyJsonReponse(requestId, activity.getChannel(), url, param, response,
                    resp);
            order.setRequestId(requestId);
            order.setStatus(OrderStatus.PAYMENT.getValue());
            order.setMsg(resp.getErrorMsg());
            if (anaFlag) {
                order.setIsSuccess(1);
            } else {
                order.setIsSuccess(0);
            }
            if (anaFlag) {
                // 设置订单reuestId和交易完成状态
                orderService.saveAndCount(order);
            } else {
                orderService.save(order);
            }
            return anaFlag;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            LOGGER.error(
                    "### doPostJsonAndSaveOrder url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            return false;
        }
    }

    /**
     * 积分消费和保存订单for老机具
     *
     * @param requestId
     * @param param
     * @param order
     * @param resp
     * @return
     */
    public boolean doPostJsonAndSaveOrderForOld(Integer requestId, String param, Order order, Response resp) {
        String url = "";
        Activity activity = activityService.getByActivityId(order
                .getActivityId());
        Store store = activityService.getByShopIdStoreIdAcId(order.getStoreId(),
                order.getActivityId());
        try {
            if (activity != null) {
                if (order.getAmt() == null) {
                    order.setAmt(activity.getIntegral());
                }
                //判断活动是否启用
                if (ActivityStatus.STOP.getValue().equals(activity.getStatus())) {
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_ALREADY_STOP
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_ALREADY_STOP
                            .getText());
                    resp.setData(getPaymentOldForXml(false, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
                    return false;
                }
                //验证该卡能否参加该活动
                if (activity.getBins() != null && !"".equals(activity.getBins())) {
                    boolean flag = false;
                    for (String bin : activity.getBins().split(",")) {
                        if (order.getCardNum() != null && order.getCardNum().startsWith(bin)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        resp.setErrorCode(ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD
                                .getValue());
                        resp.setErrorMsg(ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD
                                .getText());
                        resp.setData(getPaymentOldForXml(false, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
                        return false;
                    }
                }
                Date now = new Date();
                // 1，先判断当前时间在活动时间范围内
                // 2，再判断当前时间的所属的星期范围内
                if (activity.getStartTime().getTime() <= now.getTime()
                        && now.getTime() <= activity.getEndTime().getTime()
                        && (activity.getSelectDate().contains(DateTimeUtils
                        .getWeekOfDate(now)))) {
                } else {
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_DATE
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_DATE.getText());
                    resp.setData(getPaymentOldForXml(false, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
                    return false;
                }
                if (activity.getRule() != null && !"".equals(activity.getRule())) {
                    // 3,判断使用次数是否超过最大使用次数限制
                    List<ActivityTransRecord> tranRecords = activityService.getTranRecordByActId(order.getActivityId());
                    if (!tranRecords.isEmpty()) {
                        for (String rule : activity.getRule().split(";")) {
                            String ruleName = rule.split(":")[0];
                            String ruleValue = rule.split(":")[1];
                            for (ActivityTransRecord tranRecord : tranRecords) {
                                if (ruleName.equals(tranRecord.getTransType())) {
                                    compareRuleAndMax(ruleName, Integer.valueOf(ruleValue), tranRecord.getTransCount(), resp);
                                    if (!resp.getSuccess()) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getValue());
                resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getText());
                resp.setData(getPaymentOldForXml(false, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
                return false;
            }
            url = integralBankAdapter.getPayment(activity.getChannel());
            if (ExchangeType.OFFLINE_QUANYI.equals(activity.getExchangeType())) {
                url = PropertiesUtil.getValue("offline_payment");
            }
            String response = doPostJson(requestId, url, getPaymentDto(activity, order));
            boolean anaFlag = analyJsonReponse(requestId, activity.getChannel(), url, param, response,
                    resp);
            // 设置订单reuestId和交易完成状态
            order.setRequestId(requestId);
            order.setStatus(OrderStatus.PAYMENT.getValue());
            order.setMsg(resp.getErrorMsg());
            if (anaFlag) {
                order.setIsSuccess(1);
            } else {
                order.setIsSuccess(0);
            }
            if (anaFlag) {
                orderService.saveAndCount(order);
                resp.setData(getPaymentOldForXml(true, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
            } else {
                orderService.save(order);
                resp.setData(getPaymentOldForXml(false, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
            }
            return anaFlag;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            LOGGER.error(
                    "### doPostJsonAndSaveOrder url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            resp.setData(getPaymentOldForXml(false, activity, order, store, resp.getErrorCode(), resp.getErrorMsg()));
            return false;
        }
    }

    /**
     * 构建请求payment接口参数和查询积分接口参数
     *
     * @param activity
     * @param order
     * @return
     */
    private String getPaymentDto(Activity activity, Order order) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setOrderId(order.getOrderId());
        paymentDto.setCardNum(order.getCardNum());
        paymentDto.setCode(activity.getProductType());
        paymentDto.setTranAmt(order.getAmt() + "");
        paymentDto.setCardExpDate(order.getCardExpDate());
        return JSON.toJSONString(paymentDto);
    }

    /**
     * 构建请求订单和原订单接口参数
     *
     * @return
     */
    private String getOrderIdAndOldOrderIdParam(OrderVo orderVo) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderId", orderVo.getOrderId());
        paramMap.put("oldOrderId", orderVo.getOldOrderId());
        return JSON.toJSONString(paramMap);
    }

    /**
     * 分析请求bank端响应
     *
     * @param requestId
     * @param url
     * @param param
     * @param reponse
     * @param resp
     * @return
     */
    public boolean analyJsonReponse(Integer requestId, String channel, String url,
                                    String param, String reponse, Response resp) {
        String bankOff = PropertiesUtil.getValue("bank-off");
        if (!"true".equals(bankOff)) {
            if (reponse == null) {
                resp.setErrorCode(ErrorCodeType.POST_BANK_ERROR.getValue());
                resp.setErrorMsg(ErrorCodeType.POST_BANK_ERROR.getText());
                return false;
            }
            try {
                ResponseData responseData = JSON.parseObject(reponse,
                        ResponseData.class);
                resp.setData(responseData.getData());
                resp.setErrorCode(responseData.getErrorCode());
                resp.setErrorMsg(responseData.getErrorMsg());
                resp.setSuccess(responseData.getSuccess());
                switch (channel) {
                    case Bank.ZX_BANK_CHANNEL: {
                        if (BankStatus.getMsgByZX(responseData.getErrorCode()) != null) {
                            resp.setErrorMsg(BankStatus.getMsgByZX(responseData.getErrorCode()));
                        }
                        break;
                    }
                    case Bank.MS_BANK_CHANNEL: {
                        if (BankStatus.getMsgByMS(responseData.getErrorCode()) != null) {
                            resp.setErrorMsg(BankStatus.getMsgByMS(responseData.getErrorCode()));
                        }
                        break;
                    }
                    case Bank.JT_BANK_CHANNEL: {
                        if (BankStatus.getMsgByJT(responseData.getErrorCode()) != null) {
                            resp.setErrorMsg(BankStatus.getMsgByJT(responseData.getErrorCode()));
                        }
                        break;
                    }
                }
                return responseData.getSuccess();
            } catch (Exception e) {
                resp.setErrorCode(ErrorCodeType.ANA_RESPONSE_ERROR.getValue());
                resp.setErrorMsg(ErrorCodeType.ANA_RESPONSE_ERROR.getText());
                LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
                        url, requestId, param, e);
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 积分消费撤销
     *
     * @param requestId
     * @param param
     * @param resp
     * @return
     */
    public boolean paymentRevokeJson(Integer requestId, String param, OrderVo orderVo, Response resp) {
        String url = "";
        try {
            if (!orderService.isExistOrder(orderVo.getOldOrderId())) {
                resp.setErrorCode(ErrorCodeType.NOT_EXIST_ORDER_ERROR
                        .getValue());
                resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ORDER_ERROR
                        .getText());
                return false;
            }
            if (orderService.isExistOldOrder(orderVo.getOldOrderId())) {
                return true;
            }
            Activity activity = activityService.getActivityByOrderId(orderVo.getOldOrderId());
            url = integralBankAdapter.getPaymentRevoke(activity.getChannel());
            if (ExchangeType.OFFLINE_QUANYI.equals(activity.getExchangeType())) {
                url = PropertiesUtil.getValue("offline_paymentRevoke");
            }
            String response = doPostJson(requestId, url, getOrderIdAndOldOrderIdParam(orderVo));
            boolean anaFlag = analyJsonReponse(requestId, activity.getChannel(), url, param, response,
                    resp);
            orderVo.setRequestId(requestId);
            orderVo.setStatus(OrderStatus.PAYMENT_REVOKE.getValue());
            orderVo.setMsg(resp.getErrorMsg());
            if (anaFlag) {
                orderService.savePaymentRevoke(orderVo);
            } else {
                orderService.save(orderVo);
            }
            return anaFlag;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            LOGGER.error(
                    "### paymentRevokeJson url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            return false;
        }
    }

    /**
     * 积分冲正
     *
     * @param requestId
     * @param param
     * @param orderVo
     * @param resp
     * @return
     */
    public boolean paymentReservalJson(Integer requestId,
                                       String param, OrderVo orderVo, Response resp) {
        String url = "";
        try {
            //判断订单是否已经被撤销了
            if (orderService.isExistOldOrder(orderVo.getOldOrderId())) {
                return true;
            }
            Activity activity = activityService.getActivityByOrderId(orderVo.getOldOrderId());
            url = integralBankAdapter.getPaymentReserval(activity.getChannel());
            if (ExchangeType.OFFLINE_QUANYI.equals(activity.getExchangeType())) {
                url = PropertiesUtil.getValue("offline_paymentReserval");
            }
            String response = doPostJson(requestId, url, getOrderIdAndOldOrderIdParam(orderVo));
            boolean anaFlag = analyJsonReponse(requestId, activity.getChannel(), url, param, response, resp);
            orderVo.setRequestId(requestId);
            orderVo.setStatus(OrderStatus.PAYMENT_REVERSAL.getValue());
            orderVo.setMsg(resp.getErrorMsg());
            if (anaFlag) {
                orderService.savePaymentReserval(orderVo);
            } else {
                orderService.save(orderVo);
            }
            return anaFlag;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            LOGGER.error(
                    "### paymentReservalJson url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            return false;
        }
    }

    /**
     * 积分撤销冲正
     *
     * @param requestId
     * @param param
     * @param orderVo
     * @param resp
     * @return
     */
    public boolean paymentRevokeReservalJson(Integer requestId, String param,
                                             OrderVo orderVo, Response resp) {
        String url = "";
        try {
            Activity activity = activityService.getActivityByOrderId(orderVo.getOldOrderId());
            url = integralBankAdapter.getPaymentRevokeReserval(activity.getChannel());
            String response = doPostJson(requestId, url, getOrderIdAndOldOrderIdParam(orderVo));
            boolean anaFlag = analyJsonReponse(requestId, activity.getChannel(), url, param, response, resp);
            orderVo.setRequestId(requestId);
            orderVo.setStatus(OrderStatus.PAYMENT_REVERSAL_REVOKE.getValue());
            orderVo.setMsg(resp.getErrorMsg());
            orderService.save(orderVo);
            return anaFlag;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            LOGGER.error(
                    "### paymentRevokeReserval url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            return false;
        }
    }

    /**
     * 查询积分
     *
     * @param requestId
     * @param resp
     * @return
     */
    public boolean confirmInfoJson(Integer requestId, String param,
                                   OrderVo orderVo, Response resp) {
        String url = "";
        try {
            Activity activity = activityService.getByActivityId(orderVo
                    .getActivityId());
            if (activity == null) {
                resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getValue());
                resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getText());
                return false;
            }
            //验证该卡能否参加该活动
            if (activity.getBins() != null && !"".equals(activity.getBins())) {
                boolean flag = false;
                for (String bin : activity.getBins().split(",")) {
                    if (orderVo.getCardNum() != null && orderVo.getCardNum().startsWith(bin)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD
                            .getText());
                    return false;
                }
            }
            url = integralBankAdapter.getConfirmInfo(activity.getChannel());
//            Store store = activityService.getByShopIdStoreIdAcId(
//                    confirmInfo.getShopId() + "", null,
//                    confirmInfo.getActivityId());
//            if (store == null) {
//                resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
//                        .getValue());
//                resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
//                        .getText());
//                return false;
//            }
            String response = doPostJson(requestId, url, getPaymentDto(activity, orderVo));
            boolean anaFlag = analyJsonReponse(requestId, activity.getChannel(), url, param, response, resp);
            if (anaFlag) {
                orderService.save(orderVo);
            }
            return anaFlag;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            LOGGER.error(
                    "### confirmInfo url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            return false;
        }
    }

    /**
     * 同步活动信息
     *
     * @param enCode
     * @return
     */
    public boolean activitySyn(Integer requestId, String enCode, Response resp) {
        String activityIds = activityService.getActIdsByEnCode(enCode);
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("activityIds", activityIds.split(","));
        mapParam.put("enCode", enCode);
        String url = PropertiesUtil.getValue("activity.syn");
        String response = doPostJson(requestId, url, JSON.toJSONString(mapParam));
//        String response = HttpUtil.sendPost(url,JSON.toJSONString(mapParam),"uft-8","uft-8",5000);
        if (response == null) {
            resp.setErrorCode(ErrorCodeType.ACTIVITY_SYN_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.ACTIVITY_SYN_ERROR.getText());
            return false;
        }
        try {
            ResponseData responseData = JSON.parseObject(response,
                    ResponseData.class);
            resp.setData(responseData.getData());
            resp.setErrorCode(responseData.getErrorCode());
            resp.setErrorMsg(responseData.getErrorMsg());
            resp.setSuccess(responseData.getSuccess());
            return responseData.getSuccess();
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.ACTIVITY_SYN_RESP_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.ACTIVITY_SYN_RESP_ERROR.getText());
            LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
                    url, requestId, enCode, e);
            return false;
        }
    }

    /**
     * 同步活动信息
     *
     * @param enCode
     * @return
     */
    public boolean activityOldSyn(Integer requestId, String enCode, Response resp) {
        List<Activity> activityList = activityService.getActivityByEnCode(enCode);
        List<String> activityIds = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        for (Activity activity : activityList) {
            if (!OffLineActivity.isOffLineActivity(activity.getActivityId())) {
                activityIds.add(activity.getActivityId());
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("activityId", activity.getActivityId());
                map.put("activityName", activity.getActivityName());
                map.put("enterpriseName", "离线活动");
                mapList.add(map);
            }
        }

        resp.setExtraData(JSON.toJSONString(mapList));
//        String activityIds = activityService.getActIdsByEnCode(enCode);
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("activityIds", activityIds);
        mapParam.put("enCode", enCode);

        String url = PropertiesUtil.getValue("activity.syn");
        if (!activityIds.isEmpty()) {
            String response = doPostJson(requestId, url, JSON.toJSONString(mapParam));
            if (response == null) {
                resp.setErrorCode(ErrorCodeType.ACTIVITY_SYN_ERROR.getValue());
                resp.setErrorMsg(ErrorCodeType.ACTIVITY_SYN_ERROR.getText());
                return false;
            }
            try {
                ResponseData responseData = JSON.parseObject(response,
                        ResponseData.class);
                resp.setData(responseData.getData());
                resp.setErrorCode(responseData.getErrorCode());
                resp.setErrorMsg(responseData.getErrorMsg());
                resp.setSuccess(responseData.getSuccess());
                return responseData.getSuccess();
            } catch (Exception e) {
                resp.setErrorCode(ErrorCodeType.ACTIVITY_SYN_RESP_ERROR.getValue());
                resp.setErrorMsg(ErrorCodeType.ACTIVITY_SYN_RESP_ERROR.getText());
                LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
                        url, requestId, enCode, e);
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 更新机具信息
     *
     * @param requestId
     * @param terminalVo
     * @param resp
     * @return
     */
    public boolean updateTerminal(Integer requestId, TerminalVo terminalVo, Response resp) {

        try {
            switch (terminalVo.getOper()) {
                case OperTerminal.ADD:
                    List<Activity> activityList = activityService.getActivityByStoreId(terminalVo.getStoreId());
                    for (Activity activity : activityList) {
                        terminalVo.setActivityId(activity.getActivityId());
                        activityService.saveTerminal(terminalVo);
                    }
                    break;
                case OperTerminal.UPD:
                    activityService.updateTerminalByEnCode(terminalVo.getEnCode(), terminalVo.getOldEnCode());
                    break;
                case OperTerminal.DEL:
                    activityService.deleteTerminalByEnCode(terminalVo.getEnCode());
                    break;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 更新机具信息
     *
     * @param requestId
     * @param resp
     * @return
     */
    public boolean updateStore(Integer requestId, String oper, String storeId, Response resp) {

        try {
            switch (oper) {
                case OperStore.DEL:
                    activityService.deleteStoreByStoreId(storeId);
                    break;
            }
            return true;
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.DELETE_STORE_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.DELETE_STORE_ERROR.getText());
            return false;
        }
    }

    /**
     * 以json方式post请求接口
     *
     * @param requestId
     * @param url
     * @return
     */

    private String doPostJson(Integer requestId, String url, String param) {
        String bankOff = PropertiesUtil.getValue("bank-off");
        if (!"true".equals(bankOff)) {
            try {
                LOGGER.info("### doPost url={},requestId={},param={} ###", url,
                        requestId, param);
                String response = HttpUtil.doRestfulByHttpConnection(url, param);
                outRequestLogService.save(new OutRequestLog(requestId, url, param));
                outResponseLogService.save(new OutResponseLog(requestId, response));
                return response;
            } catch (Exception e) {
                LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
                        url, requestId, param, e);
                outRequestLogService.save(new OutRequestLog(requestId, url, param));
                outResponseLogService.save(new OutResponseLog(requestId, e
                        .getMessage()));
                return null;
            }
        } else {
            return null;
        }
    }

    private String getPaymentOldForXml(boolean flag, Activity activity, Order order, Store store, String errorCode, String msg) {
        StringBuilder stringBuilder = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table>");
        if (flag) {
            /*String orderid = Calendar.getInstance().getTimeInMillis() + "";
            orderid = orderid + RandomUtil.getString(19 - orderid.length());*/
            stringBuilder.append("<shopname>").append(store.getShopName()).append("</shopname>");
            stringBuilder.append("<shopno>").append(store.getShopId()).append("</shopno>");
            stringBuilder.append("<storename>").append(store.getStoreName()).append("</storename>");
            stringBuilder.append("<imei>").append(order.getEnCode()).append("</imei>");
            stringBuilder.append("<eitemtime>").append("null").append("</eitemtime>");
            stringBuilder.append("<trantype>").append("积分兑换").append("</trantype>");
            stringBuilder.append("<batchno>").append("").append("</batchno>");
            stringBuilder.append("<orderid>").append("000000000000").append("</orderid>");
//            stringBuilder.append("<orderid>").append(order.getOrderId()).append("</orderid>");
            stringBuilder.append("<point>").append(activity.getIntegral()).append("</point>");
            stringBuilder.append("<trantime>").append(DateTimeUtils.getDate()).append("</trantime>");
            stringBuilder.append("<admin>").append("001").append("</admin>");
            stringBuilder.append("<remark>").append("").append("</remark>");
            stringBuilder.append("<eitemname>").append(activity.getActivityName()).append("</eitemname>");
            stringBuilder.append("<bankname>").append(Bank.getBankNameByBankId(activity.getChannel())).append("</bankname>");
            stringBuilder.append("<cardno>").append(order.getCardNum()).append("</cardno>");
            stringBuilder.append("<posno>").append("000000000000").append("</posno>");
//            stringBuilder.append("<posno>").append(order.getOrderId()).append("</posno>");
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

    public void compareRuleAndMax(String ruleName, Integer ruleValue, Integer currentValue, Response resp) {
        switch (ruleName) {
            case ActivityRule.DayMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_DAY_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_DAY_MAX_OUT.getText());
                }
                break;
            case ActivityRule.WeekMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_WEEK_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_WEEK_MAX_OUT.getText());
                }
                break;
            case ActivityRule.MonthMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_MONTH_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_MONTH_MAX_OUT.getText());
                }
                break;
            case ActivityRule.YearMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_YEAR_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_YEAR_MAX_OUT.getText());
                }
                break;
            case ActivityRule.TotalMax:
                if (currentValue >= ruleValue) {
                    resp.setSuccess(false);
                    resp.setErrorCode(ErrorCodeType.ACTIVITY_TOTAL_MAX_OUT.getValue());
                    resp.setErrorMsg(ErrorCodeType.ACTIVITY_TOTAL_MAX_OUT.getText());
                }
                break;
        }
    }
}
