import com.alibaba.fastjson.JSON;
import com.mocredit.base.enums.ActActivityCodeOperType;
import com.mocredit.base.enums.ErrorCode;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.TemplateUtil;
import com.mocredit.verifyCode.model.ActActivityStore;
import com.mocredit.verifyCode.model.TActivityCode;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by YHL on 15/7/9 10:57.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TestMain {

    public static void main(String args[]){
////        System.out.println(DateUtil.strToDate("2015-04-06","yyyy-MM-dd"));
//
//        ActActivityCodeVO actActivityCodeVO =new ActActivityCodeVO();
//        List<TActivityCode>  activityCodes=new ArrayList<TActivityCode>();
//        TActivityCode tac=new TActivityCode();
//        tac.setId(UUID.randomUUID().toString().replace("-", ""));
//        tac.setCode(UUID.randomUUID().toString().replace("-", "").substring(0, 30));
//        tac.setCodeSerialNumber("200001012");
//        tac.setActivityId("00001");
//        tac.setActivityName("活动111");
//        tac.setMaxNum(1);
//        tac.setAmount( new BigDecimal(20));
//        tac.setStartTime(DateUtil.strToDate("2015-04-06","yyyy-MM-dd"));
//        tac.setEndTime( DateUtil.strToDate("2015-09-05","yyyy-MM-dd"));
//        tac.setOrderId("HY039200002");
//        tac.setCreateTime( new Date() ) ;
//        tac.setIssueEnterpriseId("0000001");
//        tac.setIssueEnterpriseName("味多美");
//        tac.setCustomMobile("18899998888");
//        tac.setReleaseTime( new Date() );
//        activityCodes.add( tac );
//        activityCodes.add( tac );
//        actActivityCodeVO.setActivityCodeList(activityCodes);
//
//        //设置store
//        List<ActActivityStore> actActivityStores=new ArrayList<ActActivityStore>();
//        ActActivityStore actActivityStore = new ActActivityStore();
//        actActivityStore.setStoreId("11111");
//        actActivityStore.setEnterpriseId("222222");
//        actActivityStore.setActivityId("3333333");
//        actActivityStore.setShopId("4444");
//        actActivityStores.add(actActivityStore);
//        actActivityStores.add(actActivityStore);
//
//        actActivityCodeVO.setActActivityStores(actActivityStores);
//        actActivityCodeVO.setActivityId("11111");
//        actActivityCodeVO.setOperType(ActActivityCodeOperType.IMPORT);
//
////        System.out.println(JSON.toJSONString(actActivityCodeVO));
//        System.out.println(JSON.toJSONStringWithDateFormat(actActivityCodeVO,"yyyy-MM-dd HH:mm:ss"));

//        System.out.println(Calendar.getInstance(TimeZone.getTimeZone("GMT8")).get(Calendar.DAY_OF_WEEK));
//        System.out.println( new Date().getDay());
//        System.out.println( DateUtil.getWeekDayForToday());

//        Date date = new Date();
////        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        String day = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
//        String time = (new SimpleDateFormat("HH:mm:ss")).format(date);
//        System.out.println( day +" "+time );

        Map m=new HashMap();
       // m.put(TemplateUtil.TemplateField.REMARK,"111");
//        m.put("aa",null);
//        m.put("bb","");
//        System.out.println(JSON.toJSONString(m));


        String s=TemplateUtil.buildStringFromTemplate(m," 备注：{remark}");
        System.out.println(s);
    }
}
