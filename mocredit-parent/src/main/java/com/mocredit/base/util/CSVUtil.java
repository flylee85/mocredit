package com.mocredit.base.util;

import java.io.*;
import java.util.*;

public class CSVUtil {

    public static void download(List<Map> exportData, Map rowMapper,
                                OutputStream outputStream) {
        BufferedWriter csvFileOutputStream = null;
        try {

            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
                    outputStream, "GB2312"), 1024);
            // 写入文件头部
            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator
                    .hasNext(); ) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator
                        .next();
                csvFileOutputStream.write("\"" + propertyEntry.getValue()
                        + "\"");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();

			/*
             * // 写入文件内容 for (Iterator iterator = exportData.iterator();
			 * iterator.hasNext();) { // Object row = (Object) iterator.next();
			 * Map row = (Map) iterator.next(); for (Iterator propertyIterator =
			 * row.entrySet().iterator(); propertyIterator .hasNext();) {
			 * java.util.Map.Entry propertyEntry = (java.util.Map.Entry)
			 * propertyIterator .next(); csvFileOutputStream.write("\"" +
			 * propertyEntry.getValue() + "\""); if (propertyIterator.hasNext())
			 * { csvFileOutputStream.write(","); } } if (iterator.hasNext()) {
			 * csvFileOutputStream.newLine(); } }
			 */
            for (Map map : exportData) {
                for (Iterator propertyIterator = rowMapper.entrySet()
                        .iterator(); propertyIterator.hasNext(); ) {
                    Map.Entry propertyEntry = (Map.Entry) propertyIterator
                            .next();
                    csvFileOutputStream.write("\""
                            + map.get(propertyEntry.getValue()) + "\"");
                    csvFileOutputStream.write(",");
                }
                csvFileOutputStream.newLine();
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File createCSVFile(List exportData, LinkedHashMap rowMapper,
                                     String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFile = new File(outPutPath + filename + ".csv");
            // csvFile.getParentFile().mkdir();
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csvFile), "GB2312"), 1024);
            // 写入文件头部
            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator
                    .hasNext(); ) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator
                        .next();
                csvFileOutputStream.write("\""
                        + propertyEntry.getValue() + "\"");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();

            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                // Object row = (Object) iterator.next();
                LinkedHashMap row = (LinkedHashMap) iterator.next();
                System.out.println(row);

                for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator
                        .hasNext(); ) {
                    Map.Entry propertyEntry = (Map.Entry) propertyIterator
                            .next();
                    // System.out.println( BeanUtils.getProperty(row,
                    // propertyEntry.getKey().toString()));
                    csvFileOutputStream.write("\""
                            + propertyEntry.getValue() + "\"");
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        List exportData = new ArrayList<Map>();
        Map row1 = new LinkedHashMap<String, String>();
        row1.put("1", "11");
        row1.put("2", "12");
        row1.put("3", "13");
        row1.put("4", "14");
        exportData.add(row1);
        row1 = new LinkedHashMap<String, String>();
        row1.put("1", "21");
        row1.put("2", "22");
        row1.put("3", "23");
        row1.put("4", "24");
        int i = 0;
        while (i < 500000) {
            exportData.add(row1);
            i++;
        }
        List propertyNames = new ArrayList();
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "第一列");
        map.put("2", "第二列");
        map.put("3", "第三列");
        map.put("4", "第四列");
        CSVUtil.createCSVFile(exportData, map, "d:/", "12");
        Long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);
    }
}
