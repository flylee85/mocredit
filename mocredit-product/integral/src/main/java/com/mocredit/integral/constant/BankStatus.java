package com.mocredit.integral.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ytq on 2015/11/19.
 */
public class BankStatus {
    final static Map<String, String> BANK_MS_MAP = new HashMap<>();
    final static Map<String, String> BANK_ZX_MAP = new HashMap<>();
    final static Map<String, String> BANK_JT_MAP = new HashMap<>();

    static {
        BANK_MS_MAP.put("00", "交易成功");
        BANK_MS_MAP.put("01", "交易失败，请联系发卡行");
        BANK_MS_MAP.put("02", "查发卡行的特殊条件,交易失败，请联系发卡行");
        BANK_MS_MAP.put("03", "无效商户");
        BANK_MS_MAP.put("04", "没收卡，请联系收单行");
        BANK_MS_MAP.put("05", "发卡不予承兑，请联系发卡行");
        BANK_MS_MAP.put("06", "发卡行故障,请联系发卡行");
        BANK_MS_MAP.put("07", "特殊条件下没收卡，请联系收单行");
        BANK_MS_MAP.put("09", "请求正在处理中，请重试");
        BANK_MS_MAP.put("12", "无效交易，请重试");
        BANK_MS_MAP.put("13", "无效金额，请重试");
        BANK_MS_MAP.put("14", "无效卡号，请联系发卡行");
        BANK_MS_MAP.put("15", "此卡不能受理");
        BANK_MS_MAP.put("19", "刷卡读取数据有误，可重新刷卡");
        BANK_MS_MAP.put("20", "无效应答");
        BANK_MS_MAP.put("21", "不做任何处理，交易失败");
        BANK_MS_MAP.put("22", "POS状态与中心不符，请重试");
        BANK_MS_MAP.put("23", "不可接受的交易费");
        BANK_MS_MAP.put("25", "未能找到文件上记录");
        BANK_MS_MAP.put("30", "格式错误，请重试");
        BANK_MS_MAP.put("31", "银联不支持的银行，此卡不能受理");
        BANK_MS_MAP.put("33", "过期的卡");
        BANK_MS_MAP.put("34", "有作弊嫌疑");
        BANK_MS_MAP.put("35", "受卡方与安全保密部门联系,有作弊嫌疑的卡");
        BANK_MS_MAP.put("36", "受限制的卡");
        BANK_MS_MAP.put("37", "受卡方呼受理方安全保密部门(没收卡)");
        BANK_MS_MAP.put("38", "超过允许的PIN试输入");
        BANK_MS_MAP.put("39", "无此信用卡帐户");
        BANK_MS_MAP.put("40", "请求的功能尚不支持");
        BANK_MS_MAP.put("41", "丢失卡");
        BANK_MS_MAP.put("42", "无此帐户");
        BANK_MS_MAP.put("43", "被窃卡");
        BANK_MS_MAP.put("44", "无此投资帐户");
        BANK_MS_MAP.put("51", "余额不足");
        BANK_MS_MAP.put("52", "无此支票账户");
        BANK_MS_MAP.put("53", "无此储蓄卡账户");
        BANK_MS_MAP.put("54", "过期的卡");
        BANK_MS_MAP.put("55", "不正确的PIN");
        BANK_MS_MAP.put("56", "无此卡记录");
        BANK_MS_MAP.put("57", "不允许持卡人进行的交易");
        BANK_MS_MAP.put("58", "不允许终端进行的交易");
        BANK_MS_MAP.put("59", "有作弊嫌疑");
        BANK_MS_MAP.put("60", "受卡方与安全保密部门联系");
        BANK_MS_MAP.put("61", "超出取款金额限制");
        BANK_MS_MAP.put("62", "受限制的卡");
        BANK_MS_MAP.put("63", "违反安全保密规定");
        BANK_MS_MAP.put("64", "原始金额不正确");
        BANK_MS_MAP.put("65", "超出取款次数限制");
        BANK_MS_MAP.put("66", "受卡方呼受理方安全保密部门");
        BANK_MS_MAP.put("67", "没收卡");
        BANK_MS_MAP.put("68", "交易超时，请重试");
        BANK_MS_MAP.put("75", "允许的输入PIN次数超限");
        BANK_MS_MAP.put("77", "需要向网络中心签到");
        BANK_MS_MAP.put("79", "脱机交易对帐不平");
        BANK_MS_MAP.put("90", "日期切换正在处理");
        BANK_MS_MAP.put("91", "发卡行或银联不能操作");
        BANK_MS_MAP.put("92", "交易失败，请稍后重试");
        BANK_MS_MAP.put("93", "交易违法");
        BANK_MS_MAP.put("94", "重复交易");
        BANK_MS_MAP.put("95", "调节控制错");
        BANK_MS_MAP.put("96", "系统异常、失效");
        BANK_MS_MAP.put("97", "POS终端号找不到");
        BANK_MS_MAP.put("98", "银联收不到发卡行应答");
//        BANK_MS_MAP.put("99", "PIN格式错");
        BANK_MS_MAP.put("A0", "MAC校验错");
        BANK_MS_MAP.put("RP", "不满足活动条件");
        BANK_MS_MAP.put("NT", "非活动日");
        BANK_MS_MAP.put("TL", "已经参加过活动");
        BANK_MS_MAP.put("NB", "卡片不能参加活动");
        BANK_MS_MAP.put(":p", "系统跟踪号重复");
    }

