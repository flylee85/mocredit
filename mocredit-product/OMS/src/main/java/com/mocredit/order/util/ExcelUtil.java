package com.mocredit.order.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: xiangdinggang Date: 12-1-5 Time: 下午3:17 To
 * change this template use File | Settings | File Templates.
 */
public class ExcelUtil {
	private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
			.getLogger(ExcelUtil.class);

	public static void exportData(ExcelExportInfo excelExportInfo,
			HttpServletResponse response) {
		String[][] arrayData = new String[excelExportInfo.getDatas().size()][excelExportInfo
				.getFeilds().length];
		for (int i = 0, size = excelExportInfo.getDatas().size(); i < size; i++) {
			Object data = excelExportInfo.getDatas().get(i);
			for (int j = 0; j < excelExportInfo.getFeilds().length; j++) {
				try {
					Object value = PropertyUtils.getProperty(data,
							excelExportInfo.getFeilds()[j]);
					if (value == null) {
						String defaultValue = excelExportInfo.getDefaultValue()[j];
						if (StringUtils.isBlank(defaultValue)) {
							defaultValue = "";
						}
						value = defaultValue;
					} else {
						String defaultFormat = excelExportInfo
								.getDefaultFormat()[j];
						if (StringUtils.isNotBlank(defaultFormat)) {
							if (value instanceof DateTime) {
								value = ((DateTime) value)
										.toString(defaultFormat);
							} else if (value instanceof Date) {
								DateTime citme = new DateTime(
										((Date) value).getTime());
								value = citme.toString(defaultFormat);
							} else if (value instanceof Number) {
								value = new DecimalFormat(defaultFormat)
										.format(value);
							}
						}
					}
					if (StringUtils.isNotBlank(value.toString())) {
						arrayData[i][j] = value.toString();
					}
				} catch (Exception e) {

				}
			}
		}
		exportData(excelExportInfo.getFileName(),
				excelExportInfo.getColumnHeader(), arrayData, response);
	}

