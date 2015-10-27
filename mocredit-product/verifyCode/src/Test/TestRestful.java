import com.alibaba.fastjson.JSON;
import com.mocredit.base.enums.ActActivityCodeOperType;
import com.mocredit.base.util.DateUtil;
import com.mocredit.verifyCode.model.ActActivityStore;
import com.mocredit.verifyCode.model.TActivityCode;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试 restful请求方式的 码导入。
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TestRestful {

    public static void main(String args[]) throws Exception {
        Date beginDate = new Date();

        ActActivityCodeVO actActivityCodeVO=new ActActivityCodeVO() ;
        actActivityCodeVO.setActivityId("0000000000000001");
        actActivityCodeVO.setActivityName("活动测试1");
        actActivityCodeVO.setTicketTitle("小票标题");
        actActivityCodeVO.setTicketContent("小票模板");
        actActivityCodeVO.setSuccessSmsMsg("发送短信的模板");
        actActivityCodeVO.setPosSuccessMsg("pos成功的提示");
        actActivityCodeVO.setOperType(ActActivityCodeOperType.IMPORT);
        actActivityCodeVO.setAmount( new BigDecimal(100));
        actActivityCodeVO.setContractId("HT00001");
        actActivityCodeVO.setEnterpriseId("QY00001");
        actActivityCodeVO.setEnterpriseName("企业001");
        actActivityCodeVO.setMaxNumber(10);
        actActivityCodeVO.setSelectDate("1,2,3,5");
        actActivityCodeVO.setStartTime(new Date());
        actActivityCodeVO.setEndTime( new Date());


        //定义门店
        List<ActActivityStore> stores = new ArrayList<ActActivityStore>();
        for( int i=0 ;i<20 ;i++ ){
            ActActivityStore actActivityStore = new ActActivityStore();
            actActivityStore.setShopId("SHOP_"+i);
            actActivityStore.setActivityId(actActivityCodeVO.getActivityId());
            actActivityStore.setEnterpriseId(actActivityCodeVO.getEnterpriseId());
            actActivityStore.setStoreId("STORE_"+i);
            stores.add(actActivityStore);
        }

        List<TActivityCode> activityCodes = new ArrayList<TActivityCode>();
        for( int i=0 ;i<600000 ;i++){
            TActivityCode activityCode = new TActivityCode();
            activityCode.setActivityId(actActivityCodeVO.getActivityId());
            activityCode.setActivityName(actActivityCodeVO.getActivityName());
            activityCode.setAmount(actActivityCodeVO.getAmount());
            activityCode.setMaxNum(actActivityCodeVO.getMaxNumber());
            activityCode.setCode("code_" + i);
            activityCode.setContractId(actActivityCodeVO.getContractId());
            activityCode.setStartTime(actActivityCodeVO.getStartTime());
            activityCode.setEndTime(actActivityCodeVO.getEndTime());
            activityCodes.add(activityCode);
        }

        actActivityCodeVO.setActActivityStores(stores);
        actActivityCodeVO.setActivityCodeList(activityCodes);

        String json= JSON.toJSONStringWithDateFormat(actActivityCodeVO, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
//        System.out.println(json);

//需要请求的restful地址
        URL url = new URL("http://192.168.1.114:8080/verify_code/ActivityCode/importActActivityCode");


        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");

//        String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";

        OutputStream outputStream = httpConnection.getOutputStream();
        outputStream.write(json.getBytes());
        outputStream.flush();

        if (httpConnection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + httpConnection.getResponseCode());
        }

        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                (httpConnection.getInputStream())));

        String output;
        System.out.println("Output from Server:\n");
        while ((output = responseBuffer.readLine()) != null) {
            System.out.println(output);
        }

        httpConnection.disconnect();
        Date endDate1=new Date();
        System.out.println("生成所需要的时间：" + (endDate1.getTime() - beginDate.getTime()));

    }

}
