package com.mocredit.base.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
  
  /**
   * 将对象转为json
   * @param obj
   * @return
   */
  public static String toJson(Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * 将json串转为对象
   * @param str
   * @param valueType
   * @return
   */
  public static <T> T toObj(String str,Class<T> valueType){
    try {
      return new ObjectMapper().readValue(str, valueType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
