package cn.m.mt.util;

public class Base64 {
	private static final byte[] encodingTable = { (byte) 'A', (byte) 'B',
			(byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
			(byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L',
			(byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
			(byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V',
			(byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a',
			(byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
			(byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k',
			(byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p',
			(byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
			(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
			(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
			(byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
			(byte) '+', (byte) '/' };
	private static final byte[] decodingTable;
	static {
		decodingTable = new byte[128];
		for (int i = 0; i < 128; i++) {
			decodingTable[i] = (byte) -1;
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			decodingTable[i] = (byte) (i - 'A');
		}
		for (int i = 'a'; i <= 'z'; i++) {
			decodingTable[i] = (byte) (i - 'a' + 26);
		}
		for (int i = '0'; i <= '9'; i++) {
			decodingTable[i] = (byte) (i - '0' + 52);
		}
		decodingTable['+'] = 62;
		decodingTable['/'] = 63;
	}

	public static byte[] encode(String s) {
		return encode(s.getBytes());
	}

	public static byte[] encode(byte[] data) {
		byte[] bytes;
		int modulus = data.length % 3;
		if (modulus == 0) {
			bytes = new byte[(4 * data.length) / 3];
		} else {
			bytes = new byte[4 * ((data.length / 3) + 1)];
		}
		int dataLength = (data.length - modulus);
		int a1;
		int a2;
		int a3;
		for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
			a1 = data[i] & 0xff;
			a2 = data[i + 1] & 0xff;
			a3 = data[i + 2] & 0xff;
			bytes[j] = encodingTable[(a1 >>> 2) & 0x3f];
			bytes[j + 1] = encodingTable[((a1 << 4) | (a2 >>> 4)) & 0x3f];
			bytes[j + 2] = encodingTable[((a2 << 2) | (a3 >>> 6)) & 0x3f];
			bytes[j + 3] = encodingTable[a3 & 0x3f];
		}
		int b1;
		int b2;
		int b3;
		int d1;
		int d2;
		switch (modulus) {
		case 0: /* nothing left to do */
			break;
		case 1:
			d1 = data[data.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = (d1 << 4) & 0x3f;
			bytes[bytes.length - 4] = encodingTable[b1];
			bytes[bytes.length - 3] = encodingTable[b2];
			bytes[bytes.length - 2] = (byte) '=';
			bytes[bytes.length - 1] = (byte) '=';
			break;
		case 2:
			d1 = data[data.length - 2] & 0xff;
			d2 = data[data.length - 1] & 0xff;
			b1 = (d1 >>> 2) & 0x3f;
			b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
			b3 = (d2 << 2) & 0x3f;
			bytes[bytes.length - 4] = encodingTable[b1];
			bytes[bytes.length - 3] = encodingTable[b2];
			bytes[bytes.length - 2] = encodingTable[b3];
			bytes[bytes.length - 1] = (byte) '=';
			break;
		}
		return bytes;
	}

	public static byte[] decode(byte[] data) {
		byte[] bytes;
		byte b1;
		byte b2;
		byte b3;
		byte b4;
		data = discardNonBase64Bytes(data);
		if (data[data.length - 2] == '=') {
			bytes = new byte[(((data.length / 4) - 1) * 3) + 1];
		} else if (data[data.length - 1] == '=') {
			bytes = new byte[(((data.length / 4) - 1) * 3) + 2];
		} else {
			bytes = new byte[((data.length / 4) * 3)];
		}
		for (int i = 0, j = 0; i < (data.length - 4); i += 4, j += 3) {
			b1 = decodingTable[data[i]];
			b2 = decodingTable[data[i + 1]];
			b3 = decodingTable[data[i + 2]];
			b4 = decodingTable[data[i + 3]];
			bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte) ((b3 << 6) | b4);
		}
		if (data[data.length - 2] == '=') {
			b1 = decodingTable[data[data.length - 4]];
			b2 = decodingTable[data[data.length - 3]];
			bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
		} else if (data[data.length - 1] == '=') {
			b1 = decodingTable[data[data.length - 4]];
			b2 = decodingTable[data[data.length - 3]];
			b3 = decodingTable[data[data.length - 2]];
			bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
		} else {
			b1 = decodingTable[data[data.length - 4]];
			b2 = decodingTable[data[data.length - 3]];
			b3 = decodingTable[data[data.length - 2]];
			b4 = decodingTable[data[data.length - 1]];
			bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
		}
		return bytes;
	}

	public static byte[] decode(String data) {
		byte[] bytes;
		byte b1;
		byte b2;
		byte b3;
		byte b4;
		data = discardNonBase64Chars(data);
		if (data.charAt(data.length() - 2) == '=') {
			bytes = new byte[(((data.length() / 4) - 1) * 3) + 1];
		} else if (data.charAt(data.length() - 1) == '=') {
			bytes = new byte[(((data.length() / 4) - 1) * 3) + 2];
		} else {
			bytes = new byte[((data.length() / 4) * 3)];
		}
		for (int i = 0, j = 0; i < (data.length() - 4); i += 4, j += 3) {
			b1 = decodingTable[data.charAt(i)];
			b2 = decodingTable[data.charAt(i + 1)];
			b3 = decodingTable[data.charAt(i + 2)];
			b4 = decodingTable[data.charAt(i + 3)];
			bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[j + 2] = (byte) ((b3 << 6) | b4);
		}
		if (data.charAt(data.length() - 2) == '=') {
			b1 = decodingTable[data.charAt(data.length() - 4)];
			b2 = decodingTable[data.charAt(data.length() - 3)];
			bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
		} else if (data.charAt(data.length() - 1) == '=') {
			b1 = decodingTable[data.charAt(data.length() - 4)];
			b2 = decodingTable[data.charAt(data.length() - 3)];
			b3 = decodingTable[data.charAt(data.length() - 2)];
			bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
		} else {
			b1 = decodingTable[data.charAt(data.length() - 4)];
			b2 = decodingTable[data.charAt(data.length() - 3)];
			b3 = decodingTable[data.charAt(data.length() - 2)];
			b4 = decodingTable[data.charAt(data.length() - 1)];
			bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
			bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
			bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
		}
		return bytes;
	}

	private static byte[] discardNonBase64Bytes(byte[] data) {
		byte[] temp = new byte[data.length];
		int bytesCopied = 0;
		for (int i = 0; i < data.length; i++) {
			if (isValidBase64Byte(data[i])) {
				temp[bytesCopied++] = data[i];
			}
		}
		byte[] newData = new byte[bytesCopied];
		System.arraycopy(temp, 0, newData, 0, bytesCopied);
		return newData;
	}

	private static String discardNonBase64Chars(String data) {
		StringBuffer sb = new StringBuffer();
		int length = data.length();
		for (int i = 0; i < length; i++) {
			if (isValidBase64Byte((byte) (data.charAt(i)))) {
				sb.append(data.charAt(i));
			}
		}
		return sb.toString();
	}

	private static boolean isValidBase64Byte(byte b) {
		if (b == '=') {
			return true;
		} else if ((b < 0) || (b >= 128)) {
			return false;
		} else if (decodingTable[b] == -1) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// String data = "1104141358525627";
		// byte[] result = Base64.encode(data.getBytes());
		// System.out.println(data);
		// System.out.println(new String(result));
		// System.out.println(new String(Base64.decode(new String(result))));
		
		// System.out.println(FileUtil.readFile("c:\\test.jpg"));
		
		String s = "8acb2190462859f0613d3b11054cd34d53f33617e64589a40fcfe879fb7c2a70e40d41fe8f7216dca3a896fe6eaff1a997dfbebebe3110d969667efd48aa575da4b96f2997f965b4883e7fde1957f4e5e90e01ce137e665be5aa99a76e7dd577a7d2bc8511605827728faa1e87a85589e410e76648d1ed62f1b45bb1f4eba23c417b4484994671a4e8bf08e41175c7cb9d151894e70fd5e9";
//		System.out.println(a(s.getBytes()));
//		System.out.println(s.length());
		s = a(s.getBytes());
		for (int i = 0; i < s.length() - 7; i++) {
			String s1 = s.substring(i);
			char[] cs = s1.toCharArray();
			System.out.println( "---" + t(cs));
		}
	}

	static String t(char[] paramArrayOfChar) {
		String str1 = new String();

		// int j = (j = paramArrayOfChar[1]) - '\006' - 4;
		// System.out.println("j==="+j);

		for (int k = 0; k < paramArrayOfChar.length - 2; k++) {
			str1 = str1
					+ (char) (48 + ((paramArrayOfChar[(k + 2)] & 0xF0) >> '\004'));
			str1 = str1 + (char) ('0' + (paramArrayOfChar[(k + 2)] & 0xF));
		}
		// for (int k = 0; k < 6; k++) {
		// str1 = str1 + 'X';
		// }
		// for (int k = 0; k < 2; k++) {
		// str1 = str1
		// + (char) (48 + ((paramArrayOfChar[(k + 5)] & 0xF0) >> '\004'));
		// str1 = str1 + (char) ('0' + (paramArrayOfChar[(k + 5)] & 0xF));
		// }

		return str1;
	}

	private static String a(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null)
			return "";
		StringBuilder localStringBuilder = new StringBuilder(
				paramArrayOfByte.length);
		byte[] arrayOfByte = paramArrayOfByte;
		int j = paramArrayOfByte.length;
		for (int i = 0; i < j; i++) {
			byte param = arrayOfByte[i];
			localStringBuilder.append(Integer.toString(
					(param & 0xFF) + 256, 16).substring(1));
		}
		return localStringBuilder.toString();
	}

}
