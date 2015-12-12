/**
 * QueryMMSUp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class QueryMMSUp  implements java.io.Serializable {
    private int minID;

    private java.lang.String tokenID;

    public QueryMMSUp() {
    }

    public QueryMMSUp(
           int minID,
           java.lang.String tokenID) {
           this.minID = minID;
           this.tokenID = tokenID;
    }


    /**
     * Gets the minID value for this QueryMMSUp.
     * 
     * @return minID
     */
    public int getMinID() {
        return minID;
    }


    /**
     * Sets the minID value for this QueryMMSUp.
     * 
     * @param minID
     */
    public void setMinID(int minID) {
        this.minID = minID;
    }


    /**
     * Gets the tokenID value for this QueryMMSUp.
     * 
     * @return tokenID
     */
    public java.lang.String getTokenID() {
        return tokenID;
    }


    /**
     * Sets the tokenID value for this QueryMMSUp.
     * 
     * @param tokenID
     */
    public void setTokenID(java.lang.String tokenID) {
        this.tokenID = tokenID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryMMSUp)) return false;
        QueryMMSUp other = (QueryMMSUp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.minID == other.getMinID() &&
            ((this.tokenID==null && other.getTokenID()==null) || 
             (this.tokenID!=null &&
              this.tokenID.equals(other.getTokenID())));
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
        _hashCode += getMinID();
        if (getTokenID() != null) {
            _hashCode += getTokenID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryMMSUp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">QueryMMSUp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "MinID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tokenID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "TokenID"));
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
