package com.mocredit.order.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelTool {
	private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
			.getLogger(ExcelTool.class);
	private static final int EXCEL_SHEET_MAX_ROW_SIZE = 65535;

	/**
	 * 输出临时文件
	 * 
	 * @param fileName
	 * @param title
	 * @param key
	 * @param values
	 * @throws java.io.IOException
	 */
	public static void buildExcelFile(String fileName, String[] title,
			String[] key, List<Map> values) throws IOException {
		String path = System.getProperty("java.io.tmpdir");
		String separator = System.getProperty("file.separator");
		if (!path.endsWith(separator)) {
			path += separator;
		}
		String filePath = path + fileName;
		File file = new File(filePath);
		if (file.exists())
			file.delete();

		HSSFWorkbook workbook = null;
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow((short) 0);
		for (int i = 0; title != null && i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		Map map = null;
		for (int i = 0; values != null && i < values.size(); i++) {
			row = sheet.createRow((short) (i + 1));
			map = values.get(i);
			for (int j = 0; j < key.length; j++) {
				cell = row.createCell(j);
				if (map.get(key[j]) == null) {
					cell.setCellValue(new HSSFRichTextString(""));
				} else {
					try {
						setCellValue(workbook, sheet, cell, map.get(key[j]));
					} catch (Exception e) {
						LOGGER.error("###error={}", e); // To change body of
														// catch statement use
														// File | Settings |
														// File Templates.
					}
				}
			}
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
		outputStream.close();
	}

	public static void setTitleStyle(HSSFWorkbook workbook,
			HSSFCellStyle titleStyle, HSSFCellStyle valueStyle) {
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 13);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setFont(font);
		valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		valueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		valueStyle.setWrapText(true);
	}

	public static void setTitleCell(HSSFSheet sheet, HSSFRow row,
			HSSFCell cell, HSSFCellStyle titleStyle, String[] title) {
		for (int i = 0; title != null && i < title.length; i++) {
			row.setHeight((short) 480);
			cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			if (title[i].getBytes().length * 256 * 2 > 255 * 256) {
				sheet.setColumnWidth(i, 255 * 256);
			} else {
				sheet.setColumnWidth(i, title[i].getBytes().length * 256 * 2);
			}
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
	}

	/**
	 * @param title
	 *            标题行 例：String[]{"名称","地址"}
	 * @param key
	 *            从查询结果List取得的MAP的KEY顺序，需要和title顺序匹配，例：String[]{"name","address"
	 *            }
	 * @param values
	 *            结果集
	 * @param outputStream
	 * @throws java.io.IOException
	 */
	public static void download(String fileName, String[] title, String[] key,
			List<Map> values, OutputStream outputStream) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFCellStyle valueStyle = workbook.createCellStyle();
		setTitleStyle(workbook, titleStyle, valueStyle);

		Map map = null;
		int sheetNo = 0;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = 0; values != null && i < values.size(); i++) {
			int r = i % EXCEL_SHEET_MAX_ROW_SIZE;
			if (r == 0) {
				sheet = workbook.createSheet(fileName + "_" + sheetNo);
				row = sheet.createRow((short) 0);
				setTitleCell(sheet, row, cell, titleStyle, title);
				sheetNo++;
			}
			row = sheet.createRow(r + 1);
			map = values.get(i);
			for (int j = 0; j < key.length; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(valueStyle);
				// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if (map.get(key[j]) == null) {
					cell.setCellValue(new HSSFRichTextString(""));
				} else {
					// cell.setCellValue(new
					// HSSFRichTextString(map.get(key[j]).toString()));
					try {
						setCellValue(workbook, sheet, cell, map.get(key[j]));
					} catch (Exception e) {
						LOGGER.error("###error={}", e); // To change body of
														// catch statement use
														// File | Settings |
														// File Templates.
					}
				}
			}
		}
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	protected static void setCellValue(HSSFWorkbook workbook, HSSFSheet sheet,
			HSSFCell cell, Object obj) throws Exception {
		String type = obj.getClass().getName();
		if (type.endsWith("String")) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue((String) obj);
		} else if (("int".equals(type)) || (type.equals("java.lang.Integer"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(((Integer) obj).intValue());
		} else if (("double".equals(type)) || (type.equals("java.lang.Double"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			BigDecimal bigDecimal = new BigDecimal((Double) obj,
					MathContext.DECIMAL32);
			cell.setCellValue(bigDecimal.doubleValue());
		} else if (("boolean".equals(type))
				|| (type.equals("java.lang.Boolean"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
			cell.setCellValue(((Boolean) obj));
		} else if (("float".equals(type)) || (type.equals("java.lang.Float"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			BigDecimal bigDecimal = new BigDecimal((Float) obj,
					MathContext.DECIMAL32);
			cell.setCellValue(bigDecimal.doubleValue());
		} else if ((type.equals("java.util.Date")) || (type.endsWith("Date"))) {
			HSSFCellStyle cellStyle = workbook.createCellStyle();

			short df = workbook.createDataFormat()
					.getFormat("yyyy/m/d h:mm:ss");
			cellStyle.setDataFormat(df);
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(cell.getColumnIndex(), 18 * 256);
			cell.setCellValue(org.apache.poi.ss.usermodel.DateUtil
					.getExcelDate((Date) obj));
		}
		// else if (type.equals("java.util.Calendar")) {
		// cell.setCellValue((Calendar) obj);
		// }
		else if (("char".equals(type)) || (type.equals("java.lang.Character"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(obj.toString());
		} else if (("long".equals(type)) || (type.equals("java.lang.Long"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(((Long) obj).longValue());
		} else if (("short".equals(type)) || (type.equals("java.lang.Short"))) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(((Short) obj).shortValue());
		} else if (type.equals("java.math.BigDecimal")) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			BigDecimal bigDecimal = (BigDecimal) obj;
			bigDecimal = new BigDecimal(bigDecimal.doubleValue(),
					MathContext.DECIMAL32);
			cell.setCellValue(bigDecimal.doubleValue());
		} else {
			throw new Exception("data type errored!");
		}
	}
}