	public static void exportData(String fileName, String[] sheetName,
			String[][] cellNumber, String[][][] arrayData,
			HttpServletResponse response) {
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ toUtf8String(fileName) + ".xls");
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int i = 0; i < cellNumber.length; i++) {
			int cellNum = cellNumber[i].length;

			HSSFSheet sheet = wb.createSheet(sheetName[i]);
			// sheet.setDefaultRowHeightInPoints(10);
			sheet.setDefaultColumnWidth(15);

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			// 字体大小为short/20=px
			font.setFontHeight((short) 280);

			HSSFFont cellTitleFont = wb.createFont();
			cellTitleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellTitleFont.setFontName("宋体");
			cellTitleFont.setFontHeight((short) 200);

			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 320);
			HSSFCell cell = row.createCell((short) 0);

			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sheetName[i]);

			sheet.addMergedRegion(new Region(0, (short) 0, 0,
					(short) (cellNum - 1)));

			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			cell.setCellStyle(style);

			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setFont(cellTitleFont);

			row = sheet.createRow(1);
			for (int j = 0; j < cellNum; j++) {
				String cellName = cellNumber[i][j];
				cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(cellName);
				cell.setCellStyle(cellStyle);
			}

			for (int j = 0; j < arrayData[i].length; j++) {
				String[] data = arrayData[i][j];
				row = sheet.createRow(j + 2);

				for (int k = 0; k < data.length; k++) {
					cell = row.createCell(k);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(data[k]);
				}
			}
		}
		try {
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();

			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("exportData error : " + e);
		}
	}

	public static void exportData(String fileName, String[] cellNumber,
			String[][] arrayData, HttpServletResponse response) {
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ toUtf8String(fileName) + ".xls");

		int cellNum = cellNumber.length;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		// sheet.setDefaultRowHeightInPoints(10);
		sheet.setDefaultColumnWidth(15);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		// 字体大小为short/20=px
		font.setFontHeight((short) 280);

		HSSFFont cellTitleFont = wb.createFont();
		cellTitleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellTitleFont.setFontName("宋体");
		cellTitleFont.setFontHeight((short) 200);

		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 320);
		HSSFCell cell = row.createCell((short) 0);

		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(fileName);

		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (cellNum - 1)));

		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFont(font);
		cell.setCellStyle(style);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(cellTitleFont);

		row = sheet.createRow(1);
		for (int i = 0; i < cellNum; i++) {
			String cellName = cellNumber[i];
			cell = row.createCell(i);
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(cellName);
			cell.setCellStyle(cellStyle);
		}

		for (int i = 0; i < arrayData.length; i++) {
			String[] data = arrayData[i];
			row = sheet.createRow(i + 2);

			for (int j = 0; j < data.length; j++) {
				cell = row.createCell(j);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(data[j]);
			}
		}

		try {
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();

			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("exportData error : " + e);
		}

	}

	public static void exportAllData(String fileName,
			Map<String, Map<String[], String[][]>> map,
			HttpServletResponse response) {
		if (fileName.length() > 20) {
			fileName = fileName.substring(0, 10);
		}
		String file = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ toUtf8String(fileName) + ".xls";
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + file);

		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			// sheet.setDefaultRowHeightInPoints(10);
			sheet.setDefaultColumnWidth(20);

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			font.setFontHeight((short) 280);

			HSSFFont cellTitleFont = wb.createFont();
			cellTitleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellTitleFont.setFontName("宋体");
			cellTitleFont.setFontHeight((short) 200);

			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);

			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setFont(cellTitleFont);

			HSSFRow row = null;

			int currentRow = 0;
			for (String title : map.keySet()) {
				Map<String[], String[][]> dataMap = map.get(title);
				for (String[] cellNumber : dataMap.keySet()) {
					String[][] arrayData = dataMap.get(cellNumber);
					int cellNum = cellNumber.length;

					row = sheet.createRow(currentRow);
					HSSFCell cell = row.createCell((short) 0);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(title);
					sheet.addMergedRegion(new Region(currentRow, (short) 0,
							currentRow, (short) (cellNum - 1)));
					cell.setCellStyle(style);

					currentRow++;

					row = sheet.createRow(currentRow);
					for (int i = 0; i < cellNum; i++) {
						String cellName = cellNumber[i];
						cell = row.createCell((short) i);
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(cellName);
						cell.setCellStyle(cellStyle);
					}
					currentRow++;

					for (int i = 0; i < arrayData.length; i++) {
						String[] data = arrayData[i];
						row = sheet.createRow(currentRow);

						for (int j = 0; j < data.length; j++) {
							cell = row.createCell((short) j);
							// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell.setCellValue(data[j]);
						}
						currentRow++;
					}
				}
			}

			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("exportData error : " + e);
		}

	}

	public static String toUtf8String(String fileName) {
		try {
			return new String(fileName.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static void exportAllData1(String fileName,
			Map<String, Map<String[], String[][]>> map,
			HttpServletResponse response) {
		if (fileName.length() > 20) {
			fileName = fileName.substring(0, 10);
		}
		String file = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ toUtf8String(fileName) + ".xls";
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + file);

		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			// sheet.setDefaultRowHeightInPoints(10);
			sheet.setDefaultColumnWidth((short) 20);

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			font.setFontHeight((short) 280);

			HSSFFont cellTitleFont = wb.createFont();
			cellTitleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellTitleFont.setFontName("宋体");
			cellTitleFont.setFontHeight((short) 200);

			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setFillForegroundColor(HSSFColor.YELLOW.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);

			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setFont(cellTitleFont);

			HSSFRow row = null;

			int currentRow = 0;
			for (String title : map.keySet()) {
				Map<String[], String[][]> dataMap = map.get(title);
				for (String[] cellNumber : dataMap.keySet()) {
					String[][] arrayData = dataMap.get(cellNumber);
					int cellNum = cellNumber.length;

					row = sheet.createRow(currentRow);
					HSSFCell cell = row.createCell((short) 0);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(title);
					sheet.addMergedRegion(new Region(currentRow, (short) 0,
							currentRow, (short) (cellNum - 1)));
					cell.setCellStyle(style);

					currentRow++;

					row = sheet.createRow(currentRow);
					for (int i = 0; i < cellNum; i++) {
						String cellName = cellNumber[i];
						cell = row.createCell((short) i);
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(cellName);
						cell.setCellStyle(cellStyle);
					}
					currentRow++;

					for (int i = 0; i < arrayData.length; i++) {
						String[] data = arrayData[i];
						row = sheet.createRow(currentRow);

						for (int j = 0; j < data.length; j++) {
							cell = row.createCell((short) j);
							// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
							cell.setCellValue(data[j]);
						}
						currentRow++;
					}
				}
			}

			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("exportData error : " + e);
		}

	}

	public static void exportData(String fileName, HttpServletResponse response)
			throws IOException {
		String file = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ toUtf8String(fileName) + ".xls";
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + file);
		OutputStream os = response.getOutputStream();
		os.close();
		try {
			response.flushBuffer();
		} catch (IOException e) {
			LOGGER.error("exportData error : " + e);// To change body of catch
													// statement use File |
													// Settings | File
													// Templates.
		}
	}
}
