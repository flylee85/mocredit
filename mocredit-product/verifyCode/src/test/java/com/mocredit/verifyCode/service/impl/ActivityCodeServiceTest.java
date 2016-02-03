package com.mocredit.verifyCode.service.impl;

import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.verifyCode.dao.*;
import com.mocredit.verifyCode.model.ActActivityStore;
import com.mocredit.verifyCode.model.TActivityCode;
import com.mocredit.verifyCode.model.TActivityInfo;
import com.mocredit.verifyCode.model.TVerifiedCode;
import com.mocredit.verifyCode.service.ActivityCodeService;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.apache.ibatis.annotations.Param;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试活动
 * Created by YHL on 15/7/24 15:23.
 *
 * @version 1.0
 * @Auth liaoy
 */
@RunWith(JMockit.class)
public class ActivityCodeServiceTest {
    @Tested
    ActivityCodeService acService = new ActivityCodeServiceImpl();
    @Injectable
    private ActivityInfoMapper activityInfoMapper;
    @Injectable
    private ActivityCodeMapper acm; // 券码mapper

    @Injectable
    private VerifiedCodeMapper vcm; // 验码记录mapper

    @Injectable
    private ActActivityStoreMapper actActivityStoreMapper;

    @Injectable
    private ActivityCodeBlackListsMapper activityCodeBlackListsMapper;

