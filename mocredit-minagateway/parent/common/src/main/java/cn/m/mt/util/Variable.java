package cn.m.mt.util;

public class Variable {
	// 短信状态
	public final static int MT_WAITING_SEND = 0;// 短信等待发送
	public final static int MT_SUCCESS_SEND = 1;// 短信发送成功
	public final static int MT_FAIL_SEND = 2;// 短信发送失败

	// 信息发送级别
	public final static int MMSBO_MTTYPE_GENERAL = 1; // 普通
	public final static int MMSBO_MTTYPE_REALTIME = 2;// 实时
	public final static int MMSBO_MTTYPE_DEFAULT = 2;// 默认 为实时

	public final static int MMSBO_TYPE_MMS = 1; // 彩信
	public final static int MMSBO_TYPE_SMS = 0;// 短信
	public static final Integer MMSBO_TYPE_SSMMMS = 2;// 短信和彩信

	public final static int MMSSTATUS_WAITE = 0;// 彩信状态待发
	public final static int MMSSTATUS_SEND = 1;// 彩信状态已发送
	public final static int MMSSTATUS_OK = 2;// 彩信状态发送成功
	public final static int MMSSTATUS_ERROR = 3;// 彩信状态发送失败

	public final static String MTTYPE_SANDTYPE_TAOBAOBUFA = "taobaobufa"; // 淘宝补发
	public final static String MTTYPE_SANDTYPE_TAOBAO = "taobao";// 淘宝接口发送
	public final static String MTTYPE_SANDTYPE_ZHUCE = "zhuce";// 注册
	// 是否重发
	public final static int ISRESEND_YES = 1;// 是
	public final static int ISRESEND_NO = 0;// 否
	//发送信息方式 
	public final static int MTTYPE_SYS = 0;//系统发送
	public final static int MTTYPE_DIRECT = 1;//直发
	public final static int MTTYPE_RESEND = 2;//手动补发
	public static final String DESKEY="2U1A261D212C5B3D";
}
