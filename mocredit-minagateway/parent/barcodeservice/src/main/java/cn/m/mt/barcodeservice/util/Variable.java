package cn.m.mt.barcodeservice.util;



public class Variable {
	
	/*验证日志记录类型*/
	public final static String CHECKTYPE_0="0";//验证成功
	public final static String CHECKTYPE_1="1";//超过活动有效次数
	public final static String CHECKTYPE_2="2";//活动已过期
	public final static String CHECKTYPE_3="3";//活动未开始
	public final static String CHECKTYPE_4="4";//不能参加互斥活动
	public final static String CHECKTYPE_5="5";//不在有效门店内
	public final static String CHECKTYPE_6="6";//作废
	
	
	/*二维码类型*/
	public final static String HUIYUAN="0";//会员码
	public final static String LIANHUIDIAN="1";//联会码
	public final static String YICIMA="2";//一次性码
	
	/*生成码类型*/
	public final static String CODE_TYPE_NORMAL="code";//生成普通二维码
	public final static String CODE_TYPE_BANK="bank";//生成银行兑换二维码
	public final static String CODE_TYPE_ONLY="only";//拿单个码
	public final static String CODE_TYPE_PAYMENT="payment";//拿单个码
	
	/*二维码来源*/
	public final static String SOURCE_EMAY="EMAY";
	public final static String SOURCE_TAOBAO="TAOBAO";
	
	
	//二维码验证方式
	public final static int CHECKMODE_MANUAL=0;
	public final static int CHECKMODE_DEVICE=1;
	public final static int CHECKMODE_ADMIN=2;
	public final static int CHECKMODE_SMS=3;
	public final static int CHECKMODE_OTHER=4;
	
	/*打印结果*/
	public final static String PRINT_0="0";//等待打印
	public final static String PRINT_1="1";//打印成功
	public final static String PRINT_2="2";//打印失败
	
	public final static String  YHTSOAPACTION="http://tempuri.org/";
	//终端版本存放目录
	public final static long DEVICE_DIR = 1;
	/* 中信正式 */
	public static final String ZXKEYSTORE = "/root/emay.jks";
//		public static final String ZXKEYSTOREPWD = "e5m8a7y5y0h5t3v53";
	public static final String ZXKEYSTOREPWD = "huilife365";
	public static final String ZXKEYSTORETYPE = "JKS";
	public static final String ZXTRUSTSTORE = "/root/zx.jks";
	public static final String ZXTRUSTSTOREPWD = "mocredit";
	public static final String ZXTRUSTSTORETYPE = "JKS";
	public static final String ZXWSURL = "https://pay.bank.ecitic.com/Payment/services/PaymentServices";
	public static final String ZXMERCHANTTYPE = "2";
//		public static final String ZXMERCHANTID = "EWCSSH";
//		public static final String ZXMERCHANTNAME = "二维码测试商户";
//		public static final String ZXMERCHANTPASSWORD = "074324bg";
//		public static final String ZXMERCHANTID = "KQG014";
	public static final String ZXMERCHANTNAME = "二维码测试商户";
//		public static final String ZXMERCHANTPASSWORD = "123abcde";
//		public static final String ZXKEYSTOREALIAS = "5bc7c71839032d2c46ceda258ce1cee2";
//		public static final String ZXKEYSTOREALIAS = "31352a96d3729167ab7bfffe600f97f8_80e4d998-954a-460f-bc5d-29764725ffa5";
	public static final String ZXKEYSTOREALIAS = "le-28b1ada3-a40b-4922-8781-bdfeee9402af";
	

	
	/* 中信测试 */
//	public static final String TEST_ZXKEYSTORE = "/root/emaytest.jks";
	public static final String TEST_ZXKEYSTORE = "/root/emaytest.jks";
	public static final String TEST_ZXKEYSTOREPWD = "123456";
	public static final String TEST_ZXKEYSTORETYPE = "JKS";
	public static final String TEST_ZXTRUSTSTORE = "/root/zxtest.jks";
	public static final String TEST_ZXTRUSTSTOREPWD = "mocredit";
	public static final String TEST_ZXTRUSTSTORETYPE = "JKS";
	public static final String TEST_ZXWSURL = "http://202.108.57.21/Payment/services/PaymentServices";
	public static final String TEST_ZXMERCHANTTYPE = "2";
	public static final String TEST_ZXMERCHANTID = "YMTEST";
	public static final String TEST_ZXMERCHANTNAME = "亿美软通";
	public static final String TEST_ZXMERCHANTPASSWORD = "a1234567";
	public static final String TEST_ZXKEYSTOREALIAS = "7a56d1f2922c8c06a4a38ec2b1d6cd80_4dee9f3c-4521-41d0-a601-c0b462b8a187";
	public static final String YHTURL = "http://zx.emay.cn/WebService/ClientWS.asmx?wsdl";
	public static final String TEST_YHTURL = "http://oa.mocredit.cn:8080/WebService/ClientWS.asmx?wsdl";
	
