package com.mocredit.integral.testcase;


import com.alibaba.fastjson.JSON;
import com.mocredit.integral.adapter.IntegralBankAdapter;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.service.ActivityService;
import com.mocredit.integral.service.HttpRequestService;
import com.mocredit.integral.service.OrderService;
import com.mocredit.integral.vo.OrderVo;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Created by ytq on 2016/1/29.
 * 积分消费撤销
 */
public class IntegralPaymentRevokeTest {
    @Tested
    HttpRequestService httpRequstService = new HttpRequestService();
    @Injectable
    private ActivityService activityService;
    @Injectable
    private IntegralBankAdapter integralBankAdapter;
    @Injectable
    private OrderService orderService;
    private static Integer requestId = -1;
    private static Response resp = new Response();
    private static final String param = "{\"oldOrderId\":\"test_payment_revoke\",\"orderId\":\"test_payment\"}";
    private static OrderVo order;

    static {
        order = JSON.parseObject(param, OrderVo.class);
    }

    //撤销订单不存在
    @Test
    public void testPaymentRevoke1() {
        new Expectations() {
            {
                orderService.isExistOrder(anyString);
                result = false;
            }
        };
        boolean flag = httpRequstService.paymentRevokeJson(requestId, param, order, resp);
        Assert.assertFalse(flag);
        Assert.assertEquals(resp.getErrorMsg(), ErrorCodeType.NOT_EXIST_ORDER_ERROR
                .getText());
    }

    //订单已撤销
    @Test
    public void testPaymentRevoke2() {
        new Expectations() {
            {
                orderService.isExistOrder(anyString);
                result = true;
            }
        };
        new Expectations() {
            {
                orderService.isExistOldOrder(anyString);
                result = true;
            }
        };
        boolean flag = httpRequstService.paymentRevokeJson(requestId, param, order, resp);
        Assert.assertFalse(flag);
        Assert.assertEquals(resp.getErrorMsg(), "该订单已撤销");
    }

    //订单已撤销
    @Test
    public void testPaymentRevoke3() {
        new Expectations() {
            {
                orderService.isExistOrder(anyString);
                result = true;
            }
        };
        new Expectations() {
            {
                orderService.isExistOldOrder(anyString);
                result = false;
            }
        };
        new Expectations() {
            {
                activityService.getActivityByOrderId(anyString);
                result = new Activity();
            }
        };
        new Expectations() {
            {
                integralBankAdapter.getPaymentRevoke(anyString);
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
                orderService.savePaymentRevoke(order);
            }
        };
        boolean flag = httpRequstService.paymentRevokeJson(requestId, param, order, resp);
        Assert.assertTrue(flag);
    }
}
