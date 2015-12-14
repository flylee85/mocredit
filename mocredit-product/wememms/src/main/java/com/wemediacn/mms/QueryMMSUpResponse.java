/**
 * QueryMMSUpResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class QueryMMSUpResponse  implements java.io.Serializable {
    private com.wemediacn.mms.QueryMMSUpResponseQueryMMSUpResult queryMMSUpResult;

    public QueryMMSUpResponse() {
    }

    public QueryMMSUpResponse(
           com.wemediacn.mms.QueryMMSUpResponseQueryMMSUpResult queryMMSUpResult) {
           this.queryMMSUpResult = queryMMSUpResult;
    }


    /**
     * Gets the queryMMSUpResult value for this QueryMMSUpResponse.
     * 
     * @return queryMMSUpResult
     */
    public com.wemediacn.mms.QueryMMSUpResponseQueryMMSUpResult getQueryMMSUpResult() {
        return queryMMSUpResult;
    }


    /**
     * Sets the queryMMSUpResult value for this QueryMMSUpResponse.
     * 
     * @param queryMMSUpResult
     */
    public void setQueryMMSUpResult(com.wemediacn.mms.QueryMMSUpResponseQueryMMSUpResult queryMMSUpResult) {
        this.queryMMSUpResult = queryMMSUpResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryMMSUpResponse)) return false;
        QueryMMSUpResponse other = (QueryMMSUpResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.queryMMSUpResult==null && other.getQueryMMSUpResult()==null) || 
             (this.queryMMSUpResult!=null &&
              this.queryMMSUpResult.equals(other.getQueryMMSUpResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getQueryMMSUpResult() != null) {
            _hashCode += getQueryMMSUpResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryMMSUpResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">QueryMMSUpResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryMMSUpResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "QueryMMSUpResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">>QueryMMSUpResponse>QueryMMSUpResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
