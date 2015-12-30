import com.alibaba.fastjson.JSON;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.verifyCode.service.ActivityCodeService;

import java.math.BigDecimal;

/**
 * Created by YHL on 15/7/9 20:40.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class VerifyCodeThread extends  Thread {

    BigDecimal amount = new BigDecimal(12.00);
    int useCount=2;
    String device="JIJU00000001";
    String store_id="STORE00001";
    String store_name="STORE分店1";
    String request_serial_number="POSliushui0001";
    String executive_enterprise_id="duihuan000001";
    String executive_enterprise_name="兑换企业000001";
    String code="f746ba9d08934b54a692d310e07bb7";


    private String name;

    private ActivityCodeService acService;

    @Override
    public void run(){

        AjaxResponseData ard3= acService.useActivityCodeWithTran(new BigDecimal(3.00), 1, device, store_id,"11"
                , store_name, request_serial_number, executive_enterprise_id, executive_enterprise_name, code);
        System.out.println( JSON.toJSONString(ard3));
    }

    public ActivityCodeService getAcService() {
        return acService;
    }

    public void setAcService(ActivityCodeService acService) {
        this.acService = acService;
    }
}
