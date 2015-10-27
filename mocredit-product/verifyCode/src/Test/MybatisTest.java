import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.enums.ActActivityCodeOperType;
import com.mocredit.base.enums.ActivityBlackListsType;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.UUIDUtils;
import com.mocredit.verifyCode.dao.*;
import com.mocredit.verifyCode.model.*;
import com.mocredit.verifyCode.service.ActivityCodeService;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by YHL on 2015/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:applicationContext.xml"
})
public class MybatisTest  {

    @Autowired
    public ActivityCodeMapper activityCodeMapper;

    @Autowired
    public VerifiedCodeMapper verifiedCodeMapper;

    @Autowired
    public ActActivityStoreMapper actActivityStoreMapper;

    @Autowired
    public ActivityCodeBlackListsMapper activityCodeBlackListsMapper;

    @Autowired
    public ActivityCodeService acService;

    @Autowired
    public VerifyLogMapper verifyLogMapper;

    @Autowired
    public ActActivitySynLogMapper actActivitySynLogMapper;

    //测试添加一个码
    public void testAdd(){
        TActivityCode tac=new TActivityCode();
        tac.setId(UUID.randomUUID().toString().replace("-", ""));
        tac.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 30));
        tac.setCodeSerialNumber("200001012");
        tac.setActivityId("00001");
        tac.setActivityName("活动111");
        tac.setMaxNum(1);
        tac.setAmount( new BigDecimal(20));
        tac.setStartTime(DateUtil.strToDate("2015-04-06","yyyy-MM-dd"));
        tac.setEndTime( DateUtil.strToDate("2015-09-05","yyyy-MM-dd"));
        tac.setOrderId("HY039200002");
        tac.setCreateTime( new Date() ) ;
        tac.setIssueEnterpriseId("0000001");
        tac.setIssueEnterpriseName("味多美");
        tac.setCustomMobile("18899998888");
        tac.setReleaseTime( new Date() );

        int i=activityCodeMapper.insertActivityCode(tac);
        System.out.println("添加结果:"+i);
    }

    //查找 券码
    public void testList_findActivityCodeByCode(){
        List<TActivityCode> tal=activityCodeMapper.findActivityCodeByCode("4244a9a90ea14039a32ec0a5d78ce2");

        SimplePropertyPreFilter spfilter=new SimplePropertyPreFilter(TActivityCode.class);
        spfilter.getExcludes().add("effective");
        for(TActivityCode t:tal){
            System.out.println(JSON.toJSONString(t, spfilter) );
        }
        System.out.println("123abc啊啊啊");
    }

    //测试添加 验码记录
    public void testAddVerifiedCode(){
        List<TActivityCode> tal=activityCodeMapper.findActivityCodeByCode("4244a9a90ea14039a32ec0a5d78ce2");
        if( tal.size()>0){
            TActivityCode tac=tal.get(0);
            TVerifiedCode tvc=new TVerifiedCode();

            tvc.setId(UUID.randomUUID().toString().replace("-",""));
            tvc.setCode(tac.getCode());
            tvc.setCodeSerialNumber(tac.getId());
            tvc.setVerifyTime( new Date() );
            tvc.setActivityId( tac.getActivityId());
            tvc.setActivityName(tac.getActivityName());
            tvc.setAmount(tac.getAmount());
            tvc.setDevice("AAABBBCC"); //机具id
            tvc.setStoreId("00000001"); //门店ID
            tvc.setStartTime( tac.getStartTime() );
            tvc.setEndTime( tac.getEndTime() );
            tvc.setNumber(1);
            tvc.setRequestSerialNumber("aaaaa"); //POS请求序列号
            tvc.setIssueEnterpriseId( tac.getIssueEnterpriseId() );
            tvc.setIssueEnterpriseName( tac.getIssueEnterpriseName() );
            tvc.setShopId("0001"); //执行企业的编号
            int result=verifiedCodeMapper.insertVerifiedCode(tvc);

            System.out.println("操作结果："+result);
        }else{
            System.out.println("没有对应的券码");
        }
    }

    //测试验码核销
    @Test
    public void testVerifyCode(){
        BigDecimal amount = new BigDecimal(12.00);
        int useCount=2;
        String device="JIJU00000001";
        String store_id="STORE00001";
        String store_name="STORE分店1";
        String request_serial_number="POSliushui0001";
        String executive_enterprise_id="duihuan000001";
        String executive_enterprise_name="兑换企业000001";
        String code="4244a9a90ea14039a32ec0a5d78ce2";

//        AjaxResponseData ard= this.acService.useActivityCode(amount,useCount,device,store_id
//                ,store_name,request_serial_number,executive_enterprise_id,executive_enterprise_name,code);
//        System.out.println( JSON.toJSONString(ard));
//
//        AjaxResponseData ard2= this.acService.useActivityCode(new BigDecimal(23.00),1,device,store_id
//                ,store_name,request_serial_number,executive_enterprise_id,executive_enterprise_name,code);
//        System.out.println( JSON.toJSONString(ard2));
//        AjaxResponseData ard3= this.acService.useActivityCode(new BigDecimal(13.00),1,device,store_id
//                ,store_name,request_serial_number,executive_enterprise_id,executive_enterprise_name,code);
//        System.out.println( JSON.toJSONString(ard3));
//

        List<Thread > threads=new ArrayList<Thread>();
        for( int i=0;i<1;i++){
            VerifyCodeThread tt=new VerifyCodeThread();
            tt.setAcService(this.acService);
            tt.setName(i+"");
            threads.add(tt);
        }

//        for(Thread tt: threads ){
//            tt.start();
//        }

        for (Thread th : threads) {
            try {
                th.start();
                th.join();
                System.out.println("线程："+th.getName()+"启动");
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }



    }

    @Test
    public void testSelectActivityStore(){
        List<ActActivityStore> as=actActivityStoreMapper.findByActivityId("5");

        System.out.println("-------------"+as.size());
        for( ActActivityStore a:as){
            System.out.println("obj="+ a.toString());
        }
    }

    /**
     * 测试删除活动，添加到黑名单表
     */
    @Test
    public void testAddBlackLists(){
        //int c=activityCodeBlackListsMapper.addBlackListsByActivityId("00001",ActivityBlackListsType.CANCEL.getValue(),ActivityBlackListsType.CANCEL.getText());
        AjaxResponseData ard=this.acService.deleteActActivityCodeWithTran("00001");
        System.out.println("###:"+JSON.toJSONString(ard));
    }


    /**
     * 测试导入
     */
    @Test
    public void testCodeImport(){
        ActActivityCodeVO aavo = new ActActivityCodeVO();
        aavo.setOperType(ActActivityCodeOperType.IMPORT);
        aavo.setActivityId("AC0001");

        //new 一个code list
        List<TActivityCode> acList = new ArrayList<TActivityCode>();
        for(int i=0;i<100 ; i++){
            TActivityCode tac=new TActivityCode();
            tac.setId(UUID.randomUUID().toString().replace("-", ""));
            tac.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 30));
            tac.setCodeSerialNumber("200001012");
            tac.setActivityId("00001");
            tac.setActivityName("活动111");
            tac.setMaxNum(1);
            tac.setAmount( new BigDecimal(20));
            tac.setStartTime(DateUtil.strToDate("2015-04-06","yyyy-MM-dd"));
            tac.setEndTime( DateUtil.strToDate("2015-09-05","yyyy-MM-dd"));
            tac.setOrderId("HY039200002");
            tac.setCreateTime( new Date() ) ;
            tac.setIssueEnterpriseId("0000001");
            tac.setIssueEnterpriseName("味多美");
            tac.setCustomMobile("18899998888");
            tac.setReleaseTime( new Date() );
            acList.add(tac);
        }
        List<ActActivityStore> aasList = new ArrayList<ActActivityStore>();
        //new store
        for( int i=0 ;i<10;i++){
            ActActivityStore aas = new ActActivityStore();
            aas.setActivityId("00001");
            aas.setShopId("SHOP001");
            aas.setEnterpriseId("E001");
            aas.setStoreId("STORE001");
            aasList.add(aas);
        }

        aavo.setActActivityStores(aasList);
