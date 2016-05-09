package cn.m.mt.util;

import javax.crypto.*;
import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.spec.*;
import javax.crypto.spec.*;

public class DES {
	public DES() {
	}
	public static void main(String[] args) {
		DES des = new DES();
		System.out.println(des.encrypt("010,2217221822192220,1306081413,钢铁侠3"));
	}
	public String encrypt(String str) {
		byte[] enc = null;
		try {
			enc = desEncrypt(str, "12345678");
		} catch (Exception ex) {
		}
		return new sun.misc.BASE64Encoder().encode(enc);
	}

	public static byte[] desEncrypt(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}
}