package com.mocredit.integral.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.integral.adapter.IntegralBankAdapter;
import com.mocredit.integral.constant.ActivityStatus;
import com.mocredit.integral.vo.OrderVo;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.constant.OrderStatus;
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
                //判断活动是否启用
                if (ActivityStatus.STOP.equals(activity.getStatus())) {
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
                // 3,判断使用次数是否超过最大使用次数限制
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
                }
                // 5,条件判断订单是否已经存在
                if (orderService.isExistOrder(order.getOrderId())) {
                    resp.setErrorCode(ErrorCodeType.EXIST_ORDER_ERROR
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.EXIST_ORDER_ERROR.getText());
                    return false;
                }
            } else {
                resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getValue());
                resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
                        .getText());
                return false;
            }
            url = integralBankAdapter.getPayment(activity.getChannel());
            String response = doPostJson(requestId, url, getPaymentDto(activity, order));
            boolean anaFlag = analyJsonReponse(requestId, url, param, response,
                    resp);
            if (anaFlag) {
                // 设置订单reuestId和交易完成状态
                order.setRequestId(requestId);
                order.setStatus(OrderStatus.PAYMENT.getValue());
                if (!orderService.save(order)) {
                    resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.SAVE_DATEBASE_ERROR
                            .getText());
                    return false;
                } else {
                    return true;
                }
            } else {
                return anaFlag;
            }
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
        paymentDto.setTransAmt(order.getAmt() + "");
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
    public boolean analyJsonReponse(Integer requestId, String url,
                                    String param, String reponse, Response resp) {
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
            // 判断bank端的接口响应是否是001(参数错误),002(系统错误)
            if ("001".equals(resp.getErrorCode())) {
                resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
                resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
            }
            if ("002".equals(resp.getErrorCode())) {
                resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
                resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
            }
            return responseData.getSuccess();
        } catch (Exception e) {
            resp.setErrorCode(ErrorCodeType.ANA_RESPONSE_ERROR.getValue());
            resp.setErrorMsg(ErrorCodeType.ANA_RESPONSE_ERROR.getText());
            LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
                    url, requestId, param, e);
            return false;
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
            Activity activity = activityService.getActivityByOrderId(orderVo.getOrderId());
            url = integralBankAdapter.getPaymentRevoke(activity.getChannel());
            String response = doPostJson(requestId, url, getOrderIdAndOldOrderIdParam(orderVo));
            boolean anaFlag = analyJsonReponse(requestId, url, param, response,
                    resp);
            if (anaFlag) {
                if (!orderService.isExistOrderAndUpdate(orderVo.getOldOrderId()) || !orderService.save(orderVo)) {
                    resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
                            .getValue());
                    resp.setErrorMsg(ErrorCodeType.SAVE_DATEBASE_ERROR
                            .getText());
                    return false;
                } else {
                    return true;
                }
            } else {
                return anaFlag;
            }
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
            Activity activity = activityService.getActivityByOrderId(orderVo.getOrderId());
            url = integralBankAdapter.getPaymentReserval(activity.getChannel());
            String response = doPostJson(requestId, url, getOrderIdAndOldOrderIdParam(orderVo));
            boolean anaFlag = analyJsonReponse(requestId, url, param, response, resp);
            if (anaFlag) {
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
            Activity activity = activityService.getActivityByOrderId(orderVo.getOrderId());
            url = integralBankAdapter.getPaymentRevokeReserval(activity.getChannel());
            String response = doPostJson(requestId, url, getOrderIdAndOldOrderIdParam(orderVo));
            boolean anaFlag = analyJsonReponse(requestId, url, param, response, resp);
            if (anaFlag) {
                orderService.save(orderVo);
            }
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
            boolean anaFlag = analyJsonReponse(requestId, url, param, response, resp);
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
     * 以json方式post请求接口
     *
     * @param requestId
     * @param url
     * @return
     */

    private String doPostJson(Integer requestId, String url, String param) {
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
    }
}