    static {
        BANK_ZX_MAP.put("00", "成功");
        BANK_ZX_MAP.put("01", "交易失败，不是有效的码");
        BANK_ZX_MAP.put("02", "交易失败，该码不能在此门店使用");
        BANK_ZX_MAP.put("03", "交易失败，该码已经作废");
        BANK_ZX_MAP.put("04", "交易失败，没有找到对应的活动");
        BANK_ZX_MAP.put("05", "交易失败，支付金额有误");
        BANK_ZX_MAP.put("06", "交易失败，该码已经过期");
        BANK_ZX_MAP.put("07", "交易失败，活动不能为空");
        BANK_ZX_MAP.put("08", "交易失败，设备未绑定");
        BANK_ZX_MAP.put("09", "交易失败，选择的活动与二维码不匹配");
        BANK_ZX_MAP.put("10", "交易失败，该码已没有使用次数");
        BANK_ZX_MAP.put("11", "交易失败，该码的余额为0");
        BANK_ZX_MAP.put("12", "交易失败，该订单已退款");
        BANK_ZX_MAP.put("13", "交易失败，系统错误，请重试");
        BANK_ZX_MAP.put("20", "撤销失败，没有找到对应的门店");
        BANK_ZX_MAP.put("21", "撤销失败，没有找到对应活动");
        BANK_ZX_MAP.put("22", "撤销失败，订单状态有误");
        BANK_ZX_MAP.put("30", "退款失败，该订单已经退款");
        BANK_ZX_MAP.put("31", "退款失败，该订单未支付");
        BANK_ZX_MAP.put("32", "退款失败,订单号有误");
        BANK_ZX_MAP.put("33", "退款失败，设备未绑定");
        BANK_ZX_MAP.put("34", "退款失败，该订单不属于本店");
        BANK_ZX_MAP.put("35", "关闭交易失败，订单已经退款");
        BANK_ZX_MAP.put("40", "不是合法的机具");
        BANK_ZX_MAP.put("41", "没有找到对应的活动");
        BANK_ZX_MAP.put("42", "没有找到对应的门店");
        BANK_ZX_MAP.put("43", "机具没有被重置密码，请与管理员联系");
        BANK_ZX_MAP.put("44", "无使用权限");
        BANK_ZX_MAP.put("45", "没有找到对应的订单");
        BANK_ZX_MAP.put("46", "积分不可撤销");
        BANK_ZX_MAP.put("47", "撤销积分过程中出现错误");
        BANK_ZX_MAP.put("50", "机具不合法");
        BANK_ZX_MAP.put("51", "密钥已过期");
        BANK_ZX_MAP.put("52", "非法请求");
        BANK_ZX_MAP.put("53", "订单置单错误");
        BANK_ZX_MAP.put("54", "没有找到订单");
        BANK_ZX_MAP.put("55", "参数错误，请重新再试");
        BANK_ZX_MAP.put("56", "机具已锁定，请联系管理员");
//        BANK_ZX_MAP.put("99", "消费自定义错误信息，请取返回的错误信息");
    }

