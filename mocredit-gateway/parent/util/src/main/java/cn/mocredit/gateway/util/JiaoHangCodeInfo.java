package cn.mocredit.gateway.util;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

public class JiaoHangCodeInfo {
    /**
     * 交通银行响应报文中39域代码对应的意思
     */
    public static final Map<String, String> JIAO_HANG_XIANG_YING_MA = init_JIAO_HANG_XIANG_YING_MA();


    private static Map<String, String> init_JIAO_HANG_XIANG_YING_MA() {
        Map<String, String> map = new HashMap<>();
        map.put("00", "承兑或交易成功");
        map.put("01", "查发卡方");
        map.put("02", "查发卡方的特殊条件");
        map.put("03", "无效商户");
        map.put("04", "没收卡");
        map.put("05", "不予承兑");
        map.put("06", "出错");
        map.put("07", "特殊条件下没收卡");
        map.put("09", "请求正在处理中");
        map.put("10", "部分金额批准");
        map.put("11", "重要人物批准（VIP）");
        map.put("12", "无效交易");
        map.put("13", "无效金额");
        map.put("14", "无效卡号（无此号）");
        map.put("15", "无此发卡方/拒绝");
        map.put("16", "批准更新第三磁道");
        map.put("17", "拒绝但不没收卡");
        map.put("19", "重新送入交易");
        map.put("20", "无效响应");
        map.put("21", "不能采取行动");
        map.put("22", "故障怀疑");
        map.put("23", "不可接受的交易费");
        map.put("25", "找不到原始交易");
        map.put("30", "格式错误");
        map.put("31", "交换中心不支持的银行");
        map.put("32", "部分完成");
        map.put("33", "过期的卡");
        map.put("34", "有作弊嫌疑");
        map.put("35", "受卡方与代理方联系");
        map.put("36", "受限制的卡");
        map.put("37", "受卡方电话通知代理方安全部门");
        map.put("38", "超过允许的PIN试输入");
        map.put("39", "无贷记账户");
        map.put("40", "请求的功能尚不支持");
        map.put("41", "挂失卡");
        map.put("42", "无此账户");
        map.put("43", "被窃卡");
        map.put("44", "无此投资账户");
        map.put("51", "资金不足");
        map.put("52", "无此支票账户");
        map.put("53", "无此储蓄卡账户");
        map.put("54", "过期的卡");
        map.put("55", "不正确的PIN");
        map.put("56", "无此卡记录");
        map.put("57", "不允许持卡人进行的交易");
        map.put("58", "不允许终端进行的交易");
        map.put("59", "有作弊嫌疑");
        map.put("60", "受卡方与代理方联系");
        map.put("61", "超出取款/转账金额限制");
        map.put("62", "受限制的卡");
        map.put("63", "侵犯安全");
        map.put("64", "原始金额错误");
        map.put("65", "超出取款次数限制");
        map.put("66", "受卡方通知受理方安全部门");
        map.put("67", "强行受理（要求在自动会员机上没收此卡）");
        map.put("68", "接收的响应已过时");
        map.put("75", "允许的输入PIN次数超限");
        map.put("76", "无效账户");
        map.put("90", "正在日终处理（系统终止一天的活动，开始第二天的活动，交易在几分钟后可再次发送）");
        map.put("91", "发卡方或交换中心不能操作");
        map.put("92", "金融机构或中间网络设施找不到或无法达到、金融机构签退");
        map.put("93", "交易违法、不能完成");
        map.put("94", "重复交易");
        map.put("95", "核对差错");
        map.put("96", "系统异常、失效");
        map.put("97", "ATM/POS终端号找不到");
        map.put("98", "交换中心收不到发卡方应答");
        map.put("99", "PIN 格式错");
        map.put("A0", "MAC鉴别失败");
        map.put("A7", "安全处理失败");
        map.put("A8", "PIN密钥错");
        map.put("A9", "MAC密钥错");
        map.put("B0", "无效帐户（实时缴费业务使用）");
        map.put("B1", "发送缴费请求的频度超过所设置的阀值（实时缴费业务使用）");
        map.put("B2", "费用未缴或无欠费（收据未打）（实时缴费业务使用）");
        map.put("B3", "交易的发卡行无效（实时缴费业务使用）");
        map.put("B4", "交易的受理行无效（实时缴费业务使用）");
        map.put("C1", "受理行状态非法");
        map.put("C3", "隔日冲正");
        map.put("D1", "机构代码错误");
        map.put("D2", "日期错误");
        map.put("D3", "无效的文件类型");
        map.put("D4", "已经处理过的文件");
        map.put("D5", "无此文件");
        map.put("D6", "接收者不支持");
        map.put("D7", "文件锁定");
        map.put("D8", "未成功");
        map.put("D9", "文件长度不符");
        map.put("DA", "文件解压缩错");
        map.put("DB", "文件名称错");
        map.put("DC", "无法接收文件");
        map.put("N1", "未登折帐目已超限，交易不成功");
        map.put("N2", "交易金额超出最高限额");
        map.put("Q1", "卡片认证失败（符合EMV标准的IC卡专用，具体使用方法参见技术规范 附录二 CUPS对IC卡交易的支持）");
        map.put("R1", "未收到原交易请求时，对关联的确认交易的承兑为有缺陷的成功交易");
        map.put("R2", "原交易为拒绝时，对关联的确认交易的承兑为有缺陷的成功交易");
        map.put("R3", "交换中心转发了原交易请求，但未收到发卡方应答时，对受理方发来的关联的确认交易的承兑为有缺陷的成功交易");
        map.put("R4", "交换中心转发了原交易请求，但未收到发卡方应答时，交换中心直接向受理方应答为有缺陷的成功交易");
        map.put("R5", "转账货币不一致");
        map.put("R6", "转入行无此账户");
        map.put("Y1", "脱机交易成功（符合EMV标准的IC卡专用，具体使用方法参见技术规范 第三部分 文件接口规范）");
        map.put("Y3", "不能联机，脱机交易成功（符合EMV标准的IC卡专用，具体使用方法参见技术规范 第三部分 文件接口规范）");
        map.put("Z1", "脱机交易失败（符合EMV标准的IC卡专用，具体使用方法参见技术规范 第三部分 文件接口规范）");
        map.put("Z3", "不能联机，脱机交易失败（符合EMV标准的IC卡专用，具体使用方法参见技术规范 第三部分 文件接口规范）");
        return unmodifiableMap(map);
    }
}
