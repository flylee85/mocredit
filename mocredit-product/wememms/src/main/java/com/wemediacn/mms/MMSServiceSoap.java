/**
 * MMSServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public interface MMSServiceSoap extends java.rmi.Remote {

    /**
     * 功能:发送单条彩信;<br/>参数说明:<br/>mobile: 手机号；<br/>mmsid: 彩信id；<br/>TokenID：企业验证码；<br/>返回值:<br/>OK:[MessageID]
     */
    public java.lang.String sendMMS(java.lang.String mobile, int mmsid, java.lang.String sToken) throws java.rmi.RemoteException;

    /**
     * 功能:群发送彩信;<br/>参数说明:<br/>XMLStr:XML数据集：mobile:手机号，mmsid:彩信id
     * .<br/>TokenID：企业验证码<br/>返回值:<br/>返回成功发送数量
     */
    public java.lang.String sendMMSCovey(java.lang.String XMLStr, java.lang.String sToken) throws java.rmi.RemoteException;

    /**
     * 个性化彩信发送
     */
    public java.lang.String sendPersonMMS(java.lang.String mobile, int mmsid, java.lang.String sToken, int parameterCount, java.lang.String parameter01, java.lang.String parameter02, java.lang.String parameter03, java.lang.String parameter04, java.lang.String parameter05, java.lang.String parameter06, java.lang.String parameter07, java.lang.String parameter08, java.lang.String parameter09) throws java.rmi.RemoteException;
    public com.wemediacn.mms.GetPublicationResponseGetPublicationResult getPublication(java.util.Calendar time, java.lang.String telephone, java.lang.String sToken) throws java.rmi.RemoteException;
    public com.wemediacn.mms.GetRecordListResponseGetRecordListResult getRecordList(java.util.Calendar time, int startID, java.lang.String sToken) throws java.rmi.RemoteException;
    public com.wemediacn.mms.GetRecordListByMmsIDResponseGetRecordListByMmsIDResult getRecordListByMmsID(java.util.Calendar time, int mmsid, int startID, java.lang.String sToken) throws java.rmi.RemoteException;

    /**
     * 功能：批量查询最新彩信发送结果<br/>参数：<br/>MinID:开始的ID号（第一次查询传0）;<br/>TokenID:企业验证码
     */
    public com.wemediacn.mms.QueryMMSSeqReportResponseQueryMMSSeqReportResult queryMMSSeqReport(int minID, java.lang.String tokenID) throws java.rmi.RemoteException;

    /**
     * 功能：查询上行彩信<br/>参数说明：<br/>MinID:开始的ID号（第一次查询传0）；<br/>TokenID:企业验证码；<br/>返回值：xml
     */
    public com.wemediacn.mms.QueryMMSUpResponseQueryMMSUpResult queryMMSUp(int minID, java.lang.String tokenID) throws java.rmi.RemoteException;
    public java.lang.String postMMSMessage(java.lang.String mmsPack, java.lang.String sToken) throws java.rmi.RemoteException;
}
