package com.mocredit.integral.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author GuoDong Ni
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Utils {

	/**
	 * 把 数组1 与数组2 相加。
	 * 
	 * @param src
	 *            开始数组，
	 * @param dst
	 *            结束数组
	 * @return 返回 开始数组+结束数据
	 */
	public static byte[] appendArray(byte[] src, byte[] dst) {
		byte[] newBytes = new byte[src.length + dst.length];
		System.arraycopy(src, 0, newBytes, 0, src.length);
		System.arraycopy(dst, 0, newBytes, src.length, dst.length);
		return newBytes;
	}

	/**
	 * 
	 * @param str
	 *            需要检查的字符串
	 * @return 如果字符串为空或者为null 返回true 。其他返回false
	 */
	public static boolean isNullOrBlank(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		else
			return false;

	}

	public static String getRandNum(int len) {
		Random rand = new Random();
		String randStr = "";
		for (int i = 0; i < len; i++) {
			int num = rand.nextInt(10);
			randStr += String.valueOf(num);
		}
		return randStr;
	}

	public static String fillLeftZero(int val, int expectedLength)
			throws Exception {
		// -2147483648~2147483647
		try {
			String s = (new Integer(val)).toString();
			int lenTmp = s.length();
			if (lenTmp >= expectedLength)
				return s;

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < expectedLength - lenTmp; i++) {
				sb.append("0");
			}
			sb.append(s);

			return sb.toString();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 金额除100
	 * 
	 * @param str
	 * @return
	 */
	public static String divide100(String str) {
		BigDecimal bigd = new BigDecimal(str);
		BigDecimal bigd100 = bigd.divide(new BigDecimal(100), 2,
				BigDecimal.ROUND_HALF_UP);
		return bigd100.toString();

	}

	/**
	 * 乘法
	 * 
	 * @param str
	 * @param str2
	 * @return
	 */
	public static String b_multiply(String str, String str2) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (str == null || "".equals(str)) {
			return "";
		}
		if (str2 == null || "".equals(str2)) {
			return "";
		}
		BigDecimal big1 = new BigDecimal(str);
		BigDecimal big2 = new BigDecimal(str2);
		BigDecimal result = big1.multiply(big2);
		String str_result = df.format(result.setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue());

		return str_result;
	}

	public static String b_subtract(String str, String str2) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (str == null || "".equals(str)) {
			return "";
		}
		if (str2 == null || "".equals(str2)) {
			return "";
		}
		BigDecimal big1 = new BigDecimal(str);
		BigDecimal big2 = new BigDecimal(str2);
		BigDecimal result = big1.subtract(big2);
		String str_result = df.format(result.setScale(2,
				BigDecimal.ROUND_HALF_UP).doubleValue());
		return str_result;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsAllMum(String str) {
		boolean tag = false;
		if (str == null || str.trim().length() == 0)
			return false;
		char ss[] = str.toCharArray();

		for (int i = 0; i < str.length(); i++) {

			if (Character.isDigit(ss[i])) {
				tag = true;

			} else {
				tag = false;
				break;
			}

		}
		return tag;
	}

	/**
	 * 获取Ascii
	 * 
	 * @param cn
	 * @return
	 */
	public static int getAscii(char cn) {

		byte[] bytes = (String.valueOf(cn)).getBytes();
		if (bytes.length == 1) {
			return bytes[0];
		} else if (bytes.length == 2) {
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];
			int ascii = (256 * hightByte + lowByte) - 256 * 256;
			return ascii;
		} else {
			return 0;
		}
	}

	/**
	 * 生成随机数
	 * 
	 * @param digits
	 * @return
	 */
	public static String doGenRamdomNum(int digits) {
		java.util.Random r = new java.util.Random();
		int random = Math.abs(r.nextInt()) % (int) (Math.pow(10, digits));
		String result = null;
		int length = (random + "").length();
		result = random * ((int) Math.pow(10, digits - length)) + "";

		return result;
	}

	/**
	 * 生成随机数
	 * 
	 * @return
	 */
	public static String doGenRamdomNum2() {
		int numberCount = 6;
		Set set = new TreeSet();
		while (set.size() < numberCount) {
			int number = (int) (Math.random() * 33) + 1;
			if (!set.contains(number + "")) {
				set.add(number + "");
			}
		}
		String result = set.toString()
				.substring(1, set.toString().length() - 1);
		return result;
	}

	/**
	 * 去null值
	 */
	public static String delNull(String str) {

		str = str == null ? "" : str;

		return str.trim();
	}

	/**
	 * 格式化卡号
	 * 
	 * @param cardno
	 * @return
	 */
	public static String formatCardno(String cardno) {
		cardno = Utils.delNull(cardno);
		if (cardno.length() > 15) {
			cardno = cardno.substring(0, 4) + "********" + cardno.substring(12);
		} else if (cardno.length() == 15) {
			cardno = cardno.substring(0, 3) + "********" + cardno.substring(11);
		}
		return cardno;
	}

	/**
	 * 获取epose商户权限代码
	 * 
	 * @param eposeCode
	 *            权限字符串
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public static String getEposePowerValue(String eposeCode, int start, int end) {
		String temp = eposeCode.substring(start, end);
		return temp;

	}

	/**
	 * 进行身份证转换15位转18位
	 * 
	 * @param fifteenNumber
	 * @return
	 */
	public static String Cover15to18(String fifteenNumber) {
		try {
			if (fifteenNumber.length() == 15) {
				fifteenNumber = fifteenNumber.substring(0, 6) + "19"
						+ fifteenNumber.substring(6, 15);

				int[] temp1 = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
						4, 2 };
				char[] temp2 = { '1', '0', 'X', '9', '8', '7', '6', '5', '4',
						'3', '2' };
				char[] temp3 = fifteenNumber.toCharArray();
				int temp4 = 0;
				int temp5 = 0;
				int temp6;
				for (int i = 0; i < 17; i++) {
					temp6 = Integer.parseInt("" + temp3[i]);
					temp4 = temp6 * temp1[i];
					temp5 += temp4;
				}
				temp5 = temp5 % 11;

				fifteenNumber += temp2[temp5];
			}
		} catch (Exception e) {
		}

		return fifteenNumber;

	}

	public static void main(String[] args) {
		System.out.println(divide100("0000000005000"));
		System.out.println(b_multiply("0.1", "100.00"));
		System.out.println(b_subtract("100.00", "10.000"));

	}

}
