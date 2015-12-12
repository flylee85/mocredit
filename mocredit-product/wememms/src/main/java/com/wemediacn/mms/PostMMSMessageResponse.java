/**
 * PostMMSMessageResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class PostMMSMessageResponse  implements java.io.Serializable {
    private java.lang.String postMMSMessageResult;

    public PostMMSMessageResponse() {
    }

    public PostMMSMessageResponse(
           java.lang.String postMMSMessageResult) {
           this.postMMSMessageResult = postMMSMessageResult;
    }


    /**
     * Gets the postMMSMessageResult value for this PostMMSMessageResponse.
     * 
     * @return postMMSMessageResult
     */
    public java.lang.String getPostMMSMessageResult() {
        return postMMSMessageResult;
    }


    /**
     * Sets the postMMSMessageResult value for this PostMMSMessageResponse.
     * 
     * @param postMMSMessageResult
     */
    public void setPostMMSMessageResult(java.lang.String postMMSMessageResult) {
        this.postMMSMessageResult = postMMSMessageResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PostMMSMessageResponse)) return false;
        PostMMSMessageResponse other = (PostMMSMessageResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.postMMSMessageResult==null && other.getPostMMSMessageResult()==null) || 
             (this.postMMSMessageResult!=null &&
              this.postMMSMessageResult.equals(other.getPostMMSMessageResult())));
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
        if (getPostMMSMessageResult() != null) {
            _hashCode += getPostMMSMessageResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PostMMSMessageResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">PostMMSMessageResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postMMSMessageResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "PostMMSMessageResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
