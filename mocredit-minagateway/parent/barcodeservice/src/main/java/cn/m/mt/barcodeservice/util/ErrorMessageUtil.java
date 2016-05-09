package cn.m.mt.barcodeservice.util;

public class ErrorMessageUtil {
	public static String GetErrorMessage(String errorid){
		String error="";
		if(Variable.ERROR_REQUEST.equals(errorid)){
			error="合作伙伴号错误";
		}else if(Variable.ERROR_SIGN_TYPE.equals(errorid)){
			error="签名方式错误";
		}else if(Variable.ERROR_PARAM.equals(errorid)){
			error="参数不合法";
		}else if(Variable.ERROR_REQUEST.equals(errorid)){
			error="无效请求";
		}else if(Variable.ERROR_TIMEOUT.equals(errorid)){
			error="超时或网络故障";
		}else if(Variable.ERROR_SYSTEM.equals(errorid)){
			error="支付宝系统错误";
		}else if(Variable.ERROR_BALANCE.equals(errorid)){
			//error="支付宝余额不足";
			error="余额不足";
		}else if(Variable.ERROR_OVER_MAX_TOTAL.equals(errorid)){
			error="超出单笔限额";
		}else if(Variable.ERROR_PAY.equals(errorid)){
			error="付款失败";
		}else if(Variable.ERROR_OVERDAY_MAX_TOTAL.equals(errorid)){
			error="超过日限额";
		}else if(Variable.ERROR_WAIT.equals(errorid)){
			error="系统维护中";
		}else if(Variable.BARCODEUNKNOWN.equals(errorid)){
			error="二维码错误";
		}else if(Variable.ERROR_SELLER_ACCOUNT.equals(errorid)){
			error="卖家账号错误";
		}else if(Variable.ERROR_INFORMAL_PARAM.equals(errorid)){
			error="参数错误";
		}else if(Variable.ERROR_USER_SEND_SMS.equals(errorid)){
			error="短信发送错误";
		}
		else if(Variable.TRADE_NOT_EXIST.equals(errorid)){
			error="该笔交易不存在";
		}
		else if(Variable.ILLEGAL_ARGUMENT.equals(errorid)){
			error="交易号不存在";
		}else if(Variable.GENERAL_FAILTURE.equals(errorid)){
			error="关闭交易其他异常";
		}else if(Variable.ERROR_MACHINE_ID.equals(errorid)){
			error="错误的终端号";
		}else{
			error=errorid;
		}
			
		return error;
	}
}
