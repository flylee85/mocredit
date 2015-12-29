package cn.mocredit.gateway.posp.bc.util;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

public class CodeInfo {
	/**
	 * 民生银行积分兑换返回报文中39域编码代表的意思
	 */
	public static final Map<String, String> MIN_SHENG_RES_FIELD_39 = init_MIN_SHENG_RES_FIELD_39();
	/**
	 * 民生银行领奖返回报文中39域编码代表的意思
	 */
	public static final Map<String, String> MIN_SHENG_LING_JAING_RES_FIELD_39 = init_MIN_SHENG_LING_JAING_RES_FIELD_39();

	private static Map<String, String> init_MIN_SHENG_RES_FIELD_39() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "交易成功");
		map.put("01", "交易失败，请联系发卡行");
		map.put("02", "交易失败，请联系发卡行");
		map.put("03", "商户未登记");
		map.put("04", "没收卡，请联系收单行");
		map.put("05", "交易失败，请联系发卡行");
		map.put("06", "交易失败，请联系发卡行");
		map.put("07", "没收卡，请联系收单行");
		map.put("09", "交易失败，请重试");
		map.put("12", "交易失败，请重试");
		map.put("13", "交易金额超限，请重试");
		map.put("14", "无效卡号，请联系发卡行");
		map.put("15", "此卡不能受理");
		map.put("19", "交易失败，请联系发卡行");
		map.put("20", "交易失败，请联系发卡行");
		map.put("21", "交易失败，请联系发卡行");
		map.put("22", "操作有误，请重试");
		map.put("23", "交易失败，请联系发卡行");
		map.put("25", "交易失败，请联系发卡行");
		map.put("30", "交易失败，请重试");
		map.put("31", "此卡不能受理");
		map.put("33", "过期卡，请联系发卡行");
		map.put("34", "没收卡，请联系收单行");
		map.put("35", "没收卡，请联系收单行");
		map.put("36", "此卡有误，请换卡重试");
		map.put("37", "没收卡，请联系收单行");
		map.put("38", "密码错误次数超限");
		map.put("39", "交易失败，请联系发卡行");
		map.put("40", "交易失败，请联系发卡行");
		map.put("41", "没收卡，请联系收单行");
		map.put("42", "交易失败，请联系发卡方");
		map.put("43", "没收卡，请联系收单行");
		map.put("44", "交易失败，请联系发卡行");
		map.put("51", "余额不足，请查询");
		map.put("52", "交易失败，请联系发卡行");
		map.put("53", "交易失败，请联系发卡行");
		map.put("54", "过期卡，请联系发卡行");
		map.put("55", "密码错，请重试");
		map.put("56", "交易失败，请联系发卡行");
		map.put("57", "交易失败，请联系发卡行");
		map.put("58", "终端无效，请联系收单行或银联");
		map.put("59", "交易失败，请联系发卡行");
		map.put("60", "交易失败，请联系发卡行");
		map.put("61", "金额太大");
		map.put("62", "交易失败，请联系发卡行");
		map.put("63", "交易失败，请联系发卡行");
		map.put("64", "交易失败，请联系发卡行");
		map.put("65", "超出取款次数限制");
		map.put("66", "交易失败，请联系收单行或银联");
		map.put("67", "没收卡");
		map.put("68", "交易超时，请重试");
		map.put("75", "密码错误次数超限");
		map.put("77", "请向网络中心签到");
		map.put("79", "POS终端重传脱机数据");
		map.put("90", "交易失败，请稍后重试");
		map.put("91", "交易失败，请稍后重试");
		map.put("92", "交易失败，请稍后重试");
		map.put("93", "交易失败，请联系发卡行");
		map.put("94", "交易失败，请稍后重试");
		map.put("95", "交易失败，请稍后重试");
		map.put("96", "交易失败，请稍后重试");
		map.put("97", "终端未登记，请联系收单行或银联");
		map.put("98", "交易超时，请重试");
		map.put("99", "校验错，请重新签到");
		map.put("A0", "校验错，请重新签到");
		map.put("RP", "拒绝参加活动");
		map.put("NT", "非活动日");
		map.put("TL", "已经参加过活动");
		map.put("NB", "卡片不能参加活动");
		map.put(":p", "产品编号错误");
		return unmodifiableMap(map);
	}
	private static Map<String, String> init_MIN_SHENG_LING_JAING_RES_FIELD_39() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "交易成功");
		map.put("01", "交易失败，请联系发卡行");
		map.put("02", "交易失败，请联系发卡行");
		map.put("03", "商户未登记");
		map.put("04", "没收卡，请联系收单行");
		map.put("05", "交易失败，请联系发卡行");
		map.put("06", "交易失败，请联系发卡行");
		map.put("07", "没收卡，请联系收单行");
		map.put("09", "交易失败，请重试");
		map.put("12", "交易失败，请重试");
		map.put("13", "交易金额超限，请重试");
		map.put("14", "无效卡号，请联系发卡行");
		map.put("15", "此卡不能受理");
		map.put("19", "交易失败，请联系发卡行");
		map.put("20", "交易失败，请联系发卡行");
		map.put("21", "交易失败，请联系发卡行");
		map.put("22", "操作有误，请重试");
		map.put("23", "交易失败，请联系发卡行");
		map.put("25", "交易失败，请联系发卡行");
		map.put("30", "交易失败，请重试");
		map.put("31", "此卡不能受理");
		map.put("33", "过期卡，请联系发卡行");
		map.put("34", "没收卡，请联系收单行");
		map.put("35", "没收卡，请联系收单行");
		map.put("36", "此卡有误，请换卡重试");
		map.put("37", "没收卡，请联系收单行");
		map.put("38", "密码错误次数超限");
		map.put("39", "交易失败，请联系发卡行");
		map.put("40", "交易失败，请联系发卡行");
		map.put("41", "没收卡，请联系收单行");
		map.put("42", "交易失败，请联系发卡方");
		map.put("43", "没收卡，请联系收单行");
		map.put("44", "交易失败，请联系发卡行");
		map.put("51", "余额不足，请查询");
		map.put("52", "交易失败，请联系发卡行");
		map.put("53", "交易失败，请联系发卡行");
		map.put("54", "过期卡，请联系发卡行");
		map.put("55", "密码错，请重试");
		map.put("56", "交易失败，请联系发卡行");
		map.put("57", "交易失败，请联系发卡行");
		map.put("58", "终端无效，请联系收单行或银联");
		map.put("59", "交易失败，请联系发卡行");
		map.put("60", "交易失败，请联系发卡行");
		map.put("61", "金额太大");
		map.put("62", "交易失败，请联系发卡行");
		map.put("63", "交易失败，请联系发卡行");
		map.put("64", "交易失败，请联系发卡行");
		map.put("65", "超出取款次数限制");
		map.put("66", "交易失败，请联系收单行或银联");
		map.put("67", "没收卡");
		map.put("68", "交易超时，请重试");
		map.put("75", "密码错误次数超限");
		map.put("77", "请向网络中心签到");
		map.put("79", "POS终端重传脱机数据");
		map.put("90", "交易失败，请稍后重试");
		map.put("91", "交易失败，请稍后重试");
		map.put("92", "交易失败，请稍后重试");
		map.put("93", "交易失败，请联系发卡行");
		map.put("94", "交易失败，请稍后重试");
		map.put("95", "此卡无权益");
		map.put("96", "交易失败，请稍后重试");
		map.put("97", "终端未登记，请联系收单行或银联");
		map.put("98", "交易超时，请重试");
		map.put("99", "校验错，请重新签到");
		map.put("A0", "校验错，请重新签到");
		return unmodifiableMap(map);
	}
}
