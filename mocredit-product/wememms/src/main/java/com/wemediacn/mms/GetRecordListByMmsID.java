/**
 * GetRecordListByMmsID.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class GetRecordListByMmsID  implements java.io.Serializable {
    private java.util.Calendar time;

    private int mmsid;

    private int startID;

    private java.lang.String sToken;

    public GetRecordListByMmsID() {
    }

    public GetRecordListByMmsID(
           java.util.Calendar time,
           int mmsid,
           int startID,
           java.lang.String sToken) {
           this.time = time;
           this.mmsid = mmsid;
           this.startID = startID;
           this.sToken = sToken;
    }


    /**
     * Gets the time value for this GetRecordListByMmsID.
     * 
     * @return time
     */
    public java.util.Calendar getTime() {
        return time;
    }


    /**
     * Sets the time value for this GetRecordListByMmsID.
     * 
     * @param time
     */
    public void setTime(java.util.Calendar time) {
        this.time = time;
    }


    /**
     * Gets the mmsid value for this GetRecordListByMmsID.
     * 
     * @return mmsid
     */
    public int getMmsid() {
        return mmsid;
    }


    /**
     * Sets the mmsid value for this GetRecordListByMmsID.
     * 
     * @param mmsid
     */
    public void setMmsid(int mmsid) {
        this.mmsid = mmsid;
    }


    /**
     * Gets the startID value for this GetRecordListByMmsID.
     * 
     * @return startID
     */
    public int getStartID() {
        return startID;
    }


    /**
     * Sets the startID value for this GetRecordListByMmsID.
     * 
     * @param startID
     */
    public void setStartID(int startID) {
        this.startID = startID;
    }


    /**
     * Gets the sToken value for this GetRecordListByMmsID.
     * 
     * @return sToken
     */
    public java.lang.String getSToken() {
        return sToken;
    }


    /**
     * Sets the sToken value for this GetRecordListByMmsID.
     * 
     * @param sToken
     */
    public void setSToken(java.lang.String sToken) {
        this.sToken = sToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetRecordListByMmsID)) return false;
        GetRecordListByMmsID other = (GetRecordListByMmsID) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.time==null && other.getTime()==null) || 
             (this.time!=null &&
              this.time.equals(other.getTime()))) &&
            this.mmsid == other.getMmsid() &&
            this.startID == other.getStartID() &&
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
        if (getTime() != null) {
            _hashCode += getTime().hashCode();
        }
        _hashCode += getMmsid();
        _hashCode += getStartID();
        if (getSToken() != null) {
            _hashCode += getSToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetRecordListByMmsID.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">GetRecordListByMmsID"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mmsid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "mmsid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "startID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
