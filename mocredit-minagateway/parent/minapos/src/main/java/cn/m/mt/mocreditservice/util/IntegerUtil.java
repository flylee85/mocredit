package cn.m.mt.mocreditservice.util;

import java.io.UnsupportedEncodingException;

public class IntegerUtil {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = intToString16(91999);
		System.out.println(str);
		System.out.println(IntegerUtil.intToString16(1));
	}

	public static String intToString16(int data) {
		String str = Integer.toHexString(data);
		if(str.length()%2!=0)
			str = "0"+str;
		return str; 
	}

	public static int String16ToInt(String data) {
		return Integer.parseInt(data,16);
	}
	
	/*
	 * 十六进制转换成二进制
	 */
	public static String convertHexToBinary3(String hexString){
	        long l = Long.parseLong(hexString, 16);
	        String binaryString = Long.toBinaryString(l);
	        int shouldBinaryLen = hexString.length()*4;
	        StringBuffer addZero = new StringBuffer();
	        int addZeroNum = shouldBinaryLen-binaryString.length();
	        for(int i=1;i<=addZeroNum;i++){
	            addZero.append("0");
	        }
	        return addZero.toString()+binaryString;
	    }
}