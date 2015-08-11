package com.mocredit.base.tools;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * mysql数据库 表结构导出工具<br>
 * 用于解决 通过数据库注释 生成数据模型，格式为 xls
 * @author zhaigt
 * 
 */
public class DatabaseMetadataV2 {
  
  private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
  private static final String JDBC_URL = "jdbc:mysql://192.168.11.140:3306/smartdbdev?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "p@ssw0rd";
  
  /**
   *  导出文件路径配置
   *  */
  private static final String OUTPUT_PATH = "d:/物流表结构整理v2.1.xls";
  
  // 不需要导出的表
  private static final String FILTER = "account|agentroad|citydistance";// 排除的表名
  
  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver((Driver) Class.forName(DRIVER_CLASS).newInstance());
    Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

    String sql = "SHOW TABLE STATUS ";// 获取数据库有那些表
    Statement st = connection.createStatement();
    ResultSet rs = st.executeQuery(sql);
    
    List all = new ArrayList();
    try {
      while (rs.next()) {
        List<HashMap> list = new ArrayList<HashMap>();
        if(rs.getString("Name").matches(FILTER)){// 过滤无需导出的表
          continue;
        }
        String title = rs.getString("Name")+" "+rs.getString("Comment");
        
//        System.out.println("表名：" + title);
//        System.out.println("\t字段\t\t字段类型\t是否为空\t是否主键\t默认值\t字段名称");
        
        sql = "SHOW FULL FIELDS FROM " + rs.getString("Name");
        Statement st2 = connection.createStatement();
        ResultSet rs2 = st2.executeQuery(sql);
        HashMap<String, String> hm = null;
        while (rs2.next()) {
          hm = new HashMap<String, String>();
          hm.put("field", rs2.getString(1));
          hm.put("type", rs2.getString(2));
          hm.put("isnull", rs2.getString(4));
          hm.put("iskey", rs2.getString(5));
          String def = rs2.getString(6);
          if(null == def || "null".equals(def)){
            def = "";
          }
          hm.put("default", def);
          hm.put("name", rs2.getString(9));
          list.add(hm);
          /*
          System.out.print("\t" + rs2.getString(1));
          System.out.print("\t" + rs2.getString(2));
          System.out.print("\t" + rs2.getString(4));
          System.out.print("\t" + rs2.getString(5));
          System.out.print("\t" + def);
          System.out.print("\t" + rs2.getString(9));
          System.out.println();
          */
        }
        HashMap hm2 = new HashMap();
        hm2.put("tabname", title);// 表名
        hm2.put("tabdata", list);// 表数据
        all.add(hm2);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      rs.close();
      st.close();
      connection.close();
    }
    writexls(all);
  }
  
