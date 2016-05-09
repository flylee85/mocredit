package cn.m.mt.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class StringUtils {
	 public static char [] chars = {   
             '0','1','2','3','4','5','6','7','8','9',   
             'a','b','c','d','e','f','g','h','i','j',   
             'k','l','m','n','o','p','q','r','s','t',   
             'u','v','w','x','y','z'  
             };   

		public static String get36Char(Long source){
	        String str36Char = new String();   
	        String str36Char_B = new String();   
	        String str36Char_E = new String();   
	           
	        long lnBvalue = source/36;   
	        long lnB = lnBvalue%36;        
	           
	        long lnE = source%36;   
	           
	        //获取首位字符   
	        char char_B = chars[Integer.valueOf(String.valueOf(lnB)).intValue()];   
	        str36Char_B = String.valueOf(char_B);   
	        //获取第二位字符   
	        char char_E = chars[Integer.valueOf(String.valueOf(lnE)).intValue()];   
	        str36Char_E = String.valueOf(char_E);   
	           
	        //获取36进制的两位字符   
	        str36Char = str36Char_B + str36Char_E;   
	        return str36Char;  
		}
		
		public static String  getTokenData(){
			String date = DateTimeUtils.dateToStr(new Date(),"yyyyMMdd");
			String consumerkey=date+"key\\!@\\#consumer";
			String token_data = consumerkey + System.nanoTime();
			return DigestUtils.md5Hex(token_data);
		}
		public static void main(String[] args){
			System.out.println(get36Char(1234567l));
		}
		public static List<MyBarcode> createBarcode(int fromIndex,int toIndex){
			List<MyBarcode> list = new ArrayList<MyBarcode>();
			for(int i=0;i<10000000;i++){
				MyBarcode mybarcode = new MyBarcode();
				String numbercode = ""+i;
				String barcode = Integer.toHexString(i);
					String zero="";
					for(int j=0;j<7-numbercode.length();j++){
						zero+="0";
					}
					String zerohex="";
					for(int k=0;k<6-barcode.length();k++){
						zerohex+="0";
					}
					barcode = zerohex+barcode;
					barcode = barcode.toUpperCase();
					if(i%2==1){
						barcode=barcode.replace("3", "U").replace("8", "L").replace("C", "H").replace("4", "K");
					}else{
						barcode=barcode.replace("F", "R").replace("B", "P").replace("9", "S").replace("5", "M");
					}
					numbercode=zero+numbercode;
					mybarcode.setNumbercode(numbercode);
					mybarcode.setBarcode(barcode);
					Random random = new Random();
					String s=""+random.nextInt(10000000);
					mybarcode.setSort(s);
					list.add(mybarcode);
					if(i%1000000==0)
						System.out.println("i==="+i);
				}
			Collections.sort(list);
//			for(int i=0;i<10000;i++){
//				System.out.println("["+i+"]======="+list.get(i).getNumbercode()+","+list.get(i).getBarcode());
//			}
			return list.subList(fromIndex, toIndex);
		}
}
