/**
 * @author  liuguoqing
 *
 * @date： 日期：2012-1-11 时间：下午04:39:34

 *@version 1.0
 */
package cn.m.mt.barcodeservice.yht;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.types.Schema;

import cn.m.mt.barcodeservice.util.Variable;

/**
 * @author  liuguoqing
 *
 * @date： 日期：2012-1-11 时间：下午04:39:34
 *@version 1.0
 */
public class YhtWSClient {
	public static String getYhtContent(String methods,String[] params,Object[] values,String yhturl){
    	StringBuffer sb=new StringBuffer();
        Service service=new Service(); 
        try{ 
            Call call=(Call)service.createCall(); 
            call.setTargetEndpointAddress(yhturl); 
            call.setOperationName(new QName(Variable.YHTSOAPACTION,methods)); //设置要调用哪个方法 、
            for(String param:params){
            	   call.addParameter(new QName(Variable.YHTSOAPACTION,param), //设置要传递的参数 
                   		org.apache.axis.encoding.XMLType.XSD_STRING, 
                   		javax.xml.rpc.ParameterMode.IN);
            }
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_SCHEMA);   
            call.setUseSOAPAction(true); 
            call.setSOAPActionURI(Variable.YHTSOAPACTION + methods);     
            Schema schema = (Schema)call.invoke(values);//调用方法并传递参数         
            if(null!=schema && schema.get_any()[1].getChildNodes().item(0)!=null){
            	int nLength=schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().getLength();
            	for(int j=0;j<schema.get_any()[1].getChildNodes().item(0).getChildNodes().getLength();j++){
            		sb.append("<Table>");
	        	   for(int i=0;i<nLength;i++){
		            	String name=schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(j).getChildNodes().item(i).getNodeName();
	            		sb.append("<");
	            		sb.append(name);
	            		sb.append(">");
	            		if(schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(j).getChildNodes().item(i).getChildNodes().getLength()==1){
	            			String value=schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(j).getChildNodes().item(i).getChildNodes().item(0).getNodeValue();
	            			if(name.indexOf("ValidFrom")>=0 || name.indexOf("ValidTo")>=0){
	            				value=value.replace("T"," ").substring(0,10);
		            		}
	            			if(name.indexOf("CurrentDateTime")>=0){
	            				value=value.replace("T"," ").substring(0,19);
	            			}
	            			sb.append(value);
	            		}
	            			
	            		sb.append("</");
	            		sb.append(name);
	            		sb.append(">");
		            }
	        	   sb.append("<Canusenum>1</Canusenum>");
	        	   sb.append("</Table>");
	           }
            	
            }else{
//            	sb.append("<Table>");
//            	sb.append("null");
//            	sb.append("</Table>");
//            	return sb.toString();
            	return null;
            }
        }catch(Exception ex) 
        { 
        	ex.printStackTrace(); 
        	return null;
        }        
        
