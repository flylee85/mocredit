package cn.m.mt.util;

import java.util.Random;

public class KeyUtil {

	 public static String getDESKey(int length)
	  {
		  String base = "z0xBcNvDb7qnwEmle6FrkatCys8uHidIofp9g5JhKXj1L4MOGPQRSA2TUV3WYZ";   //生成字符串从此序列中取
		  Random random = new Random();   
		  StringBuffer sb = new StringBuffer();   
		  for (int i = 0; i < length; i++) {   
		      int number = random.nextInt(base.length());   
		      sb.append(base.charAt(number));   
		  }  
		  return sb.toString();
	  }
}   
