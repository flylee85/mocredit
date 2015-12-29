package com.yimeihuijin.commonlibrary.constants;


import com.yimeihuijin.commonlibrary.utils.PreferenceUtil;

public class URLs {
	/**
	 * IP address
	 */
	 private static String IP = "117.121.20.146";
//	private static String IP = "192.168.3.5";

//	private static String IP = "192.168.100.156";

	public static final String PREFIX_HTTP = "http://";

//	private static String PORT = ":80";
	 private static String PORT = ":9125";
//	private static String PORT = ":12345";

	private static String HOST;

	public static String URL_SIGNIN;
	public static String URL_SIGNIN_RECEIPT;
	public static String URL_COMM_TEST;
	public static String URL_ENCRYPT_TEST;
	public static String URL_ACTIVITIES;
	public static String URL_HEARTBEAT;
	public static String URL_CHECKCODE;
	public static String URL_BONUS_CONSUME;
	public static String URL_BONUS_CONSUME_CORRECT;
	public static String URL_BONUS_REVOKE;
	public static String URL_BONUS_REVOKE_CORRECT;
	public static String URL_CHECKCODE_CANCEL;

	/**
	 * @description
	 * @author qinsen.liao
	 * @createDate 2015�?5�?11�?
	 * @return
	 */
	public static String getHOST() {
		return HOST;
	}

	/**
	 * @description
	 * @author qinsen.liao
	 * @createDate 2015�?5�?11�?
	 * @param host
	 */
	public static void setHOST(String host) {
		HOST = host;
	}

	public static String getIP() {
		return IP;
	}

	public static void setIP(String iP) {
		IP = iP;
		generateURLs();
	}

	public static String getPORT() {
		return PORT;

	}

	public static void setPORT(String pORT) {
		PORT = pORT;
		generateURLs();
	}

	public static void initIP() {
//		String tmp_ip = PreferenceUtil.getString(PreferenceUtil.DEFAULT_IP,
//				PreferenceUtil.DEFAULT_IP);
//		String tmp_port = PreferenceUtil.getString(PreferenceUtil.DEFAULT_IP,
//				PreferenceUtil.DEFAULT_PORT);
//		if (tmp_ip != null && tmp_port != null) {
//			IP = tmp_ip;
//			PORT = tmp_port;
//		}
		generateURLs();
	}

	public static void generateURLs() {
		HOST = PREFIX_HTTP + IP + PORT;
		URL_SIGNIN = HOST + "/qiandao";
		URL_SIGNIN_RECEIPT = HOST + "/qiandaohuizhi";
		URL_COMM_TEST = HOST + "/tongxinceshi";
		URL_ENCRYPT_TEST = HOST + "/jiamiceshi";
		URL_ACTIVITIES = HOST + "/huodongliebiao";
		URL_HEARTBEAT = HOST + "/xintiao";
		URL_CHECKCODE = HOST + "/yanma";
		URL_BONUS_CONSUME = HOST +"/xiaofei";
		URL_BONUS_CONSUME_CORRECT = HOST +"/xiaofeichongzheng";
		URL_BONUS_REVOKE = HOST +"/chexiao";
		URL_BONUS_REVOKE_CORRECT = HOST +"/chexiaochongzheng";
		URL_CHECKCODE_CANCEL = HOST +"/yanmachexiao";
	}
}