    @Test
    public void testVerifyCodeForNew() {
        final String device = "device001";
        final String orderId = "orderId001";
        final String code = "10101010101010ss1010";
        new MockUp<HttpUtil>() {
            @Mock
            public String doRestful(String url, String jsonStr) {
                return "{\"success\":true,\"data\":\"1\"}";
            }
        };
        new MockUp<PropertiesUtil>() {
            @Mock
            public String getValue(String key) {
                return "1";
            }
        };
//        new MockUp<ActivityCodeMapper>() {
//            @Mock
//            public List<TActivityCode> findActivityCodeByCode(String code) {
//               return findByCodeMock(code);
//            }
//            @Mock
//            public TActivityCode selectActivityCodeForUpdateById(String id){
//                return new TActivityCode();
//            }
//        };
//        new MockUp<ActivityInfoMapper>() {
//            @Mock
//            public List<TActivityInfo> findByActivityId(@Param("activity_id") String activity_id) {
//                return findByActivityIdMock("1");
//            }
//        };
//        new MockUp<ActActivityStoreMapper>() {
//            @Mock
//            public ActActivityStore findByActivityIdAndStoreId(Map<String, Object> param) {
//                return findByActivityIdAndStoreMock();
//            }
//        };
        new Expectations() {
            {
                acm.findActivityCodeByCode(code);
                result = findByCodeMock(code);
            }
        };
        new Expectations() {
            {
                activityInfoMapper.findByActivityId(anyString);
                result = findByActivityIdMock("1");
            }
        };
        new Expectations() {
            {
                acm.selectActivityCodeForUpdateById(anyString);
                result = new TActivityCode();
                ;
            }
        };
        new Expectations() {
            {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("activityId", "1");
                param.put("storeId", "1");
                actActivityStoreMapper.findByActivityIdAndStoreId(param);
                result = findByActivityIdAndStoreMock();
            }
        };

        new Expectations() { {
            acm.updateActivityCode(withAny(new TActivityCode()));
            result=1;
        }};
//        new Expectations() { {
//            vcm.insertVerifiedCode(new TVerifiedCode());
//            result=1;
//        }};
        AjaxResponseData ajaxResponseData = acService.verifyCodeForNewPos(device, orderId, code);
        Assert.assertTrue(ajaxResponseData.getSuccess());
        System.out.println("testVerifyCodeForNew:"  + acService);
    }
    @Test
    public void testVerifyCodeForOld() {
        final String device = "device001";
        final String bachNo = "00000001";
        final String searchNo="00000001";
        final String code = "10101010101010ss1010";
        new MockUp<HttpUtil>() {
            @Mock
            public String doRestful(String url, String jsonStr) {
                return "{\"success\":true,\"data\":\"1\"}";
            }
        };
        new MockUp<PropertiesUtil>() {
            @Mock
            public String getValue(String key) {
                return "1";
            }
        };
//        new MockUp<ActivityCodeMapper>() {
//            @Mock
//            public List<TActivityCode> findActivityCodeByCode(String code) {
//               return findByCodeMock(code);
//            }
//            @Mock
//            public TActivityCode selectActivityCodeForUpdateById(String id){
//                return new TActivityCode();
//            }
//        };
//        new MockUp<ActivityInfoMapper>() {
//            @Mock
//            public List<TActivityInfo> findByActivityId(@Param("activity_id") String activity_id) {
//                return findByActivityIdMock("1");
//            }
//        };
//        new MockUp<ActActivityStoreMapper>() {
//            @Mock
//            public ActActivityStore findByActivityIdAndStoreId(Map<String, Object> param) {
//                return findByActivityIdAndStoreMock();
//            }
//        };
        new Expectations() {
            {
                acm.findActivityCodeByCode(code);
                result = findByCodeMock(code);
            }
        };
        new Expectations() {
            {
                activityInfoMapper.findByActivityId(anyString);
                result = findByActivityIdMock("1");
            }
        };
        new Expectations() {
            {
                acm.selectActivityCodeForUpdateById(anyString);
                result = new TActivityCode();
                ;
            }
        };
        new Expectations() {
            {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("activityId", "1");
                param.put("storeId", "1");
                actActivityStoreMapper.findByActivityIdAndStoreId(param);
                result = findByActivityIdAndStoreMock();
            }
        };

        new Expectations() { {
            acm.updateActivityCode(withAny(new TActivityCode()));
            result=1;
        }};
//        new Expectations() { {
//            vcm.insertVerifiedCode(new TVerifiedCode());
//            result=1;
//        }};
        String result = acService.verifyCodeForOldPos(bachNo, searchNo, device, code);
        String success=null;
        try {
            Document doc = DocumentHelper.parseText(result);
            Element root = doc.getRootElement();
            Element successElement = root.element("Table").element("isSuccess");
            success=successElement.getText();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Assert.assertTrue("true".equals(success));
        System.out.println("testVerifyCodeForNew:"  + acService);
    }
    @Test
    public void testVerifyCodeForRecharge() {
        final String orderId = "CZ100120521095105";
        final String phone="18628322244";
        final String code = "10101010101010ss1010";
        new MockUp<HttpUtil>() {
            @Mock
            public String doRestful(String url, String jsonStr) {
                return "{\"success\":true,\"data\":\"1\"}";
            }
        };
        new MockUp<PropertiesUtil>() {
            @Mock
            public String getValue(String key) {
                return "1";
            }
        };
//        new MockUp<ActivityCodeMapper>() {
//            @Mock
//            public List<TActivityCode> findActivityCodeByCode(String code) {
//               return findByCodeMock(code);
//            }
//            @Mock
//            public TActivityCode selectActivityCodeForUpdateById(String id){
//                return new TActivityCode();
//            }
//        };
//        new MockUp<ActivityInfoMapper>() {
//            @Mock
//            public List<TActivityInfo> findByActivityId(@Param("activity_id") String activity_id) {
//                return findByActivityIdMock("1");
//            }
//        };
//        new MockUp<ActActivityStoreMapper>() {
//            @Mock
//            public ActActivityStore findByActivityIdAndStoreId(Map<String, Object> param) {
//                return findByActivityIdAndStoreMock();
//            }
//        };
        new Expectations() {
            {
                acm.findActivityCodeByCode(code);
                result = findByCodeMock(code);
            }
        };
        new Expectations() {
            {
                activityInfoMapper.findByActivityId(anyString);
                result = findByActivityIdMock("1");
            }
        };
        new Expectations() {
            {
                acm.selectActivityCodeForUpdateById(anyString);
                result = new TActivityCode();
                ;
            }
        };
        new Expectations() { {
            acm.updateActivityCode(withAny(new TActivityCode()));
            result=1;
        }};
//        new Expectations() { {
//            vcm.insertVerifiedCode(new TVerifiedCode());
//            result=1;
//        }};
        AjaxResponseData ad = acService.verifyCodeForRecharge(orderId, code, phone);
        Assert.assertTrue(ad.isSuccess());
    }

    private List<TActivityCode> findByCodeMock(String code) {
        List<TActivityCode> list = new ArrayList<TActivityCode>();
        TActivityCode ac = new TActivityCode();
        ac.setStatus("01");
        ac.setActivityCode("HD001");
        ac.setActivityId("1");
        ac.setActivityName("测试活动");
        ac.setAmount(new BigDecimal(25));
        ac.setCodeSerialNumber("12124");
        ac.setContractId("1");
        ac.setCreateTime(DateUtil.strToDate("2016-01-29 14:30:00"));
        ac.setCustomMobile("18628322244");
        ac.setCustomName("张三");
        ac.setStartTime(DateUtil.strToDate("2016-01-28 14:30:00"));
        ac.setEndTime(DateUtil.strToDate("2099-02-01 14:30:00"));
        ac.setExchangeChannel("1,2");
        ac.setIssueEnterpriseId("1");
        ac.setIssueEnterpriseName("测试企业");
        ac.setCode(code);
        ac.setOutCode("HD001");
        ac.setSelectDate("1,2,3,4,5,6,7");
        list.add(ac);
        return list;
    }

    private List<TActivityInfo> findByActivityIdMock(String activityId) {
        List<TActivityInfo> list = new ArrayList<TActivityInfo>();
        TActivityInfo ta = new TActivityInfo();
        ta.setId("1");
        ta.setStatus("01");
        ta.setActivityId(activityId);
        ta.setPosSuccessMsg("消费成功");
        ta.setSuccessSmsMsg("消费成功");
        ta.setTicketContent("消费成功");
        ta.setTicketTitle("消费成功");
        list.add(ta);
        return list;
    }

    private ActActivityStore findByActivityIdAndStoreMock() {
        ActActivityStore aas = new ActActivityStore();
        aas.setActivityId("1");
        aas.setEnterpriseId("1");
        aas.setShopCode("SC01");
        aas.setShopId("1");
        aas.setShopName("测试商户");
        aas.setStoreCode("ST01");
        aas.setStoreId("1");
        aas.setStoreName("测试门店");
        return aas;
    }
}
