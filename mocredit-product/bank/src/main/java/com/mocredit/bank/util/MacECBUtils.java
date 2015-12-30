package com.mocredit.bank.util;

public class MacECBUtils {
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
     */  
    public static String clacMac(byte[] key, byte[] Input) {  
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
		try {
			buff = MacEncryption.encrypt(str1.getBytes(), key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		byte[] t = bytesXOR(str2.getBytes(), buff);  
		try {
			buff = MacEncryption.encrypt(t, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ASCIIUtil.String2HexString(ASCIIUtil.bytesToHexString(buff).toUpperCase().substring(0, 8));
    }
    /**
     * 民生领奖mac
     * @param keyL
     * @param keyR
     * @param Input
     * @return
     */
    public static String clacPrizeMac(byte[] key, byte[] Input) {
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
//         String str = ASCIIUtil.bytesToHexString(oper1);
//         String str1  = str.substring(0,8).toUpperCase();
//         String str2  = str.substring(8,16).toUpperCase();
 		 
 		try {
 			buff = MacEncryption.encrypt(oper1, key);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
// 		String s = ASCIIUtil.String2HexString(ASCIIUtil.bytesToHexString(buff).toUpperCase());
 		String s = ASCIIUtil.bytesToHexString(buff).toUpperCase();
 		System.out.println(":::"+s);
 		return s;
    }
    
    /** 
     * 民生银行MAC生成算法
     * (1)MAC数据先按8字节分组，表示为D0～Dn，如果Dn不足8字节时，尾部以字节00补齐。
     * (2)用MAC密钥左半部加密D0，加密结果与D1异或作为下一次的输入。
     * (3)将上一步的加密结果与下一分组异或，然后用MAC密钥左半部加密。
     * (4)直至所有分组结束。
     * (5)用MAC密钥右半部解密(5)的结果。
     * (6)用MAC密钥左半部加密(6)的结果。
     * (7)取(7)的结果的左半部作为MAC。
     * @param keyL
     * @param keyR
     * @param Input 
     * @return 
     */  
    public static String clacMsyhMac(byte[] keyL,byte[] keyR, byte[] Input) {
    	//先获取数据的长度
        int length = Input.length;
        //除八求余
        int x = length % 8;
        //设置补0个数
        int addLen = 0;
        if (x != 0) {
            addLen = 8 - length % 8;  
        }
        int pos = 0;
        //设置计算data补零后的长度
        byte[] data = new byte[length + addLen];
        //将inputcopy到data中
        System.arraycopy(Input, 0, data, 0, length);
        //声明一个8位的字节数组
        byte[] oper1 = new byte[8];
        //从data中取8位放到oper1中
        System.arraycopy(data, pos, oper1, 0, 8);
        try {
			oper1 =  MacEncryption.encrypt(oper1, keyL);
//			System.out.println("OPER1::["+HexBinary.encode(oper1)+"]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //pos+8
        pos += 8;
        for (int i = 1; i < data.length / 8; i++) {  
            byte[] oper2 = new byte[8];
            System.arraycopy(data, pos, oper2, 0, 8);
            try {
				oper1 = MacEncryption.encrypt(bytesXOR(oper1, oper2), keyL);
				System.out.println("["+i+"]="+HexBinary.encode(oper1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            pos += 8;  
        }
        try {
			oper1 = MacEncryption.decrypt(oper1, keyR);
//			System.out.println("秘钥右半部分加密:["+HexBinary.encode(oper1)+"]");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        byte[] buff = null;
		try {
			buff = MacEncryption.encrypt(oper1, keyL);
//			System.out.println("左半部分加密"+HexBinary.encode(buff));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return ASCIIUtil.String2HexString(ASCIIUtil.bytesToHexString(buff).toUpperCase().substring(0, 8));
    }
    /**测试方法*/
    public static void main(String[] args) {
//		String mac = MacECBUtils.clacMac(HexBinary.decode("31A1319DF8494AD5"), HexBinary.decode("0200302404C020C09815000000000000120000000096110602106406374048390000105909D11061010000035400002F303030303030303130303030303030303030303030303131353641B4F3410D5FD9C52600000000000000000822000001000449503036"));
		String mac =  MacECBUtils.clacMsyhMac(HexBinary.decode("86A2ED76E908FD11A20C5F5392082A3B".substring(0,16)),
				HexBinary.decode("86A2ED76E908FD11A20C5F5392082A3B".substring(16,32)),  HexBinary.decode("02007024048000C080151662262300172933490000000000000009990000521609010004303030303030303133303531313030303030303930303531353600082200000600194143544E303030303030303030303030303431"));
		System.out.println(mac);
	}
}
