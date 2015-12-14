/**
 * QueryMMSSeqReportResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wemediacn.mms;

public class QueryMMSSeqReportResponse  implements java.io.Serializable {
    private com.wemediacn.mms.QueryMMSSeqReportResponseQueryMMSSeqReportResult queryMMSSeqReportResult;

    public QueryMMSSeqReportResponse() {
    }

    public QueryMMSSeqReportResponse(
           com.wemediacn.mms.QueryMMSSeqReportResponseQueryMMSSeqReportResult queryMMSSeqReportResult) {
           this.queryMMSSeqReportResult = queryMMSSeqReportResult;
    }


    /**
     * Gets the queryMMSSeqReportResult value for this QueryMMSSeqReportResponse.
     * 
     * @return queryMMSSeqReportResult
     */
    public com.wemediacn.mms.QueryMMSSeqReportResponseQueryMMSSeqReportResult getQueryMMSSeqReportResult() {
        return queryMMSSeqReportResult;
    }


    /**
     * Sets the queryMMSSeqReportResult value for this QueryMMSSeqReportResponse.
     * 
     * @param queryMMSSeqReportResult
     */
    public void setQueryMMSSeqReportResult(com.wemediacn.mms.QueryMMSSeqReportResponseQueryMMSSeqReportResult queryMMSSeqReportResult) {
        this.queryMMSSeqReportResult = queryMMSSeqReportResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryMMSSeqReportResponse)) return false;
        QueryMMSSeqReportResponse other = (QueryMMSSeqReportResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.queryMMSSeqReportResult==null && other.getQueryMMSSeqReportResult()==null) || 
             (this.queryMMSSeqReportResult!=null &&
              this.queryMMSSeqReportResult.equals(other.getQueryMMSSeqReportResult())));
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
        if (getQueryMMSSeqReportResult() != null) {
            _hashCode += getQueryMMSSeqReportResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryMMSSeqReportResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">QueryMMSSeqReportResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryMMSSeqReportResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://mms.wemediacn.com/", "QueryMMSSeqReportResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mms.wemediacn.com/", ">>QueryMMSSeqReportResponse>QueryMMSSeqReportResult"));
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
