package com.mocredit.base.datastructure;
/**
 * Response数据对象接口
 * @author lishoukun
 * @date 2015/07/02
 */
public interface ResponseData {
	
	public boolean isSuccess();

	public void setSuccess(boolean success);

	public String getErrorMsg();

	public void setErrorMsg(String errorMsg);
	
	public void setErrorMsg(String errorMsg, Exception e);

	public Object getData();

	public void setData(Object data);

	public int getErrorCode();

	public void setErrorCode(int errorCode);
}
