package com.mocredit.bank.util;



public class Variable {
	
	public final static String OK="OK";
	
//	/* 中信正式 */
//	public static final String ZXKEYSTORE = "/root/emay.jks";
////		public static final String ZXKEYSTOREPWD = "e5m8a7y5y0h5t3v53";
//	public static final String ZXKEYSTOREPWD = "huilife365";
//	public static final String ZXKEYSTORETYPE = "JKS";
//	public static final String ZXTRUSTSTORE = "/root/zx.jks";
//	public static final String ZXTRUSTSTOREPWD = "mocredit";
//	public static final String ZXTRUSTSTORETYPE = "JKS";
//	public static final String ZXWSURL = "https://pay.bank.ecitic.com/Payment/services/PaymentServices";
//	public static final String ZXMERCHANTTYPE = "2";
////		public static final String ZXMERCHANTID = "EWCSSH";
////		public static final String ZXMERCHANTNAME = "二维码测试商户";
////		public static final String ZXMERCHANTPASSWORD = "074324bg";
////		public static final String ZXMERCHANTID = "KQG014";
//	public static final String ZXMERCHANTNAME = "二维码测试商户";
////		public static final String ZXMERCHANTPASSWORD = "123abcde";
////		public static final String ZXKEYSTOREALIAS = "5bc7c71839032d2c46ceda258ce1cee2";
////		public static final String ZXKEYSTOREALIAS = "31352a96d3729167ab7bfffe600f97f8_80e4d998-954a-460f-bc5d-29764725ffa5";
//	public static final String ZXKEYSTOREALIAS = "le-28b1ada3-a40b-4922-8781-bdfeee9402af";
//	
	/*中信消息 消息类型*/
	/**
	 * 消息类型 登录
	 */
	public static final String ZX_INFO_TYPE_LOGIN="0800"; 
	/**
	 * 消息类型 维持连接状态
	 */
	public static final String ZX_INFO_TYPE_SESSION="0200"; 
	/**
	 * 消息类型 支付
	 */
	public static final String ZX_INFO_TYPE_PAYMENT="0200";
	/**
	 * 消息类型 撤销/冲正
	 */
	public static final String ZX_INFO_TYPE_REVERSAL="0420";
	
	/*中信  支付类型*/
	/**
	 * 支付类型 积分
	 */
	public final static String ZX_PAY_WAY_INTEGRAL="02";
	
	/*中信  查询类型*/
	/**
	 * 支付类型 积分
	 */
	public final static String ZX_CONFIRM_TYPE_POINT="POINT";
	
	/*中信 货币代码*/
	/**
	 * 货币代码 人民币
	 */
	public final static String ZX_CURR_CODE="156"; 
	
	/*中信 POS输入方式*/
	/**
	 * POS输入方式 刷卡
	 */
	public final static String ZX_INPUT_TYPE_BRUSH="031";
	/**
	 * POS输入方式 手工输入
	 */
	public final static String ZX_INPUT_TYPE_KEY="042";
	
	/*中信 服务点条件码*/
	/**
	 * 服务点条件码  商户积分EPOS
	 */
	public final static String ZX_POS_CONDITIONCODE_EPOS="03";
	/* 中信测试 */
//	public static final String TEST_ZXKEYSTORE = "/root/emaytest.jks";
//	public static final String TEST_ZXKEYSTOREPWD = "123456";
	public static final String ZXKEYSTORE = "D:/cert/client.pfx";
	public static final String ZXSERVERPUBCERT = "D:/cert/server.cer";
	public static final String ZXKEYSTOREPWD = "123456";
	public static final String ZXKEYSTORETYPE = "pkcs12";
//	public static final String TEST_ZXTRUSTSTORE = "/root/zxtest.jks";
//	public static final String TEST_ZXTRUSTSTOREPWD = "mocredit";
	public static final String ZXTRUSTSTORE = "D:/cert/truststore";
	public static final String ZXTRUSTSTOREPWD = "changeit";
	public static final String ZXTRUSTSTORETYPE = "jks";
//	public static final String TEST_ZXWSURL = "http://202.108.57.21/Payment/services/PaymentServices";
//	public static final String ZXWSURL="https://pay.test.bank.ecitic.com/Payment/services/PaymentServices";
	public static final String ZXWSURL="http://127.0.0.1:8081/citicserver/payment";
	public static final String ZXMERCHANTTYPE = "2";
	public static final String TEST_ZXMERCHANTID = "YMTEST";
	public static final String TEST_ZXMERCHANTNAME = "亿美测试";
	public static final String TEST_ZXMERCHANTPASSWORD = "a1234567";
	public static final String ZXKEYSTOREALIAS = "b5585ded-570d-43f5-a7e2-4b1fff37c22a";
	public static final String YHTURL = "http://zx.emay.cn/WebService/ClientWS.asmx?wsdl";
	public static final String TEST_YHTURL = "http://oa.mocredit.cn:8080/WebService/ClientWS.asmx?wsdl";
	
	public static final String RESSERVERURL = "http://res.huilife365.com/McnStoreResServ/";
	public static final String TEST_RESSERVERURL = "http://116.58.219.209/McnStoreResServ/";
	
	public static final int ISZXCHECK = 1;
	/**
	 * 民生积分兑换IP
	 */
	public static String MS_INTEGER_IP="198.203.188.78";
	/**
	 * 民生积分兑换PORT
	 */
	 public static int MS_INTEGER_PORT=5000;
}