package com.mocredit.verifyCode.vo;

import com.mocredit.base.util.TemplateUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 定义一个发送短信的VO
 * Created by YHL on 15/7/31 21:40.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class SmsVO {

    /**
     * 接收人姓名
     */
    private String custormName="";

    /**
     * 接收人手机号
     */
    private String custormMobile="";

    /**
     * 消费金额
     */
    private BigDecimal amount = new BigDecimal(0);

    private String code="";

    /**
     * 存放发送短信的模板
     */
    private String templateString="";

    public Map<TemplateUtil.TemplateField , String > getTemplateMap(){
        Map<TemplateUtil.TemplateField , String > m = new HashMap();
        m.put(TemplateUtil.TemplateField.CUSTOM_NAME, custormName);
        m.put(TemplateUtil.TemplateField.CUSTOM_MOBILE,custormMobile);
        m.put(TemplateUtil.TemplateField.AMOUNT,amount.toString());
        m.put(TemplateUtil.TemplateField.CODE,code);
        return m;
    }


    public String getCustormName() {
        return custormName;
    }

    public void setCustormName(String custormName) {
        this.custormName = custormName;
    }

    public String getCustormMobile() {
        return custormMobile;
    }

    public void setCustormMobile(String custormMobile) {
        this.custormMobile = custormMobile;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
