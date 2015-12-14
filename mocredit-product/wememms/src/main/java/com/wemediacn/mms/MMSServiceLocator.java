/**
 * MMSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class MMSServiceLocator extends org.apache.axis.client.Service implements com.wemediacn.mms.MMSService {

    public MMSServiceLocator() {
    }


    public MMSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MMSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MMSServiceSoap
    private java.lang.String MMSServiceSoap_address = "http://www.wemediacn.net/webservice/mmsservice.asmx";

    public java.lang.String getMMSServiceSoapAddress() {
        return MMSServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MMSServiceSoapWSDDServiceName = "MMSServiceSoap";

    public java.lang.String getMMSServiceSoapWSDDServiceName() {
        return MMSServiceSoapWSDDServiceName;
    }

    public void setMMSServiceSoapWSDDServiceName(java.lang.String name) {
        MMSServiceSoapWSDDServiceName = name;
    }

    public com.wemediacn.mms.MMSServiceSoap getMMSServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MMSServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMMSServiceSoap(endpoint);
    }

    public com.wemediacn.mms.MMSServiceSoap getMMSServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.wemediacn.mms.MMSServiceSoapStub _stub = new com.wemediacn.mms.MMSServiceSoapStub(portAddress, this);
            _stub.setPortName(getMMSServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMMSServiceSoapEndpointAddress(java.lang.String address) {
        MMSServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.wemediacn.mms.MMSServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.wemediacn.mms.MMSServiceSoapStub _stub = new com.wemediacn.mms.MMSServiceSoapStub(new java.net.URL(MMSServiceSoap_address), this);
                _stub.setPortName(getMMSServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MMSServiceSoap".equals(inputPortName)) {
            return getMMSServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://mms.wemediacn.com/", "MMSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "MMSServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MMSServiceSoap".equals(portName)) {
            setMMSServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
