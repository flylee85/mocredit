package cn.m.mt.mocreditservice.util;

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

import cn.m.mt.mocreditservice.bo.EitemListBo;

public class XmlUtil {

	public static void main(String[] args) {

//		String xmlstr = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
//		xmlstr = xmlstr
//				+ "<stream><RETCODE>AAAAAAA</RETCODE><RETMSG>123</RETMSG><OREDR>OREDR</OREDR>";
//		xmlstr = xmlstr + "</stream>";
//		Order order = getBO(new Order().getClass(), xmlstr);
//		System.out.println(order.getRETCODE());
//		System.out.println(order.getRETMSG());
//		getBO(new OrderBO().getClass(), "<?xml version=\"1.0\" encoding=\"GBK\"?><stream><RETCODE>AAAAAAA</RETCODE><QRCODEORDERID>20130206094239000068</QRCODEORDERID><ORDERNO>136011502400792603</ORDERNO><ORDERST>2</ORDERST><NAME></NAME><PAYBANK>中信银行</PAYBANK><CARDNO>4427300200002138</CARDNO><EQUIPNAME>中信商户</EQUIPNAME><AMT>500.00</AMT><CURRTYPE>156</CURRTYPE><ORDERDATE>2013-02-06</ORDERDATE><ORDERTIME>09:39:15</ORDERTIME><EQUIPID>355798021393855</EQUIPID><ORDERNOYL></ORDERNOYL><ORDERTIMEYL></ORDERTIMEYL><STDAUTHID>806676</STDAUTHID><BANKTYPE>1</BANKTYPE><CARDTYPE>2</CARDTYPE></stream>");
		List<EitemListBo> eitem = (List<EitemListBo>) getBO(new EitemListBo().getClass(), "<?xml version=\"1.0\" encoding=\"utf8\"?><NewDataSet><Table><isSuccess>false</isSuccess><resultInfo>不是合法的机具。</resultInfo></Table><Table><isSuccess>1</isSuccess><resultInfo>不是合法的机具。</resultInfo></Table><Table><isSuccess>2</isSuccess><resultInfo>不是合法的机具。</resultInfo></Table></NewDataSet>");
		for(EitemListBo eb:eitem){
			System.out.println(eb.getIsSuccess());
			System.out.println(eb.getResultInfo());
		}
	}

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
