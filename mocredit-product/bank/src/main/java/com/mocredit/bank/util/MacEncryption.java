package com.mocredit.bank.util;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class MacEncryption {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String enMac(String str) {
		// zak(mackey)
		byte[] MACKEY = new byte[] { 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31 };
		// 待计算数据
		byte[] data = str.getBytes();
		// 进行分组
		int group = (data.length + (8 - 1)) / 8;
		// 偏移量
		int offset = 0;
		// 输入计算数据
		byte[] edata = null;
		for (int i = 0; i < group; i++) {
			byte[] temp = new byte[8];
			if (i != group - 1) {
				System.arraycopy(data, offset, temp, 0, 8);
				offset += 8;
			} else {// 只有最后一组数据才进行填充0x00
				System.arraycopy(data, offset, temp, 0, data.length - offset);
			}

			if (i != 0) {// 只有第一次不做异或
				temp = XOR(edata, temp);
			}
			try {
				edata = encrypt(MACKEY, temp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println(java.util.Arrays.toString(byte2Int(edata)));
		return new String(HexBinary.BytesToStr(edata)).substring(0, 8);
	}

	public static byte[] XOR(byte[] edata, byte[] temp) {
		byte[] result = new byte[8];
		for (int i = 0, j = result.length; i < j; i++) {
			result[i] = (byte) (edata[i] ^ temp[i]);
		}
		return result;
	}

	public static int[] byte2Int(byte[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++) {
			if (data[i] < 0) {
				result[i] = data[i] + 256;
			} else {
				result[i] = data[i];
			}
		}

		return result;
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

	private static SecretKey getSecretKeySpec(byte[] keyB)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("Des");
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyB, "Des");
		return secretKeyFactory.generateSecret(secretKeySpec);
	}

	public static void main(String[] args) {
		System.out.println(ASCIIUtil.String2HexString(MacEncryption
				.enMac("C091")));
	}
}
