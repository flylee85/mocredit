package com.mocredit.recharge.util;
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map; 

import org.dom4j.Attribute;
import org.dom4j.Document; 
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element; 
public class XmlUtils { 
	
	public static Map<String, Object> Xml2Map(String xml){
		try {
			Document doc = DocumentHelper.parseText(xml);
			return Dom2Map(doc);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
  @SuppressWarnings("unchecked")  
    public static Map<String, Object> Dom2Map(Document doc){ 
        Map<String, Object> map = new HashMap<String, Object>(); 
        if(doc == null) 
            return map; 
        Element root = doc.getRootElement(); 
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
            Element e = (Element) iterator.next(); 
            List list = e.elements(); 
            if(list.size() > 0){ 
                map.put(e.getName(), Dom2Map(e)); 
            }else {
            	map.put(e.getName(), e.getText()); 
            }
        } 
        return map; 
    } 
     @SuppressWarnings("unchecked")
    public static Map Dom2Map(Element e){ 
        Map map = new HashMap(); 
        List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else 
            map.put(e.getName(), e.getText()); 
        return map; 
    } 
     /**
      * @description 将xml字符串转换成map
      * @param xml
      * @return Map
      */
     public static Map<String,String> xml2Map(String xml) {
         Map<String,String> map = new HashMap<String,String>();
         Document doc = null;
             // 将字符串转为XML
             try {
				doc = DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				e.printStackTrace();
			} 
             // 获取根节点
             Element rootElt = doc.getRootElement(); 
             String rootName = rootElt.getName();
             // 拿到根节点的名称
             map.put("root.name", rootName);
             convert(rootElt,map,rootName);
         return map;
     }
     /**
 	 * 递归函数，找出最下层的节点并加入到map中，由xml2Map方法调用。
 	 * 
 	 * @param e
 	 *            xml节点，包括根节点
 	 * @param map
 	 *            目标map
 	 * @param lastname
 	 *            从根节点到上一级节点名称连接的字串
 	 */
 	public static void convert(Element e, Map<String, String> map,
 			String lastname) {
 		if (e.attributes().size() > 0) {
 			Iterator it_attr = e.attributes().iterator();
 			while (it_attr.hasNext()) {
 				Attribute attribute = (Attribute) it_attr.next();
 				String attrname = attribute.getName();
 				String attrvalue = e.attributeValue(attrname);
 				map.put(lastname + "." + attrname, attrvalue);
 			}
 		}
 		List children = e.elements();
 		Iterator it = children.iterator();
 		while (it.hasNext()) {
 			Element child = (Element) it.next();
 			String name = lastname + "." + child.getName();
 			// 如果有子节点，则递归调用
 			if (child.elements().size() > 0) {
 				convert(child, map, name);
 			} else {
 				// 如果没有子节点，则把值加入map
 				
 				map.put(name, child.getText());
 				// 如果该节点有属性，则把所有的属性值也加入map
 				if (child.attributes().size() > 0) {
 					Iterator attr = child.attributes().iterator();
 					while (attr.hasNext()) {
 						Attribute attribute = (Attribute) attr.next();
 						String attrname = attribute.getName();
 						String attrvalue = child.attributeValue(attrname);
 						map.put(name + "." + attrname, attrvalue);
 					}
 				}
 			}
 		}
 	}
 	public static Map<String,String> getItemMap(String xmlstr){
 		Document dom=null;
		try {
			dom = DocumentHelper.parseText(xmlstr);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
        Element root=dom.getRootElement();
        List<Element> list = root.element("items").elements();
        Map<String,String> map = new HashMap<String,String>(); 
        for(Element ele:list){
	        String name = ele.attribute("name").getValue();
	        String value = ele.attribute("value").getValue();
	        map.put(name, value);
        }
        return map;
 	}
     public  static void main(String args[]) throws DocumentException{
//    		String restr = "<?xml version=\"1.0\" encoding=\"gb2312\"?><orderinfo><err_msg></err_msg><retcode>1</retcode><orderid>S1301065430819</orderid><cardid>151201</cardid><cardnum>1</cardnum><ordercash>19.850</ordercash><cardname>北京联通话费20元直充</cardname><sporder_id>20130106112643316265947</sporder_id><game_userid>18600429925</game_userid><game_state>0</game_state></orderinfo>";
//            Map resultmap = XmlUtils.Xml2Map(restr);
         String restr = "<?xml version=\"1.0\" encoding=\"GB2312\"?><fill version=\"1.0\"><items><item name=\"dealerid\" value=\"201305131412510585\"/><item name=\"resultno\" value=\"0000\"/><item name=\"balance\" value=\"1000.0\"/> <item name=\"verifystring\" value=\"e7931c4cea6f0ed03c56ba26109c674f\"/> </items></fill>";  
//         Map map = xml2Map(restr);
//         Iterator iters = map.keySet().iterator();
//         while (iters.hasNext()) {
//             String key = iters.next().toString(); // 拿到键
//             String val = map.get(key).toString(); // 拿到值
//             System.out.println(key + "=" + val);
//         }
         Document dom=DocumentHelper.parseText(restr);
         Element root=dom.getRootElement();
         List<Element> list = root.element("items").elements();
         Map<String,String> map = new HashMap<String,String>(); 
         for(Element ele:list){
	        String name = ele.attribute("name").getValue();
	        String value = ele.attribute("value").getValue();
	        map.put(name, value);
         }
         System.out.println("resultno==="+map.get("resultno"));
     }
} 