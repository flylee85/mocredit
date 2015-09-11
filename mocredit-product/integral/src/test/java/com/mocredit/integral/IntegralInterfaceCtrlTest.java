package com.mocredit.integral;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.PropertyUtil;
import com.mocredit.integral.util.HttpRequestUtil;
import com.mocredit.integral.vo.ActivityImportVo;
import com.mocredit.integral.vo.ActivityVo;
import com.mocredit.integral.vo.ConfirmInfoVo;
import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ytq on 2015/8/28.
 */
public class IntegralInterfaceCtrlTest {
    public final static String BASE_URL = "http://localhost:8080/integral/";

    /**
     * bank  银行代码 中信：citic，民生：cmbc
     * device    终端号
     * activityId    活动ID
     * orderId     订单号  当天、当前机具上不重复
     * shopId     销售商户号
     * shopName    销售商户名
     * storeId     门店ID
     * storeName    门店名称
     * cardNum    卡号
     * integral 消费积分
     */
    @Test
    public void paymentTest() throws Exception {
        String jsonStr = "{\"bank\":\"citic\",\"device\":\"10011\",\"activityId\":\"100\",\"orderId\":\"12342\"," +
                "\"shopId\":\"1\",\"shopName\":\"星巴克\",\"storeId\":\"1\",\"storeName\":\"环球中心\",\"cardNum\":\"62140038786912\",\"integral\":\"100\"}";

        HttpRequestUtil.doPostJson(BASE_URL + "payment", jsonStr);
    }

    /**
     * device    终端号
     * orderId  原订单号
     */
    @Test
    public void paymentRevoke() throws Exception {
        String jsonStr = "{\"device\":\"10011\",\"orderId\":\"12342\"}";
        HttpRequestUtil.doPostJson(BASE_URL + "paymentRevoke", jsonStr);
    }

    /**
     * bank    银行代码 中信：citic，民生：cmbc
     * activityId     活动ID
     * cardNum 卡号
     * shopId 商铺id
     */
    @Test
    public void confirmInfoTest() throws Exception {
        ConfirmInfoVo confirmInfoVo = new ConfirmInfoVo();
        confirmInfoVo.setActivityId(100);
        confirmInfoVo.setBank("citic");
        confirmInfoVo.setCardNum("62140038786912");
        confirmInfoVo.setShopId(1246);
        String jsonStr = JSON.toJSONString(confirmInfoVo);
        HttpRequestUtil.doPostJson(BASE_URL + "confirmInfo", jsonStr);
    }

    /**
     * http请求参数
     * {
     * activityId   活动ID
     * activityName 活动名
     * productCode  银行内部代码
     * operCode  操作代码， 1导入 2 更新 3 取消 4 启用
     * startTime  活动开始时间，年月日时分秒yyyy-MM-dd HH:mm:ss
     * endTime   活动结束时间，年月日时分秒yyyy-MM-dd HH:mm:ss
     * selectDate  指定选择日期（周几），如果是周一和周二，则是1,2,如果是周五周六周日，则是5,6,7  使用英文字符分割
     * integral   积分，允许小数
     * maxType    最大类型，暂定01代表每日，02代表每周，03代表每月，空代表不限制
     * maxNumber   最大次数
     * status    01启用，02停止
     * storeList  门店列表
     * [
     * {
     * storeId   门店Id
     * shopId    商户Id
     * }
     * ]
     * 其他活动信息待定
     * <p/>
     * }
     * * operCode为更新时，需要活动的完整信息（包括门店）
     */
    @Test
    public void activityImport() throws Exception {


        String jsonStr = "{\"activityId\":\":actId\",\"activityName\":\"星巴克\",\"productCode\":\"citic\"," +
                "\"operCode\":\"1\",\"selectDate\":\"1,2,3,4,5\",\"integral\":\"1000\",\"maxType\":\"01\"," +
                "\"maxNumber\":\"1000\",\"status\":\"01\",\"startTime\":\"2015-08-01 10:11:11\",\"endTime\":\"2015-08-31 23:10:11\"," +
                "\"storeList\":[{\"shopId\":\"1\",\"storeId\":\"1\"},{\"shopId\":\"2\",\"storeId\":\"2\"},{\"shopId\":\"3\",\"storeId\":\"3\"}]}";
//        ActivityVo activityVo = JSON.parseObject(jsonStr, ActivityVo.class);
//        ActivityImportVo activityVo = JSON.parseObject(jsonStr, ActivityImportVo.class);
        ActivityVo activityVo = new ActivityVo();
        activityVo.setOperCode(1);
        activityVo.setActivityName("星巴克");
        activityVo.setActivityId(111);
        for (int i = 100; i <= 1000; i++) {
//            HttpRequestUtil.doPostJson(BASE_URL + "activityImport", jsonStr.replace(":actId", i + ""));
            HttpRequestUtil.doPostJson(BASE_URL + "activityImport", jsonStr.replace(":actId", i + ""));
        }

    }
}
