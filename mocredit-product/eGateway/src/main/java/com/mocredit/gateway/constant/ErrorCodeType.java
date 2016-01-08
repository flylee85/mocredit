package com.mocredit.gateway.constant;

public enum ErrorCodeType {
    ACTIVITY_NOT_EXIST("001", "活动不存在"),
    CARD_NOT_RULE("002", "活动卡规则不存在"),
    CARD_DAY_MAX_OUT("003", "本日卡使用超过次数"),
    CARD_WEEK_MAX_OUT("004", "本周卡使用超过次数"),
    CARD_MONTH_MAX_OUT("005", "本月卡使用超过次数"),
    CARD_YEAR_MAX_OUT("006", "本年卡使用超过次数"),
    CARD_TOTAL_MAX_OUT("007", "本卡使用超过次数"),
    SYSTEM_ERROR("008", "系统异常"),
    ACTIVITY_ALREADY_STOPPING("009", "活动已停用"),
    EXIST_ACTIVITY_ERROR("503", "已存在该活动"),
    EXIST_STORE_ERROR("504", "已存在该门店"),
    PARAM_ERROR("505", "参数错误"),
    ACTIVITY_OUT_DATE("506", "不在活动时间内"),
    ACTIVITY_OUT_COUNT("507", "超过活动限制次数"),
    EXIST_ORDER_ERROR("508", "已存在该订单"),
    ACTIVITY_NOT_EXIST_SHOP("509", "该活动不包含该商铺"),
    ACTIVITY_NOT_EXIST_SHOP_STORE("510", "该活动不包含该商铺的门店"),
    NOT_EXIST_ORDER_ERROR("511", "不存在该订单"),
    ORDER_ALREADY_REVOKE("512", "该订单已经撤销"),
    ACTIVITY_SYN_ERROR("513", "活动同步失败"),
    ACTIVITY_SYN_RESP_ERROR("514", "活动同步响应参数错误"),
    ACTIVITY_ALREADY_STOP("515", "活动已停止"),
    DELETE_STORE_ERROR("516", "删除门店出错"),
    ACTIVITY_NOT_EXIST_STORE("517", "该活动不包含该门店");

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
