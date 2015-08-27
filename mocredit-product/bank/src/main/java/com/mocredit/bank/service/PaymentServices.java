/**
 * PaymentServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mocredit.bank.service;

public interface PaymentServices extends java.rmi.Remote {
    public java.lang.String maintainSession(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String getDynamicPwd(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String checkAccount(java.lang.String paraXML) throws java.rmi.RemoteException;
   /**
    * 分期支付
    * @param paraXML
    * @return
    * @throws java.rmi.RemoteException
    */
    public java.lang.String dividedPayment(java.lang.String paraXML,String cardNum,int requestId) throws java.rmi.RemoteException;
    public java.lang.String changePassword(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String logout(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String upload(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String settltment(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String getQuestResult(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String confirmInfo(java.lang.String paraXML,String cardNum,int requestId) throws java.rmi.RemoteException;
    public java.lang.String hirePurchaseReturn(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String download(java.lang.String paraXML) throws java.rmi.RemoteException;
    public java.lang.String dividedPaymentReversal(java.lang.String paraXML,String cardNum,int requestId) throws java.rmi.RemoteException;
    /**
     * 登录
     * @param paraXML
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String login(java.lang.String paraXML,String cardNum,int requestId) throws java.rmi.RemoteException;

    void setEndPoint(String endPoint);
}
