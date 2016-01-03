package com.mocredit.bank.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.mocredit.bank.util.FormatException;



public class UP_LoadSchema {
	private static Logger logger = Logger.getLogger(UP_LoadSchema.class);
	private static Document schemaDoc = null;
	private static Namespace namespace = null;
	private static String xsdFilePath = "";
	private static ConcurrentHashMap<Integer, String> id_name_mapping =new ConcurrentHashMap<Integer, String>();
	
	/**POS报文有64个域*/
	private static Element[] fullSchemaElementCache = new Element[64];
	private static Map<String, Element> name_element_mapping = new HashMap<String, Element>();
	private static Map<String, Element> name_format_mapping = new HashMap<String, Element>();
	
	public static void loadSchema(String rootPath) throws Exception {
		//if(null!=rootPath) return;
		String path = rootPath+"POS8583.xsd";
		xsdFilePath = path;
		try {
			schemaDoc = new SAXBuilder().build(path);
			Element root = schemaDoc.getRootElement();
			for(int i=0;i<128;i++) {
				/**
				 * 通过XPath获取如下格式的element
				 * <element>
				 * 	<annotation>
				 * 		<documentation>
				 * 		</documentation>
				 * 		<appinfo>
				 * 			<format/>
				 * 		</appinfo>
				 * 	</annotation>
				 * </element>
				 */
				
				//XPath xpath = XPath.newInstance("xsd:element[xsd:annotation/xsd:appinfo/xsd:format[@id='"+i+"']]");
				 XPath xpath = XPath.newInstance("xsd:element[xsd:annotation/xsd:appinfo/xsd:format[@id='"+i+"']]");
				Namespace ns = root.getNamespace();
				namespace=ns;
				xpath.addNamespace("xsd",root.getNamespace().getURI());
				Element element = (Element) xpath.selectSingleNode(root);
				if(null!=element) {
					String elementName = element.getAttributeValue("name");
					id_name_mapping.put(i, elementName);
					name_element_mapping.put(elementName, element);
					Element format = element.getChild("annotation",ns).getChild("appinfo",ns).getChild("format",ns);
					name_format_mapping.put(elementName, format);
					if(i==0) {
						fullSchemaElementCache[i] = element;
					}else {
						fullSchemaElementCache[i-1] = element;
					}
					
				}
			}
		} catch (JDOMException e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw e;
		} catch (IOException e1) {
			logger.error(e1.getMessage());
			throw e1;
		}
		
	}
	
	public static Document getSchema() {
		return schemaDoc;
	}
	
	public static String getElementNameById(int id)
			throws FormatException {
		if(id<0 || id>128)
			throw new FormatException("get element name by id,id is:["+id+"],error!");
			String name = id_name_mapping.get(id);
		if(null==name) {
			throw new FormatException("in unionpay full schema,can not get the element name by id:["+id+"]");
		}else if(name.length()<1) {
			throw new FormatException("the attribute name do not hava value,id:["+id+"]");
		}
		return name;
	}
	
	public static Element getElementById(int id) {
		return fullSchemaElementCache[id];
	} 
	
	public static Element getElementByName(String name){
		return name_element_mapping.get(name);
	}
	public static Element getFormatElementByName(String name){
		return name_format_mapping.get(name);
	}
	
	public static Namespace getNamespace(){
		return namespace;
	}
	
	public static String getXsdFilePath() {
		return xsdFilePath;
	}

	public static void setXsdFilePath(String xsdFilePath) {
		UP_LoadSchema.xsdFilePath = xsdFilePath;
	}
	
	public static void main(String[] args) throws Exception{
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
//		String root = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String root = "conf/";
		UP_LoadSchema.loadSchema(root);
		System.out.println(fullSchemaElementCache.length+","+fullSchemaElementCache[63].getName());
		Iterator<String> it = name_element_mapping.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key+","+name_element_mapping.get(key));
		}
	}

}
