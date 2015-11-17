package com.mocredit.integral.constant;

public enum ErrorCodeType {
    /**
     * 银行接口错误编码
     * PARAM_ERROR("001", "参数错误"),
     * SYETEM_ERROR("002", "系统错误"),
     * LOGIN_ERROR("003", "登录失败"),
     * PAY_ERROR("004", "支付失败"),
     * HAS_REVERSED("005", "已撤销成功"),
     * NO_REPORT("006","交易记录不存在"),
     * INVALID_SHOP("007","该商户没有权限"),
     * INVALID_BANK("008","未签约银行"),
     */
    POST_BANK_ERROR("400", "请求Bank出错"),
    ANA_RESPONSE_ERROR("401", "解析Bank响应出错"),
    SYSTEM_ERROR("500", "系统出错"),
    SAVE_DATEBASE_ERROR("501", "保存数据库出错"),
    NOT_EXIST_ACTIVITY_ERROR("502", "不存在该活动"),
    EXIST_ACTIVITY_ERROR("503", "已存在该活动"),
    EXIST_STORE_ERROR("504", "已存在该门店"),
    PARAM_ERROR("505", "参数错误"),
    ACTIVITY_OUT_DATE("506", "不在活动时间内"),
    ACTIVITY_OUT_COUNT("507", "超过活动限制次数"),
    EXIST_ORDER_ERROR("508", "已存在该订单"),
    ACTIVITY_NOT_EXIST_SHOP("509", "该活动不包含该商铺"),
    ACTIVITY_NOT_EXIST_SHOP_STORE("510", "该活动不包含该商铺的门店"),
    NOT_EXIST_ORDER_ERROR("511", "不存在该订单"),
    ACTIVITY_NOT_EXIST_BANK_CARD("512", "该卡不能参与该活动"),
    ACTIVITY_SYN_ERROR("513", "活动同步失败"),
    ACTIVITY_SYN_RESP_ERROR("514", "活动同步响应参数错误");

    private String value;
    private String text;

    ErrorCodeType(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
