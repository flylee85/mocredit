/*
 * Created on 2007-11-9
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.mocredit.bank.constant;

/**
 * @author GuoDong Ni
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ZXConst { 

	public static final String ENET = "02";
	public static final String ECOMM_CHNL = "06 ";
	public static final String UTF8 = "UTF-8";
	public static final String EPOS = "01";
	public static final String EPOS07 = "07";
	public static final String EPOS08 = "08";
	public static final String CHANGEFILE = "chargeFile";
	public static final String CHANGEFILESQL = "chargeFileSql";
	public static final String XMLHEAD = "<?xml";
	public static final int BININFO_LEN = 6; //卡BIN 的长度
	public static final String BINFLAG = "BIN"; //表示是从主机或者卡BIN信息
	public static final String SMSMSG = "中信银行信用卡电话支付手机验证码：";
	public static final String SMSMSGNET = "中信银行信用卡网上支付手机验证码";
	//-begin---add by wuyun 2009-11-25
	public static final String SMSMSGNET_BNO = "验证码编号：";
	public static final String SMSMSGNET_BCODE = "验证码：";
	//-end---add by wuyun 2009-11-25
	public static final int EXPTIME = 30; //动态密码失效时间，以分计算
	public static final String TAIL = "TAIL";
	public static final String HEAD = "HEAD";
	public static final String BEANSXML = "beans.xml";
	public static final String ENCODING = "GBK";
	public static final String CONFIGNAME = "config.xml"; //配置文件名
	public static final String TRANSLOGNAME = "transLogs"; //交易原始信息日志
	public static final String NETLOGNAME = "netLogs"; //交易原始信息日志
	public static final String GBK = "GBK";
	public static final String GB18030 = "GB18030";
	public static final String UTF_8 = "UTF-8";
	public static final String CHANGE_FILE_LOGNAME = "changefileLogs";
	public static final String CHANGE_FILE_ERROR_LOGNAME = "changefileSQErrorLogs";
	//网上交易需要返回给最终用户的错误代码	
	public static final String ERRORCODE_LIST =
		"CIDN001,CMBL001,CPIN001,CNAM001,CRDF002,6666678,AUTH015,AUTH0E1,AUTH0E6,6666667,"
			+ "6666678,AUTH043,6666680,AUTH009,AUTH011,AUTH0CB,AUTH0EI,AUTH004,AUTH005,AUTH009,AUTH011,AUTH013,AUTH014,AUTH015,AUTH018,AUTH028,AUTH043,AUTH0BA,AUTH0CA,AUTH0CB";
	public static final String ERRORCODE_LIST_POINT =
		"7100004,7100005,7100006,7100007,7100008,7100009,7100010,7100011,7100012,7100013,7100014,7100015,7100016,7100017,7100018,7100019,7104007,7104008,7104009,7104010,6666667,6666678,7104012,7104011,7104012,7104020,6666680";

	//changefile日志

	public static final int RANDOM_NUM_LEN = 6; //动态密码的长度
	/**
	 * 客户请求的消息类型的代码以及返回响应类型的代码
	 */
	public static final String LOGIN_INFOTYPE_REQUEST = "0800"; //登入请求码
	public static final String LOGIN_INFOTYPE_RESPONSE = "0810"; //登入响应码

	public static final String LOGOUT_INFOTYPE_REQUEST = "0800"; //登出请求码
	public static final String LOGOUT_INFOTYPE_RESPONSE = "0810"; //登出响应码

	public static final String CHANGEPASSWORD_INFOTYPE_REQUEST = "0800";
	//修改密码交易请求码
	public static final String CHANGEPASSWORD_INFOTYPE_RESPONSE = "0810";
	//修改密码交易响应码

	public static final String GETDYNAMICPWD_INFOTYPE_REQUEST = "0200";
	//获取动态密码请求码
	public static final String GETDYNAMICPWD_INFOTYPE_RESPONSE = "0210";
	//获取动态密码响应码

	public static final String DIVIDEDPAYMENT_INFOTYPE_REQUEST = "0200";
	//分期支付交易请求码
	public static final String DIVIDEDPAYMENT_INFOTYPE_RESPONSE = "0210";
	//分期支付交易响应码

	public static final String DIVIDEDPAYMENTREVERSAL_INFOTYPE_REQUEST = "0420";
	//分期支付交易冲正请求码
	public static final String DIVIDEDPAYMENTREVERSAL_INFOTYPE_RESPONSE = "0430";
	//分期支付交易冲正响应码

	public static final String CONFIRMINFO_INFOTYPE_REQUEST = "0200";
	//验证持卡人信息请求码
	public static final String CONFIRMINFO_INFOTYPE_RESPONSE = "0210";
	//分验证持卡人信息响应码

	public static final String GETQUESTRESULT_INFOTYPE_REQUEST = "0200";
	//获取交易清单请求码
	public static final String GETQUESTRESULT_INFOTYPE_RESPONSE = "0210";
	//获取交易清单响应码

	public static final String HIREPURCHASERETURN_INFOTYPE_REQUEST = "0200";
	//分期支付退货交易请求码
	public static final String HIREPURCHASERETURN_INFOTYPE_RESPONSE = "0210";
	//分期支付退货交易响应码

	public static final String SETTLTMENT_INFOTYPE_REQUEST = "0200";
	//结算交易请求码 	
	public static final String SETTLTMENT_INFOTYPE_RESPONSE = "0210"; //结算交易响应码

	public static final String CHECKACCOUNT_INFOTYPE_REQUEST = "0200";
	//对帐明细请求码
	public static final String CHECKACCOUNT_INFOTYPE_RESPONSE = "0210";
	//对帐明细响应码

	//下载文件请求码
	public static final String DOWNLOAD_INFOTYPE_REQUEST = "0200";
	//下载文件响应码
	public static final String DOWNLOAD_INFOTYPE_RESPONSE = "0210";

	//上载文件请求码
	public static final String UPLOAD_INFOTYPE_REQUEST = "0200";
	//上载文件响应码
	public static final String UPLOAD_INFOTYPE_RESPONSE = "0210";
	//上载文件请求码
	public static final String MAINTAINSESSION_INFOTYPE_REQUEST = "0200";
	//上载文件响应码
	public static final String MAINTAINSESSION_INFOTYPE_RESPONSE = "0210";
	
	public static final String GETREQUESTTRANS_REQUEST = "0200";
	//获取指定交易清单请求码
	public static final String GETREQUESTTRANS_RESPONSE = "0210";
	//获取指定交易清单响应码
	
	public static final String GETREQUESTBATCH_REQUEST = "0200";
	//获取指定批次清单
	public static final String GETREQUESTBATCH_RESPONSE = "0210";
	//获取指定批次清单
	
    //商户信息同步交易请求码 
	public static final String UPDATEMERCHANT_INFOTYPE_REQUEST = "0200";
		
	public static final String UPDATEMERCHANT_INFOTYPE_RESPONSE = "0210"; //结算交易响应码
	
