import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.UUIDUtils;
import com.mocredit.verifyCode.dao.*;
import com.mocredit.verifyCode.model.ActActivityStore;
import com.mocredit.verifyCode.model.TActivityInfo;
import com.mocredit.verifyCode.service.ActivityCodeService;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.plugin2.message.MarkTaintedMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试活动
 * Created by YHL on 15/7/24 15:23.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:applicationContext.xml"
})
public class ActivityTest {
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

    @Autowired
    public  ActivityInfoMapper activityInfoMapper;

    @Test
    public void testUpdateActivity(){

        ActActivityCodeVO aacVO=new ActActivityCodeVO();
        aacVO.setActivityId("0001");
        aacVO.setActivityName("HD001");
        aacVO.setEnterpriseId("QY0001");
        aacVO.setEnterpriseName("企业1");
        aacVO.setStartTime(DateUtil.strToDate("2015-01-01"));
        aacVO.setEndTime(DateUtil.strToDate("2015-07-07"));
        aacVO.setContractId("HT0001");
        aacVO.setAmount( new BigDecimal(128));
        aacVO.setSelectDate("6,7");

        List<ActActivityStore> aasList=new ArrayList<ActActivityStore>();
        ActActivityStore aas=new ActActivityStore();
        aas.setStoreId("STORE001");
        aas.setEnterpriseId("QY0001");
        aas.setActivityId("0001");
        aas.setShopId("SHOP001");

        aasList.add( aas );
//        aasList.add( new ActActivityStore());
        aacVO.setActActivityStores(aasList);

        //System.out.println(JSON.toJSONString(aacVO));


        acService.updateActActivityWithTran(aacVO);


    }

    /**
     * 测试活动信息的保存
     */
    @Test
    public void testInfo_save(){
        TActivityInfo activityInfo = new TActivityInfo();
        activityInfo.setId(UUIDUtils.UUID32());
        activityInfo.setActivityId("1111111111");
        activityInfo.setTicketTitle("title");
        activityInfo.setTicketContent("content");
        activityInfo.setPosSuccessMsg("pos msg");
        activityInfo.setSuccessSmsMsg("succ msg");
        int i= activityInfoMapper.save(activityInfo);
        System.out.println("保存："+i);

        // 测试查找

        List<TActivityInfo> infos=null;
        infos=activityInfoMapper.findByActivityId("1111111111");
        System.out.println("size:"+infos.size());
        activityInfoMapper.deleteByActivityId("1111111111");

        infos=activityInfoMapper.findByActivityId("1111111111");
        System.out.println("new size:"+infos.size());
    }
}
