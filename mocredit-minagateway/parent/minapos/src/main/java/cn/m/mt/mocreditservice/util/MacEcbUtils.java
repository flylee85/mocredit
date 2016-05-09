package cn.m.mt.mocreditservice.util;

import cn.m.mt.util.ASCIIUtil;


public class MacEcbUtils {  
    public static byte[] IV = new byte[8];  
  
    public static byte byteXOR(byte src, byte src1) {  
        return (byte) ((src & 0xFF) ^ (src1 & 0xFF));  
    }  
  
    public static byte[] bytesXOR(byte[] src, byte[] src1) {  
        int length = src.length;  
        if (length != src1.length) {  
            return null;  
        }  
        byte[] result = new byte[length];  
        for (int i = 0; i < length; i++) {  
            result[i] = byteXOR(src[i], src1[i]);  
        }  
        return result;  
    }  
  
    /** 
     * mac计算,数据不为8的倍数，需要补0，将数据8个字节进行异或，再将异或的结果与下一个8个字节异或，一直到最后，将异或后的数据进行DES计算 
     *  
     * @param key 
     * @param Input 
     * @return 
     * @throws Exception 
     */  
    public static String clacMac(byte[] key, byte[] Input) throws Exception {  
        int length = Input.length;  
        int x = length % 8;  
        int addLen = 0;  
        if (x != 0) {  
            addLen = 8 - length % 8;  
        }  
        int pos = 0;  
        byte[] data = new byte[length + addLen];  
        System.arraycopy(Input, 0, data, 0, length);  
        byte[] oper1 = new byte[8];  
        System.arraycopy(data, pos, oper1, 0, 8);  
        pos += 8;  
        for (int i = 1; i < data.length / 8; i++) {  
            byte[] oper2 = new byte[8];  
            System.arraycopy(data, pos, oper2, 0, 8);  
            byte[] t = bytesXOR(oper1, oper2);  
            oper1 = t;  
            pos += 8;  
        }  
        byte[] buff = null;
        String str = ASCIIUtil.bytesToHexString(oper1);
        String str1  = str.substring(0,8).toUpperCase();
        String str2  = str.substring(8,16).toUpperCase();
		buff = MacEncryption.encrypt(str1.getBytes(), key);
		byte[] t = bytesXOR(str2.getBytes(), buff);  
		buff = MacEncryption.encrypt(t, key);
		
		return ASCIIUtil.String2HexString(ASCIIUtil.bytesToHexString(buff).toUpperCase().substring(0, 8));
    }  
  
    public static void main(String[] args) { 
    	String str = "003D600032000060310031120312600020000000C000110001253132333435363738313233343536373839303132333435000801000013" +
    			"3437464437454639";
    	System.out.println("---"+ASCIIUtil.String2HexString("VavicApp"));
    	String reqstr = str.substring(22, str.length() - 16);
    	System.out.println(reqstr);
        byte[] hexbyte= ASCIIUtil.hexStringToByte(reqstr);
        //4f65396a6f467a50
        byte[] key =ASCIIUtil.hexStringToByte(ASCIIUtil.String2HexString("11111111"));// {0x4F,0x65,0x39,0x6A,0x6F,0x46,0x7A,0x50};
        try {
			System.out.println(clacMac(key, hexbyte));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
//        byte[] msg = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff}; 
//        byte[] pass = {0x31,0x31,0x31,0x31,0x31,0x31,0x31,0x31}; 
//        try {
//        	byte[] bs = MacEncryption.encrypt(msg,pass);
//			for(byte b:bs){
//				System.out.println(b);
//			}	
//			System.out.println(ASCIIUtil.bytesToHexString(bs));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
    }  
}  