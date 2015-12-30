package cn.mocredit.gateway.posp.bc.util;

import java.security.Security;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DesTools {
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	public static byte[] encrypt(byte[] msg, byte[] pass) throws Exception {
		byte[] input = msg;

		byte[] keyBytes = pass;

		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NOPADDING");

		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		return cipherText;

	}

	public static byte[] decrypt(byte[] s, byte[] k) throws Exception {

		byte[] input = s;

		byte[] keyBytes = k;
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/NOPADDING");

		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		return cipherText;

	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String getDESKey(int length) {
		String base = "0BND7E6FC8HI95JKX1L4MOGPQRSA2TUV3WYZ"; // 生成字符串从此序列中取
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	public static String str2des(String zkey,String mackey){
		byte[] mankeys = zkey.getBytes();// 主密钥
		byte[] mackeys = mackey.getBytes();// mac密钥

		try {
			byte[] macbyte = DesTools.encrypt(mackeys, mankeys);
			String desmac =  DesTools.bytesToHexString(macbyte);
			byte[] checkvals = {0,0,0,0,0,0,0,0};
			byte[] checkvalbyte = DesTools.encrypt(checkvals, mackeys);
			String checkval =  DesTools.bytesToHexString(checkvalbyte);
			String desstr = desmac+checkval.substring(0,8);
			desstr = desstr+desstr;
			return desstr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}