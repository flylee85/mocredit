package com.yimeihuijin.codeandbonusapp.model.vo;


import com.yimeihuijin.codeandbonusapp.model.DeviceModel;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Chanson on 2015/12/18.
 */
public class VO {

    /**
     * 积分消费请求VO
     */
    public static class BonusConsumeObject {
        public String orderId;
        public String cardNo;
        public String exp_date;
        public String activitId;
        public String amt;
        public String orgOrderId;

        public BonusConsumeObject() {
            resetOrderId();
        }

        public void resetOrderId() {
            orderId = StringUtils.getOrderId();
        }
    }

    /**
     * 积分消费响应VO
     */
    public static class BonusConsumeResponseObject {
        public boolean success;
        public String state;
        public String errorCode;
        public String errorMsg;
        public String errorMes;
        public String printInfo;
        public String qr;
        public String data;
    }

    /**
     * 验码请求VO
     */
    public static class CodeConsumeObject {

        public CodeConsumeObject() {
            orderId = StringUtils.getOrderId();
        }

        public String orderId;
        public String code;
        public String ticketType = "03";
    }

    /**
     * 验码响应VO
     */
    public static class CodeConsumeResponseObject {
        public String title;
        public String rtnFlag;
        public String errorMes;
        public String orderId;
        public String ymOrderId;
        public String expData;
        public String des;
        public String amount;
        public String printInfo;
        public String erweima;
        public String posSuccessMsg;
        public String posno;
        public String mmsid;
        public String batchno;
    }

    /**
     * 验码撤销请求VO
     */
    public static class CodeRevoke {
        public CodeRevoke(String orderId){
            device = DeviceModel.getInstance().getDevice().en;
            requestSerialNumber = orderId;
        }
        public String requestSerialNumber;
        public String device;
        public String posno;
    }

    /**
     * 验码撤销响应VO
     */
    public static class ARO {

        public List<AO> data;
        public String errorMsg;
        public boolean success;
    }

    /**
     * 活动信息VO
     */
    public static class AO {
        public String activityId;
        public String activityCode;
        public String activityOutCode;
        public String activityType;
        public String amt;
        public String activityName;
        public String enterpriseName;
        public String enterpriseCode;
        public String storeCode;
        public String storeName;
        public String shopCode;
        public String shopName;
        public String deviceCode;
        public String storeId;
        public String sTime;
        public String eTime;
        public String selectDate;
        public String remark;
        public String cardBin;
        public String receiptTitle;
        public String receiptPrint;
        public String posSuccessMsg;
        public String activityStyle;
        public String amtLimit;
        public String discount;

        public String activitId;
        public String activitName;
        public String activit;
        public String activitRule;
    }


    public static class ActivityId {
        public String activityId;
        public String orderId;
    }

    /**
     * 签到响应VO
     */
    public class SO {

        public String state;
        public String newakey;
        public String jieruhao;
    }
    /**
     * 签到回执响应VO
     */
    public static class SRO {

        public String oldAkey;
        public String state;
        public String newAkey;
    }

    /**
     * 加密测试响应VO
     */
    public static class ETO {
        public String uuid;
        public String time;

        public ETO() {
            uuid = StringUtils.getRKey(16);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date date = new Date(System.currentTimeMillis());
            time = sdf.format(date);
        }
    }
}
