package cn.m.mt.barcodeservice.util;

import java.security.MessageDigest;

/**
 * 使用java.security.MessageDigest类写的一个工具类用来获取MD5码
 * 
 * @see java.security.MessageDigest
 */
public class MD5Util {
	/** */
	/**
	 * 向getMD5方法传入一个你需要转换的原始字符串，将返回字符串的MD5码
	 * 
	 * @param code
	 *            原始字符串
	 * @return 返回字符串的MD5码
	 */
	public static String getMD5(String code) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] bytes = code.getBytes();
		byte[] results = messageDigest.digest(bytes);
		StringBuilder stringBuilder = new StringBuilder();
		for (byte result : results) {
			// 将byte数组转化为16进制字符存入stringbuilder中
			stringBuilder.append(String.format("%02x", result));
		}
		return stringBuilder.toString();
	}

	/**
	 * main方法用于测试
	 */
	public static void main(String[] args) {
		// 字符'a'的MD5码是0cc175b9c0f1b6a831c399e269772661,如果是，则成功。
		try {
			System.out.println(MD5Util.getMD5("a"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
