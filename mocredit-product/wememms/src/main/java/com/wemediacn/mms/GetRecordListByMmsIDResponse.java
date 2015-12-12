/**
 * GetRecordListByMmsIDResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class GetRecordListByMmsIDResponse  implements java.io.Serializable {
    private com.wemediacn.mms.GetRecordListByMmsIDResponseGetRecordListByMmsIDResult getRecordListByMmsIDResult;

    public GetRecordListByMmsIDResponse() {
    }

    public GetRecordListByMmsIDResponse(
           com.wemediacn.mms.GetRecordListByMmsIDResponseGetRecordListByMmsIDResult getRecordListByMmsIDResult) {
           this.getRecordListByMmsIDResult = getRecordListByMmsIDResult;
    }


    /**
     * Gets the getRecordListByMmsIDResult value for this GetRecordListByMmsIDResponse.
     * 
     * @return getRecordListByMmsIDResult
     */
    public com.wemediacn.mms.GetRecordListByMmsIDResponseGetRecordListByMmsIDResult getGetRecordListByMmsIDResult() {
        return getRecordListByMmsIDResult;
    }


    /**
     * Sets the getRecordListByMmsIDResult value for this GetRecordListByMmsIDResponse.
     * 
     * @param getRecordListByMmsIDResult
     */
    public void setGetRecordListByMmsIDResult(com.wemediacn.mms.GetRecordListByMmsIDResponseGetRecordListByMmsIDResult getRecordListByMmsIDResult) {
        this.getRecordListByMmsIDResult = getRecordListByMmsIDResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetRecordListByMmsIDResponse)) return false;
        GetRecordListByMmsIDResponse other = (GetRecordListByMmsIDResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getRecordListByMmsIDResult==null && other.getGetRecordListByMmsIDResult()==null) || 
             (this.getRecordListByMmsIDResult!=null &&
              this.getRecordListByMmsIDResult.equals(other.getGetRecordListByMmsIDResult())));
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
        if (getGetRecordListByMmsIDResult() != null) {
            _hashCode += getGetRecordListByMmsIDResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetRecordListByMmsIDResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">GetRecordListByMmsIDResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getRecordListByMmsIDResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "GetRecordListByMmsIDResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">>GetRecordListByMmsIDResponse>GetRecordListByMmsIDResult"));
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