	public static final String RESSERVERURL = "http://res.huilife365.com/McnStoreResServ/";
	public static final String TEST_RESSERVERURL = "http://116.58.219.209/McnStoreResServ/";
	
	public static final int ISZXCHECK = 1;
	
	
	public static final String ERROR_PARTNER = "ERROR_PARTNER";
	public static final String ERROR_SIGN_TYPE = "ERROR_SIGN_TYPE";					
	public static final String ERROR_SIGN = "ERROR_SIGN";							
	public static final String ERROR_PARAM = "ERROR_PARAM";		
	public static final String ERROR_REQUEST = "ERROR_REQUEST";		
	public static final String ERROR_TIMEOUT = "ERROR_TIMEOUT";	
	public static final String ERROR_SYSTEM = "ERROR_SYSTEM";
	public static final String ERROR_BALANCE = "ERROR_BALANCE";	
	public static final String ERROR_OVER_MAX_TOTAL = "ERROR_OVER_MAX_TOTAL";
	public static final String ERROR_OVERDAY_MAX_TOTAL = "ERROR_OVERDAY_MAX_TOTAL";
	public static final String ERROR_PAY = "ERROR_PAY";
	public static final String ERROR_WAIT = "ERROR_WAIT";
	public static final String TRADE_NOT_EXIST= "TRADE_NOT_EXIST";
	public static final String ILLEGAL_ARGUMENT= "ILLEGAL_ARGUMENT";
	public static final String TRADE_STATUS_NOT_AVAILD= "TRADE_STATUS_NOT_AVAILD";
	public static final String BARCODEUNKNOWN= "barcodeUnknown";
	public static final String GENERAL_FAILTURE= "GENERAL_FAILTURE";
	
	
	public static final String TRADE_OVER_TIME= "TRADE_OVER_TIME";
	public static final String ERROR_TRADE_INVALID= "ERROR_TRADE_INVALID";
	public static final String ERROR_TRADE_FAIL= "ERROR_TRADE_FAIL";
	public static final String ERROR_SELLER_ACCOUNT="ERROR_SELLER_ACCOUNT";
	public static final String ERROR_INFORMAL_PARAM="ERROR_INFORMAL_PARAM";
	public static final String ERROR_USER_SEND_SMS="ERROR_USER_SEND_SMS";
	public static final String ERROR_MACHINE_ID="ERROR_MACHINE_ID";
	
	//public static final String ALIPAY_URL = "http://115.124.16.86/cooperate/gateway.do";//支付宝URL
	public static final String ALIPAY_URL = "https://mapi.alipay.com/cooperate/gateway.do";//支付宝URL
	public static final String ALIPAY_KEY = "7ocr3bfy8wwd8jvf7jexwpdqi61244un";//支付宝密钥
	public static final String PARTNER ="2088701368725120" ;//pid
	public static final String TYPY_CODE="wap10007100091320913246620";//业务码
	
	
	public final static String ALIPAY_GOODS = "支付宝手机支付";
	
}