//	差错交易请款请求码 
	  public static final String ADJUSTMENTCLAIM_INFOTYPE_REQUEST = "0200";
		
	  public static final String ADJUSTMENTCLAIM_INFOTYPE_RESPONSE = "0210"; //结算交易响应码
	/**查询订单请求码*/
	public static final String GETORDERRESULT_INFOTYPE_REQUEST = "0200";
	/**查询订单响应码*/
	public static final String GETORDERRESULT_INFOTYPE_RESPONSE = "0210";
	/**获取校验动态验证码请求码*/
	public static final String DYNAMICVALIDATECODE_INFOTYPE_REQUEST = "0200";
	/**获取校验动态验证码响应码*/
	public static final String DYNAMICVALIDATECODE_INFOTYPE_RESPONSE = "0210";
	
	/**卡信息校验请求码*/
	public static final String CHECKCARDINFO_INFOTYPE_REQUEST = "0200";
	/**卡信息校验响应码*/
	public static final String CHECKCARDINFO_INFOTYPE_RESPONSE = "0210";
	
	/**签约请求码*/
	public static final String SIGN_INFOTYPE_REQUEST = "0200";
	/**签约响应码*/
	public static final String SIGN_INFOTYPE_RESPONSE = "0210";
	
	/**解约请求码*/
	public static final String SIGNCANCEL_INFOTYPE_REQUEST = "0200";
	/**解约响应码*/
	public static final String SIGNCANCEL_INFOTYPE_RESPONSE = "0210";
	
	/**查询签约解约请求码*/
	public static final String QUERYSIGN_INFOTYPE_REQUEST = "0200";
	/**查询签约解约响应码*/
	public static final String QUERYSIGN_INFOTYPE_RESPONSE = "0200";
	
	/**快捷支付签约请求码*/
	public static final String SIGNKJ_INFOTYPE_REQUEST = "0200";
	/**快捷支付签约响应码*/
	public static final String SIGNKJ_INFOTYPE_RESPONSE = "0210";
	
	/**
	 * 客户请求的的类型
	 */
	public static final String LOGIN_RESPONSE_METHOD = "login";
	public static final String LOGOUT_RESPONSE_METHOD = "logout";
	public static final String CHANGEPASSWORD_RESPONSE_METHOD = "changePassword";
	public static final String CONFIRMINFO_RESPONSE_METHOD = "confirmInfo";
	public static final String DIVIDEDPAYMENT_RESPONSE_METHOD = "dividedpayment";
	public static final String DIVIDEDPAYMENTREVERSAL_RESPONSE_METHOD = "dividedPaymentReversal";
	public static final String GETQUESTRESULT_RESPONSE_METHOD = "getQuestResult";
	public static final String GETDYNAMICPWD_RESPONSE_METHOD = "getDynamicPWD";
	public static final String HIREPURCHASERETURN_RESPONSE_METHOD = "hirePurchaseReturn";
	public static final String MAINTAINSESSION_RESPONSE_METHOD = "maintainSession";
	public static final String SETTLTMENT_RESPONSE_METHOD = "settltment";
	public static final String CHECKACCOUNT_RESPONSE_METHOD = "checkAccount";
	public static final String DOWNLOAD_RESPONSE_METHOD = "download";

	public static final String VALIDATECARD_RESPONSE_METHOD = "validateCard";
	public static final String ERROR_RESPONSE_METHOD = "error";
	public static final String PAYMENT_RESPONSE_METHOD = "payment";

	public static final String UPLOAD_RESPONSE_METHOD = "upload";
	
	public static final String GETREQUESTTRANS_METHOD = "getRequestTrans";
	public static final String GETREQUESTBATCH_METHOD = "getRequestBatch";
	
	public static final String UPDATEMERCHANTINFO_METHOD = "updateMerchantInfo";
	public static final String UPDATEMERCHANT_METHOD = "updateMerchant";
	
	public static final String ADJUSTMENTCLAIM_METHOD = "adjustmentClaimInfo";
	public static final String GETORDERRESULT_METHOD = "getOrderResult";
	public static final String DYNAMICVALIDATECODE_METHOD = "dynamicValidateCode";
	public static final String CHECKCARDINFO_RESPONSE_METHOD = "checkCardInfo";
	public static final String SIGN_RESPONSE_METHOD = "sign";
	public static final String SIGNCANCEL_RESPONSE_METHOD = "signCancel";
	public static final String QUERYSIGN_RESPONSE_METHOD = "querySign";
	public static final String SIGNKJ_RESPONSE_METHOD = "signKJ";
	/**
	 * 发送给主机的交易代码
	 */
	public static final String LOGINMSG_CODE = "MPMD0001"; //登录，主机的报文交易码
	public static final String LOGOUTMSG_CODE = "MPMD0002"; //登出，主机的报文交易码
	public static final String CHANGEPASSWORDMSG_CODE = "MPMD0003";
	//修改密码，主机的报文交易码
	public static final String GETDYNAMICPWDMSG_CODE = "MPIN0004";
	//持卡人信息，主机的报文交易码
	public static final String CONFIRMINFOMSG_CODE = "MPMD0005";
	//持卡人信息，主机的报文交易码
	public static final String DIVIDEDPAYMENTMSG_CODE = "MPMD0010";
	//分期支付交易，主机的报文交易码
	public static final String DIVIDEDPAYMENTREVERSALMSG_CODE = "MPMD0011";

	//-----------------------
	//新版本的交易代码
	public static final String DIVIDEDPAYMENTMSG_CODE_NEW = "MPMD0020";
	//新版本的冲正代码
	public static final String DIVIDEDPAYMENTREVERSALMSG_CODE_NEW = "MPMD0021";
	//新版本的退款代码
	public static final String HIREPURCHASERETURNMSG_CODE_NEW = "MPMD0022";
	//-----------------------

	//分期支付撤消交易，主机的报文交易码
	public static final String GETQUESTRESULTMSG_CODE = "MPIN0070";
	//获取交易清单，主机的报文交易码
	public static final String HIREPURCHASERETURNMSG_CODE = "MPMD0012";
	//分期支付退货交易，主机的报文交易码
	public static final String SETTLTMENT_CODE = "MPMD0080"; //结算交易，主机的报文交易码
	public static final String CHECKACCOUNTMSG_CODE = "MPMD0081";

	public static final String DOWNLOAD_CODE = "MPIN0005"; // 查询BIN表

	public static final String VALIDATECARDHOSTMSG_CODE = "MPIN0006"; //查询卡状态
	
	public static final String GETREQUESTTRANS_CODE = "MPIN0072"; //订单清单交易
	
	public static final String GETREQUESTBATCH_CODE = "MPIN0073"; //批次清单交易
	
	//更新商户信息
	public static final String UPDATEMERCHANTINFO_CODE = "MPMD9001"; //商户信息同步 
	//差错交易请款退款
	public static final String ADJUSTMENTCLAIM_CODE = "MPMD9011"; //请款
	public static final String ADJUSTMENTRETURN_CODE = "MPMD9012"; //退款  
	/**
	 * 发送给电子商务的交易代码
	 */
	public static final String LOGIN_ECOMMMSG_CODE = "EPOS7101";
	public static final String CHANGEPASSWORD_ECOMMMSG_CODE = "EPOS7102";
	public static final String GETQUESTRESULT_ECOMMMSG_CODE = "EPOS7103";
	public static final String DIVIDEDPAYMENT_ECOMMMSG_CODE = "EPOS7104";
	public static final String DIVDPAYRESL_ECOMMMSG_CODE = "EPOS7105";
	public static final String CHECKACCOUNT_ECOMMMSG_CODE = "EPOS7106";
	public static final String HIREPURCHRET_ECOMMMSG_CODE = "EPOS7107";
	public static final String CONFIRMINFO_ECOMMMSG_CODE = "EPOS7108";
	public static final String CONFIRMCOUNTINFO_ECOMMMSG_CODE = "EPOS7109";
	public static final String LOGOUT_ECOMMMSG_CODE = "EPOS7109";
	
	//二维码交易
	public static final String DIVIDEDPAYMENT_TWO_CODE = "SMMS7104";
	
	public static final String DIVIDEDPAYMENT_THREE_CODE = "YBEP7104";

	//对明细交易，主机的报文交易码

	/**
	  * 商户具体操作的代码
	  */
	public static final int LOGIN_ORP_CODE = 1; //登录，操作的代码
	public static final int LOGOUT_ORP_CODE = 2; //登出，操作的代码
	public static final int CHANGEPASSWORD_ORP_CODE = 3; //修改密码，操作的代码
	public static final int GETDYNAMICPWD_ORP_CODE = 4; //动态密码，操作的代码
	public static final int DIVIDEDPAYMENT_ORP_CODE = 5; //分期支付交易，操作的代码
	public static final int DIVIDEDPAYMENTREVERSAL_ORP_CODE = 6;
	//分期支付撤消交易，操作的代码
	public static final int HIREPURCHASERETURN_ORP_CODE = 7; //分期支付退货交易，操作的代码
	public static final int CONFIRMINFO_ORP_CODE = 8; //持卡人信息，操作的代码
	public static final int GETQUESTRESULT_ORP_CODE = 9; //获取交易清单，操作的代码
	public static final int SETTLTMENT_ORP_CODE = 10; //结算，操作的代码
	public static final int CHECKACCOUNT_ORP_CODE = 11; //对帐明细结算，操作的代码
	public static final int DOWNLOAD_ORP_CODE = 12; //获取交易清单，操作的代码
	public static final int UPLOAD_ORP_CODE = 13; //上传文件，操作的代码

	public static final int LOGIN_ORP_CODE_POINT = 14; //积分登录，操作的代码
	public static final int LOGOUT_ORP_CODE_POINT = 15; //积分登出，操作的代码
	public static final int CHANGEPASSWORD_ORP_CODE_POINT = 16; //积分修改密码，操作的代码

	public static final int DIVIDEDPAYMENT_ORP_CODE_POINT = 17; //积分分期支付交易，操作的代码
	public static final int DIVIDEDPAYMENTREVERSAL_ORP_CODE_POINT = 18; //积分撤销/冲正
	//分期支付撤消交易，操作的代码
	public static final int HIREPURCHASERETURN_ORP_CODE_POINT = 19; //积分分期支付退货交易，操作的代码
	public static final int CONFIRMINFO_ORP_CODE_POINT = 20; //积分持卡人信息，操作的代码
	public static final int GETQUESTRESULT_ORP_CODE_POINT = 21; //积分获取交易清单，操作的代码

	public static final int CHECKACCOUNT_ORP_CODE_POINT = 22; //积分对帐明细结算，操作的代码
	public static final int DOWNLOAD_ORP_CODE_POINT = 23; //积分下载凭证，操作的代码
	public static final int UPLOAD_ORP_CODE_POINT = 27; //积分上传文件，操作的代码	

	public static final int LOGIN_NET_ORP_CODE = 24; //网上登录，操作的代码	
	public static final int LOGIN_NET_ORP_CODE_POINT = 25; //积分网上登录，操作的代码		
	public static final int GETWORD_ORP_CODE = 26; //获取提示语，操作的代码	
	public static final int GETWORD_ORP_CODE_POINT = 28; //积分获取提示语，操作的代码	
	public static final int DIVIDEDPAYMENT_NET_ORP_CODE = 29; //网上交易，操作的代码	
	public static final int DIVIDEDPAYMENT_NET_ORP_CODE_POINT = 30; //积分网上交易，操作的代码
	
	public static final int GET_REQUEST_TRANS = 31; //订单交易清单	
	
	public static final int GET_REQUEST_BATCH_TRANS = 32; //批次清单	
	public static final int UPDATEMERCHANTINFO_TRANS = 33; //同步商户信息	

	/**获取/校验动态验证码*/
	public static final int DYNAMICVALIDATECODE_ORP_CODE = 34;
	/**卡信息校验*/
	public static final int CHECKCARDINFO_ORP_CODE = 35;
	/**签约*/
	public static final int SIGN_ORP_CODE = 36;
	
	/**解约*/
	public static final int SIGNCANCEL_ORP_CODE = 37;
	/**快捷支付签约*/
	public static final int SIGNKJ_ORP_CODE = 38;
	

	/**
	 * 交易类型
	 */
	public static final int TRANSTYPE_DIVIDEDPAYMENT = 1; //分期支付
	public static final int TRANSTYPE_DIVIDEDPAYMENTREVERSAL = 2; //撤消
	public static final int TRANSTYPE_HIREPURCHASERETURN = 3; //退货交易

	public static final int TRANSTYPE_DIVIDEDPAYMENT_POINT = 4; //积分分期支付
	public static final int TRANSTYPE_DIVIDEDPAYMENTREVERSAL_POINT = 5; //积分撤销
	public static final int TRANSTYPE_HIREPURCHASERETURN_POINT = 6; //积分退货

	public static final String RETCODE_SUCCEED = "0000000"; //发送到主机的报文类型。

	/**
	 *  SESSION 保存名字的配置 
	 */
	public static final String SESSION_MERCHANTID = "merchantID";
	//商户号码的session名字
	public static final String SESSION_VERIFYTYPE = "dynamicPWDFlag";
	//商户验证方式的session名字
	public static final String SESSION_MERCHANTNAME = "merchantNAME";
	//商户名称的session名字
	/**
	 * 验证类型
	 */
	public static final int VERIFYDYNAMICPWD = 0; // 0 表示需验证动态密码 
	public static final int VERIFYCHIDNUM = 1; // 1 表示需验证持卡人身份证
	public static final int VERIFYCHNAME = 2; // 2 表示需验证持卡人姓名
	public static final int VERIFYMOBILE = 3; // 3 表示需验证持卡人手机号码
	public static final int VERIFYCVV2 = 4; // 4 表示需验证持卡人CVV2
	public static final int VERIFYPASSWDMAC = 5; // 5 表示需验证持卡人交易密码密文
	public static final String SEQUELFLAG = "Y"; // 交易清单查询是否续查标志
	public static final int VERIFYDYNAMICPWDNET = 0; // 0 表示网上交易需验证动态密码 	

	public static int DATACOUNT = 20; // 交易明细条数.
	public static String DATASET = "dataSet";
	public static String REQMETHOD = "method";
	public static final String CERT = "CERT";
	public static final String POINT = "A";
	public static final String BIN = "BIN";
	public static final String SUM = "Z";

	public static final String BATCH_REV = "ALI";

	public static final String CERT_BEGIN = "-----BEGIN CERTIFICATE-----";

	public static final String CERT_END = "-----END CERTIFICATE-----";
		
	public static final String POINT_BEGIN = "-----BEGIN POINT-----";
	
	public static final String POINT_END = "-----END POINT-----";
	
	//提供给网上支付使用。
	public static final int VALIDATECARDCOUNT = 20;
	public static final String MER_VER_1 = "1.00";

	/**
	 * 
	 * @author wuyun_kzx
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public static final String ONLINE_CHECK_RESPONSE_METHOD = "check";
	public static final String ONLINE_QUERY_RESPONSE_METHOD = "query";
	public static final String ONLINE_ADD_RESPONSE_METHOD = "add";
	public static final String ONLINE_MODIFY_RESPONSE_METHOD = "modify";
	public static final String NETPAY_AUTH_RESPONSE_METHOD = "netPayAuth";
	public static final int ONLINE_QUERY_ORP_CODE = 33; //查询，操作的代码
	public static final int ONLINE_ADD_ORP_CODE = 31; //新增，操作的代码
	public static final int ONLINE_MODIFY_ORP_CODE = 32; //修改，操作的代码
	public static final int ONLINE_CHECK_ORP_CODE = 33; //修改，操作的代码

	public static final String NETPAY_CHECK_CODE = "    5571";
	public static final String NETPAY_AUTH_CODE = "    5570";
	public static final String NETPAY_CANCHECK_CODE = "    5573";
	public static final String NETPAY_CANAUTH_CODE = "    5572";
	public static final String NETPAY_OTHER_CODE = "    5574";
	public static final String NETPAY_URL_CODE = "    5575";
	/**打印错误日志标识*/
	public static String ERRORSYMBOL = "★★★";
	
	/**获取校验动态验证码-交易类型-获取*/
	public static final String PROCESSTYPE_HQ = "0000";
	/**获取校验动态验证码-交易类型-校验*/
	public static final String PROCESSTYPE_XY = "0001";
	
	/**指纹支付*/
	public static final String TRADETYPE_ZWPAY = "ZWPAY";
	/**财付通支付*/
	public static final String TRADETYPE_CFTZF = "CFTZF";
	/**财付通积分*/
	public static final String TRADETYPE_CFTJF = "CFTJF";
}
