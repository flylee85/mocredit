package com.mocredit.integral.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.Random;

public class RandomUtil {
	
//	/**
//	 * Description: 创建二维码
//	 * @Version1.0 2011-5-31 下午03:45:25 by 凡红恩（fanhongen@emay.cn）创建
//	 * @param codetype 二维码类型  0会员码，1联惠码，2一次性码
//	 * @return numcode+","+charcode
//	 */
//	public static String createRandom(String codetype){
//		StringBuffer numcode = new StringBuffer();//数字二维码
//		StringBuffer charcode = new StringBuffer();//字母二维码
//		String seeds[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
//		Random random=new Random();
//		int codelength=ResourceUtil.YICIXING_CODE_LENGTH;
//		if(codetype.equals(Variable.HUIYUAN)){
//			//首数字代0表会员码,所以第一位以0标示
//			numcode.append(0);
//			charcode.append(0);
//			codelength=ResourceUtil.HUIYUAN_CODE_LENGTH;
//		}else if(codetype.equals(Variable.LIANHUIDIAN)){
//			//首数字1代表联会店
//			numcode.append(1);
//			charcode.append(1);
//			codelength=ResourceUtil.LIANHUIDIAN_CODE_LENGTH;
//		}else{
//			//首数字非0,1代表一次性码
//			int temp=0;
//			while(temp==0 || temp==1){
//				temp=random.nextInt(10);
//			}
//			numcode.append(temp);
//			charcode.append(temp);
//		}
//		if(codelength<=0) return null;
//		for(int i=1;i<codelength;i++){
//			numcode.append(random.nextInt(10));//nextInt(10) 0-10之间的整数、不包含10
//			if(i%2==0){
//				int index=random.nextInt(26);
//				if(index>=26) index=25;
//				charcode.append(seeds[index]);
//			}else{
//				charcode.append(random.nextInt(10));
//			}
//		}
//		//System.out.println(numcode.toString()+","+charcode.toString());
//		return numcode.toString()+","+charcode.toString();
//		
//	}
	/**
	 * 生成二维码串"数字码+，+字符码 "
	 */
	public static String createRandom(int codelength){
		StringBuffer numcode = new StringBuffer();//数字二维码
		StringBuffer charcode = new StringBuffer();//字母二维码
		String seeds[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		Random random=new Random();
		if(codelength<=0) return null;
		for(int i=0;i<codelength;i++){
			numcode.append(random.nextInt(10));//nextInt(10) 0-10之间的整数、不包含10
			if(i%2==0){
				int index=random.nextInt(26);
				if(index>=26) index=25;
				charcode.append(seeds[index]);
			}else{
				charcode.append(random.nextInt(10));
			}
		}
		return numcode.toString()+","+charcode.toString();
		
	}
	
	public static void main(String str[]){
		/*for(int i=0;i<10;i++){
			System.out.println(RandomUtil.createRandom(Variable.LIANHUIDIAN));
		}*/
//		String a=RandomUtil.createRandom(Variable.LIANHUIDIAN);
//		String b=RandomUtil.createRandom(Variable.HUIYUAN);
		//String c=RandomUtil.randomStr(10);
//		System.out.println(RandomUtil.getCodeType(a));
//		System.out.println(RandomUtil.getCodeType(b));
		System.out.println(new Date());
		for(int i=0;i<100;i++){
			System.out.println(createRandom(7,6));
		}
		System.out.println(new Date());
		
		//System.out.println(c);
		
	}
	
	
	
	
	public static String randomStr(int length){
		
		StringBuffer result= new StringBuffer("");
		Random random=new Random();
		if(length<=0) return "";
		for(int i=0;i<length;i++){
			result.append(random.nextInt(10));
		}
		return result.toString();
	}
	
	/**
	 * 生成二维码串"数字码+，+字符码 "(最新码库)
	 * 刘国庆
	 */
	public static String createRandom(int codelength,int charlength){
		StringBuffer numcode = new StringBuffer();//数字二维码
		StringBuffer charcode = new StringBuffer();//字母二维码
		String seeds[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		Random random=new Random();
		if(codelength<=0) return null;
		for(int i=0;i<codelength;i++){
			numcode.append(random.nextInt(10));//nextInt(10) 0-10之间的整数、不包含10
		}
		for(int i=0;i<charlength;i++){
			if(i%2==0){
				int index=random.nextInt(26);
				if(index>=26) index=25;
				charcode.append(seeds[index]);
			}else{
				charcode.append(random.nextInt(10));
			}
		}
		
		return numcode.toString()+","+charcode.toString();
	}
	public static String getRandom(){
		Random rand = new Random(); 
		int tmp = Math.abs(rand.nextInt());
		return String.valueOf(tmp % (999999 - 100000 + 1) + 100000);
	}
	
	public static String getString(int length){
		  String base = "0123456789";   //生成字符串从此序列中取
		  Random random = new Random();   
		  StringBuffer sb = new StringBuffer();   
		  for (int i = 0; i < length; i++) {   
		      int number = random.nextInt(base.length());   
		      sb.append(base.charAt(number));   
		  }  
		  return sb.toString();
	  }
}
