package cn.m.mt.charge.util;



public class ChargeVariable {
//	public static ResourceBundle system = PropertyResourceBundle.getBundle("system");
	
	public final static int SMSRECHARGE_SMS=0;//短彩信充值记录短信
	public final static int SMSRECHARGE_MMS=1;//短彩信充值记录彩信
	
	public final static int CHARGE_NOPAID = 0;//未付款
	public final static int CHARGE_PAID = 1;//已付款
	public final static int CHARGE_CLOSE =2;//交易已关闭
	public final static int CHARGE_COLLECT = 3;//交易成功
	
	public final static int TYPE_INCOME=0;//收入
	public final static int TYPE_PAY=1;//支出
}