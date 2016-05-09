package cn.m.mt.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class ASCIIUtil {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(decode("6839336d694d3739").toString());
//		System.out.println(decode("383838383838383838383838").toString());
		
		String hexstr = ASCIIUtil.encode("aaaaaaaa");
		System.out.println("==hexstr=="+hexstr);
		System.out.println(ASCIIUtil.decode(hexstr));
		
		
	}
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
			result[i] = (byte) (result[i]&0xFF);
		}
		return result;
	}
	public static void printHexString( byte[] b) {    
		   for (int i = 0; i < b.length; i++) {   
		     String hex = Integer.toHexString(b[i] & 0xFF);   
		     if (hex.length() == 1) {   
		       hex = '0' + hex;   
		     }   
		     System.out.print(hex.toUpperCase() +"");   
		   }   
		  
		} 
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  

	public static String String2HexString(String str) {
		str = str==null?"":str;
		try {
			str = bytesToHexString(str.getBytes("gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str==null?"":str;
	}
	public static String String2Add0(String str,int length){
		if(str.length()>=length){
			return str;
		}else{
			int len = length - str.length();
			for(int i=0;i<len;i++){
				str = "0" + str;
			}
			return str;
		}
	}
	private static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}
	 public static byte[] hexStr2Bytes(String src)  
	    {  
	        int m=0,n=0;  
	        int l=src.length()/2;  
	        System.out.println(l);  
	        byte[] ret = new byte[l];  
	        for (int i = 0; i < l; i++)  
	        {  
	            m=i*2+1;  
	            n=m+1;  
	            ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));  
	        }  
	        return ret;  
	    }  

	public static String String2BCD(String str) {
		return new String(ASCII_To_BCD(str.getBytes(), str.length()));
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
	  public static String AsciiStringToString(String content) {
	        String result = "";
	        int length = content.length() / 2;
	        for (int i = 0; i < length; i++) {
	            String c = content.substring(i * 2, i * 2 + 2);
	            int a = hexStringToAlgorism(c);
	            char b = (char) a;
	            String d = String.valueOf(b);
	            result += d;
	        }
	        return result;
	    }
	  public static int hexStringToAlgorism(String hex) {
	        hex = hex.toUpperCase();
	        int max = hex.length();
	        int result = 0;
	        for (int i = max; i > 0; i--) {
	            char c = hex.charAt(i - 1);
	            int algorism = 0;
	            if (c >= '0' && c <= '9') {
	                algorism = c - '0';
	            } else {
	                algorism = c - 55;
	            }
	            result += Math.pow(16, max - i) * algorism;
	        }
	        return result;
	    }
	private static String hexString = "0123456789ABCDEF";

	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}

		return bcd;
	}



	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

}