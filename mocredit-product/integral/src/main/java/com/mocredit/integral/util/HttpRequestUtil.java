package com.mocredit.integral.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestUtil {
    private final static String CHARSET = "utf-8";
    private final static Integer CONNECTIMREOUT = 5000;
    private final static Integer SOCKETTIMROUT = 5000;

    /**
     * Do GET request
     *
     * @param url
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static String doGet(String url) throws Exception {

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection
                .setRequestProperty("Content-Type", "application/json");
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception(
                    "HTTP Request is not success, Response code is "
                            + httpURLConnection.getResponseCode());
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, CHARSET);
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } finally {

            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }

    /**
     * Do POST request
     *
     * @param url
     * @param parameterMap
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<?, ?> parameterMap)
            throws Exception {

		/* Translate parameter map to parameter date string */
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator<?> iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = parameterMap.get(key) + "";
                } else {
                    value = "";
                }

                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }

        System.out.println("POST parameter : " + parameterBuffer.toString());

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection
                .setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Content-Length",
                String.valueOf(parameterBuffer.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is "
                                + httpURLConnection.getResponseCode());
            }

            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, CHARSET);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }

    public static String doPostJson(String url, String param) throws Exception {
        URL localURL = new URL(url);
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection
                .setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("ContentType", CHARSET);
        httpURLConnection.setRequestProperty("Content-Length",
                String.valueOf(param.length()));
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, CHARSET);
            outputStreamWriter.write(param);
            outputStreamWriter.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is "
                                + httpURLConnection.getResponseCode());
            }
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, CHARSET);
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }

    private static URLConnection openConnection(URL localURL)
            throws IOException {
        URLConnection connection;
        connection = localURL.openConnection();
        connection.setConnectTimeout(CONNECTIMREOUT);
        connection.setReadTimeout(SOCKETTIMROUT);
        return connection;
    }

    public static void main(String[] args) throws Exception {
        // System.out.println(doPostJson("http://localhost:8080/integral/interface/activityImport",
        // "{\"activityId\":\"1\",\"startTime\":\"2015-08-12 11:11:11\",\"storeList\":\"[{\"shopId\":\"1\",\"storeId\":\"3\"}]\"}")
        // );

        System.out
                .println(doPostJson(
                        "http://117.121.20.137:10000/bank/payment",
                        "{\"cardExpDate\":\"0924\",\"cardNum\":\"6214850280354785\",\"code\":\"MS0001\",\"orderId\":\"000059002467\",\"tranAmt\":\"100\"}"));

        System.out
                .println(doPostJson(
                        "http://117.121.20.137:10000/bank/paymentRevoke",
                        "{\"activityId\":\"MS00001\",\"orderId\":\"000059002468\",\"oldOrderId\":\"000059002467\"}"));
//        System.out
//                .println(doPostJson(
//                        "http://127.0.0.1:8080/payment",
//                        "{\"activityId\":\"ZSYH11\",\"cardExpDate\":\"2003\",\"cardNum\":\"4392260028467460\",\"enCode\":\"82393007\",\"orderId\":\"5773e998185723513\",\"transAmt\":\"null\"}"));
//        System.out
//                .println(doPostJson(
//                        "http://127.0.0.1:8080/paymentOld?activityId=ZSYH11&cardExpDate=2003&cardNum=4392260028467460&enCode=82393007", ""));
    }
}
