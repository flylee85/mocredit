package cn.m.mt.mocreditservice.util;

import java.util.HashMap;
import java.util.Map;


public class ErrorMessage {
	public static Map<String,String> errormap = new HashMap<String,String>();
	static{
		errormap.put("交易失败,不是有效的码","01");
		errormap.put("交易失败,该码不能在此门店使用","02");
		errormap.put("交易失败,该码已经作废","03");
		errormap.put("交易失败,没有找到对应的活动","04");
		errormap.put("交易失败,支付金额有误","05");
		errormap.put("交易失败,该码已经过期","06");
		errormap.put("交易失败,活动不能为空","07");
		errormap.put("交易失败,设备未绑定","08");
		errormap.put("交易失败,选择的活动与二维码不匹配","09");
		errormap.put("交易失败,该码已没有使用次数","10");
		errormap.put("交易失败,该码的余额为0","11");
		errormap.put("交易失败,该订单已退款","12");
		errormap.put("交易失败,系统错误,请重试","13");
		errormap.put("撤销失败,没有找到对应的门店","20");
		errormap.put("撤销失败,没有找到对应活动","21");
		errormap.put("撤销失败,订单状态有误.","22");
		errormap.put("退款失败,该订单已经退款","30");
		errormap.put("退款失败,该订单未支付","31");
		errormap.put("退款失败,订单号有误","32");
		errormap.put("退款失败,设备未绑定","33");
		errormap.put("退款失败,该订单不属于本店","34");
		errormap.put("关闭交易失败,订单已经退款","35");
		errormap.put("不是合法的机具","40");
		errormap.put("没有找到对应的活动","41");
		errormap.put("没有找到对应的门店","42");
		errormap.put("机具没有被重置密码,请与管理员联系","43");
		errormap.put("无使用权限.","44");
		errormap.put("没有找到对应的订单","45");
		errormap.put("积分不可撤销","46");
		errormap.put("撤销积分过程中出现错误","47");
		errormap.put("机具不合法!","50");
		errormap.put("密钥已过期!","51");
		errormap.put("非法请求","52");
		errormap.put("非法请求!","52");
		errormap.put("订单置单错误","53");
		errormap.put("没有找到订单","54");
		errormap.put("参数错误,请重新再试","55");
		errormap.put("机具已锁定,请联系管理员","56");
	}
	public static String getErrorCode(String key){
		String errorCode = errormap.get(key);
		if(errorCode==null || "".equals( errorCode)){
			errorCode = "99";
		}
		
		return errorCode;
	}
	
	public static void main(String[] args){
		 System.out.println(ErrorMessage.getErrorCode("不是合法的机具"));
	}
}