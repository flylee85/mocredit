package com.wemediacn.mms;

public class MMSServiceSoapProxy implements com.wemediacn.mms.MMSServiceSoap {
  private String _endpoint = null;
  private com.wemediacn.mms.MMSServiceSoap mMSServiceSoap = null;
  
  public MMSServiceSoapProxy() {
    _initMMSServiceSoapProxy();
  }
  
  public MMSServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initMMSServiceSoapProxy();
  }
  
  private void _initMMSServiceSoapProxy() {
    try {
      mMSServiceSoap = (new com.wemediacn.mms.MMSServiceLocator()).getMMSServiceSoap();
      if (mMSServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mMSServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mMSServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mMSServiceSoap != null)
      ((javax.xml.rpc.Stub)mMSServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.wemediacn.mms.MMSServiceSoap getMMSServiceSoap() {
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap;
  }
  
  public java.lang.String sendMMS(java.lang.String mobile, int mmsid, java.lang.String sToken) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.sendMMS(mobile, mmsid, sToken);
  }
  
  public java.lang.String sendMMSCovey(java.lang.String XMLStr, java.lang.String sToken) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.sendMMSCovey(XMLStr, sToken);
  }
  
  public java.lang.String sendPersonMMS(java.lang.String mobile, int mmsid, java.lang.String sToken, int parameterCount, java.lang.String parameter01, java.lang.String parameter02, java.lang.String parameter03, java.lang.String parameter04, java.lang.String parameter05, java.lang.String parameter06, java.lang.String parameter07, java.lang.String parameter08, java.lang.String parameter09) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.sendPersonMMS(mobile, mmsid, sToken, parameterCount, parameter01, parameter02, parameter03, parameter04, parameter05, parameter06, parameter07, parameter08, parameter09);
  }
  
  public com.wemediacn.mms.GetPublicationResponseGetPublicationResult getPublication(java.util.Calendar time, java.lang.String telephone, java.lang.String sToken) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.getPublication(time, telephone, sToken);
  }
  
  public com.wemediacn.mms.GetRecordListResponseGetRecordListResult getRecordList(java.util.Calendar time, int startID, java.lang.String sToken) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.getRecordList(time, startID, sToken);
  }
  
  public com.wemediacn.mms.GetRecordListByMmsIDResponseGetRecordListByMmsIDResult getRecordListByMmsID(java.util.Calendar time, int mmsid, int startID, java.lang.String sToken) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.getRecordListByMmsID(time, mmsid, startID, sToken);
  }
  
  public com.wemediacn.mms.QueryMMSSeqReportResponseQueryMMSSeqReportResult queryMMSSeqReport(int minID, java.lang.String tokenID) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.queryMMSSeqReport(minID, tokenID);
  }
  
  public com.wemediacn.mms.QueryMMSUpResponseQueryMMSUpResult queryMMSUp(int minID, java.lang.String tokenID) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.queryMMSUp(minID, tokenID);
  }
  
  public java.lang.String postMMSMessage(java.lang.String mmsPack, java.lang.String sToken) throws java.rmi.RemoteException{
    if (mMSServiceSoap == null)
      _initMMSServiceSoapProxy();
    return mMSServiceSoap.postMMSMessage(mmsPack, sToken);
  }
  
  
}