  public static void writexls(List<HashMap<String,List<HashMap>>> list){
    // poi xls 表格头部设置
//    String[] titles =  {"字段","字段类型","是否为空","是否主键","默认值","字段名称"};
//    String[] field = { "field","type","isnull","iskey","default","name"};
    String[] titles =  {"字段","字段类型","是否为空","字段名称"};
    String[] field = { "field","type","isnull","name"};
    HSSFWorkbook wb = new HSSFWorkbook();
    wb = writeExcelData(wb, "物流表结构", titles, field, list);
    try {
      OutputStream writer = new FileOutputStream(OUTPUT_PATH);
      wb.write(writer);
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  /**
   * 拼写数据到表格
   * @param wb
   * @param fileName
   * @param titles
   * @param fieldNames
   * @param data
   * @return
   */
  public static HSSFWorkbook writeExcelData(HSSFWorkbook wb, String fileName, String titles[],
      String fieldNames[], List<HashMap<String,List<HashMap>>> all) {
    try {
      HSSFSheet sheet = wb.createSheet(fileName);
      
      HSSFCellStyle titleStyle = wb.createCellStyle();
      titleStyle.setFillForegroundColor((short) 22);
      titleStyle = wb.createCellStyle();
      titleStyle.setFillForegroundColor((short) 22);
      titleStyle.setFillPattern((short) 1);
      titleStyle.setAlignment((short) 2);
      short color = 22;
      titleStyle.setBorderLeft(color);
      titleStyle.setBorderBottom(color);
      titleStyle.setBorderRight(color);
      titleStyle.setBorderTop(color);
      titleStyle.setBorderBottom((short) 6);
      titleStyle.setBottomBorderColor(color);
      titleStyle.setBorderLeft((short) 6);
      titleStyle.setLeftBorderColor(color);
      titleStyle.setBorderRight((short) 6);
      titleStyle.setRightBorderColor(color);
      titleStyle.setBorderTop((short) 6);
      titleStyle.setTopBorderColor(color);
      HSSFFont titleFont = wb.createFont();
      titleFont.setBoldweight((short) 12);
      titleFont.setFontHeightInPoints((short) 14);
      titleFont.setFontName("宋体");
      titleStyle.setFont(titleFont);

      HSSFCellStyle dataStyle = wb.createCellStyle();
      dataStyle.setAlignment((short) 2);
      dataStyle.setBorderLeft(color);
      dataStyle.setBorderBottom(color);
      dataStyle.setBorderRight(color);
      dataStyle.setBorderTop(color);
      dataStyle.setBorderBottom((short) 6);
      dataStyle.setBottomBorderColor(color);
      dataStyle.setBorderLeft((short) 6);
      dataStyle.setLeftBorderColor(color);
      dataStyle.setBorderRight((short) 6);
      dataStyle.setRightBorderColor(color);
      dataStyle.setBorderTop((short) 6);
      dataStyle.setTopBorderColor(color);
      HSSFFont dataFont = wb.createFont();
      dataFont.setFontHeightInPoints((short) 14);
      dataFont.setFontName("宋体");
      dataStyle.setFont(dataFont);
      int count = all != null ? all.size() : 0;
      
      int hang = 1;// 行标
      HSSFRow row = sheet.createRow((short)hang);// 根据行标创建行
      
      for (int i = 0; i < count; i++) {// 所有表
        HSSFCell cell = null;
        HashMap hm = all.get(i);// 表数据
        List<HashMap> tabs = (List<HashMap>)hm.get("tabdata");// 表数据
        String tabname = (String)hm.get("tabname");// 标题
        
        row = sheet.createRow((short) (hang));// 创建行
        hang++;
        row = sheet.createRow((short) (hang));// 创建行
        cell = row.createCell((short) 0);
        cell.setCellValue(tabname);//设置表名
        cell.setCellStyle(titleStyle);
        sheet.setColumnWidth(0, (35 * 150));
        hang++;
        
        row = sheet.createRow((short) (hang));// 创建行
        // 创建表头
        for (int t = 0; t < titles.length; t++) {
          cell = row.createCell((short) t);
          cell.setCellValue(titles[t]);
          cell.setCellStyle(titleStyle);
          sheet.setColumnWidth(t, (35 * 220));
        }
        hang++;
        
        for (int j = 0; j < tabs.size(); j++) {
          row = sheet.createRow((short) (hang));// 创建行
          HashMap<String,String> map = tabs.get(j);
          for (int k = 0; k < fieldNames.length; k++) {// 创建列
            cell = row.createCell((short) k);//列标
            cell.setCellValue(map.get(fieldNames[k]));//设置列值
            cell.setCellStyle(dataStyle);
          }
          hang++;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return wb;
  }

  private static String reflectObjectFieldValue(Object obj, String fieldName) {
    // zhaigt 增加 map类型扩展
    if (obj instanceof Map) {
      return ((Map)obj).get(fieldName).toString();
    } else {
      Method m = null;
      Object value = null;
      StringBuffer sb = new StringBuffer("get");
      try {
        m = obj.getClass().getMethod(
            sb.append(fieldName.substring(0, 1).toUpperCase())
            .append(fieldName.substring(1)).toString(), new Class[0]);
        value = m.invoke(obj, new Object[0]);
      } catch (Exception e) {
        e.printStackTrace();
        return (new StringBuilder("can't find ")).append(fieldName).toString();
      }
      if (value == null) {
        return "";
      }
      return value.toString();
    }
  }
}
