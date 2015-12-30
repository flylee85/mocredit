package com.mocredit.base.enums;

/**
 * Created by YHL on 15/8/11 10:11.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public enum  ErrorCode {
    CODE_00("00","承兑或交易成功"),
    CODE_03("03","无效商户"),
    CODE_13("13","无效金额"),
    CODE_14("14","无效的码"),
    CODE_15("15","无效卡号"),
    CODE_16("16","无效活动"),
    CODE_25("25","找不到原始交易"),
    CODE_30("30","报文格式错误"),
    CODE_51("51","资金不足"),
    CODE_58("58","不允许终端进行交易"),
    CODE_59("59","不允许门店兑换"),
    CODE_60("60","非活动日"),
    CODE_61("61","超出金额限制"),
    CODE_64("64","原始金额错误"),
    CODE_94("94","重复交易"),
    CODE_97("97","终端号找不到"),
    CODE_98("98","通讯超时"),
    CODE_99("99","交易失败")
    ;

    private String code;
    private String msg; //代表含义，可能描述有轻微差异
    ErrorCode(String _code, String _msg){
        code=_code;
        msg=_msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
