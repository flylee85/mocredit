package cn.mocredit.posp.bc.util;

import java.util.List;

public class StrUtil {
	
	
	/**
	 * 验证list中所有字段是否都存在
	 * @param fieldsList 要验证的字段list
	 */
	public static void validateFieldExistence(List<String> fieldsList){
		for(String field:fieldsList){
			if(null==field||field.length()==0){
				throw new RuntimeException("非空字段为空!");
			}
		}
	}
	/**
	 * 替换指定位置字符串
	 * @param index
	 * @param res
	 * @param str
	 * @return
	 */
    public static String replaceIndex(int index,String res,String str){
		return res.substring(0, index)+str+res.substring(index+1);
	}
    
    /**
     * 把ip地址格式化为：000.000.000.000
     * @param ip
     * @return 返回规格ip
     */
    public static String strfullip(String ip){
     StringBuffer buff = new StringBuffer();
     buff.append("");
     String strzero = "000";
     int ilen = 0;
     if(ip != null){
      String[] arrip = ip.split("\\.");
      if(arrip.length == 4){
       for(int i = 0; i < 4; i++){
        if (i==0){
         ilen = arrip[i].length();
         if(ilen < 3){
          buff.append(strzero.substring(0,3-ilen)).append(arrip[i]);
         }else{
          buff.append(arrip[i]);
         }
        }else{
         ilen = arrip[i].length();
         if(ilen < 3){
          buff.append(".").append(strzero.substring(0,3-ilen)).append(arrip[i]);
         }else{
          buff.append(".").append(arrip[i]);
         }
        }
       }
      }
     }
     return buff.toString();
    }

}
