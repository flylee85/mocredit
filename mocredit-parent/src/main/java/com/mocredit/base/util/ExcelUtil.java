package com.mocredit.base.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel工具类 
 * 涉及到的技术有POI,JXL
 * @author lishoukun
 * @date 2015/06/09
 * 
 */
public class ExcelUtil {
	/**
	 * 将excel文件转换为二维的List 使用POI技术，支持office2003,2007
	 * 
	 * @param filePath,excel文件路径
	 * @return
	 */
	public static List<List<Object>> excel2List(String filePath) {
		try {
			InputStream in = new FileInputStream(filePath);
			return excel2List(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将excel文件转换为二维的List 使用POI技术，支持office2003,2007
	 * 
	 * @param in,输入流
	 * @return
	 */
	public static List<List<Object>> excel2List(InputStream in) {
		// 定义数据列表，用来存储后续解析的数据
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		try {
			// 创建对Excel工作簿文件的引用
			Workbook wookbook = WorkbookFactory.create(in);
			// 在Excel文档中，第一张工作表的缺省索引是0，语句如下:Sheet sheet = wookbook.getSheetAt(0);也可以使用sheet的名称获取，语句如下：Sheet sheet = wookbook.getSheet("Sheet1");
			Sheet sheet = wookbook.getSheetAt(0);
			// 获取到Excel文件中的所有【有效行数】
			int rows = sheet.getPhysicalNumberOfRows();
			// 遍历所有行
			for (int i = 0; i < rows; i++) {
				// 读取左上端单元格
				Row row = sheet.getRow(i);
				// 当该行不为空的情况下
				if (row != null) {
					// 获取最后一列不为空的列数，代码如下：row.getLastCellNum(); 也可以获取所有不为空的列数，代码如下：int cells = row.getPhysicalNumberOfCells();
					int cells = row.getLastCellNum();
					// 定义存储每一行数据的列表
					ArrayList<Object> cellList = new ArrayList<Object>();
					boolean isHaveData = false;
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						Cell cell = row.getCell(j);
						Object value = null;
						if (cell != null) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								// 时间对象 特殊处理
								boolean isDate = HSSFDateUtil.isCellDateFormatted(cell);
								if(isDate){
									int dataFormat = cell.getCellStyle().getDataFormat();
									if (dataFormat == 14 || dataFormat == 178 || dataFormat == 180 || dataFormat == 181 || dataFormat == 182) {
										value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
										break;
									}
									if (dataFormat == 176 || dataFormat == 22) {
										value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
										break;
									}
								}else{
									cell.setCellType(Cell.CELL_TYPE_STRING);
									value = cell.getStringCellValue().toString().trim();
								}
								break;
							case Cell.CELL_TYPE_STRING:
								// value = cell.getStringCellValue();
								value = cell.getRichStringCellValue().toString().trim();
								break;
							case Cell.CELL_TYPE_FORMULA:
								value = String.valueOf(cell.getCellFormula()).trim();
								break;
							case Cell.CELL_TYPE_BLANK:
								// value = String.valueOf(cell.getStringCellValue());
								value = String.valueOf(cell.getRichStringCellValue().toString()).trim();
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								value = String.valueOf(cell.getBooleanCellValue()).trim();
								break;
							case Cell.CELL_TYPE_ERROR:
								value = String.valueOf(cell.getErrorCellValue()).trim();
								break;
							}
						} else {
							value = "";
						}
						//判断该行数据不为空
	    				if(!"".equals(value)){ 
	    					 isHaveData = true; 
	    				} 
						cellList.add(value);
						//System.out.println(value);
					}
					//如果该行数据不为空，则添加该行数据
	    			 if(isHaveData){ 
	    				 dataList.add(cellList); 
	    			 } 
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 将excel文件转换为二维的List 使用POI技术，支持office 2003
	 * 
	 * @param in
	 *            ,输入流
	 * @return
	 */
	public static List<List<Object>> excel2ListByPOI(InputStream in) {
		// 定义数据列表，用来存储后续解析的数据
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook wookbook = new HSSFWorkbook(in);
			// 在Excel文档中，第一张工作表的缺省索引是0，语句如下:HSSFSheet sheet = wookbook.getSheetAt(0);也可以使用sheet的名称获取，语句如下：HSSFSheet sheet = wookbook.getSheet("Sheet1");
			HSSFSheet sheet = wookbook.getSheetAt(0);
			// 获取到Excel文件中的所有【有效行数】
			int rows = sheet.getPhysicalNumberOfRows();
			// 遍历所有行
			for (int i = 0; i < rows; i++) {
				// 读取左上端单元格
				HSSFRow row = sheet.getRow(i);
				// 当该行不为空的情况下
				if (row != null) {
					// 获取最后一列不为空的列数，代码如下：row.getLastCellNum(); 也可以获取所有不为空的列数，代码如下：int cells = row.getPhysicalNumberOfCells();
					int cells = row.getLastCellNum();
					// 定义存储每一行数据的列表
					ArrayList<Object> cellList = new ArrayList<Object>();
					boolean isHaveData = false;
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						HSSFCell cell = row.getCell(j);
						Object value = null;
						if (cell != null) {
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC:
								// 时间对象 特殊处理
//								int dataFormat = cell.getCellStyle().getDataFormat();
//								if (dataFormat == 14 || dataFormat == 178|| dataFormat == 180|| dataFormat == 181|| dataFormat == 182) {
//									value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
//									break;
//								}
//								if (dataFormat == 176 || dataFormat == 22) {
//									value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
//									break;
//								}
								cell.setCellType(Cell.CELL_TYPE_STRING);
								value = cell.getStringCellValue().toString().trim();
								break;
							case HSSFCell.CELL_TYPE_STRING:
								// value = cell.getStringCellValue();
								value = cell.getRichStringCellValue().toString().trim();
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								value = String.valueOf(cell.getCellFormula()).trim();
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								// value =
								// String.valueOf(cell.getStringCellValue());
								value = String.valueOf(cell.getRichStringCellValue().toString()).trim();
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = String.valueOf(cell.getBooleanCellValue()).trim();
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = String.valueOf(cell.getErrorCellValue()).trim();
								break;
							}
						} else {
							value = "";
						}
						//判断该行数据不为空
	    				if(!"".equals(value)){ 
	    					 isHaveData = true; 
	    				} 
						cellList.add(value);
						// System.out.println(value);
						// System.out.println(cellList.size());
					}
					 //如果该行数据不为空，则添加该行数据
	    			 if(isHaveData){ 
	    				 dataList.add(cellList); 
	    			 } 
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	/**
	 * 将excel文件转换为二维的List 使用JXL技术导入，只支持office 2003
	 * 
	 * @param in ,输入流
	 * 
	 */
//	 public static List<List> excel2ListByJXL(InputStream in){
//	     //定义数据列表，存储后续解析出来的数据
//		 List<List> dataList = new ArrayList<List>();
//	     try { 
//	    	 // 创建对Excel工作簿文件的引用
//	    	 Workbook wookbook = Workbook.getWorkbook(in); 
//	    	 // 在Excel文档中，第一张工作表的缺省索引是0，其语句为：Sheet sheet = wookbook.getSheet(0);也可以使用sheet页的名称，语句如下：Sheet sheet = wookbook.getSheet("Sheet1"); 
//	    	 Sheet sheet = wookbook.getSheet(0);
//	         //获取到Excel文件中的所有行数 
//	    	 int rows = sheet.getRows(); 
//	    	 //遍历所有行 
//	    	 for (int i = 0; i < rows; i++) { 
//	    		 //读取左上端单元格 
//	    		 Cell[] cells = sheet.getRow(i);
//	    		 //当行不为空的情况下 
//	    		 if (cells != null) { 
//	    			 boolean isHaveData = false;
//	    			 ArrayList cellList = new ArrayList(); 
//	    			 //遍历列
//	    			 for (int j = 0; j < cells.length; j++) { 
//	    				//获取到列的值
//	    				Cell cell = cells[j]; 
//	    				Object value = null; 
//	    				if(cell != null){ 
//	    					 value = cell.getContents(); 
//	    				}else{
//	    					value = ""; 
//	    				} 
//	    				//判断该行数据不为空
//	    				if(!"".equals(value)){ 
//	    					 isHaveData = true; 
//	    				} 
//	    				cellList.add(value); 
//	    				//System.out.println(value);
//	    				//System.out.println(cellList.size()); 
//	    			 } 
//	    			 //如果该行数据不为空，则添加该行数据
//	    			 if(isHaveData){ 
//	    				 dataList.add(cellList); 
//	    			 } 
//	    		} 
//	    	} 
//	    	wookbook.close(); 
//	    }catch (FileNotFoundException e) { 
//	    	e.printStackTrace(); 
//	    }
//	    return dataList; 
//	}
}