//        aavo.setActivityCodeList(acList);
//        System.out.println("############");
        this.actActivityStoreMapper.batchSave(null);
        System.out.println(JSON.toJSONStringWithDateFormat(aavo,DateUtil.FORMAT_YYYYMMDD_HHMMSS));

    }


    @Test
    public  void testAddVerifyLog(){
        TVerifyLog verifyLog = new TVerifyLog();
        verifyLog.setSuccess(false);
        verifyLog.setStoreId("S00001");
        verifyLog.setStoreName("门店111");
        verifyLog.setActivityId("hd0001");
        verifyLog.setActivityName("活动001");
        verifyLog.setCode("QM000000001");
        verifyLog.setVerifyTime( new Date());
        verifyLog.setId(UUIDUtils.UUID32());
        List<TVerifyLog> l = new ArrayList<TVerifyLog>();
        l.add(verifyLog);
        int c= verifyLogMapper.batchSave(l);

        System.out.println("#### c="+c);
    }

    /**
     * 测试添加 码导入删除的同步日志
     */
    @Test
    public void testActActivityLog(){

        ActActivitySynLog actActivitySynLog = new ActActivitySynLog();
        actActivitySynLog.setId(UUIDUtils.UUID32());
        actActivitySynLog.setSuccess(false);
        actActivitySynLog.setOperType(ActActivityCodeOperType.IMPORT);
        actActivitySynLog.setSynNum(1);
        List<ActActivitySynLog> l = new ArrayList<ActActivitySynLog>();
        l.add(actActivitySynLog);

        int c=actActivitySynLogMapper.batchSave(l);

        System.out.println("#####:"+c);
    }


}
