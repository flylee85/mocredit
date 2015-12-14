/**
 * PostMMSMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class PostMMSMessage  implements java.io.Serializable {
    private java.lang.String mmsPack;

    private java.lang.String sToken;

    public PostMMSMessage() {
    }

    public PostMMSMessage(
           java.lang.String mmsPack,
           java.lang.String sToken) {
           this.mmsPack = mmsPack;
           this.sToken = sToken;
    }


    /**
     * Gets the mmsPack value for this PostMMSMessage.
     * 
     * @return mmsPack
     */
    public java.lang.String getMmsPack() {
        return mmsPack;
    }


    /**
     * Sets the mmsPack value for this PostMMSMessage.
     * 
     * @param mmsPack
     */
    public void setMmsPack(java.lang.String mmsPack) {
        this.mmsPack = mmsPack;
    }


    /**
     * Gets the sToken value for this PostMMSMessage.
     * 
     * @return sToken
     */
    public java.lang.String getSToken() {
        return sToken;
    }


    /**
     * Sets the sToken value for this PostMMSMessage.
     * 
     * @param sToken
     */
    public void setSToken(java.lang.String sToken) {
        this.sToken = sToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PostMMSMessage)) return false;
        PostMMSMessage other = (PostMMSMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mmsPack==null && other.getMmsPack()==null) || 
             (this.mmsPack!=null &&
              this.mmsPack.equals(other.getMmsPack()))) &&
            ((this.sToken==null && other.getSToken()==null) || 
             (this.sToken!=null &&
              this.sToken.equals(other.getSToken())));
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
        if (getMmsPack() != null) {
            _hashCode += getMmsPack().hashCode();
        }
        if (getSToken() != null) {
            _hashCode += getSToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PostMMSMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">PostMMSMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mmsPack");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "mmsPack"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SToken");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "sToken"));
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
