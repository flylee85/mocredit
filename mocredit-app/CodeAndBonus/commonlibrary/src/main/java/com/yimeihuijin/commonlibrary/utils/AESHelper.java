package com.yimeihuijin.commonlibrary.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static org.apaches.commons.codec.binary.Hex.decodeHex;
import static org.apaches.commons.codec.binary.Hex.encodeHexString;

public class AESHelper {

	public static final String CHARSET_NAME = "UTF-8";

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return 16进制字符串
	 */
	public static String encrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(ENCRYPT_MODE,
					new SecretKeySpec(decodeHex(password.toCharArray()), "AES"));
			return encodeHexString(cipher.doFinal(content
					.getBytes(CHARSET_NAME)));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容(16进制的字符串)
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(DECRYPT_MODE,
					new SecretKeySpec(decodeHex(password.toCharArray()), "AES"));

			return new String(cipher.doFinal(decodeHex(content.toCharArray())),
					CHARSET_NAME);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String content = "ABCDEFG";
		String password = "221F52ACDFA04ED593EDD94D4BD15CD5";
		System.out.println("加密前：" + content);
		String encrypt = encrypt(content, password);
		System.out.println("加密后：" + encrypt);
		System.out.println("解密后：" + decrypt(encrypt, password));
	}
}
