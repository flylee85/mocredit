package cn.m.mt.util;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class DomParse {
     public DomParse(){
        DocumentBuilderFactory domfac=DocumentBuilderFactory.newInstance();        try {
            DocumentBuilder dombuilder=domfac.newDocumentBuilder();
           InputStream is=new FileInputStream("d:\\emay-google-local_6.xml"); 
           Document doc=dombuilder.parse(is);
          Element root=doc.getDocumentElement();
          NodeList books=root.getChildNodes();
          if(books!=null){
              for(int i=0;i<=books.getLength();i++){
                  Node book=books.item(i);
                  if(book.getNodeType()==Node.ELEMENT_NODE&&"listing".equals(book.getNodeName())){
                	  int f = 0;
                      for(Node node=book.getFirstChild();node!=null;node=node.getNextSibling()){
                    	  f++;
                              if(node.getNodeName().equals("address")){
                            	  Element e = (Element) node;
                            	  NodeList addresss = e.getChildNodes();
                            	  if(addresss!=null){
                            		  for(int m=0;m<addresss.getLength();m++){
                            			  Node childNode = addresss.item(m);
                            			  if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            				  if(childNode.getFirstChild()!=null)
                            				    // System.out.println("  Element: " +
                            				    		 	childNode.getFirstChild().getNodeValue();
                            				     // 获得属性
                            				     // attribute start
                            				     NamedNodeMap attrs = childNode.getAttributes();
                            				     for (int j = 0; j < attrs.getLength(); j++) {
                            				    	 Node attr = attrs.item(j);
                            				    	 if("addr1".equals(attr.getNodeValue())){
                            				    		System.out
																.println("address++++"+ childNode.getFirstChild().getNodeValue());
                            				    	 }else if("city".equals(attr.getNodeValue())){
                            				    		 System.out
															.println("city++++"+ childNode.getFirstChild().getNodeValue());
                            				    	 }else if("province".equals(attr.getNodeValue())){
                            				    		 System.out
															.println("province++++"+ childNode.getFirstChild().getNodeValue());
                            				    	 }
//                            				      //System.out.println("Attribute:  "
//                            				        + attr.getNodeName() + " = "
//                            				        + attr.getNodeValue());
                            				     }
                            		  }
                            	  }
                              }

                              }
                              if(node.getNodeName().equals("phone")){
                            	  String phone=node.getFirstChild().getNodeValue();
                            	  //System.out.println("phone==="+phone);
                              }
                              if(node.getNodeName().equals("name")){
                            	  String name=node.getFirstChild().getNodeValue();
                            	  //System.out.println("name==="+name);
                              }
                              if(node.getNodeName().equals("category")){
                            	  String category=node.getFirstChild().getNodeValue();
                            	  if(f==8){
                            		  //System.out.println("category1==="+category);
                            	  }
                            	  if(f==9){
                            		  //System.out.println("category2==="+category);
                            	  }
                            	  
                              }
              }
              }
                  
              }} }catch (Exception e) {
            	  e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new DomParse();
    }
    
 }