    static {
        BANK_JT_MAP.put("00", "交易成功");
        BANK_JT_MAP.put("01", "查发卡方");
        BANK_JT_MAP.put("02", "查发卡方的特殊条件");
        BANK_JT_MAP.put("03", "无效商户");
        BANK_JT_MAP.put("04", "没收卡");
        BANK_JT_MAP.put("05", "不予承兑");
        BANK_JT_MAP.put("06", "出错");
        BANK_JT_MAP.put("07", "特殊条件下没收卡");
        BANK_JT_MAP.put("09", "请求正在处理中");
        BANK_JT_MAP.put("10", "部分金额批准");
        BANK_JT_MAP.put("11", "重要人物批准");
        BANK_JT_MAP.put("12", "无效交易");
        BANK_JT_MAP.put("13", "无效金额");
        BANK_JT_MAP.put("14", "无效卡号（无此号）");
        BANK_JT_MAP.put("15", "无此发卡方/拒绝");
        BANK_JT_MAP.put("16", "批准更新第三磁道");
        BANK_JT_MAP.put("17", "拒绝但不没收卡");
        BANK_JT_MAP.put("19", "重新送入交易");
        BANK_JT_MAP.put("20", "无效响应");
        BANK_JT_MAP.put("21", "不能采取行动");
        BANK_JT_MAP.put("22", "故障怀疑");
        BANK_JT_MAP.put("23", "不可接受的交易费");
        BANK_JT_MAP.put("25", "找不到原始交易");
        BANK_JT_MAP.put("30", "格式错误");
        BANK_JT_MAP.put("31", "交换中心不支持的银行");
        BANK_JT_MAP.put("32", "部分完成");
        BANK_JT_MAP.put("33", "过期的卡");
        BANK_JT_MAP.put("34", "有作弊嫌疑");
        BANK_JT_MAP.put("35", "受卡方与代理方联系");
        BANK_JT_MAP.put("36", "受限制的卡");
        BANK_JT_MAP.put("37", "受卡方电话通知代理方安全部门s");
        BANK_JT_MAP.put("38", "超过允许的PIN试输入");
        BANK_JT_MAP.put("39", "无贷记账户");
        BANK_JT_MAP.put("40", "请求的功能尚不支持s");
        BANK_JT_MAP.put("41", "挂失卡");
        BANK_JT_MAP.put("42", "无此账户");
        BANK_JT_MAP.put("43", "被窃卡");
        BANK_JT_MAP.put("44", "无此投资账户");
        BANK_JT_MAP.put("51", "资金不足");
        BANK_JT_MAP.put("52", "无此支票账户");
        BANK_JT_MAP.put("53", "无此储蓄卡账户");
        BANK_JT_MAP.put("54", "过期的卡");
        BANK_JT_MAP.put("55", "不正确的PIN");
        BANK_JT_MAP.put("56", "无此卡记录");
        BANK_JT_MAP.put("57", "不允许持卡人进行的交易");
        BANK_JT_MAP.put("58", "不允许终端进行的交易");
        BANK_JT_MAP.put("59", "有作弊嫌疑");
        BANK_JT_MAP.put("60", "受卡方与代理方联系");
        BANK_JT_MAP.put("61", "超出取款/转账金额限制");
        BANK_JT_MAP.put("62", "受限制的卡");
        BANK_JT_MAP.put("63", "侵犯安全");
        BANK_JT_MAP.put("64", "原始金额错误");
        BANK_JT_MAP.put("65", "超出取款次数限制");
        BANK_JT_MAP.put("66", "受卡方通知受理方安全部门");
        BANK_JT_MAP.put("67", "强行受理（要求在自动会员机上没收此卡）");
        BANK_JT_MAP.put("68", "接收的响应已过时");
        BANK_JT_MAP.put("75", "允许的输入PIN次数超限");
        BANK_JT_MAP.put("76", "无效账户");
        BANK_JT_MAP.put("90", "正在日终处理（系统终止一天的活动，开始第二天的活动，交易在几分钟后可再次发送）");
        BANK_JT_MAP.put("91", "发卡方或交换中心不能操作");
        BANK_JT_MAP.put("92", "金融机构或中间网络设施找不到或无法达到、金融机构签退");
        BANK_JT_MAP.put("93", "交易违法、不能完成");
        BANK_JT_MAP.put("94", "重复交易");
        BANK_JT_MAP.put("95", "核对差错");
        BANK_JT_MAP.put("96", "系统异常、失效");
        BANK_JT_MAP.put("97", "ATM/POS终端号找不到");
        BANK_JT_MAP.put("98", "交换中心收不到发卡方应答");
//        BANK_JT_MAP.put("99", "PIN 格式错");
        BANK_JT_MAP.put("A0", "MAC鉴别失败");
    }

    public static String getMsgByMS(String code) {
        return BANK_MS_MAP.get(code);
    }

    public static String getMsgByZX(String code) {
        return BANK_ZX_MAP.get(code);
    }

    public static String getMsgByJT(String code) {
        return BANK_JT_MAP.get(code);
    }
}
