package com.yimiehuijin.codeandbonuslibrary.utils;

import android.text.format.DateFormat;

import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.constants.Constants;
import com.yimiehuijin.codeandbonuslibrary.data.ActivityInfo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {

	private static long tmpId = 0l;

	public static boolean isNumber(String s) {
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(s);

		return m.matches();
	}
	
	public static boolean isNumberOrAlphabet(String s) {
		Pattern p = Pattern.compile("[0-9a-zA-Z]*");
		Matcher m = p.matcher(s);

		return m.matches();
	}
	
	public static long getHeartBeatRate(String s){
		if(s == null || "".equals(s) || !isNumber(s)){
			return 3600000l;
		}
		return Long.parseLong(s);
	}

	public static String getRKey(int length) {
		return UUID.randomUUID().toString().replace("-", "")
				.substring(0, length).toUpperCase();
	}

	public static String MD5(String str) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] message = digest.digest(str.getBytes());
			for (int i = 0; i < message.length; i++) {
				sb.append(leftPad( // ����
						Integer.toHexString( // ת��16������
								(Integer) toUnsignedNumber(message[i])), // ת���޷�����
						"0", 2).toUpperCase()); // ת�ɴ�д
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("������md5����");
		}
		return sb.toString();
	}

	public static Number toUnsignedNumber(Number num) {
		if (num instanceof Byte) {
			return (Byte) num & 0xff;
		} else if (num instanceof Short) {
			return (Short) num & 0xffff;
		} else if (num instanceof Integer) {
			return (Integer) num & 0xffffffffL;
		} else {
			return -1;
		}
	}

	public static String leftPad(String str, String pad, int len) {
		String newStr = (str == null ? "" : str);
		while (newStr.length() < len) {
			newStr = pad + newStr;
		}
		if (newStr.length() > len) {
			newStr = newStr.substring(newStr.length() - len);
		}
		return newStr;
	}

	public static String rightPad(String str, String pad, int len) {
		String newStr = (str == null ? "" : str);
		while (newStr.length() < len) {
			newStr = newStr + pad;
		}
		if (newStr.length() > len) {
			newStr = newStr.substring(0, len);
		}
		return newStr;
	}

	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String getH() {
		String tmp = App.aid + App.rkey;
		if (tmp.length() != 48) {
			return null;
		}
		return RSAHelper.encryptByPublicKey(tmp, Constants.TEST_PUBLIC_KEY);
	}

	public static String getH(String rkey) {
		String tmp = App.aid + rkey;
		if (tmp.length() != 48) {
			return null;
		}
		return RSAHelper.encryptByPublicKey(tmp, Constants.TEST_PUBLIC_KEY);
	}

	public static String getT(String json) {
		String password = MD5(App.akey + App.rkey);
		// System.out.println(password);
		// String ret = "";
		// try {
		// ret = AES.encrypt(password,json);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return AESHelper.encrypt(json, password);
	}

	public static String getT(String json, String rkey) {
		String password = MD5(App.akey + rkey);
		// System.out.println(password);
		// String ret = "";
		// try {
		// ret = AES.encrypt(password,json);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return AESHelper.encrypt(json, password);
	}

	public static String deryptT(String json)
			throws UnsupportedEncodingException {
		String password = MD5(App.akey + App.rkey);
		return AESHelper.decrypt(json, password);
	}

	public static void prepare4Signin() {
		App.aid = MD5(App.getInstance().deviceInfo.en);
	}

	public static String getResponse(String response) {
		return AESHelper.decrypt(response, MD5(App.akey + App.rkey));
	}

	public static synchronized  String getOrderId() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2010);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long t2010 = c.getTimeInMillis() / 1000;
		long key = System.currentTimeMillis() / 1000 - t2010;
		while(key == tmpId){
			key = System.currentTimeMillis() / 1000 - t2010;
		}
		tmpId = key;
		return App.getInstance().deviceInfo.en + key;
	}

	public static String getPrintInfo(ActivityInfo info, String orderid) {
		if (info == null || info.receiptPrint == null) {
			return null;
		}
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_ACTIVITY_CODE,
				info.activityCode==null?"":info.activityCode);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_ACTIVITY_NAME,
				info.activityName==null?"":info.activityName);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_ACTIVITY_OUT_CODE,
				info.activityOutCode==null?"":info.activityOutCode);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_AMOUNT, info.amt==null?"": info.amt);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_CODE_SERIAL_NUMBER,
				orderid==null?"":orderid);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_DEVICE_CODE,
				info.deviceCode==null?"":App.getInstance().deviceInfo.en);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_END_TIME, info.eTime==null?"":info.eTime);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_ENTERPRISE_CODE,
				info.enterpriseCode==null?"":info.enterpriseCode);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_ENTERPRISE_NAME,
				info.enterpriseName==null?"":info.enterpriseName);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_REMARK, info.remark==null?"":info.remark);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_SELECT_DATE,
				info.selectDate==null?"":info.selectDate);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_SHOP_CODE, info.shopCode==null?"":info.shopCode);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_SHOP_NAME, info.shopName==null?"":info.shopName);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_START_TIME, info.sTime==null?"":info.sTime);
		info.receiptPrint = info.receiptPrint
				.replace(Constants.TEMPLATE_STORE_CODE, info.storeCode==null?"":info.storeCode);
		info.receiptPrint = info.receiptPrint
				.replace(Constants.TEMPLATE_STORE_NAME, info.storeName==null?"":info.storeName);
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_SYSDATE,
				DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()));
		info.receiptPrint = info.receiptPrint.replace(Constants.TEMPLATE_SYSTIME,
				DateFormat.format("HH:mm:ss", System.currentTimeMillis()));
		return info.receiptPrint;
	}

	public static float getFloat(String str,float def){
		try{
			BigDecimal bd = new BigDecimal(str);
			return bd.setScale(2).floatValue();
		}catch (Exception E){
			return def;
		}
	}

	public static String getCurrentDate(){
		return DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()).toString();
	}

	public static String getCurrentTime(){
		return DateFormat.format("HH:mm:ss", System.currentTimeMillis()).toString();
	}
}