        return sb.toString();
	}
	
	public static String getYhtContentToString(String methods,String[] params,Object[] values,String yhturl){
		Service service=new Service(); 
        StringBuffer sb=new StringBuffer();
        sb.append("<Table>");
        try{ 
            Call call=(Call)service.createCall(); 
            call.setTargetEndpointAddress(yhturl); 
            call.setOperationName(new QName(Variable.YHTSOAPACTION,methods)); //设置要调用哪个方法 、
            for(String param:params){
            	   call.addParameter(new QName(Variable.YHTSOAPACTION,param), //设置要传递的参数 
                   		org.apache.axis.encoding.XMLType.XSD_STRING, 
                   		javax.xml.rpc.ParameterMode.IN);
            }
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_SCHEMA);   
            call.setUseSOAPAction(true); 
            call.setSOAPActionURI(Variable.YHTSOAPACTION + methods);    
            Schema schema = (Schema)call.invoke(values);//调用方法并传递参数       
            if(null!=schema && schema.get_any()[1].getChildNodes().item(0)!=null){
            	int nLength=schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().getLength();
	            for(int i=0;i<nLength;i++){
	            	String name=schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getNodeName();
            		sb.append("<");
            		sb.append(name);
            		sb.append(">");
            		if(schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getChildNodes().getLength()>0){
            			sb.append(schema.get_any()[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(0).getNodeValue());
            		}
            		sb.append("</");
            		sb.append(name);
            		sb.append(">");
	            }
            }else{
            	sb.append("null");
            	sb.append("</Table>");
            	return null;
            }
        }catch(Exception ex) 
        { 
        	ex.printStackTrace(); 
        	sb.append("null");
        	sb.append("</Table>");
        	return null;
        }       
        sb.append("<Canusenum>1</Canusenum>");
        sb.append("</Table>");
        System.out.println("===sb111111111111111111111111111111111111==="+sb.toString());
        return sb.toString();
	}
	
	
	
	public static String[] getYhtContentToStringList(String methods,String[] params,Object[] values,String yhturl){
        Service service=new Service(); 
        String[] strs=null;
        try{ 
            Call call=(Call)service.createCall(); 
            call.setTargetEndpointAddress(yhturl); 
            call.setOperationName(new QName(Variable.YHTSOAPACTION,methods)); //设置要调用哪个方法 、
            for(String param:params){
            	   call.addParameter(new QName(Variable.YHTSOAPACTION,param), //设置要传递的参数 
                   		org.apache.axis.encoding.XMLType.XSD_STRING, 
                   		javax.xml.rpc.ParameterMode.IN);
            }
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_SCHEMA);   
            call.setUseSOAPAction(true); 
            call.setSOAPActionURI(Variable.YHTSOAPACTION + methods);     
            Schema schema = (Schema)call.invoke(values);//调用方法并传递参数         
            if(null!=schema){
	            int nLength=schema.get_any().length;
	            strs=new String[nLength];
	            for(int i=0;i<nLength;i++){
	            	strs[i]=schema.get_any()[i].getValue();
	            }
            }else{
            	return null;
            }
        }catch(Exception ex) 
        { 
        	ex.printStackTrace(); 
        	return null;
        }         
        return strs;
	}
	
	public static void main(String args[]){
		
//		
//		String[] params=new String[4];
//		params[0]="IMEI";
//		params[1]="verifyPwd";
//		params[2]="MmsId";
//		params[3]="PrintNo";
//		String[] yht=YhtWSClient.getYhtContentToStringList("PasswordVerifyByMmsId", params, new Object[]{"357462020008265","95734212","5b9ca9eb-7473-4094-92bc-ed2dca07ddd4","51031203160001"});
//		for(String t:yht)
//			System.out.println(t);
		
		String[] params=new String[2];
		
		params[0]="IMEI";
		params[1]="code";
		String yht=YhtWSClient.getYhtContentToString("showBarcodeAmount", params,new Object[]{"357462020028453","79759708"},"http://zx.emay.cn/WebService/ClientWS.asmx?wsdl");
		System.out.println(yht+"===yht==");
//        Service service=new Service(); 
//        StringBuffer sb=new StringBuffer();
//        sb.append("<Table>");
//        try{ 
//            Call call=(Call)service.createCall(); 
//            call.setTargetEndpointAddress(Variable.YHTURL); 
//            call.setOperationName(new QName(Variable.YHTSOAPACTION,"TestDeviceByIMEI")); //设置要调用哪个方法 、
//            	   call.addParameter(new QName(Variable.YHTSOAPACTION,"IMEI"), //设置要传递的参数 
//                   		org.apache.axis.encoding.XMLType.XSD_STRING, 
//                   		javax.xml.rpc.ParameterMode.IN);
//            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_SCHEMA);   
//            call.setUseSOAPAction(true); 
//            call.setSOAPActionURI(Variable.YHTSOAPACTION + "TestDeviceByIMEI");     
//            Schema schema = (Schema)call.invoke(new Object[]{"357462020006574"});//调用方法并传递参数       
//           //System.out.println(schema.get_any().length);
//        }catch(Exception ex) 
//        { 
//        	ex.printStackTrace(); 
//        	sb.append("null");
//        	sb.append("</Table>");
//        }       
//        sb.append("</Table>");
	

	}
	
}

