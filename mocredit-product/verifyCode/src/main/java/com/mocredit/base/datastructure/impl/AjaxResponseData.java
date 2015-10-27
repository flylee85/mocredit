package com.mocredit.base.datastructure.impl;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.mocredit.base.datastructure.ResponseData;
/**
 * Response数据对象实现类
 * @author lishoukun
 * @date 2015/07/02
 */
public class AjaxResponseData implements Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 3248208771348746025L;
  /**
   * 错误友好提示,例如"服务器出现异常,请稍候再试"
   */
  private String errorMsg = "";

  /**
   * 错误代码
   */
  private String errorCode="";
  /**
   * 是否访问成功,如果后台出现异常则返回false
   */
  private boolean success = true ;
  /**
   * 返回的异常内容
   */
  private Exception exception = null;
  /**
   * 返回的内容
   */
  private Object data = null;

  /**
   * 冗余数据，目前主要用作存放日志用的数据对象。
   * 使用注解，防止FASTJSON 序列化它
   */
  @JSONField(serialize = false)
  private  Object redundancyData = null;

  
  public boolean isSuccess() {
		return success;
  }
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
  public void setSuccess(boolean isSuccess) {
    this.success = isSuccess;
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

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public Object getRedundancyData() {
    return redundancyData;
  }

  public void setRedundancyData(Object redundancyData) {
    this.redundancyData = redundancyData;
  }
}
