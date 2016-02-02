package com.mocredit.integral.testcase;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.adapter.IntegralBankAdapter;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.entity.*;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.HttpRequestService;
import com.mocredit.integral.service.OrderService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ytq on 2016/1/29.
 */
public class IntegralPaymentTest {
    @Tested
    HttpRequestService httpRequstService = new HttpRequestService();
    @Injectable
    private ActivityService activityService;
    @Injectable
    private IntegralBankAdapter integralBankAdapter;
    @Injectable
    private OrderService orderService;
    private static Response resp = new Response();
    private static final String param = "{\"activityId\":\"1\",\"orderId\":\"test_payment\",\"cardNum\":\"62140038786912\",\"amt\":\"100\",\"cardExpDate\":\"0915\",\"enCode\":\"5773e998\"}";
    private static Order order;

    static {
        order = JSON.parseObject(param, Order.class);
    }

    //活动不存在
    @Test
    public void payment1Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                result = null;
            }
        };
        boolean payFlag1 = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag1);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR.getText());
//        new MockUp<ActivityService>() {
//            @SuppressWarnings("unused")
//            @Mock
//            public Activity getByActivityId(String actId) {
//                return new Activity();
//            }
//        }.getMockInstance();


    }

    //活动停止
    @Test
    public void payment2Test() {
        //活动停止
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStatus("02");
                result = activity;
            }
        };
        boolean payFlag2 = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag2);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_ALREADY_STOP.getText());
    }

    //CardBin验证
    @Test
    public void payment3Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setBins("622700");
                activity.setStatus("01");
                result = activity;
            }
        };
        boolean payFlag3 = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag3);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_NOT_EXIST_BANK_CARD.getText());
    }

    // 验证该商户，该门店是否能参加该活动
    @Test
    public void payment4Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setBins("621400");
                activity.setStatus("01");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = null;
            }
        };
        boolean payFlag4 = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag4);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_NOT_EXIST_SHOP_STORE.getText());
    }

    // 验证活动的开始结束时间
    @Test
    public void payment5Test() {

        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1454054747000L));
                activity.setEndTime(new Date(1454054757000L));
                activity.setBins("621400");
                activity.setStatus("01");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        boolean payFlag5 = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag5);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_OUT_DATE.getText());
    }

    //验证活动日使用次数
    @Test
    public void payment6Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1414054747000L));
                activity.setEndTime(new Date(1464054757000L));
                activity.setSelectDate("1,2,3,4,5,6,7");
                activity.setBins("621400");
                activity.setStatus("01");
                activity.setRule("DayMax:1");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        new Expectations() {
            {
                activityService.getTranRecordByActId(anyString);
                List<ActivityTransRecord> tranRecords = new ArrayList<>();
                ActivityTransRecord activityTransRecord = new ActivityTransRecord();
                activityTransRecord.setTransType("DayMax");
                activityTransRecord.setTransCount(1);
                tranRecords.add(activityTransRecord);
                result = tranRecords;
            }
        };
        boolean payFlag6 = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag6);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_DAY_MAX_OUT.getText());
    }

    //验证活动周使用次数
    @Test
    public void payment7Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1414054747000L));
                activity.setEndTime(new Date(1464054757000L));
                activity.setSelectDate("1,2,3,4,5,6,7");
                activity.setBins("621400");
                activity.setStatus("01");
                activity.setRule("WeekMax:1");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        new Expectations() {
            {
                activityService.getTranRecordByActId(anyString);
                List<ActivityTransRecord> tranRecords = new ArrayList<>();
                ActivityTransRecord activityTransRecord = new ActivityTransRecord();
                activityTransRecord.setTransType("WeekMax");
                activityTransRecord.setTransCount(1);
                tranRecords.add(activityTransRecord);
                result = tranRecords;
            }
        };
        boolean payFlag = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_WEEK_MAX_OUT.getText());
    }

    //验证活动月使用次数
    @Test
    public void payment8Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1414054747000L));
                activity.setEndTime(new Date(1464054757000L));
                activity.setSelectDate("1,2,3,4,5,6,7");
                activity.setBins("621400");
                activity.setStatus("01");
                activity.setRule("MonthMax:1");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        new Expectations() {
            {
                activityService.getTranRecordByActId(anyString);
                List<ActivityTransRecord> tranRecords = new ArrayList<>();
                ActivityTransRecord activityTransRecord = new ActivityTransRecord();
                activityTransRecord.setTransType("MonthMax");
                activityTransRecord.setTransCount(1);
                tranRecords.add(activityTransRecord);
                result = tranRecords;
            }
        };
        boolean payFlag = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_MONTH_MAX_OUT.getText());
    }

    //验证活动年使用次数
    @Test
    public void payment9Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1414054747000L));
                activity.setEndTime(new Date(1464054757000L));
                activity.setSelectDate("1,2,3,4,5,6,7");
                activity.setBins("621400");
                activity.setStatus("01");
                activity.setRule("YearMax:1");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        new Expectations() {
            {
                activityService.getTranRecordByActId(anyString);
                List<ActivityTransRecord> tranRecords = new ArrayList<>();
                ActivityTransRecord activityTransRecord = new ActivityTransRecord();
                activityTransRecord.setTransType("YearMax");
                activityTransRecord.setTransCount(1);
                tranRecords.add(activityTransRecord);
                result = tranRecords;
            }
        };
        boolean payFlag = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_YEAR_MAX_OUT.getText());
    }

    //验证活动总使用次数
    @Test
    public void payment10Test() {
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1414054747000L));
                activity.setEndTime(new Date(1464054757000L));
                activity.setSelectDate("1,2,3,4,5,6,7");
                activity.setBins("621400");
                activity.setStatus("01");
                activity.setRule("TotalMax:1");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        new Expectations() {
            {
                activityService.getTranRecordByActId(anyString);
                List<ActivityTransRecord> tranRecords = new ArrayList<>();
                ActivityTransRecord activityTransRecord = new ActivityTransRecord();
                activityTransRecord.setTransType("TotalMax");
                activityTransRecord.setTransCount(1);
                tranRecords.add(activityTransRecord);
                result = tranRecords;
            }
        };
        boolean payFlag = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertFalse(payFlag);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.ACTIVITY_TOTAL_MAX_OUT.getText());
    }

    //验证请求并验证成功
    @Test
    public void payment11Test() {
        resp = new Response();
        new Expectations() {
            {
                activityService.getByActivityId(anyString);
                Activity activity = new Activity();
                activity.setStartTime(new Date(1414054747000L));
                activity.setEndTime(new Date(1464054757000L));
                activity.setSelectDate("1,2,3,4,5,6,7");
                activity.setBins("621400");
                activity.setStatus("01");
                result = activity;
            }
        };
        new Expectations() {
            {
                activityService.getByShopIdStoreIdAcId(anyString, anyString);
                result = new Store();
            }
        };
        new Expectations() {
            {
                integralBankAdapter.getPayment(anyString);
                result = "";
            }
        };
        new Expectations(httpRequstService) {
            {
                invoke(httpRequstService, "doPostJson", anyInt, anyString, anyString);
                result = null;
            }
        };
        new Expectations(httpRequstService) {
            {
                invoke(httpRequstService, "analyJsonReponse", anyInt, anyString, anyString, anyString, anyString, resp);
                result = true;
            }
        };
        new Expectations() {
            {
                orderService.saveAndCount(order);
            }
        };
        boolean payFlag = httpRequstService.doPostJsonAndSaveOrder(-1, param, order, resp);
        Assert.assertTrue(payFlag);
        Assert.assertEquals(resp.getErrorMsg(), null);
    }
}
