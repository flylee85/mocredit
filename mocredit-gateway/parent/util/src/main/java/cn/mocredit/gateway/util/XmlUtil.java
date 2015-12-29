package cn.mocredit.gateway.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {

	public static <T> List<T> getBO(Class<T> clazz, String xmlstr) {
		List<T> varlist = new ArrayList<T>();
		T rsp = null;
		try {
			xmlstr = stripNonValidXMLCharacters(xmlstr);
			InputStream in = new ByteArrayInputStream(xmlstr.getBytes("utf8"));
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = builder.parse(in);
			NodeList employees = document.getChildNodes();
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();
					rsp = clazz.newInstance();
					for(int k=0;k<employeeMeta.getLength();k++){
						Node varnode = employeeMeta.item(k);
						NodeList employeeVar = varnode.getChildNodes();
						Field nameField = rsp.getClass().getDeclaredField(
								varnode.getNodeName());
						if (employeeVar.getLength() > 0){
							nameField.set(rsp, employeeVar.item(0).getTextContent());
						}
					}
					varlist.add(rsp);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return varlist;
	}

	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}
}
