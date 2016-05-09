package cn.m.mt.mocreditservice.util;

import java.util.Map;

import cn.m.mt.util.ASCIIUtil;

public class StringUtils {
	public static void main(String[] args) {
		int n=6; 

		String s="abc"; 

		System.out.println("%1$0"+(n-s.length())+"d"); 

		System.out.println(String.format("%04s",s));
	}
	 public static String moveFirst2Last(String str){
		  String str1 = str.substring(0,2);
		  String str2 = str.substring(2,6);
		  String str3 = str.substring(6,10);
		  return str1+str2+str3;
	 } 
	 public static String flushLeft(String str){
		int len =  str.length();
		 for(int i=0;i<4-len;i++){
			 str = "0"+str;
		 }
		 return str;
	 } 
	 
	
	 public static String Map2TLV(Map<String,String> data){
		 StringBuffer orderstr = new StringBuffer();
		 String str1 = "";
		 for(int i=1;i<=data.size();i++){
			 String key = "";
			 String value = "";
			 if(i<10){
				 key = "0"+i;
			 }else{
				 key = ""+i;
			 }
			 value = data.get(key);
		 	orderstr.append(key);
			str1 = ASCIIUtil.String2HexString(value);
			orderstr.append(IntegerUtil.intToString16(str1.length() / 2));
			orderstr.append(str1);
		 }
		 String temp = orderstr.toString();
		StringBuffer order5str = new StringBuffer();
		order5str.append(String.format("%04d", temp.length() / 2));
		order5str.append(temp);
		System.out.println(order5str.toString()+"====1====");
		return order5str.toString();
	 }
	/* public static String  getBatchid(int batchid){
		 if(batchid>999999){
			 return "0000001";
		 }
		 int id = batchid+1;
		 
//		 String batch
	 }*/
}
