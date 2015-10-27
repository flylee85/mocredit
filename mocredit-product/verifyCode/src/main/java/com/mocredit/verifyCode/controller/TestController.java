package com.mocredit.verifyCode.controller;


import com.mocredit.base.util.UUIDUtils;
import com.mocredit.log.task.SmsSendTask;
import com.mocredit.log.task.VerifyCodeLogTask;
import com.mocredit.verifyCode.model.TVerifyLog;
import com.mocredit.verifyCode.vo.SmsVO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by YHL on 2015/7/6.
 */
@Controller
//@RequestMapping("/test")
public class TestController {

    @RequestMapping("/abc")
    public String test(){
        System.out.println("---------------------------------------------");
        System.out.println("TestController.test");

//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.getBean(VerifyCodeLogTask.class);

//        TVerifyLog verifyLog = new TVerifyLog();
//        verifyLog.setSuccess(false);
//        verifyLog.setStoreId("S00001");
//        verifyLog.setStoreName("门店111");
//        verifyLog.setActivityId("hd0001");
//        verifyLog.setActivityName("活动001");
//        verifyLog.setCode("QM000000001");
//        verifyLog.setVerifyTime( new Date());
//        verifyLog.setId(UUIDUtils.UUID32());
//
//        VerifyCodeLogTask.verifyogList.add( verifyLog);


//        SmsVO s=new SmsVO();
//        s.setAmount( new BigDecimal(1.29));
//        s.setCustormMobile("13111111111");
//        s.setCustormName("张三");
//        s.setCode("ABC");
//        s.setTemplateString("尊敬的用户：{customName}，您于{sysdate} {systime} 成功消费价值{amount}的兑换码{code}");
//        SmsSendTask.smsList.add(s);
        return "test";
    }
}
