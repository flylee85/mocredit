package com.mocredit.base.datastructure.impl;

import java.io.Serializable;

import com.mocredit.base.datastructure.ResponseData;
/**
 * Response数据对象实现类
 * @author lishoukun
 * @date 2015/07/02
 */
public class AjaxResponseData implements ResponseData,Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 3248208771348746025L;
  /**
   * 错误编码，例如服务器出现异常,请稍后重试
   */
  private String code = "200";
  /**
   * 错误友好提示,例如"服务器出现异常,请稍候再试"
   */
  private String errorMsg = "";
  /**
   * 是否访问成功,如果后台出现异常则返回false
   */
  private boolean success = true ;
  /**
   * 返回的内容
   */
  private Exception exception = null;
  /**
   * 返回的内容
   */
  private Object data = null;
  
  /**
   * @return the errorMsg
   */
  public String getErrorMsg() {
    return errorMsg;
  }
  /**
   * @param errorMsg the errorMsg to set
   */
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
  /**
   * @param errorMsg the errorMsg to set
   */
  public void setErrorMsg(String errorMsg,Exception e) {
	this.success = false;
    this.errorMsg = errorMsg;
    //如果是自定义的异常类
    if(e.getClass().getName().startsWith("com.mocredit.base.exception")){
    	this.code = "200";
    }else{
    	this.code = "500";
    }
    this.exception = e;
  }
  /**
   * @return the isSuccess
   */
  public boolean getSuccess() {
    return success;
  }
  /**
   * @param isSuccess the isSuccess to set
   */
  public void setSuccess(boolean success) {
    this.success = success;
  }
  /**
   * @return the data
   */
  public Object getData() {
    return data;
  }
  /**
   * @param data the data to set
   */
  public void setData(Object data) {
    this.data = data;
  }
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
  
}
