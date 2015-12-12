/**
 * MMSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public interface MMSService extends javax.xml.rpc.Service {
    public java.lang.String getMMSServiceSoapAddress();

    public com.wemediacn.mms.MMSServiceSoap getMMSServiceSoap() throws javax.xml.rpc.ServiceException;

    public com.wemediacn.mms.MMSServiceSoap getMMSServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
